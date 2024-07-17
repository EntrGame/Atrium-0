package nespisnikersni.dirty;

import nespisnikersni.dirty.blocks.DirtyBlocks;
import nespisnikersni.dirty.blocks.entities.DirtyBlockEntities;
import nespisnikersni.dirty.blocks.entities.processing_table.ProcessingTableEntity;
import nespisnikersni.dirty.enchants.DirtyEnchants;
import nespisnikersni.dirty.entities.ModEntities;
import nespisnikersni.dirty.entities.mobs.guard.GuardEntity;
import nespisnikersni.dirty.entities.mobs.mud.MudEntity;
import nespisnikersni.dirty.events.DirtyEvents;
import nespisnikersni.dirty.items.DirtyItems;
import nespisnikersni.dirty.recipes.types.DirtRecyclerRecipeType;
import nespisnikersni.dirty.recipes.types.ProcessingTableRecipeType;
import nespisnikersni.dirty.recipes.types.SieveRecipeType;
import nespisnikersni.dirty.screenhandlers.DirtyScreenHandlers;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dirty implements ModInitializer {
	public static final String MOD_ID = "dirty";
    public static final Logger LOGGER = LoggerFactory.getLogger("dirty");

	@Override
	public void onInitialize() {
		DirtyBlocks.register();
		DirtyItems.register();
		DirtyEnchants.register();
		DirtyBlockEntities.register();
		PlayerBlockBreakEvents.AFTER.register(new DirtyEvents());
		DirtyScreenHandlers.register();
		FabricDefaultAttributeRegistry.register(ModEntities.MUD_ENTITY_TYPE, MudEntity.createMudAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.GUARD_ENTITY_TYPE, GuardEntity.createGuardAttributes());
		DirtRecyclerRecipeType.register();
		ProcessingTableRecipeType.register();
		SieveRecipeType.register();
		FuelRegistry.INSTANCE.add(DirtyItems.BIOFUEL,1200);
	}

}
