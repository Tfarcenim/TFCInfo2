package tfar.tfcinfo.util;

public class KnowledgeMemoryPair {

    private final String base;

    public KnowledgeMemoryPair(String base) {
        this.base = base;
    }

    public String knowledge() {
        return base + "_knowledge";
    }

    public String memory() {
        return base +"_memory";
    }

    public String base() {
        return base;
    }
 }
