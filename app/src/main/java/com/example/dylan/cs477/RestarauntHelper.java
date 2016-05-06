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
            catalog.add(new Restaraunt("Collins", res
                    .getDrawable(R.mipmap.collins),
                    ""));
            catalog.add(new Restaraunt("Maloney's", res
                    .getDrawable(R.mipmap.logo),
                    ""));
            catalog.add(new Restaraunt("Monsoon's", res
                    .getDrawable(R.mipmap.r),
                    ""));
        }

        return catalog;
    }
}