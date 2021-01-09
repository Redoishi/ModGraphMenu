package fr.redoishi.modGraphMenu.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

/**
 * @author Redoishi
 */
public class GraphMenu extends Screen {

    private final Screen previousScreen;

    public GraphMenu(Screen previousScreen) {
        super(new TranslatableText("modgraphmenu.title"));
        this.previousScreen = previousScreen;
    }

    @Override
    protected void init() {

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(0);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 10, Formatting.WHITE.getColorValue());
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        this.client.openScreen(previousScreen);
    }
}
