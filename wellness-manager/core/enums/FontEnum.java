package core.enums;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public enum FontEnum {
    TITLE_FONT(Font.font("Verdana", FontWeight.EXTRA_BOLD, 48)),
    SUB_TITLE_FONT(Font.font("Verdana", FontWeight.BOLD, 32)),
    LBL_FONT(Font.font("Verdana", FontWeight.BOLD, 14)),
    LOG_BTN_FONT(Font.font("Verdana", FontWeight.BOLD, 14)),
    DEF_BTN_FONT(Font.font("Verdana", FontWeight.BOLD, 12));

    private Font font;

    private FontEnum(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return this.font;
    }
}
