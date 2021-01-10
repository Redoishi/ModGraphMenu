package fr.redoishi.modGraphMenu.gui.utils;

import net.minecraft.client.gui.widget.ButtonWidget;

/**
 * @author Resoishi
 */
public enum ETooltipPositions {
    LEFT() {
        @Override
        public int processBtnX(DefTooltipSupplier tooltip, ButtonWidget button) {
            return button.x - (12 - MARGIN);// TODO
        }

        @Override
        public int processBtnY(DefTooltipSupplier tooltip, ButtonWidget button) {
            return button.y + (12 + MARGIN) + MARGIN;
        }

        @Override
        public int processX(DefTooltipSupplier tooltip, int x) {
            return x - (12 - MARGIN);// TODO
        }

        @Override
        public int processY(DefTooltipSupplier tooltip, int y) {
            return y + (12 + MARGIN);
        }
    },
    RIGHT() {
        @Override
        public int processBtnX(DefTooltipSupplier tooltip, ButtonWidget button) {
            return button.x - (12 - MARGIN) + button.getWidth();
        }

        @Override
        public int processBtnY(DefTooltipSupplier tooltip, ButtonWidget button) {
            return LEFT.processBtnY(tooltip, button);
        }

        @Override
        public int processY(DefTooltipSupplier tooltip, int y) {
            return y + (12 + MARGIN);
        }
    },
    TOP(),
    BOTTOM() {
        @Override
        public int processBtnY(DefTooltipSupplier tooltip, ButtonWidget button) {
            return button.y + (12 + MARGIN) + button.getHeight();
        }

        @Override
        public int processX(DefTooltipSupplier tooltip, int x) {
            return x - (12 - MARGIN);
        }

        @Override
        public int processY(DefTooltipSupplier tooltip, int y) {
            return y + (12 + MARGIN) + 10; //TODO 10 ??
        }
    };

    private static final int MARGIN = 4;
    private static final int LINE_HEIGHT = 10;

    ETooltipPositions() {
    }

    public int processBtnX(DefTooltipSupplier tooltip, ButtonWidget button) {
        return button.x;
    }

    public int processBtnY(DefTooltipSupplier tooltip, ButtonWidget button) {
        return button.y;
    }

    public int processX(DefTooltipSupplier tooltip, int x) {
        return x;
    }

    public int processY(DefTooltipSupplier tooltip, int y) {
        return y;
    }

    private int getMaxWidth(DefTooltipSupplier tooltip) {
        // TODO for LEFT
        return 0;
    }
}
