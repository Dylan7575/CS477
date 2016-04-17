package com.example.dylan.cs477;

/**
 * Created by Dylan on 4/14/2016.
 */

import android.content.res.Resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ShoppingCartHelper {

    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static List<Product> catalog;
    private static Map<Product, ShoppingCartEntry> cartMap = new HashMap<Product, ShoppingCartEntry>();
    public static List<Product> getCatalog(Resources res,String restaurantName){
         int doasize=2;
         int switchsize=2;
         int watchmensize=5;
            if (catalog==null) {
                catalog = new Vector<Product>();
                if (restaurantName.equals("Dead or Alive")) {
                    catalog.add(new Product("Dead or Alive", res
                            .getDrawable(R.mipmap.ic_launcher),
                            "Dead or Alive by Tom Clancy with Grant Blackwood", 29.99));
                    catalog.add(new Product("Dead or Alive", res
                            .getDrawable(R.mipmap.ic_launcher),
                            "Dead or Alive by Tom Clancy with Grant Blackwood", 29.99));
                    catalog.add(new Product("Dead or Alive", res
                            .getDrawable(R.mipmap.ic_launcher),
                            "Dead or Alive by Tom Clancy with Grant Blackwood", 29.99));
                }
                if (restaurantName.equals("Switch")) {
                    catalog.add(new Product("Switch", res
                            .getDrawable(R.mipmap.ic_launcher),
                            "Switch by Chip Heath and Dan Heath", 24.99));
                    catalog.add(new Product("Switch", res
                            .getDrawable(R.mipmap.ic_launcher),
                            "Switch by Chip Heath and Dan Heath", 24.99));
                    catalog.add(new Product("Switch", res
                            .getDrawable(R.mipmap.ic_launcher),
                            "Switch by Chip Heath and Dan Heath", 24.99));

                }
                if (restaurantName.equals("Watchmen")) {

                    catalog.add(new Product("Watchmen1", res
                            .getDrawable(R.mipmap.ic_launcher),
                            "Watchmen by Alan Moore and Dave Gibbons", 14.99));
                    catalog.add(new Product("Watchmen2", res
                            .getDrawable(R.mipmap.ic_launcher),
                            "Watchmen by Alan Moore and Dave Gibbons", 14.99));
                    catalog.add(new Product("Watchmen3", res
                            .getDrawable(R.mipmap.ic_launcher),
                            "Watchmen by Alan Moore and Dave Gibbons", 14.99));
                    catalog.add(new Product("Watchmen4", res
                            .getDrawable(R.mipmap.ic_launcher),
                            "Watchmen by Alan Moore and Dave Gibbons", 14.99));
                    catalog.add(new Product("Watchmen5", res
                            .getDrawable(R.mipmap.ic_launcher),
                            "Watchmen by Alan Moore and Dave Gibbons", 14.99));
                    catalog.add(new Product("Watchmen6", res
                            .getDrawable(R.mipmap.ic_launcher),
                            "Watchmen by Alan Moore and Dave Gibbons", 14.99));
                }
            }
       else {
                for(int j=0;j<catalog.size()-1;j++) {
                    if (restaurantName.equals("Dead or Alive") && catalog.get(j).title != ("Dead or Alive")) {
                        for (int i = catalog.size()-1; i >=0; i--) {
                            if(catalog.get(i)!=null) {
                                if (i <= doasize) {
                                    catalog.set(i, new Product("Dead or Alive", res
                                            .getDrawable(R.mipmap.ic_launcher),
                                            "Dead or Alive by Tom Clancy with Grant Blackwood", 29.99));
                                }
                                else catalog.remove(i);
                            }


                            }for(int i =catalog.size();i<doasize;i++){
                                catalog.add(new Product("Dead or Alive", res
                                        .getDrawable(R.mipmap.ic_launcher),
                                        "Dead or Alive by Tom Clancy with Grant Blackwood", 29.99));
                        }

                    }
                    if (restaurantName.equals("Switch") && catalog.get(j).title != ("Switch")) {

                        for (int i = catalog.size()-1; i >= 0; i--) {
                            if (catalog.get(i)!=null) {
                                if (i <= switchsize) {
                                    catalog.set(i, new Product("Switch", res
                                            .getDrawable(R.mipmap.ic_launcher),
                                            "Switch by Chip Heath and Dan Heath", 24.99));
                                } else {
                                    catalog.remove(i);
                                }
                            }
                        }
                        for(int i=catalog.size();i<switchsize;i++) {
                            catalog.add(new Product("Switch", res
                                    .getDrawable(R.mipmap.ic_launcher),
                                    "Switch by Chip Heath and Dan Heath", 24.99));
                        }



                    }

                    if (restaurantName.equals("Watchmen") && catalog.get(j).title != ("Watchmen")&& catalog.get(j).title != ("Watchmen1")&& catalog.get(j).title != ("Watchmen2")
                            && catalog.get(j).title != ("Watchmen3")&& catalog.get(j).title != ("Watchmen4")&& catalog.get(j).title != ("Watchmen5")&& catalog.get(j).title != ("Watchmen6")) {
                        for (int i = catalog.size()-1; i >=0; i--) {
                            if(catalog!=null) {
                                if (i <= watchmensize) {
                                    catalog.set(i, new Product("Watchmen", res
                                            .getDrawable(R.mipmap.ic_launcher),
                                            "Watchmen by Alan Moore and Dave Gibbons", 14.99));
                                } else
                                    catalog.remove(i);
                            }
                        }
                        for(int i = catalog.size();i<=watchmensize;i++){
                            catalog.add( new Product("Watchmen", res
                                    .getDrawable(R.mipmap.ic_launcher),
                                    "Watchmen by Alan Moore and Dave Gibbons", 14.99));
                        }
                    }
                }
            }return catalog;
    }

    public static void setQuantity(Product product, int quantity) {
        // Get the current cart entry
        ShoppingCartEntry curEntry = cartMap.get(product);

        // If the quantity is zero or less, remove the products
        if(quantity <= 0) {
            if(curEntry != null)
                removeProduct(product);
            return;
        }

        // If a current cart entry doesn't exist, create one
        if(curEntry == null) {
            curEntry = new ShoppingCartEntry(product, quantity);
            cartMap.put(product, curEntry);
            return;
        }

        // Update the quantity
        curEntry.setQuantity(quantity);
    }

    public static int getProductQuantity(Product product) {
        // Get the current cart entry
        ShoppingCartEntry curEntry = cartMap.get(product);

        if(curEntry != null)
            return curEntry.getQuantity();

        return 0;
    }

    public static void removeProduct(Product product) {
        cartMap.remove(product);
    }

    public static List<Product> getCartList() {
        List<Product> cartList = new Vector<Product>(cartMap.keySet().size());
        for(Product p : cartMap.keySet()) {
            cartList.add(p);
        }

        return cartList;
    }


}