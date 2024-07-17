package nespisnikersni.dirty.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import nespisnikersni.dirty.Dirty;
import nespisnikersni.dirty.screenhandlers.DirtRecyclerScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DirtRecyclerScreen extends HandledScreen<DirtRecyclerScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(Dirty.MOD_ID,"textures/gui/dirt_recycler_screen.png");
    public DirtRecyclerScreen(DirtRecyclerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0,TEXTURE);
        int x = (width-backgroundWidth)/2;
        int y = (height-backgroundHeight)/2;
        context.drawTexture(TEXTURE,x,y,0,0,backgroundWidth,backgroundHeight);
        renderProgressArrow(context,x,y);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context,mouseX,mouseY);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        context.drawTexture(TEXTURE,x+116,y+30,176,26,8,handler.getScaledFuel());
        if(handler.isCrafting()){
            context.drawTexture(TEXTURE,x+85,y+30,176,0,8,handler.getScaledProgress());
        }
    }
}
