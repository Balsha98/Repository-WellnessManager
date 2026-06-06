package core.enums;

import javafx.scene.paint.Color;

public enum ColorEnum {
    WHITE(Color.rgb(255, 255, 255)),
    BLUE(Color.rgb(0, 56, 101));

    private Color color;

    private ColorEnum(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
