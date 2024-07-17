package nespisnikersni.dirty.screenhandlers;
import nespisnikersni.dirty.Dirty;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class DirtyScreenHandlers {
    public static final ScreenHandlerType<DirtRecyclerScreenHandler> DIRT_RECYCLER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Dirty.MOD_ID, "dirt_recycler"),
                    new ExtendedScreenHandlerType<>(DirtRecyclerScreenHandler::new));

    public static void register() {}
}
