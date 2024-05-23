package mvp.communication;

import javafx.scene.paint.Color;

/**
 * Stores the colors we will use to display update messages to the user
 */
public enum Colors {
    ERROR(Color.RED),
    SUCCESS(Color.GREEN);

    private final Color color;

    Colors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
