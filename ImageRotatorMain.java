import javax.swing.*;

/**
 * The ImageRotatorMain method is for
 * ImageRotatorGUI to be run in a frame.
 */
public class ImageRotatorMain {
    /**
     * The Main method is for the GUI itself to run
     * for the user.
     */
    public static void main(String[] args) {

        /**
         * The JFrame frame is used so the ImageRotatorGUI
         * can be placed in it.
         */
        JFrame frame = new JFrame("Image Rotator GUI");
        //add the gui to the frame
        frame.add(new ImageRotatorGUI());
        //set frame attributes
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setVisible(true);
    }
}
