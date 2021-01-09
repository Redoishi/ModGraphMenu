package fr.redoishi.modGraphMenu;

import net.fabricmc.api.ClientModInitializer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Redoishi
 */
public class ModGraphMenu implements ClientModInitializer {

    public static final String MOD_ID = "modgraphmenu";
    public static final String MOD_PREFIX = "MGM";
    private static final Logger LOGGER = getLogger("");

    public static Logger getLogger(String className) {
        String suffix = StringUtils.isBlank(className) ? "" : "|" + className;
        return LogManager.getLogger(MOD_PREFIX + suffix);
    }

    @Override
    public void onInitializeClient() {

    }
}
