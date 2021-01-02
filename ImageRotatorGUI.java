import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The ImageRotatorGUI is Class is made
 * to allow a user to rotate an image (my dog)
 * up to 360 degrees. The GUI is controlled with
 * two JSliders and two JCheckBoxes. One slider
 * controls the angle of rotation and the other
 * controls the speed. The checkboxes allow for
 * continual rotation one degree at a time,
 * continual rotation by a given number of degrees,
 * or rotation to a given angle through 10 clock
 * cycles.
 */
public class ImageRotatorGUI extends JPanel {

    /**
     * The double angle keeps track of the
     * total rotation angle.
     */
    private static double angle = 0;

    /**
     * The double speed keeps track of the
     * speed for the GUI. A higher value
     * gets a shorter clock
     */
    private static double speed = 0;

    /**
     * The double increment is use for gradual
     * rotation and is equal to 1/10 of the
     * total of the angle.
     */
    private static double increment = 0;

    /**
     * The int tick is used to keep track of
     * clock cycles. The tick allows the GUI
     * to rotate through 10 increments and then
     * wait 10 clock cycles. Set to reset to 0
     * on first cycle.
     */
    private static int tick = 21;

    /**
     * The AffineTransform transformA is used
     * to allow an image to be rotated a number
     * of degrees. The AffineTransform is commonly
     * used to rotate images and has a matrix
     * associated with it to perform an image
     * translation.
     */
    private AffineTransform transformA = new AffineTransform();

    /**
     * The JSlider angleSlider is used to set the angle
     * of rotation.
     */
    private JSlider angleSlider;

    /**
     * The JSlider speedSlider is
     * used to set the clock between 1 sec and 10 milisec.
     */
    private JSlider speedSlider;

    /**
     * The JCheckBox continueBox is used to implement
     * the functionality of the GUI by allowing for
     * continual rotation. See ImageRotatorGUI for
     * detailed explaination.
     */
    private JCheckBox continueBox;

    /**
     * The JCheckBox stopBox is used to implement
     * the functionality of the GUI by allowing the
     * user to lock in the desired angle of rotation.
     * See ImageRotatorGUI for detailed explanation.
     * */
    private JCheckBox stopBox;

    /**
     * The Timer timer is the clock used to control the speed
     * by setting the refresh of the image. The timer is initially
     * set to 1 sec but can be lowered down to 10ms.
     */
    private Timer timer;

    /**
     * The ImageRotatorGUI is used to allow a user to rotate
     * and image a number of degrees and allows continual
     * rotation if selected.
     * The JSlider angle slider is used to set the angle
     * of rotation. The JSlider speed slider is
     * used to set the clock between 1 sec and 10 milisec.
     * If neither check box is checked the
     * user is free to rotate the image as they please.
     * If the lock angle box is checked and the continual
     * rotation box isnt checked, the image will rotate
     * over 10 clock cycles from 0 to the position set by
     * the user. It then waits 10 cycles before resetting
     * and starting over.
     * If the continual rotation box is
     * checked and the lock angle box is not, the image rotates
     * by 1 degree at a time.
     * If both boxes are checked the image rotates at 1/10 the
     * angle set by the user every clock cycle
     */
    ImageRotatorGUI() {

        //set layout
        setLayout(new BorderLayout());

        //give speedSlider a new JSlider
        speedSlider = new JSlider(JSlider.VERTICAL, 0, 100, 0);
        //Set speedSlider attributes
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setMajorTickSpacing(20);
        speedSlider.setMinorTickSpacing(5);

        //give angleSlider a new JSlider
        angleSlider = new JSlider(JSlider.VERTICAL, 0, 360, 0);
        //Set angleSlider attributes
        angleSlider.setPaintTicks(true);
        angleSlider.setPaintLabels(true);
        angleSlider.setMajorTickSpacing(90);
        angleSlider.setMinorTickSpacing(15);

        //give continueBox a new JCheckBox with title
        continueBox = new JCheckBox("Enable Continuous Rotation");
        //give stopBox a new JCheckBox with a title
        stopBox = new JCheckBox("Lock Angle");
        //give timer new timer and create the actionlistener and logic
        timer = new Timer(1000, new ActionListener() {
            /**
             * The actionPerformed method is set to go off
             * on every clock cycle. It checks for what
             * combination of boxes are checked before
             * continuing.
             *
             * @param e  The parameter e is only to come from the
             *           clock itself.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //check for source being timer
                if (e.getSource() == timer) {
                    //check what combination of options are selected.
                    if (!stopBox.isSelected() && !continueBox.isSelected()) {
                        //repaint image
                        repaint();

                    } else if (stopBox.isSelected() && !continueBox.isSelected()) {
                        //Check to see if in first 10 cycles
                        if (tick < 10) {
                            angle = angle + (Math.toDegrees(increment) / 10);
                            //if the angle goes over 360, reduce by 360
                            if (360 < angle) {
                                angle = angle - 360;
                            }//end if else loop

                            //set value to updated values
                            angleSlider.setValue((int) (angle));
                            tick++;

                        //check to see if in cycles 10-20
                        } else if (tick < 20) {
                            //wait
                            tick++;
                        //reset values and start again
                        } else {
                            angleSlider.setValue((0));
                            tick = 0;
                            angle = 0;
                        }//end if else loop

                        //repaint image
                        repaint();

                    } else if (!stopBox.isSelected() && continueBox.isSelected()) {
                        //increment angle by 1 each clock
                        angle++;
                        //if angle is over 360, subtract 360
                        if (360 < angle) {
                            angle = angle - 360;
                        }//end if else loop

                        //set values
                        angleSlider.setValue((int) angle);

                        //repaint image
                        repaint();

                    } else if (stopBox.isSelected() && continueBox.isSelected()) {
                        //angle increments by 1/10 its original value
                        angle = angle + (Math.toDegrees(increment) / 10);
                        //if angle is over 360, subtract 360
                        if (360 < angle) {
                            angle = angle - 360;
                        }//end if else loop

                        //set values
                        angleSlider.setValue((int) (angle));

                        //repaint image
                        repaint();

                    }//end if else loop
                }//end if else loop
            }//end actionListener declaration
        });
        //add angleSlider Action Listener
        angleSlider.addChangeListener(new ChangeListener() {
            /**
             * The ChangeListener is added to the
             * angleSlider and is used to detect changes
             * in the angle and increment
             *
             * @param e The parameter e is only allowed
             *          to come from the angleSlider
             *          and when the lock angle box is disabled.
             */
            @Override
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() == angleSlider && !stopBox.isSelected() && !continueBox.isSelected()) {
                    //update values
                    angle = convertToRadians();
                    increment = convertToRadians();
                }//end if else loop
            }
        });
        //add speedSlider Action Listener
        speedSlider.addChangeListener(new ChangeListener() {
            /**
             * The ChangeListener is added to the
             * speedSlider and is used to detect
             * changes in the speed and adjust
             * the clock accordingly.
             *
             * @param e The parameter e is only allowed
             *          to come from the speedSlider and
             *          allows the speed to be adjusted
             *          dynamically.
             */
            @Override
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() == speedSlider) {
                    //update values
                    speed = getSpeed();
                    //set clock but don't divide by 0
                    timer.setDelay((int) (1000 / (speed + 1)));
                }//end if else loop
            }//end actionListener declaration
        });
        //add continueBox item listener
        continueBox.addItemListener(new ItemListener() {
            /**
             * The itemStateChanged method is used to
             * set the functionality from the continueBox
             * check box.
             *
             * @param e The parameter e is only allowed to
             *          come from the continueBox.
             */
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getSource() == continueBox) {
                    if (continueBox.isSelected()) {

                    } else if (!continueBox.isSelected()) {
                        angle = 0;
                        angleSlider.setValue((int) angle);
                        tick = 0;
                    }//end if else loop
                }//end if else loop

                //repaint image
                repaint();
            }
        });
        //add stopBox item listener
        stopBox.addItemListener(new ItemListener() {
            /**
             * The itemStateChanged method is used to
             * set the functionality from the stopBox
             * check box.
             *
             * @param e The parameter e is only allowed to
             *          come from the stopBox.
             */
            @Override
            public void itemStateChanged(ItemEvent e) {
                //check to see if the source is stopBox
                if (e.getSource() == stopBox) {
                    //check to see if stopBox is selected
                    if (stopBox.isSelected()) {
                        //disable angleSlider and reset to 0
                        angleSlider.setEnabled(false);
                        angle = 0;
                        angleSlider.setValue((int) angle);
                    } else if (!stopBox.isSelected()) {
                        angleSlider.setEnabled(true);
                    }//end else if loop
                }//end else if loop

                //repaint image
                repaint();
            }//end ItemListener declaration
        });

        //add items to Jpanel
        add(stopBox, BorderLayout.SOUTH);
        add(continueBox, BorderLayout.NORTH);
        add(speedSlider, BorderLayout.EAST);
        add(angleSlider, BorderLayout.WEST);

        //start timer
        timer.start();
    }

    /**
     * The convertToRadians method is used to
     * quickly convert the angleSlider value to
     * radians for the rotate functionality.
     *
     * @return The double returned is the value of the
     *         angle slider in radians.
     */
    private double convertToRadians() {
        return Math.toRadians(angleSlider.getValue());
    }

    /**
     * The getSpeed method is used to get the speed
     * of the speedSlider for other functionality to
     * use it.
     *
     * @return The double returned is the value of the
     *         speedSlider in the range of 0-100.
     */
    private double getSpeed() {
        return speedSlider.getValue();
    }

    /**
     * The loadInImage method is used to attempt to
     * load in an image in resources based on the
     * name of the file.
     *
     * @param filename The parameter filename is a string
     *                 used to look for the file in resources.
     * @return         The returned BufferedImage is the image
     *                 loaded in from the resources.
     */
    private BufferedImage loadInImage(String filename) {

        /**
         * The BufferedImage img is used
         * to hold the image loaded and
         * returned.
         */
        BufferedImage img = null;

        //try to load resource
        try {
            img = ImageIO.read(new File(getClass().getResource(filename).getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }//end try catch block

        return img;
    }

    /**
     * The paintComponent method is overriden to
     * set up a the transform for the image.
     * The java API states to not override paint,
     * but to override paintComponent instead.
     *
     * @param g The parameter g is the graphic to be
     *          draw by the method.
     */
    public void paintComponent(Graphics g) {
        //call super constructor
        super.paintComponent(g);

        /**
         * The BufferedImage rotated is given the
         * image from resources and loaded to be
         * rotated and then drawn.
         */
        BufferedImage rotated = loadInImage("CleoFace.gif");

        //set transform position and the transform rotation angle
        transformA = AffineTransform.getTranslateInstance(100, 100);
        transformA.rotate(convertToRadians(), rotated.getWidth() / 2, rotated.getHeight() / 2);

        /**
         * The Graphics2D g2d is used to create the graphic to
         * be drawn by the paintComponent
         */
        Graphics2D g2d = (Graphics2D) g.create();
        //draw rotated image
        g2d.drawImage(rotated, transformA, null);
    }
}
