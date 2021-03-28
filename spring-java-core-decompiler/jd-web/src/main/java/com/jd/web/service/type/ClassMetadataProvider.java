package com.jd.web.service.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.jd.core.v1.api.loader.Loader;
import com.jd.core.v1.api.loader.LoaderException;
import com.jd.web.model.Type;

public class ClassMetadataProvider extends AbstractMetadataProvider {
	
	public static MetadataType readMetadata(String internalTypeName, Loader loader) throws Exception {
		MetadataType type = new MetadataType(internalTypeName, loader, -1);
        return type;
	}

	private static ClassReader load(String internalTypeName, Loader loader) {
		try {
			byte[] data = loader.load(internalTypeName);
			if (data == null) return null;
			
			ClassReader classReader = new ClassReader(data);
	        return classReader;
		} catch (LoaderException e) {
			throw new RuntimeException("Could get the bytes for class: " + internalTypeName, e);
		}
        
	}
	
	static class MetadataType implements Type {
		protected int access;
        protected String name;
        protected String superName;
        protected String outerName;

        protected String displayTypeName;
        protected String displayInnerTypeName;
        protected String displayPackageName;

        protected List<Type> innerTypes;
        protected List<Type.Field> fields = new ArrayList<>();
        protected List<Type.Method> methods = new ArrayList<>();
        
        protected MetadataType(String internalTypeName, Loader loader, final int outerAccess) {
        	ClassReader classReader = load(internalTypeName, loader);
        	if (classReader == null) throw new RuntimeException("Could not read attributes for class: " + internalTypeName);
        	
        	ClassVisitor classAndInnerClassesVisitor = new ClassVisitor(Opcodes.ASM7) {
        		@Override
        		public void visit(int version, int access, String name, String signature, String superName,
        				String[] interfaces) {
        			MetadataType.this.access = (outerAccess == -1) ? access : outerAccess;
        			MetadataType.this.name = name;
        			MetadataType.this.superName = ((access & Opcodes.ACC_INTERFACE) != 0) && "java/lang/Object".equals(superName) ? null : superName;
        		}
        		@Override
        		public void visitInnerClass(String name, String outerName, String innerName, int access) {
        			if (MetadataType.this.name.equals(name)) {
                        // Inner class path found
        				MetadataType.this.outerName = outerName;
        				MetadataType.this.displayInnerTypeName = innerName;
                    } else if (((access & (Opcodes.ACC_SYNTHETIC|Opcodes.ACC_BRIDGE)) == 0) && MetadataType.this.name.equals(outerName)) {
                    	if (innerTypes == null) {
                    		innerTypes = new ArrayList<Type>();
                    	}
                    	innerTypes.add(new MetadataType(name, loader, access));
                    }
        		}
        	};
        	
            classReader.accept(classAndInnerClassesVisitor, ClassReader.SKIP_CODE|ClassReader.SKIP_DEBUG|ClassReader.SKIP_FRAMES);

            int lastPackageSeparatorIndex = name.lastIndexOf('/');
            if (lastPackageSeparatorIndex == -1) {
                displayPackageName = "";

                if (outerName == null) {
                    displayTypeName = name;
                } else {
                    displayTypeName = getDisplayTypeName(outerName, 0, loader) + '.' + displayInnerTypeName;
                }
            } else {
                displayPackageName = name.substring(0, lastPackageSeparatorIndex).replace('/', '.');

                if (outerName == null) {
                    displayTypeName = name;
                } else {
                    displayTypeName = getDisplayTypeName(outerName, lastPackageSeparatorIndex, loader) + '.' + displayInnerTypeName;
                }

                displayTypeName = displayTypeName.substring(lastPackageSeparatorIndex+1);
            }
            
            ClassVisitor fieldsAndMethodsVisitor = new ClassVisitor(Opcodes.ASM7) {
            	@Override
            	public FieldVisitor visitField(int access, String name, String descriptor, String signature,
            			Object value) {
            		if ((access & (Opcodes.ACC_SYNTHETIC|Opcodes.ACC_ENUM)) == 0) {
                        fields.add(new Type.Field() {
                            public int getFlags() {
                            	return access;
                            	
                            }
                            public String getName() { return name; }
                            public String getDescriptor() { return descriptor; }

                            public String getDisplayName() {
                                StringBuilder sb = new StringBuilder();
                                sb.append(name).append(" : ");
                                writeSignature(sb, descriptor, descriptor.length(), 0, false);
                                return sb.toString();
                            }
                        });
                    }
                    return null;
            	}
            	
            	@Override
            	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
            			String[] exceptions) {
            		if ((access & (Opcodes.ACC_SYNTHETIC|Opcodes.ACC_ENUM|Opcodes.ACC_BRIDGE)) == 0) {
                        methods.add(new Type.Method() {
                            public int getFlags() { return access; }
                            public String getName() { return name; }
                            public String getDescriptor() { return descriptor; }

                            public String getDisplayName() {
                                boolean isInnerClass = (MetadataType.this.displayInnerTypeName != null);
                                String constructorName = isInnerClass ? MetadataType.this.displayInnerTypeName : MetadataType.this.displayTypeName;
                                StringBuilder sb = new StringBuilder();
                                writeMethodSignature(sb, MetadataType.this.access, access, isInnerClass, constructorName, name, descriptor);
                                return sb.toString();
                            }
                        });
                    }
                    return null;
            	}
            };
            classReader.accept(fieldsAndMethodsVisitor, ClassReader.SKIP_CODE|ClassReader.SKIP_DEBUG|ClassReader.SKIP_FRAMES);
        }
        
        protected String getDisplayTypeName(String name, int packageLength, Loader loader) {
            int indexDollar = name.lastIndexOf('$');

            if (indexDollar > packageLength) {
            	
            	ClassReader classReader = load(name, loader);
                InnerClassVisitor classVisitor = new InnerClassVisitor(name);

                classReader.accept(classVisitor, ClassReader.SKIP_CODE|ClassReader.SKIP_DEBUG|ClassReader.SKIP_FRAMES);

                String outerName = classVisitor.getOuterName();

                if (outerName != null) {
                    // Inner class path found => Recursive call
                    return getDisplayTypeName(outerName, packageLength, loader) + '.' + classVisitor.getInnerName();
                }
            }

            return name;
        }
        
		@Override
		public int getFlags() {return this.access;}

		@Override
		public String getName() {return this.name;}

		@Override
		public String getSuperName() {return this.superName;}

		@Override
		public String getOuterName() {return this.outerName;}

		@Override
		public String getDisplayTypeName() {return this.displayTypeName;}

		@Override
		public String getDisplayInnerTypeName() {return this.displayInnerTypeName;}

		@Override
		public String getDisplayPackageName() {return this.displayPackageName;}

		@Override
		public Collection<Type> getInnerTypes() {return this.innerTypes;}

		@Override
		public Collection<Field> getFields() {return this.fields;}

		@Override
		public Collection<Method> getMethods() {return this.methods;}
		
	    protected static class InnerClassVisitor extends ClassVisitor {
	        protected String name;
	        protected String outerName;
	        protected String innerName;

	        public InnerClassVisitor(String name) {
	            super(Opcodes.ASM7);
	            this.name = name;
	        }

	        @Override
	        public void visitInnerClass(String name, String outerName, String innerName, int access) {
	            if (this.name.equals(name)) {
	                // Inner class path found
	                this.outerName = outerName;
	                this.innerName = innerName;
	            }
	        }

	        public String getOuterName() {
	            return outerName;
	        }

	        public String getInnerName() {
	            return innerName;
	        }
	    }

	}
}
