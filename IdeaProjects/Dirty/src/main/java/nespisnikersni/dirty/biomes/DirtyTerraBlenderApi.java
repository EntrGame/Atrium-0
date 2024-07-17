package nespisnikersni.dirty.biomes;

import nespisnikersni.dirty.Dirty;
import nespisnikersni.dirty.biomes.surface.DirtyMaterialsRules;
import net.minecraft.util.Identifier;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class DirtyTerraBlenderApi implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new DirtyOverworldRegion(new Identifier(Dirty.MOD_ID,"mud_swamp"),6));
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD,Dirty.MOD_ID,DirtyMaterialsRules.makeRules());
    }
}