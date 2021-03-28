package com.jd.web.decompiler;
//package com.jd.web.decompiler.loader;
//protected static class ReferenceData {
//        public String typeName;
//        /**
//         * Field or method name or null for type
//         */
//        public String name;
//        /**
//         * Field or method descriptor or null for type
//         */
//        public String descriptor;
//        /**
//         * Internal type name containing reference or null for "import" statement.
//         * Used to high light items matching with URI like "file://dir1/dir2/file?highlightPattern=hello&highlightFlags=drtcmfs&highlightScope=type".
//         */
//        public String owner;
//        /**
//         * "Enabled" flag for link of reference
//         */
//        public boolean enabled = false;
//
//        public ReferenceData(String typeName, String name, String descriptor, String owner) {
//            this.typeName = typeName;
//            this.name = name;
//            this.descriptor = descriptor;
//            this.owner = owner;
//        }
//
//        boolean isAType() { return name == null; }
//        boolean isAField() { return (descriptor != null) && descriptor.charAt(0) != '('; }
//        boolean isAMethod() { return (descriptor != null) && descriptor.charAt(0) == '('; }
//        boolean isAConstructor() { return "<init>".equals(name); }
//    }