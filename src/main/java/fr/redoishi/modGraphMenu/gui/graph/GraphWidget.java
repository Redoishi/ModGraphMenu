package fr.redoishi.modGraphMenu.gui.graph;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mxgraph.model.mxGeometry;
import fr.redoishi.modGraphMenu.ModGraphMenu;
import fr.redoishi.modGraphMenu.gui.utils.DefTooltipSupplier;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

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
    private HashMap<AbstractButtonWidget, mxGeometry> btnModList;

    public GraphWidget(Screen parent, int x, int y, int width, int height) {
        this.parent = parent;
        this.xOrigin = x;
        this.yOrigin = y;
        this.width = width;
        this.height = height;

        // init coord center graph
        this.x = xOrigin;
        this.y = yOrigin;
        this.initRenderGraph();
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

        this.renderGraph(matrices, mouseX, mouseY, delta);

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

    private void initRenderGraph() {
        // TMP
        GenerateGraph.loadGraph();

        btnModList = new HashMap<>();
        List<Cell> modsCell = GenerateGraph.getModCell();
        for (Cell modCell : modsCell) {
            // btn
            mxGeometry geometry = modCell.modCell.getGeometry();
            btnModList.put(new TexturedButtonWidget(x + (int) geometry.getX(), y + (int) geometry.getY(),
                    GenerateGraph.SIZE, GenerateGraph.SIZE, 0, 0, 0,
                    modCell.img,
                    GenerateGraph.SIZE,
                    GenerateGraph.SIZE,
                    button -> {
                    },
                    new DefTooltipSupplier(this.parent, null, modCell.modName),
                    NarratorManager.EMPTY
            ), geometry);
        }
    }

    private void renderGraph(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        btnModList.forEach((abstractButtonWidget, geometry) -> {
            abstractButtonWidget.x = this.x + (int) geometry.getX();
            abstractButtonWidget.y = this.y + (int) geometry.getY();
            abstractButtonWidget.render(matrices, mouseX, mouseY, delta);
        });
    }
}
