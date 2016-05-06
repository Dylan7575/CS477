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

        Product whisky =new Product("Whiskey and Coke", res
                .getDrawable(R.mipmap.jack),
                "On the Rocks with your choice of Jack Daniels or Evan Williams",4.00);
        Product coke=new Product("Coke", res
                .getDrawable(R.mipmap.cok),
                "The Original", 2.00);
        Product screwdriver=new Product("Screwdriver", res
                .getDrawable(R.mipmap.screwdriver),
                "Vodka and orange juice", 6.00);
        Product Tequila=new Product("Tequila", res
                .getDrawable(R.mipmap.tequila),"Shots",3.00);
        Product coorsLight =new Product("Coors Light", res
                .getDrawable(R.mipmap.can),
                "The Silver Bullet", 2.00);
        Product MolotovCocktail =new Product("Molotov Cocktail", res
                .getDrawable(R.mipmap.mol),
                "Fireball and vodka", 4.00);
        Product Dude=new Product("White Russian", res
                .getDrawable(R.mipmap.kahlua),
                "That's just like your opinion man", 6.00);
        Product Fireball =(new Product("Fireball", res
                .getDrawable(R.mipmap.fireball),
                "2 Shots all included", 8.00));
        Product MountainDew =new Product("Mountain Dew", res
                .getDrawable(R.mipmap.dew),
                "Do the Dew", 1.00);
        List<Product> Maloney = new Vector<Product>();
        Maloney.add(Tequila);
        Maloney.add(coorsLight);
        Maloney.add(MolotovCocktail);
        List<Product> Collins = new Vector<Product>();
        Collins.add(whisky);
        Collins.add(coke);
        Collins.add(screwdriver);
        List<Product>Monsoon = new Vector<Product>();
        Monsoon.add(Dude);
        Monsoon.add(Fireball);
        Monsoon.add(MountainDew);
            if (catalog==null) {
                catalog = new Vector<Product>();
                if (restaurantName.equals("Collins")) {
                        catalog.add(whisky);
                        catalog.add(coke);
                        catalog.add(screwdriver);

                }
                if (restaurantName.equals("Maloney's")) {
                    catalog.add(Tequila);
                    catalog.add(coorsLight);
                    catalog.add(MolotovCocktail);
                }
                if (restaurantName.equals("Monsoon's")) {
                    catalog.add(Dude);
                    catalog.add(Fireball);
                    catalog.add(MountainDew);
                }
            }
       else {
                if (restaurantName.equals("Monsoon's")){
                    for(int i=0;i<catalog.size();i++){
                        if(catalog.get(i).title!=Monsoon.get(i).title) {
                            catalog.set(i, Monsoon.get(i));
                        }
                    }

                }
                if (restaurantName.equals("Maloney's")){
                    for(int i=0;i<catalog.size();i++){
                        if(catalog.get(i).title!=Maloney.get(i).title) {
                            catalog.set(i, Maloney.get(i));
                        }
                    }
                }
                if (restaurantName.equals("Collins")){
                    for(int i=0;i<catalog.size();i++){
                        if(catalog.get(i).title!=Collins.get(i).title) {
                            catalog.set(i, Collins.get(i));
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