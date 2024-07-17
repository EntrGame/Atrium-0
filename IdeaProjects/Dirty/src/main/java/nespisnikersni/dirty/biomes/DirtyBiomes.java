package nespisnikersni.dirty.biomes;

import nespisnikersni.dirty.Dirty;
import nespisnikersni.dirty.entities.ModEntities;
import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeSources;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.OceanPlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

public class DirtyBiomes {
    public static final RegistryKey<Biome> MUD_SWAMP = regBiome("mud_swamp");

    private static RegistryKey<Biome> regBiome(String id) {
        return RegistryKey.of(RegistryKeys.BIOME, new Identifier(Dirty.MOD_ID, id));
    }

    public static void boostrap(Registerable<Biome> context) {
        RegistryEntryLookup<PlacedFeature> featureLookup = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
        RegistryEntryLookup<ConfiguredCarver<?>> carverLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER);
        context.register(MUD_SWAMP, createMudSwamp(featureLookup,carverLookup));
    }


    public static Biome createMudSwamp(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup) {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SLIME, 1, 1, 1));
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FROG, 10, 2, 5));
        builder.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.TROPICAL_FISH, 25, 8, 8));
        GenerationSettings.LookupBackedBuilder lookupBackedBuilder = new GenerationSettings.LookupBackedBuilder(featureLookup, carverLookup);
        DefaultBiomeFeatures.addFossils(lookupBackedBuilder);
        addBasicFeatures(lookupBackedBuilder);
        DefaultBiomeFeatures.addDefaultOres(lookupBackedBuilder);
        DefaultBiomeFeatures.addGrassAndClayDisks(lookupBackedBuilder);
        DefaultBiomeFeatures.addMangroveSwampFeatures(lookupBackedBuilder);
        lookupBackedBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, OceanPlacedFeatures.SEAGRASS_SWAMP);
        MusicSound musicSound = MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_SWAMP);
        return new Biome.Builder()
                .precipitation(true)
                .temperature(0.8F)
                .downfall(0.9F)
                .effects((new BiomeEffects.Builder())
                        .waterColor(0x57D48A)
                        .waterFogColor(0x4DBD7B)
                        .skyColor(0x28E548)
                        .grassColor(0x4AB25C)
                        .foliageColor(0x139729)
                        .fogColor(0x1EC139)
                        .moodSound(BiomeMoodSound.CAVE)
                        .music(MusicType.createIngameMusic(RegistryEntry.of(SoundEvents.BLOCK_BAMBOO_WOOD_HIT))).build())
                .spawnSettings(builder.build())
                .generationSettings(lookupBackedBuilder.build())
                .build();
    }
    private static void addBasicFeatures(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
    }
}


