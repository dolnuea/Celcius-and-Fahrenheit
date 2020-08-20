import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.lang.Number;
import java.lang.*;

/** This class is a Graphic User Interface for user to do the measurement unit
 * convert.
 * The system will performance the following operations:
 * 1) Display GUI for user to choose the converter option
 * 2) Retrieve user input and store in appropriate data structure
 * 3) If any error occur (multi-fields are filled, input is not a number,etc),
 report errors in a message box.
 * 4) If information is correctly keyed in, echo the
 *    information to screen in message box
 * 5) Continuesly append information into a database behind the scene.
 *  	 until the user require to exit.
 * 6) Exit the program if the user wish.
 * @author
 * @version
 */




public class Converter implements ActionListener {
    //define constants
    public final double C_KG2LB= 2.2;		// 1kg=2.2lbs
    public final double C_LB2KG= 0.45;		//1lbs=0.45kg
    public final double C_M2FT= 3.28;		//1m=3.28ft
    public final double C_FT2M= 0.3;		//1ft=0.3m

    private JFrame converterFrame;
    private JPanel converterPanel;
    private JLabel weight, length, temprature, weight1, length1,temprature1;
    private JTextField w1,w2,l1,l2,t1,t2;
    private JRadioButton jr[];
    private JButton submit, quit, clear;
    //private FileWriter outfile;
    //private File outputFile;
    //private FileOutputStream outstream;


    /****************************************************************
     *check the input, if it include characters other than number, return 0, otherwise return 1.
     */
    private int checkInput(String inString) {

        StringBuffer tStringBuf;
        int flag=0;
        tStringBuf=new StringBuffer(inString);
        //check each input character to see if it is a number
        for(int index=0; index<tStringBuf.length(); index++){
            char ch=tStringBuf.charAt(index);
            if( (ch<'0' || ch>'9') && (ch !='.')){
                flag=1;
                break;
            }
        }
        if(flag==1) return 0;
            //not a number input
        else return 1;	//input is right

    }

    /****************************************************************
     *Constructor
     */
    public Converter() {
        // Create the frame and container.
        converterFrame = new JFrame("My Converter");
        converterPanel = new JPanel();
        converterPanel.setLayout(new GridLayout(0, 5, 40, 40));

        // Add the widgets.
        addWidgets();

        // Add the panel to the frame.
        converterFrame.getContentPane().add(converterPanel, BorderLayout.CENTER);

        // Exit when the window is closed.
        converterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Show the converter.
        converterFrame.setSize(800, 300);
        converterFrame.setVisible(true);

    }

    /****************************************************************
     * Create and add the widgets to the converter interface.
     */
    private void addWidgets() {

        // Create widgets including all labels, textfields and buttons etc.
        ButtonGroup convertgroup=new ButtonGroup();
        jr=new JRadioButton[3];

        // Define weight-related controls
        jr[0]=new JRadioButton("Weight");
        weight = new JLabel("kg    =", SwingConstants.LEFT);
        w1 = new JTextField(15);
        w2 = new JTextField(25);
        weight1 = new JLabel("lbs ", SwingConstants.LEFT);
        convertgroup.add(jr[0]);


        // Define length-related controls
        jr[1]=new JRadioButton("Length");
        length = new JLabel("m    = ", SwingConstants.LEFT);
        l1 = new JTextField(25);
        l2 = new JTextField(25);
        length1 = new JLabel("ft", SwingConstants.LEFT);
        convertgroup.add(jr[1]);

        // Define temporature-related controls
        jr[2]=new JRadioButton("Temporature");
        temprature = new JLabel("c    =", SwingConstants.LEFT);
        t1 = new JTextField(25);
        t2 = new JTextField(25);
        temprature1 = new JLabel("F", SwingConstants.LEFT);
        convertgroup.add(jr[2]);

        // Define Command Buttons
        submit = new JButton("Convert");
        clear = new JButton("Clear");
        quit = new JButton("Quit");

        // Listen to events from Submit and quit button.
        submit.addActionListener(this);
        clear.addActionListener(this);
        quit.addActionListener(this);

        // Add widgets to container.
        converterPanel.add(jr[0]);
        converterPanel.add(w1);
        converterPanel.add(weight);
        converterPanel.add(w2);
        converterPanel.add(weight1);

        converterPanel.add(jr[1]);
        converterPanel.add(l1);
        converterPanel.add(length);
        converterPanel.add(l2);
        converterPanel.add(length1);

        converterPanel.add(jr[2]);
        converterPanel.add(t1);
        converterPanel.add(temprature);
        converterPanel.add(t2);
        converterPanel.add(temprature1);

        converterPanel.add(submit);
        converterPanel.add(clear);
        converterPanel.add(quit);

        // set border of the labels
        weight.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        length.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        temprature.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    }



    /****************************************************************
     * write the 155.txt of the conversion result
     */
    private void record(String aResult){
        try{
            File dataFile = new File("CSE155.txt");
            if (dataFile.exists()){
                System.out.print("existed");
                FileWriter myF1Writer = new FileWriter(dataFile,true);
                BufferedWriter myB1Writer = new BufferedWriter(myF1Writer);
                myB1Writer.write(aResult + ",");
                myB1Writer.newLine();
                myB1Writer.close();
                myF1Writer.close();
            }
            else{
                System.out.print("create new");
                FileWriter myFWriter = new FileWriter(dataFile);
                BufferedWriter myBWriter = new BufferedWriter(myFWriter);
                myBWriter.write(aResult + ",");
                myBWriter.newLine();
                myBWriter.close();
                myFWriter.close();
            }
        }
        catch(Exception e){
        }

    }

    /****************************************************************
     * convert weight
     */
    private void weightConvert(){

        // check if both area are empty.
        if( w1.getText().length()==0 && w2.getText().length()==0){
            // Give a warning if both textfields are filled
            JOptionPane.showMessageDialog(converterFrame,
                    "no input ! ", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Convert if only one text area is filled.
        if( w1.getText().length()!=0 && w2.getText().length()!=0){
            // Give a warning if both textfields are filled
            JOptionPane.showMessageDialog(converterFrame,
                    "input only one field ", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        // if the input include characters other than number, give a warning
        if( (w1.getText().length()!=0 && checkInput(w1.getText()) ==0 )||
                (w2.getText().length()!=0 && checkInput(w2.getText()) ==0 ) ){
            JOptionPane.showMessageDialog(converterFrame,
                    "input is not a number", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // start convert the weight from KG to LB
        if(w1.getText().length()!=0 ){
            double in = Double.parseDouble(w1.getText());
            double out=in*C_KG2LB;
            JOptionPane.showMessageDialog(converterFrame,
                    "your input:\n"+in+" kg\n The result:\n"+out+"lb",
                    "echo result", JOptionPane.INFORMATION_MESSAGE);
            record("your input:\n"+in+" kg\n The result:\n"+out+"lb");
        }
        // start convert the weight from LB to KG
        if(w2.getText().length()!=0){
            double in = Double.parseDouble( w2.getText() );
            double out=in*C_LB2KG;
            JOptionPane.showMessageDialog(converterFrame,
                    "your input:\n"+in+"lb\n The result:\n"+out+"kg",
                    "echo result", JOptionPane.INFORMATION_MESSAGE);
            record("your input:\n"+in+"lb\n The result:\n"+out+"kg");
        }


    }

    /****************************************************************
     * convert length
     */
    private void lengthConvert(){

        // check if both area are empty.
        if( l1.getText().length()==0 && l2.getText().length()==0){
            // Give a warning if both textfields are filled
            JOptionPane.showMessageDialog(converterFrame,
                    "no input ! ", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Convert if only one text area is filled.
        if( l1.getText().length()!=0 && l2.getText().length()!=0){
            // Give a warning if both textfields are filled
            JOptionPane.showMessageDialog(converterFrame,
                    "input only one field ", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // if the input include characters other than number, give a warning
        if( (l1.getText().length()!=0 && checkInput(l1.getText()) ==0 )||
                (l2.getText().length()!=0 && checkInput(l2.getText()) ==0 ) ){
            JOptionPane.showMessageDialog(converterFrame,
                    "input is not a number", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // start convert the weight from KG to LB
        if(l1.getText().length()!=0){
            double in = Double.parseDouble( l1.getText() );
            double out=in*C_M2FT;
            JOptionPane.showMessageDialog(converterFrame,
                    "your input:\n"+in+" m\n The result:\n"+out+"ft",
                    "echo result", JOptionPane.INFORMATION_MESSAGE);
            record("your input:\n"+in+" m\n The result:\n"+out+"ft");
        }
        if(l2.getText().length()!=0){
            double in = Double.parseDouble( l2.getText() );
            double out=in*C_FT2M;
            JOptionPane.showMessageDialog(converterFrame,
                    "your input:\n"+in+"ft\n The result:\n"+out+"m",
                    "echo result", JOptionPane.INFORMATION_MESSAGE);
            record("your input:\n"+in+"ft\n The result:\n"+out+"m");
        }

    }

    /****************************************************************
     * convert temperature
     */
    private void temperatureConvert(){

        // check if both area are empty.
        if( t1.getText().length()==0 && t2.getText().length()==0){
            // Give a warning if both textfields are filled
            JOptionPane.showMessageDialog(converterFrame,
                    "no input ! ", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Convert if only one text area is filled.
        if( t1.getText().length()!=0 && t2.getText().length()!=0){
            // Give a warning if both textfields are filled
            JOptionPane.showMessageDialog(converterFrame,
                    "input only one field ", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        // if the input include characters other than number, give a warning
        if( (t1.getText().length()!=0 && checkInput(t1.getText()) ==0 )||
                (t2.getText().length()!=0 && checkInput(t2.getText()) ==0 ) ){
            JOptionPane.showMessageDialog(converterFrame,
                    "input is not a number", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        // start convert the weight from KG to LB
        if(t1.getText().length()!=0){
            double in = Double.parseDouble( t1.getText() );
            double out=in*9/5+32;
            JOptionPane.showMessageDialog(converterFrame,
                    "your input:\n"+in+" c\n The result:\n"+out+"f",
                    "echo result", JOptionPane.INFORMATION_MESSAGE);
            record("your input:\n"+in+" c\n The result:\n"+out+"f");
        }
        if(t2.getText().length()!=0){
            double in = Double.parseDouble( t2.getText() );
            double out=(in-32)*5/9;
            JOptionPane.showMessageDialog(converterFrame,
                    "your input:\n"+in+"f\n The result:\n"+out+"c",
                    "echo result", JOptionPane.INFORMATION_MESSAGE);
            record("your input:\n"+in+"f\n The result:\n"+out+"c");
        }
    }

    /****************************************************************
     * Implementation of ActionListener interface.
     */
    public void actionPerformed(ActionEvent event){

        //
        //* If the quit button is clicked, system quit
        //

        if (event.getSource() == quit) {
            System.exit(0);
        }

        //
        //* If the clear button is clicked
        //

        if (event.getSource() == clear) {
            w1.setText("");
            w2.setText("");
            l1.setText("");
            l2.setText("");
            t1.setText("");
            t2.setText("");

        }

        //
        //* If the submit button is clicked
        //

        if (event.getSource()== submit) {
            // capture information in the text field and convert

            //check if a radio button is selected
            if(!jr[0].isSelected() && !jr[1].isSelected() && !jr[2].isSelected())
                JOptionPane.showMessageDialog(converterFrame,
                        "No RadioButton has been Selected ", "Warning",
                        JOptionPane.WARNING_MESSAGE);

            //
            //if weight is choosen
            //
            if(jr[0].isSelected()){
                weightConvert();
                return;
            }

            //
            //if length is choosen
            //
            if(jr[1].isSelected()){
                lengthConvert();
                return;
            }

            //
            //if temperature is choosen
            //

            if(jr[2].isSelected()){
                temperatureConvert();
                return;
            }


        }// if submit


    }


    /****************************************************************
     * main method
     */
    public static void main(String[] args) throws IOException {

        // Set the look and feel. UIManager to make
        // sure the interface will run and display as what you
        // see now with no distortion on any platform.
        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        } catch(Exception e) {}

        // create a new instance of converter class
        Converter myConverter = new Converter();
    }
}

