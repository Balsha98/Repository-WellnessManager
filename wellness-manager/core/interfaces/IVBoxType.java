package core.interfaces;

import javafx.scene.layout.VBox;

public interface IVBoxType {

    // App size constants.
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    /**
     * Getting the parent.
     * 
     * @return - a VBox.
     */
    public VBox getVBox();

    /**
     * Setting the parent.
     */
    public void createVBox();
}
