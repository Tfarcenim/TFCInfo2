package tfar.tfcinfo.util;

public class KnowledgeMemoryPair {

    private final String base;

    public KnowledgeMemoryPair(String base) {
        this.base = base;
    }

    //need item and knowledge unlock
    public String knowledge() {
        return base + "_knowledge";
    }

    //this item unlocks memorization
    public String memory() {
        return base +"_memory";
    }

    //just need to have the item
    public String base() {
        return base;
    }

    public String unlocksKnowledge() {
        return "unlocks_"+ knowledge();
    }
 }
