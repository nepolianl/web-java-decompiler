package com.jd.web.service.type;

import com.jd.web.model.Type;

public abstract class AbstractMetadataProvider {

	protected static int writeSignature(StringBuilder sb, String descriptor, int  length, int index, boolean varargsFlag) {
        while (true) {
            // Print array : '[[?' ou '[L[?;'
            int dimensionLength = 0;

            if (descriptor.charAt(index) == '[') {
                dimensionLength++;

                while (++index < length) {
                    if ((descriptor.charAt(index) == 'L') && (index+1 < length) && (descriptor.charAt(index+1) == '[')) {
                        index++;
                        length--;
                        dimensionLength++;
                    } else if (descriptor.charAt(index) == '[') {
                        dimensionLength++;
                    } else {
                        break;
                    }
                }
            }

            switch(descriptor.charAt(index)) {
                case 'B': sb.append("byte"); index++; break;
                case 'C': sb.append("char"); index++; break;
                case 'D': sb.append("double"); index++; break;
                case 'F': sb.append("float"); index++; break;
                case 'I': sb.append("int"); index++; break;
                case 'J': sb.append("long"); index++; break;
                case 'L': case '.':
                    int beginIndex = ++index;
                    char c = '.';

                    // Search ; or de <
                    while (index < length) {
                        c = descriptor.charAt(index);
                        if ((c == ';') || (c == '<'))
                            break;
                        index++;
                    }

                    String internalClassName = descriptor.substring(beginIndex, index);
                    int lastPackageSeparatorIndex = internalClassName.lastIndexOf('/');

                    if (lastPackageSeparatorIndex >= 0) {
                        // Cut package name
                        internalClassName = internalClassName.substring(lastPackageSeparatorIndex + 1);
                    }

                    sb.append(internalClassName.replace('$', '.'));

                    if (c == '<') {
                        sb.append('<');
                        index = writeSignature(sb, descriptor, length, index+1, false);

                        while (descriptor.charAt(index) != '>') {
                            sb.append(", ");
                            index = writeSignature(sb, descriptor, length, index, false);
                        }
                        sb.append('>');

                        // pass '>'
                        index++;
                    }

                    // pass ';'
                    if (descriptor.charAt(index) == ';')
                        index++;
                    break;
                case 'S': sb.append("short"); index++; break;
                case 'T':
                    beginIndex = ++index;
                    index = descriptor.substring(beginIndex, length).indexOf(';');
                    sb.append(descriptor.substring(beginIndex, index));
                    index++;
                    break;
                case 'V': sb.append("void"); index++; break;
                case 'Z': sb.append("boolean"); index++; break;
                case '-':
                    sb.append("? super ");
                    index = writeSignature(sb, descriptor, length, index+1, false);
                    break;
                case '+':
                    sb.append("? extends ");
                    index = writeSignature(sb, descriptor, length, index+1, false);
                    break;
                case '*': sb.append('?'); index++; break;
                case 'X': case 'Y': sb.append("int"); index++; break;
                default:
                    throw new RuntimeException("SignatureWriter.WriteSignature: invalid signature '" + descriptor + "'");
            }

            if (varargsFlag) {
                if (dimensionLength > 0) {
                    while (--dimensionLength > 0)
                        sb.append("[]");
                    sb.append("...");
                }
            } else {
                while (dimensionLength-- > 0)
                    sb.append("[]");
            }

            if ((index >= length) || (descriptor.charAt(index) != '.'))
                break;

            sb.append('.');
        }

        return index;
    }
	
	protected static void writeMethodSignature(
            StringBuilder sb, int typeAccess, int methodAccess, boolean isInnerClass,
            String constructorName, String methodName, String descriptor) {

        if (methodName.equals("<clinit>")) {
            sb.append("{...}");
        } else {
            boolean isAConstructor = methodName.equals("<init>");

            if (isAConstructor) {
                sb.append(constructorName);
            } else {
                sb.append(methodName);
            }

            // Skip generics
            int length = descriptor.length();
            int index = 0;

            while ((index < length) && (descriptor.charAt(index) != '('))
                index++;

            if (descriptor.charAt(index) != '(') {
                throw new RuntimeException("Signature format exception: '" + descriptor + "'");
            }

            sb.append('(');

            // pass '('
            index++;

            if (descriptor.charAt(index) != ')') {
                if (isAConstructor && isInnerClass && ((typeAccess & Type.FLAG_STATIC) == 0)) {
                    // Skip first parameter
                    int lengthBackup = sb.length();
                    index = writeSignature(sb, descriptor, length, index, false);
                    sb.setLength(lengthBackup);
                }

                if (descriptor.charAt(index) != ')') {
                    int varargsParameterIndex;

                    if ((methodAccess & Type.FLAG_VARARGS) == 0) {
                        varargsParameterIndex = Integer.MAX_VALUE;
                    } else {
                        // Count parameters
                        int indexBackup = index;
                        int lengthBackup = sb.length();

                        varargsParameterIndex = -1;

                        while (descriptor.charAt(index) != ')') {
                            index = writeSignature(sb, descriptor, length, index, false);
                            varargsParameterIndex++;
                        }

                        index = indexBackup;
                        sb.setLength(lengthBackup);
                    }

                    // Write parameters
                    index = writeSignature(sb, descriptor, length, index, false);

                    int parameterIndex = 1;

                    while (descriptor.charAt(index) != ')') {
                        sb.append(", ");
                        index = writeSignature(sb, descriptor, length, index, (parameterIndex == varargsParameterIndex));
                        parameterIndex++;
                    }
                }
            }

            if (isAConstructor) {
                sb.append(')');
            } else {
                sb.append(") : ");
                writeSignature(sb, descriptor, length, ++index, false);
            }
        }
    
	}
}
