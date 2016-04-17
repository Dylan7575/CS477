package com.example.dylan.cs477;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

public class ShoppingCart extends Activity {

    private List<Product> mCartList;
    private ProductAdapter mProductAdapter;
    private int possy=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        final int pos=getIntent().getExtras().getInt(ShoppingCartHelper.PRODUCT_INDEX);
        mCartList = ShoppingCartHelper.getCartList();
        final String h=getIntent().getExtras().getString("a");
        // Make sure to clear the selections
        for(int i=0; i<mCartList.size(); i++) {
            mCartList.get(i).selected = false;
        }
                    // Create the list
        final ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        mProductAdapter = new ProductAdapter(mCartList, getLayoutInflater(), true,false);
        listViewCatalog.setAdapter(mProductAdapter);

        listViewCatalog.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent productDetailsIntent = new Intent(getBaseContext(), ProductDetails.class);

                List <Product> f=ShoppingCartHelper.getCatalog(getResources(),h);
                for(int i=0;i<f.size();i++){
                    Product g= f.get(i);
                    for (int j=0;j<mCartList.size();j++) {
                        if (mCartList.get(position) == g) {
                            possy = i;
                        }


                    }

                }productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX, possy);
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
        NumberFormat f=NumberFormat.getCurrencyInstance();

        TextView productPriceTextView = (TextView) findViewById(R.id.TextViewSubtotal);
        productPriceTextView.setText("Subtotal: " + f.format(subTotal));
    }

}