package com.example.android.justjava;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.text.NumberFormat;

import static android.R.id.message;
import static android.R.id.toggle;
import static android.content.Intent.EXTRA_SUBJECT;
import static android.content.Intent.EXTRA_TEXT;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int numberOfCoffees=1;
    boolean whippedCream = false;
    boolean choco = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void composeOrderEmail(View view) {
        String summaryString = "";
        summaryString = addTotal(addQuantity(addAddons(addCustomerName(summaryString))));
        summaryString += "~~Thank you!~~"+"\n";

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("*/*");
        emailIntent.putExtra(EXTRA_SUBJECT,addCustomerName("JustJava order for "));
        emailIntent.putExtra(EXTRA_TEXT,summaryString);

        if(emailIntent.resolveActivity(getPackageManager()) != null)
            startActivity(emailIntent);


//        displayMessage(summaryString); //displays the string below the ORDER SUMMARY in UI
   }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_value);
        quantityTextView.setText("" + number);
    }
    /**
     * These methods will increase or decrease the quantities of coffee accordingly
     */
    public void incrementOrder(View view) {
        if(numberOfCoffees == 100) return;
        else numberOfCoffees++;
        display(numberOfCoffees);
    }
    public void decrementOrder(View view) {
        if(numberOfCoffees == 1) return;
        else numberOfCoffees--;
        display(numberOfCoffees);
    }
//    /**
//     * This method displays the given text on the screen.
//     */
//    private void displayMessage(String summary) {
//        TextView priceTextView = (TextView) findViewById(R.id.summary_content_text);
//        priceTextView.setText(summary);
//    }
    /*
    * This method will determine if the user wants whipped cream or NO whipped cream
    * aka (checked for yes/true or blank for oppostite state
    * */
    public void onWhippedCheckBoxClicked(View view) {
        whippedCream = !whippedCream;
    }
    public void onChocoCheckBoxClicked(View view) {
        choco = !choco;
    }
    /*
    * obtain the text from the user input at EditText
    * add the text from edittext to order summary
    */
    public String addCustomerName( String stringList)
    {
        TextView nameTextView = (TextView) findViewById(R.id.name_text_view);
        return stringList += "--NAME: " + nameTextView.getText().toString() + "\n";
    }
    /**
     * add the addon text to the order summary
     */
    public String addAddons(String stringList) {
        stringList += "--ADDONS: ";
        if(whippedCream) {
            stringList += "Whipped Cream";
            stringList += "\n";
        }
        if(choco) {
            stringList += "Choocolate";
            stringList += "\n";
        }
        return stringList;
    }
    /*
    * Method will add the quantity of coffees to the order Summary
    * */
    public String addQuantity(String stringList){
        stringList += "--QUANTITY: " + numberOfCoffees +"\n";
        return stringList;
    }
    /*
   * Method will add the total price to order summary
   * */
    public String addTotal(String stringList){
        int price =5*numberOfCoffees;
        if(whippedCream == true && choco == false)
            price = 6*numberOfCoffees;
        else if(whippedCream == false && choco == true)
            price = 7*numberOfCoffees;
        else if(whippedCream&&choco)
            price = 8*numberOfCoffees;

        return stringList+= "--TOTAL: $" + price + "\n";
    }



}