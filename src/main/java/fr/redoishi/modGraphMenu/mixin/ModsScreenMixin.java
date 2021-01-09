package fr.redoishi.modGraphMenu.mixin;

import fr.redoishi.modGraphMenu.ModGraphMenu;
import fr.redoishi.modGraphMenu.gui.GraphMenu;
import io.github.prospector.modmenu.gui.ModsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModsScreen.class)
public class ModsScreenMixin extends Screen {

    protected ModsScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "init()V")
    private void init(CallbackInfo info) {
        this.addButton(new TexturedButtonWidget(10, 10, 20, 20, 0, 0, 20,
                new Identifier(ModGraphMenu.MOD_ID, "textures/gui/graph_button.png"),
                20,
                40,
                button -> {
                    MinecraftClient.getInstance().openScreen(new GraphMenu(this));
                }));
    }
}
