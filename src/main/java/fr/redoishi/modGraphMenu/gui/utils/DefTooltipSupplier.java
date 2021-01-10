package fr.redoishi.modGraphMenu.gui.utils;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * @author Redoishi
 */
public class DefTooltipSupplier implements ButtonWidget.TooltipSupplier {

    protected List<Text> text;
    protected Screen screen;
    protected ETooltipPositions pos;

    /**
     * @param screen
     * @param positions default in {@link ETooltipPositions#TOP}
     * @param keyText   min one
     */
    public DefTooltipSupplier(Screen screen, @Nullable ETooltipPositions positions, String... keyText) {
        this(screen, positions, Arrays.stream(keyText).map(TranslatableText::new).toArray(TranslatableText[]::new));
    }

    public DefTooltipSupplier(Screen screen, @Nullable ETooltipPositions positions, Text... text) {
        assert text != null && text.length > 0;
        this.text = Arrays.asList(text);
        this.screen = screen;
        this.pos = positions == null ? ETooltipPositions.TOP : positions;
    }

    @Override
    public void onTooltip(ButtonWidget button, MatrixStack matrices, int mouseX, int mouseY) {
        if (button.isFocused()) {
            screen.renderTooltip(matrices, text, pos.processBtnX(this, button), pos.processBtnY(this, button));
        }else if (button.isHovered()) { // ! isHovered test if hover or Focus is true
            screen.renderTooltip(matrices, text, pos.processX(this, mouseX), pos.processY(this, mouseY));
        }
    }

    public List<Text> getText() {
        return text;
    }

    public Screen getScreen() {
        return screen;
    }
}
