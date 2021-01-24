package fr.redoishi.modGraphMenu.gui.graph;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.redoishi.modGraphMenu.ModGraphMenu;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author Redoishi
 */
public class GraphWidget extends DrawableHelper implements Drawable, Element {

    private static final Logger LOGGER = ModGraphMenu.getLogger("GraphWidget");

    private final int xOrigin;
    private final int yOrigin;
    private final int width;
    private final int height;
    private final Screen parent;
    private int x;
    private int y;

    public GraphWidget(Screen parent, int x, int y, int width, int height) {
        this.parent = parent;
        this.x = x;
        this.xOrigin = x;
        this.y = y;
        this.yOrigin = y;
        this.width = width;
        this.height = height;
        this.init();
    }

    private void init(){

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        GL11.glPushMatrix();

        // init draw zone
        RenderSystem.enableDepthTest();
        RenderSystem.translatef(0.0F, 0.0F, 950.0F);
        RenderSystem.colorMask(false, false, false, false);
        fill(matrices, 0, 0, this.parent.width, this.parent.height, Color.BLACK.getRGB());
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.translatef(0.0F, 0.0F, -950.0F);
        RenderSystem.depthFunc(GL11.GL_GEQUAL);
        fill(matrices, xOrigin, yOrigin, xOrigin + width, yOrigin + height, new Color(0F, 0F, 0F, 0.5F).getRGB());
        RenderSystem.depthFunc(GL11.GL_LEQUAL);

        // TODO graph

        RenderSystem.depthFunc(GL11.GL_GEQUAL);
        RenderSystem.translatef(0.0F, 0.0F, -950.0F);
        RenderSystem.colorMask(false, false, false, false);
        fill(matrices, 0, 0, this.parent.width, this.parent.height, Color.BLACK.getRGB());
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.translatef(0.0F, 0.0F, 950.0F);
        RenderSystem.depthFunc(GL11.GL_LEQUAL);

        GL11.glPopMatrix();
    }


    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= xOrigin && mouseX < xOrigin + width && mouseY >= yOrigin && mouseY < yOrigin + height;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        this.x += deltaX;
        this.y += deltaY;
        return true;
    }
}
