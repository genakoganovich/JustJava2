package com.example.android.justjava2;

/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava2;
 *
 */


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

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private int quantity = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display(quantity);
    }
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name) {
        String message = getString(R.string.name_colon) + name + "\n";
        message += getString(R.string.add_whipped_cream) + hasWhippedCream + "\n";
        message += getString(R.string.add_chocolate) + hasChocolate + "\n";
        message += getString(R.string.quantity_colon) + quantity + "\n";
        message += getString(R.string.total) + price + "\n";
        message += getString(R.string.thank_you);
        return message;

    }
    private int calculatePrice() {
        boolean hasWhippedCream = ((CheckBox) findViewById(R.id.add_whipped_cream_checkbox)).isChecked();
        boolean hasChocolate = ((CheckBox) findViewById(R.id.add_chocolate_checkbox)).isChecked();
        int whippedCreamPrice = 0;
        int chocolatePrice = 0;
        if (hasWhippedCream) {
            whippedCreamPrice = 1;
        }
        if (hasChocolate) {
            chocolatePrice = 2;
        }

        return (quantity + whippedCreamPrice + chocolatePrice) * 5;
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        display(quantity);
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.add_whipped_cream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.add_chocolate_checkbox);
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);

        String name = nameEditText.getText().toString();
        String message = createOrderSummary(calculatePrice(), whippedCreamCheckBox.isChecked(), chocolateCheckBox.isChecked(), name);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + " order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    public void increment(View view) {
        if (quantity < 100) {
            quantity++;
            display(quantity);
        } else {
            Toast.makeText(this, getString(R.string.you_cant_have_more), Toast.LENGTH_SHORT).show();
        }
    }
    public void decrement(View view) {
        if(quantity > 1) {
            quantity--;
            display(quantity);
        } else {
            Toast.makeText(this, getString(R.string.you_cant_have_less), Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));
    }
    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(String message) {
        displayMessage(message);
    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }
}
