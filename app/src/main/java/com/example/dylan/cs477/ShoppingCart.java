package com.example.dylan.cs477;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.List;

public class ShoppingCart extends Activity {
    EditText tablenum;

    private List<Product> mCartList;
    private ProductAdapter mProductAdapter;
    private int possy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        tablenum = (EditText)findViewById(R.id.Tableid);
        final int pos = getIntent().getExtras().getInt(ShoppingCartHelper.PRODUCT_INDEX);
        mCartList = ShoppingCartHelper.getCartList();
        final String h = getIntent().getExtras().getString("a");
        // Make sure to clear the selections
        for (int i = 0; i < mCartList.size(); i++) {
            mCartList.get(i).selected = false;
        }
        // Create the list

        final ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        mProductAdapter = new ProductAdapter(mCartList, getLayoutInflater(), true, false);
        listViewCatalog.setAdapter(mProductAdapter);

        listViewCatalog.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent productDetailsIntent = new Intent(getBaseContext(), ProductDetails.class);

                List<Product> f = ShoppingCartHelper.getCatalog(getResources(), h);
                for (int i = 0; i < f.size(); i++) {
                    Product g = f.get(i);
                    for (int j = 0; j < mCartList.size(); j++) {
                        if (mCartList.get(position) == g) {
                            possy = i;
                        }


                    }

                }
                productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX, possy);
                productDetailsIntent.putExtra("A", h);
                startActivity(productDetailsIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Refresh the data
        if (mProductAdapter != null) {
            mProductAdapter.notifyDataSetChanged();
        }

        double subTotal = 0;
        for (Product p : mCartList) {
            int quantity = ShoppingCartHelper.getProductQuantity(p);
            subTotal += p.price * quantity;
        }
        NumberFormat f = NumberFormat.getCurrencyInstance();

        TextView productPriceTextView = (TextView) findViewById(R.id.TextViewSubtotal);
        productPriceTextView.setText("Subtotal: " + f.format(subTotal));
    }
    public static boolean isNumeric(String s){
        try {
            double d = Double.parseDouble(s);
        }
        catch(NumberFormatException f){
            return false;
        }
        return true;
    }
    public void order(View v){
        if(!mCartList.isEmpty() && !tablenum.getText().toString().equals("") && isNumeric(tablenum.getText().toString())&&Integer.parseInt(tablenum.getText().toString())>0) {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ShoppingCart.this);
            alertDialog.setTitle("Order Confirmation");
            alertDialog.setMessage("Are you sure this is correct?");
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            });
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = mCartList.size() - 1; i >= 0; i--) {
                        ShoppingCartHelper.removeProduct(mCartList.get(i));

                        dialog.cancel();
                        final AlertDialog.Builder received = new AlertDialog.Builder(ShoppingCart.this);
                        received.setMessage("Order Received");
                        received.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        });
                        received.show();
                    }
                }

            });
            alertDialog.show();


        }
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(ShoppingCart.this).create();
            alertDialog.setTitle("Empty");
            alertDialog.setMessage("The Cart is empty or you entered the Table Number Incorrectly");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            tablenum.setText("");
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }
}