import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Dolunay Dagci
 * 2.24.19
 * Assignment: Ch12 Celcius and Fahrenheit
 * CISS 111-360
 * This program converts from Celsius to Fahrenheit,  also converts Fahrenheit to Celsius depending on the user's choice.
 *
 */
public class DD_DegreeConverter extends JFrame {

    private JRadioButton celsButton, fahButton; //Celsius Button, Fahrenheit Button
    private JTextField temperature;
    private JButton calculate;

    public DD_DegreeConverter() {

        setTitle("Fahrenheit and Celsius Converter");
        setSize(400, 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //build panel
        fahButton = new JRadioButton("Convert Fahrenheit to Celsius");
        celsButton = new JRadioButton("Convert Celsius to Fahrenheit");
        ButtonGroup buttonGroup= new ButtonGroup();

        temperature = new JTextField(20);

        buttonGroup.add(fahButton);
        buttonGroup.add(celsButton);

        calculate = new JButton("Convert");
        calculate.setBackground(Color.BLUE);
        calculate.setForeground(Color.white);

        calculate.addActionListener(new CalculateListener()); //add listener for calculate button

        add(fahButton, BorderLayout.WEST);
        add(calculate, BorderLayout.SOUTH);
        add(celsButton, BorderLayout.EAST);
        add(temperature,BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    /**
     * Action Listener for Calculator
     */
    private class CalculateListener implements ActionListener { //create the listener class for calculate
        double input; //user input


        //create methods for chosen conversions
        /**
         *
         * @param input
         * @return converted celsius
         */
         double cels_to_fah_conversion(double input) {
            return  Math.round((1.8*input) + 32); }

        /**
         *
         * @param input
         * @return converted fahrenheit
         */
         double fah_to_cels_conversion(double input) {
            return Math.round((input-32)/1.8); } //round values so they don't become a long number

        @Override
        public void actionPerformed(ActionEvent e) {

             if (!fahButton.isSelected() && !celsButton.isSelected()){ //if nothing's selected pop up a warning message
                 JOptionPane.showMessageDialog(null, "Please select an option.",
                         "Choose an Option", JOptionPane.WARNING_MESSAGE);
             }
             //apply chosen conversion types
            if (fahButton.isSelected()) {
                //convert fah to cels
                try {
                    input = Double.parseDouble(temperature.getText()); //parse text double
                    JOptionPane.showMessageDialog(null, "Converted Value: " +
                                    fah_to_cels_conversion(input) + " Celsius."
                            , "Fahrenheit To Celsius", JOptionPane.INFORMATION_MESSAGE);
                } catch (RuntimeException a) {
                    JOptionPane.showMessageDialog(null, "Enter a valid input please.", "Invalid Input",
                            JOptionPane.WARNING_MESSAGE); //if user enters, for example, a letter, it tells the user that it is an invalid input
                }
            }
            if (celsButton.isSelected()) {
                try {
                    //convert cels to fah
                    input = Double.parseDouble(temperature.getText());//parse text double
                    JOptionPane.showMessageDialog(null, "Converted Value: " +
                                    cels_to_fah_conversion(input) + " Fahrenheit."
                            , "Celsius To Fahrenheit.", JOptionPane.INFORMATION_MESSAGE);
                } catch (RuntimeException a) {
                    JOptionPane.showMessageDialog(null, "Enter a valid input please.", "Invalid Input",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    /**
     *
     * @param args
     * Main
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { }
        new DD_DegreeConverter(); } }
