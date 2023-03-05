package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    /** Global variables */
    int quantity = 1;
    boolean AddWhippedCream;
    boolean AddChocolate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String message = createOrderSummary(quantity);
        displayMessage(message);

        /** The intent views permit me call to the other application and send some data to run in it. */
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, "alonso.bernabeb@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.orderview));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void increment(View view) {
        quantity = quantity + 1;

        if (quantity > 100){
            quantity = 100;
        }

        displayQuantity(quantity);
    }

    public void decrement(View view) {
        quantity = quantity - 1;

        if (quantity < 1){
            quantity = 1;
        }

        displayQuantity(quantity);
    }

    private int calculatePrice(int quantity, boolean vchocolate, boolean vwhipped) {
        int baseprice = quantity * 5;

        if (vwhipped && vchocolate) {
            baseprice = baseprice + 3;
        }else if (vchocolate) {
            baseprice = baseprice + 2;
        }else if (vwhipped) {
            baseprice = baseprice + 1;
        }


        /** Snackbar displays a popup alert on screen */
        Snackbar myAlert = Snackbar.make(findViewById(R.id.scrollviewlayout), R.string.message, Snackbar.LENGTH_SHORT);
        myAlert.show();

        return baseprice;
    }

    private String whippedCream(){

        String whippedCreamS;
        CheckBox creamView = (CheckBox) findViewById(R.id.twhipped);
        AddWhippedCream = creamView.isChecked();

        if(AddWhippedCream){
            whippedCreamS = getString(R.string.whippedCreamS);
        }else{
            whippedCreamS = "No";
        }

        return whippedCreamS;
    }

    private String chocolateTopping(){

        String ChocolateS;
        CheckBox chocolate = (CheckBox) findViewById(R.id.tchocolate);
        AddChocolate = chocolate.isChecked();

        if(AddChocolate){
            ChocolateS = getString(R.string.ChocolateS);
        }else{
            ChocolateS = "No";
        }

        return ChocolateS;
    }


    private String nameCustomerF(){
        EditText nameCustomer = (EditText) findViewById(R.id.namecustomer);
        return nameCustomer.getText().toString(); /** When I get value in the EditText I always need to convert to string. */
    }


    private String createOrderSummary(int quantity) {
        String hasWhippedCream = getString(R.string.twhipped, whippedCream()); /** Method getString() extracts the string values of the resources. */
        String hasChocolate = getString(R.string.tchocolate, chocolateTopping());
        String priceMessage = "Total: $ " + calculatePrice(quantity, AddChocolate, AddWhippedCream);
        String customer = getString(R.string.customer, nameCustomerF());
        String mquantity = getString(R.string.mquantity, quantity);

        String message = customer + "\n" + mquantity + "\n" + hasWhippedCream + "\n" + hasChocolate + "\n" + priceMessage + "\n" + getString(R.string.thanks);
        return message;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.value_quantity);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */

    private void displayMessage(String message){
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_view);
        priceTextView.setText(message);
    }
}