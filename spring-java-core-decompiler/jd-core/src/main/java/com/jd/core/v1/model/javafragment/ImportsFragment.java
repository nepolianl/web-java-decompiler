/*
 * Copyright (c) 2008-2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package com.jd.core.v1.model.javafragment;

import com.jd.core.v1.model.fragment.FlexibleFragment;
import com.jd.core.v1.util.DefaultList;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;


public class ImportsFragment extends FlexibleFragment implements JavaFragment {
    protected static final ImportCountComparator COUNT_COMPARATOR = new ImportCountComparator();

    protected final HashMap<String, ImportsFragment.Import> importMap = new HashMap<>();

    public ImportsFragment(int weight) {
        super(0, -1, -1, weight, "Imports");
    }

    public void addImport(String internalName, String qualifiedName) {
        Import imp = importMap.get(internalName);

        if (imp == null) {
            importMap.put(internalName, new Import(internalName, qualifiedName));
        } else {
            imp.incCounter();
        }
    }

    public boolean isEmpty() {
        return importMap.isEmpty();
    }

    public void initLineCounts() {
        maximalLineCount = initialLineCount = lineCount = importMap.size();
    }

    public boolean contains(String internalName) {
        return importMap.containsKey(internalName);
    }

    @Override
    public int getLineCount() {
        assert (lineCount != -1) : "Call initLineCounts() before";
        return lineCount;
    }

    public Collection<Import> getImports() {
        int lineCount = getLineCount();
        int size = importMap.size();

        if (lineCount < size) {
            DefaultList<Import> imports = new DefaultList<>(importMap.values());

            imports.sort(COUNT_COMPARATOR);

            // Remove less used imports
            imports.subList(lineCount, size).clear();

        return imports;
        } else {
            return importMap.values();
        }
    }

    public static class Import {
        protected String internalName;
        protected String qualifiedName;
        protected int counter;

        public Import(String internalName, String qualifiedName) {
            this.internalName = internalName;
            this.qualifiedName = qualifiedName;
            this.counter = 1;
        }

        public String getInternalName() {
            return internalName;
        }
        public String getQualifiedName() {
            return qualifiedName;
        }
        public int getCounter() {
            return counter;
        }
        public void incCounter() {
            counter++;
        }
    }

    @Override
    public void accept(JavaFragmentVisitor visitor) {
        visitor.visit(this);
    }

    protected static class ImportCountComparator implements Comparator<Import> {
        public int compare(Import tr1, Import tr2) {
            return tr2.getCounter() - tr1.getCounter();
        }
        public boolean equals(Object obj) {
            return this.equals(obj);
        }
    }
}
