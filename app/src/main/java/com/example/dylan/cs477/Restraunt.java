package com.example.dylan.cs477;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;


public class Restraunt extends Activity {
    List<Restaraunt> mRestraunt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceState=null;
        setContentView(R.layout.activity_restraunt);
        mRestraunt = RestarauntHelper.getCatalog(getResources());
        for (int i = 0; i < mRestraunt.size(); i++) {
            mRestraunt.get(i).selected = false;
        }

        //if(ShoppingCartHelper.getCatalog(getResources(),"")!=null){}
        //List<Product>a=ShoppingCartHelper.getCatalog(getResources(),"");
        //if(a!=null) {
          //  TextView f = (TextView) findViewById(R.id.restrauntText);
          //  f.setText(a.get(0).title);
       // }
        ListView listViewCatalog = (ListView) findViewById(R.id.ListViewRestaraunt);
        listViewCatalog.setAdapter(new RestarauntAdapter(mRestraunt, getLayoutInflater(), false));
        listViewCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Restaraunt selectedProduct = mRestraunt.get(position);
                if (selectedProduct.selected) {
                    selectedProduct.selected = false;

                } else
                    selectedProduct.selected = true;
                Intent RestarauntIntent = new Intent(getBaseContext(), Catalog.class);
                RestarauntIntent.putExtra(RestarauntHelper.PRODUCT_INDEX, position);
                startActivity(RestarauntIntent);
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();





    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
