/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditnumbervalidator;

import static javafx.application.Application.launch;
import java.io.FileInputStream;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

/**
 *
 * @author jeremy
 * 
 * issues:
 * 
 * No base JDK:
 * Package will use system JRE:
 * Executing /Users/jeremy/NetBeansProjects/CreditNumberValidator/dist/run2080461087/CreditNumberValidator.jar
 * using platform /Library/Java/JavaVirtualMachines/jdk1.8.0_211.jdk/Contents/Home/jre/bin/java
 * 
 * build & clean:
 * Caused by: java.io.FileNotFoundException: ./icons/search.png (No such file or directory)
 * imbed images?
 * 
 * platform change to windows / submission?
 * 
 */
public class CreditNumberValidator extends Application
{
    
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // NODES:
        // label:
        Text text = new Text("Enter Credit Card Number to Validate:");
        text.setFill(Color.BLACK);
        text.setStyle("-fx-font: 18 arial;");
        // text field:
        TextField tf = new TextField();
        tf.setPrefWidth(400);
        // button:
        Button button = new Button("Validate");
        button.setMaxSize(575, 225);
        button.setStyle("-fx-background-color: #88dd88; -fx-font: 22 arial; -fx-border: 33; -fx-border-color:black; fx-border-width: 0 3 3 0;");
        // images:
        Image searchIcon = new Image("/icons/search.png");
        Image amexIcon = new Image("/icons/amex.png");
        Image masterIcon = new Image("/icons/master.png");
        Image visaIcon = new Image("/icons/visa.png");
        Image discoverIcon = new Image("/icons/discover.png");
        Image invalidIcon = new Image("/icons/invalid.png");
        ImageView iv = new ImageView(searchIcon);
        iv.setFitWidth(280);
        iv.setFitHeight(180);

        // PANE:
        AnchorPane aPane = new AnchorPane(text, tf, button, iv);
        // anchor sets:
        AnchorPane.setTopAnchor(text, 65.0);
        AnchorPane.setLeftAnchor(text, 70.0);
        AnchorPane.setTopAnchor(tf, 120.0);
        AnchorPane.setLeftAnchor(tf, 25.0);
        AnchorPane.setBottomAnchor(button, 70.0);
        AnchorPane.setLeftAnchor(button, 170.0);
        AnchorPane.setTopAnchor(iv, 55.0);
        AnchorPane.setRightAnchor(iv, 35.0);
        
        // SCENE:
        Scene scene = new Scene(aPane, 800, 300);
        scene.setFill(Color.BEIGE);

        // STAGE:
        primaryStage.setTitle("Card Validator");
        primaryStage.setScene(scene);
        primaryStage.show();

        button.setOnAction((ActionEvent event) ->
        {
            // VALID AMEX:      3782 8224 6310 005
            //                  3714 4963 5398 431
            //                  3787 3449 3671 000
            // VALID MASTER:    5500 0000 0000 0004
            //                  5105 1051 0510 5100
            //                  5555 5555 5555 4444
            // VALID VISA:      4111 1111 1111 1111
            //                  4012 8888 8888 1881
            //                  4222 2222 2222 2
            // VALID DISCOVER:  6011 0000 0000 0004
            //                  6011 1111 1111 1117
            //                  6011 0009 9013 9424
            
            // logic block:
            if (tf.getText().length() < 13 || tf.getText().length() > 16)
            {
                System.out.println("Invalid card number LENGTH.");
                iv.setImage(invalidIcon);
            }
            else if (isValid(tf.getText()))
            {
                if (startsWith(tf.getText(), "4"))
                {
                    iv.setImage(visaIcon);
                }
                else if (startsWith(tf.getText(), "5"))
                {
                    iv.setImage(masterIcon);
                }
                else if (startsWith(tf.getText(), "37"))
                {
                    iv.setImage(amexIcon);
                }
                else if (startsWith(tf.getText(), "6"))
                {
                    iv.setImage(discoverIcon);
                }
                else
                {
                    iv.setImage(invalidIcon);
                    System.out.println("Unknown credit card company encountered.");
                }
            }
            else
            {
                iv.setImage(invalidIcon);
                System.out.println("INVALID card number.");
            }
        });

    }

    // returns true if valid card number:
    public static boolean isValid(String cardNumber)
    {
        return (sumOfDoubleEvenPlace(cardNumber) + sumOfOddPlace(cardNumber)) % 10 == 0;
    }

    // returns result of step 2:
    private static int sumOfDoubleEvenPlace(String cardNumber)
    {
        int evenSum = 0;
        int digitPtr = cardNumber.length() - 2;

        while (digitPtr >= 0)
        {
            evenSum += getDigit(2 * Character.getNumericValue(cardNumber.charAt(digitPtr)));
            digitPtr -= 2;
        }

        return evenSum;
    }

    // step 1:
    // returns number if single digit:
    // else returns sum of two digits:
    private static int getDigit(int number)
    {
        if (number < 10)
        {
            return number;
        }
        else
        {
            return (number % 10) + (number % 100 / 10);
        }

    }

    // returns result of step 3:
    private static int sumOfOddPlace(String cardNumber)
    {
        int oddSum = 0;
        int digitPtr = cardNumber.length() - 1;

        while (digitPtr >= 0)
        {
            oddSum += Character.getNumericValue(cardNumber.charAt(digitPtr));
            digitPtr -= 2;
        }

        return oddSum;
    }

    // returns true if substr is the prefix for card number:
    public static boolean startsWith(String cardNumber, String subStr)
    {
        return (cardNumber.startsWith(subStr));
    }

}