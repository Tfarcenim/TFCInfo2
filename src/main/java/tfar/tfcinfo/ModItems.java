package tfar.tfcinfo;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import tfar.tfcinfo.item.SlimeCompassItem;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static final List<Item> items = new ArrayList<>();

    static final CreativeTabs tab = new CreativeTabs(TerrafirmaCraftInfo.MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Blocks.COAL_BLOCK);
        }
    };
    public static Item SPIRIT_THERMOMETER = create();
    public static Item RECENT_TEMPERATURE_RECORDS = create();
    public static Item MONSTER_FEROCITY_SAMPLE = create();
    public static Item MONSTER_MIGRATION_SAMPLE = create();
    public static Item CALENDAR = create();
    public static Item ALMANAC = create();
    public static Item PLUMB_BOB = create();
    public static Item SEXTANT = create();
    public static Item NAVIGATOR_BUNDLE = create();
    public static Item PIONEER_GEAR = create();
    public static Item MOON_CHART = create();
    public static Item SLIME_SAMPLE = create();
    public static Item RANGER_BUNDLE = create();
    public static Item ENHANCED_RANGER_BUNDLE = create();
    public static Item TEMPERATURE_BUNDLE = create();
    public static Item ENHANCED_TEMPERATURE_BUNDLE = create();
    public static Item SPACE_BUNDLE = create();
    public static Item ENHANCED_SPACE_BUNDLE = create();

    public static Item SLIME_COMPASS = new SlimeCompassItem().setCreativeTab(tab);

    public static Item create() {
        Item item = new Item().setCreativeTab(tab);
        items.add(item);
        return item;
    }

    public static void registerItems(RegistryEvent.Register<Item> e) {
        e.getRegistry().registerAll(
                SPIRIT_THERMOMETER.setRegistryName("spirit_thermometer").setTranslationKey(TerrafirmaCraftInfo.MODID + ".spirit_thermometer"),
                RECENT_TEMPERATURE_RECORDS.setRegistryName("recent_temperature_records").setTranslationKey(TerrafirmaCraftInfo.MODID + ".recent_temperature_records"),
                MONSTER_FEROCITY_SAMPLE.setRegistryName("monster_ferocity_sample").setTranslationKey(TerrafirmaCraftInfo.MODID + ".monster_ferocity_sample"),
                MONSTER_MIGRATION_SAMPLE.setRegistryName("monster_migration_sample").setTranslationKey(TerrafirmaCraftInfo.MODID + ".monster_migration_sample"),
                CALENDAR.setRegistryName("calendar").setTranslationKey(TerrafirmaCraftInfo.MODID + ".calendar"),
                ALMANAC.setRegistryName("almanac").setTranslationKey(TerrafirmaCraftInfo.MODID + ".almanac"),
                NAVIGATOR_BUNDLE.setRegistryName("navigator_bundle").setTranslationKey(TerrafirmaCraftInfo.MODID + ".navigator_bundle"),
                SEXTANT.setRegistryName("sextant").setTranslationKey(TerrafirmaCraftInfo.MODID + ".sextant"),
                PLUMB_BOB.setRegistryName("plumb_bob").setTranslationKey(TerrafirmaCraftInfo.MODID + ".plumb_bob"),
                SLIME_COMPASS.setRegistryName("slime_compass").setTranslationKey(TerrafirmaCraftInfo.MODID + ".slime_compass"),
                MOON_CHART.setRegistryName("moon_chart").setTranslationKey(TerrafirmaCraftInfo.MODID + ".moon_chart"),
                SLIME_SAMPLE.setRegistryName("slime_sample").setTranslationKey(TerrafirmaCraftInfo.MODID + ".slime_sample"),
                TEMPERATURE_BUNDLE.setRegistryName("temperature_bundle").setTranslationKey(TerrafirmaCraftInfo.MODID + ".temperature_bundle"),
                ENHANCED_TEMPERATURE_BUNDLE.setRegistryName("enhanced_temperature_bundle").setTranslationKey(TerrafirmaCraftInfo.MODID + ".enhanced_temperature_bundle"),
                RANGER_BUNDLE.setRegistryName("ranger_bundle").setTranslationKey(TerrafirmaCraftInfo.MODID + ".ranger_bundle"),
                ENHANCED_RANGER_BUNDLE.setRegistryName("enhanced_ranger_bundle").setTranslationKey(TerrafirmaCraftInfo.MODID + ".enhanced_ranger_bundle"),
                PIONEER_GEAR.setRegistryName("pioneer_gear").setTranslationKey(TerrafirmaCraftInfo.MODID + ".pioneer_gear"),
                SPACE_BUNDLE.setRegistryName("space_bundle").setTranslationKey(TerrafirmaCraftInfo.MODID + ".space_bundle"),
                ENHANCED_SPACE_BUNDLE.setRegistryName("enhanced_space_bundle").setTranslationKey(TerrafirmaCraftInfo.MODID + ".enhanced_space_bundle")
        );
    }
}
