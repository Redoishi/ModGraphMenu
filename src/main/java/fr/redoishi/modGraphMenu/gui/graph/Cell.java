package fr.redoishi.modGraphMenu.gui.graph;

import com.mxgraph.model.mxCell;
import fr.redoishi.modGraphMenu.ModGraphMenu;
import io.github.prospector.modmenu.ModMenu;
import io.github.prospector.modmenu.util.HardcodedUtil;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * @author Redoishi
 */
public class Cell {

    private static final Logger LOGGER = ModGraphMenu.getLogger("Cell");
    private static final Identifier fabric = new Identifier(ModMenu.MOD_ID, "fabric_icon.png");
    private static final Identifier minecraft = new Identifier(ModMenu.MOD_ID, "mc_icon.png");
    private static final Identifier fabricGrey = new Identifier(ModMenu.MOD_ID, "grey_fabric_icon.png");

    public mxCell modCell;
    public String modId;
    public String modName;
    public Identifier img;

    public Cell(mxCell modCell, ModContainer mod) {
        this.modCell = modCell;

        ModMetadata metadata = mod.getMetadata();
        this.modId = metadata.getId();
        this.modName = metadata.getName();

        // img
        Optional<String> iconPath = metadata.getIconPath(64);
        if (iconPath.isPresent()) {
            this.img = new Identifier(this.modId, "icon.png");
        }else {
            if (HardcodedUtil.getFabricMods().contains(modId)) {
                this.img = fabric;
            }else if (modId.equals("minecraft")) {
                this.img = minecraft;
            }else {
                this.img = fabricGrey;
            }
        }
    }
}
