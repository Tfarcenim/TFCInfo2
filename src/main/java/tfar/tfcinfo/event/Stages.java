package tfar.tfcinfo.event;

import tfar.tfcinfo.util.KnowledgeMemoryPair;

import java.util.Arrays;
import java.util.HashSet;

public class Stages {

	public static final KnowledgeMemoryPair regionalTemp = new KnowledgeMemoryPair("regional_temp");
	public static final KnowledgeMemoryPair averageTemp = new KnowledgeMemoryPair("avg_temp");
	public static final KnowledgeMemoryPair minTemp =  new KnowledgeMemoryPair("min_temp");
	public static final KnowledgeMemoryPair maxTemp =  new KnowledgeMemoryPair("max_temp");
	public static final KnowledgeMemoryPair rainfall =  new KnowledgeMemoryPair("rainfall");
	public static final KnowledgeMemoryPair localDifficulty =  new KnowledgeMemoryPair("local_difficulty");
	public static final KnowledgeMemoryPair date =  new KnowledgeMemoryPair("date");
	public static final KnowledgeMemoryPair time =  new KnowledgeMemoryPair("time");
	public static final KnowledgeMemoryPair moonPhase =  new KnowledgeMemoryPair("moon_phase");
	public static final KnowledgeMemoryPair slimeChunk = new KnowledgeMemoryPair("slime_chunk");
	public static final KnowledgeMemoryPair lightLevel = new KnowledgeMemoryPair("light_level");
	public static final KnowledgeMemoryPair spawnProtectionTimer = new KnowledgeMemoryPair("spawn_protection_timer");
	public static final KnowledgeMemoryPair facing = new KnowledgeMemoryPair("facing");
	public static final KnowledgeMemoryPair biome = new KnowledgeMemoryPair("biome");
	public static final KnowledgeMemoryPair nutrition = new KnowledgeMemoryPair("nutrition");
	public static final KnowledgeMemoryPair hwyla = new KnowledgeMemoryPair("hwyla");
	public static final KnowledgeMemoryPair flora = new KnowledgeMemoryPair("flora");
	public static final KnowledgeMemoryPair arboreal = new KnowledgeMemoryPair("arboreal");
	public static final KnowledgeMemoryPair misc = new KnowledgeMemoryPair("misc");



	//display X coord
	public static final KnowledgeMemoryPair longitudinal =  new KnowledgeMemoryPair("longitudinal");
	//display Y coord
	public static final KnowledgeMemoryPair depth =  new KnowledgeMemoryPair("depth");
	//display Z coord
	public static final KnowledgeMemoryPair constellation =  new KnowledgeMemoryPair("constellation");

	public static final KnowledgeMemoryPair skills = new KnowledgeMemoryPair("skills");
	public static final KnowledgeMemoryPair currentTemp = new KnowledgeMemoryPair("current_temp");

	protected static HashSet<String> matches = new HashSet<>();

	static {
		Arrays.stream(Stages.class.getFields()).forEach(field -> {
			try {
				KnowledgeMemoryPair pair = (KnowledgeMemoryPair) field.get(null);
				matches.add(pair.knowledge());
				matches.add(pair.memory());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		});
	}


}
