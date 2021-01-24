package fr.redoishi.modGraphMenu.gui;

import fr.redoishi.modGraphMenu.ModGraphMenu;
import fr.redoishi.modGraphMenu.gui.graph.GraphWidget;
import fr.redoishi.modGraphMenu.gui.utils.DefTooltipSupplier;
import fr.redoishi.modGraphMenu.gui.utils.ETooltipPositions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

/**
 * @author Redoishi
 */
public class GraphMenu extends Screen {

    private final Screen previousScreen;
    private GraphWidget graphWidget;

    public GraphMenu(Screen previousScreen) {
        super(new TranslatableText("modgraphmenu.title"));
        this.previousScreen = previousScreen;
    }

    @Override
    protected void init() {
        // return btn
        this.addButton(new TexturedButtonWidget(0, 0, 20, 20, 0, 0, 20,
                new Identifier(ModGraphMenu.MOD_ID, "textures/gui/list_button.png"),
                20,
                40,
                button -> this.onClose(),
                new DefTooltipSupplier(this, ETooltipPositions.RIGHT, "modmenu.title"),
                NarratorManager.EMPTY
        ));
        //
        this.graphWidget = new GraphWidget(this, 5, this.height / 4, this.width - 10, (this.height / 4 * 3) - 5);
        addChild(this.graphWidget);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(0);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 10, Formatting.WHITE.getColorValue());
        this.graphWidget.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        this.client.openScreen(previousScreen);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button != 0) {
            return false;
        }
        if (this.graphWidget.isMouseOver(mouseX, mouseY)) {
            return this.graphWidget.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
        return false;
    }
}
