package com.jd.web.decompiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class ClassFilePrinter extends StringBuilderPrinter {
	protected HashMap<String, ReferenceData> referencesCache = new HashMap<>();
	protected ArrayList<ReferenceData> references = new ArrayList<>();
	protected ArrayList<StringData> strings = new ArrayList<>();
	protected HashMap<String, DeclarationData> declarations = new HashMap<>();
    protected TreeMap<Integer, DeclarationData> typeDeclarations = new TreeMap<>();
    protected TreeMap<Integer, HyperlinkData> hyperlinks = new TreeMap<>();
	
	@Override
	public void start(int maxLineNumber, int majorVersion, int minorVersion) {
		super.start(maxLineNumber, majorVersion, minorVersion);
	}

	@Override
	public void end() {
		// setText(stringBuffer.toString());
	}

	// --- Add strings --- //
	@Override
	public void printStringConstant(String constant, String ownerInternalName) {
		if (constant == null) constant = "null";
        if (ownerInternalName == null) ownerInternalName = "null";

        strings.add(new StringData(stringBuffer.length(), constant.length(), constant, ownerInternalName));
        super.printStringConstant(constant, ownerInternalName);
	}

	@Override
	public void printDeclaration(int type, String internalTypeName, String name, String descriptor) {
		if (internalTypeName == null) internalTypeName = "null";
        if (name == null) name = "null";
        if (descriptor == null) descriptor = "null";

        switch (type) {
            case TYPE:
                DeclarationData data = new DeclarationData(stringBuffer.length(), name.length(), internalTypeName, null, null);
                declarations.put(internalTypeName, data);
                typeDeclarations.put(stringBuffer.length(), data);
                break;
            case CONSTRUCTOR:
                declarations.put(internalTypeName + "-<init>-" + descriptor, new DeclarationData(stringBuffer.length(), name.length(), internalTypeName, "<init>", descriptor));
                break;
            default:
                declarations.put(internalTypeName + '-' + name + '-' + descriptor, new DeclarationData(stringBuffer.length(), name.length(), internalTypeName, name, descriptor));
                break;
        }
        super.printDeclaration(type, internalTypeName, name, descriptor);
	}

	@Override
	public void printReference(int type, String internalTypeName, String name, String descriptor,
			String ownerInternalName) {
		if (internalTypeName == null) internalTypeName = "null";
        if (name == null) name = "null";
        if (descriptor == null) descriptor = "null";

        switch (type) {
            case TYPE:
                addHyperlink(new HyperlinkReferenceData(stringBuffer.length(), name.length(), newReferenceData(internalTypeName, null, null, ownerInternalName)));
                break;
            case CONSTRUCTOR:
                addHyperlink(new HyperlinkReferenceData(stringBuffer.length(), name.length(), newReferenceData(internalTypeName, "<init>", descriptor, ownerInternalName)));
                break;
            default:
                addHyperlink(new HyperlinkReferenceData(stringBuffer.length(), name.length(), newReferenceData(internalTypeName, name, descriptor, ownerInternalName)));
                break;
        }
        super.printReference(type, internalTypeName, name, descriptor, ownerInternalName);
	}

	@Override
	public void startLine(int lineNumber) {
		super.startLine(lineNumber);
	}

	@Override
	public void endLine() {
		super.endLine();
	}

	@Override
	public void extraLine(int count) {
		super.extraLine(count);
	}
	
	public ReferenceData newReferenceData(String internalName, String name, String descriptor, String scopeInternalName) {
        String key = internalName + '-' + name + '-'+ descriptor + '-' + scopeInternalName;
        ReferenceData reference = referencesCache.get(key);

        if (reference == null) {
            reference = new ReferenceData(internalName, name, descriptor, scopeInternalName);
            referencesCache.put(key, reference);
            references.add(reference);
        }

        return reference;
    }
	
	public void addHyperlink(HyperlinkData hyperlinkData) {
        hyperlinks.put(hyperlinkData.startPosition, hyperlinkData);
    }
	
	public static class StringData {
        int startPosition;
        int endPosition;
        String text;
        String owner;

        public StringData(int startPosition, int length, String text, String owner) {
            this.startPosition = startPosition;
            this.endPosition = startPosition + length;
            this.text = text;
            this.owner = owner;
        }
    }
	
	protected static class ReferenceData {
        public String typeName;
        /**
         * Field or method name or null for type
         */
        public String name;
        /**
         * Field or method descriptor or null for type
         */
        public String descriptor;
        /**
         * Internal type name containing reference or null for "import" statement.
         * Used to high light items matching with URI like "file://dir1/dir2/file?highlightPattern=hello&highlightFlags=drtcmfs&highlightScope=type".
         */
        public String owner;
        /**
         * "Enabled" flag for link of reference
         */
        public boolean enabled = false;

        public ReferenceData(String typeName, String name, String descriptor, String owner) {
            this.typeName = typeName;
            this.name = name;
            this.descriptor = descriptor;
            this.owner = owner;
        }

        boolean isAType() { return name == null; }
        boolean isAField() { return (descriptor != null) && descriptor.charAt(0) != '('; }
        boolean isAMethod() { return (descriptor != null) && descriptor.charAt(0) == '('; }
        boolean isAConstructor() { return "<init>".equals(name); }
    }
	
	public static class DeclarationData {
        int startPosition;
        int endPosition;
        String typeName;
        /**
         * Field or method name or null for type
         */
        String name;
        String descriptor;

        public DeclarationData(int startPosition, int length, String typeName, String name, String descriptor) {
            this.startPosition = startPosition;
            this.endPosition = startPosition + length;
            this.typeName = typeName;
            this.name = name;
            this.descriptor = descriptor;
        }

        public boolean isAType() { return name == null; }
        public boolean isAField() { return (descriptor != null) && descriptor.charAt(0) != '('; }
        public boolean isAMethod() { return (descriptor != null) && descriptor.charAt(0) == '('; }
        public boolean isAConstructor() { return "<init>".equals(name); }
    }
	
	public static class HyperlinkData {
        public int startPosition;
        public int endPosition;

        public HyperlinkData(int startPosition, int endPosition) {
            this.startPosition = startPosition;
            this.endPosition = endPosition;
        }
    }
	
	public static class HyperlinkReferenceData extends HyperlinkData {
        public ReferenceData reference;

        public HyperlinkReferenceData(int startPosition, int length, ReferenceData reference) {
            super(startPosition, startPosition+length);
            this.reference = reference;
        }
    }
}