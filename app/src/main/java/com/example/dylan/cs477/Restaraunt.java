package com.example.dylan.cs477;

/**
 * Created by Dylan on 4/15/2016.
 */
import android.graphics.drawable.Drawable;

public class Restaraunt {

    public String Name;
    public Drawable RestarauntImage;
    public String description;
    public boolean selected;

    public Restaraunt(String title, Drawable productImage, String description) {
        this.Name = title;
        this.RestarauntImage = productImage;
        this.description = description;

    }

}