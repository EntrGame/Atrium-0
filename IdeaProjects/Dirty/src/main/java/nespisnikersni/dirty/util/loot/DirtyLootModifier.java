package nespisnikersni.dirty.util.loot;


import nespisnikersni.dirty.items.DirtyItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

public class DirtyLootModifier {
    public static void register(){
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, table, setter) -> {
            if(id.getPath().startsWith("chests/")) {
                LootPool pool = LootPool.builder()
                        .rolls(UniformLootNumberProvider.create(0, 1))
                        .with(ItemEntry.builder(DirtyItems.LOOT_BOX)
                                .weight(8)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 3))))
                        .with(ItemEntry.builder(DirtyItems.LOOT_BOX)
                                .weight(8)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 3))))
                        .build();
                table.pool(pool);
            }
        });
    }
}