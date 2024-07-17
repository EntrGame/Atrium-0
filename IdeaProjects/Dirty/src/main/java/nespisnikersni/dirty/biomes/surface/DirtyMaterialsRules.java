package nespisnikersni.dirty.biomes.surface;

import nespisnikersni.dirty.biomes.DirtyBiomes;
import nespisnikersni.dirty.blocks.DirtyBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class DirtyMaterialsRules {
    private static final MaterialRules.MaterialRule DIRT = makeStateRule(Blocks.DIRT);
    private static final MaterialRules.MaterialRule GRASS_BLOCK = makeStateRule(DirtyBlocks.DIRT_BLOCK);

    public static MaterialRules.MaterialRule makeRules() {
        MaterialRules.MaterialCondition isAtOrAboveWaterLevel = MaterialRules.water(-1, 0);

        MaterialRules.MaterialRule grassSurface = MaterialRules.sequence(MaterialRules.condition(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);

        return MaterialRules.sequence(
                MaterialRules.sequence(MaterialRules.condition(MaterialRules.biome(DirtyBiomes.MUD_SWAMP),
                                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, GRASS_BLOCK)),
                        MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, GRASS_BLOCK)),
                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, grassSurface)
        );
    }

    private static MaterialRules.MaterialRule makeStateRule(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }
}









