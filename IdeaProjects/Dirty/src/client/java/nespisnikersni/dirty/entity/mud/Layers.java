package nespisnikersni.dirty.entity.mud;

import nespisnikersni.dirty.Dirty;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class Layers {
    public static final EntityModelLayer MUD = new EntityModelLayer(new Identifier(Dirty.MOD_ID,"mud_dog"),"Muddog");
    public static final EntityModelLayer GUARD = new EntityModelLayer(new Identifier(Dirty.MOD_ID,"guard"),"Guard");
}
