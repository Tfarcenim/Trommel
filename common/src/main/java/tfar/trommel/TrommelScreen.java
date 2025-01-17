package tfar.trommel;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class TrommelScreen extends AbstractContainerScreen<TrommelMenu> {
    private static final ResourceLocation TEXTURE = Trommel.id("textures/container/trommel.png");

    public TrommelScreen(TrommelMenu $$0, Inventory $$1, Component $$2) {
        super($$0, $$1, $$2);
        this.titleLabelX = 59;
        inventoryLabelY += 26;
        imageHeight +=26;
    }

    @Override
    public void render(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(graphics);
        super.render(graphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(graphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int $$1, int $$2) {
        super.renderLabels(graphics, $$1, $$2);

    }

    @Override
    protected void renderBg(GuiGraphics graphics, float pPartialTick, int pX, int pY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        graphics.blit(TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);

        if (this.menu.isLit()) {
            int k = this.menu.getLitProgress();
            graphics.blit(TEXTURE, i + 33, j + 45 - k, 176, 12 - k, 14, k + 1);
        }
        int burn = menu.getBurnProgress();
        int frame = burn / 6;

        graphics.blit(TEXTURE, i + 105, j + 50, 176 + 16 * frame, 16, 16, 16);


    }
}
