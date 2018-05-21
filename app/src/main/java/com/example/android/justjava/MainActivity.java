/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private int quantity = 1;
    private double PRICE_PER_COFFEE = 5.00;
    private double PRICE_FOR_CHOCOLATE = 2.00;
    private double PRICE_FOR_CREAM = 1.00;
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        setName(view);
        CheckBox checkBoxForWhippedCream = findViewById(R.id.Whipped_cream_checkbox);
        boolean hasWhippedCream = checkBoxForWhippedCream.isChecked();
        CheckBox checkboxForChocolate = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = checkboxForChocolate.isChecked();
        double price = calculatePrice(hasWhippedCream, hasChocolate);
        createOrderSummary(price, hasWhippedCream, hasChocolate );
        //displayMessage(priceMessage);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryView = (TextView) findViewById(R.id.order_summary_view);
//        orderSummaryView.setText(message);
//    }

    public void increment(View view){
        if (quantity == 100){
            Toast.makeText(this, "You cannot order more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        display(quantity);
    }

    public void decrement(View view){
        if (quantity == 1){
            Toast.makeText(this, "You cannot order less than 1 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        display(quantity);

    }

    /**
     * calculate the price of the order
     */
    private double calculatePrice(boolean whippedCream, boolean hasChocolate) {
        double eachCoffeePrice = PRICE_PER_COFFEE;
        if (whippedCream){
            eachCoffeePrice += PRICE_FOR_CREAM;
        }
        if (hasChocolate){
            eachCoffeePrice += PRICE_FOR_CHOCOLATE;
        }
        return quantity * eachCoffeePrice;
    }

    private void createOrderSummary(double price, boolean whippedCream, boolean hasChocolate){
        String message = "Name: " + name;
        message += "\nadded whipped cream: " + whippedCream;
        message += "\nadded chocolate: " + hasChocolate;
        message += "\nQuantity: " + quantity + "\nTotal: $" + price
                + "\nThank You!";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    private void setName(View view){
        EditText nameText = (EditText) findViewById(R.id.name_text_field);
        name = nameText.getText().toString();
    }
}