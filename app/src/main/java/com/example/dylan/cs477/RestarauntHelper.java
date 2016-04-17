package com.example.dylan.cs477;

/**
 * Created by Dylan on 4/15/2016.
 */

import android.content.res.Resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class RestarauntHelper {

    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static List<Restaraunt> catalog;
    private static Map<Product, ShoppingCartEntry> cartMap = new HashMap<Product, ShoppingCartEntry>();

    public static List<Restaraunt> getCatalog(Resources res) {
        if (catalog == null) {
            catalog = new Vector<Restaraunt>();
            catalog.add(new Restaraunt("Dead or Alive", res
                    .getDrawable(R.mipmap.ic_launcher),
                    "Dead or Alive by Tom Clancy with Grant Blackwood"));
            catalog.add(new Restaraunt("Switch", res
                    .getDrawable(R.mipmap.ic_launcher),
                    "Switch by Chip Heath and Dan Heath"));
            catalog.add(new Restaraunt("Watchmen", res
                    .getDrawable(R.mipmap.ic_launcher),
                    "Watchmen by Alan Moore and Dave Gibbons"));
        }

        return catalog;
    }
}