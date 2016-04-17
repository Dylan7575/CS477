package com.example.dylan.cs477;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Catalog extends Activity {

    private List<Product> mProductList;
    String restaurantName;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        List<Restaraunt> catalog = RestarauntHelper.getCatalog(getResources());
        final int productIndex = getIntent().getExtras().getInt(
                ShoppingCartHelper.PRODUCT_INDEX);
        final Restaraunt selectedProduct = catalog.get(productIndex);
        restaurantName=selectedProduct.Name;

        List<Product>mcartlist=ShoppingCartHelper.getCartList();
        if(ShoppingCartHelper.getCartList()!=null) {
            for (int i = 0; i < mcartlist.size(); i++) {
                Product ShopRemove = mcartlist.get(i);
                ShoppingCartHelper.removeProduct(ShopRemove);
                TextView u = (TextView) findViewById(R.id.ProdCat);
                // Intent productDetailsIntent = new Intent(getBaseContext(), ShoppingCart.class);
                //productDetailsIntent.putExtra("A", restaurantName);
                //startActivity(productDetailsIntent);

            }
        }
        // Obtain a reference to the product catalog
        mProductList = ShoppingCartHelper.getCatalog(getResources(),restaurantName);

        for(int i=0; i<mProductList.size(); i++) {
            mProductList.get(i).selected = false;
        }

        // Create the list
        ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        listViewCatalog.setAdapter(new ProductAdapter(mProductList, getLayoutInflater(), false, false));

        listViewCatalog.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Product selectedProduct = mProductList.get(position);
                if (selectedProduct.selected) {
                    selectedProduct.selected = false;

                } else
                    selectedProduct.selected = true;

                Intent productDetailsIntent = new Intent(getBaseContext(), ProductDetails.class);
                productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX, position);
                productDetailsIntent.putExtra("A", restaurantName);
                startActivity(productDetailsIntent);
            }
        });
        Button toShoppingCart = (Button)findViewById(R.id.ButtonViewCart);
        toShoppingCart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ShoppingCart.class);
                intent.putExtra("a", restaurantName);
                startActivity(intent);
            }
        });
    }
}