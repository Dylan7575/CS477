package com.example.dylan.cs477;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RestarauntAdapter extends BaseAdapter {

    private List<Restaraunt> mRestarauntList;
    private LayoutInflater mInflater;
    private boolean mShowQuantity;
    private boolean mShowCheckBox;

    public RestarauntAdapter(List<Restaraunt> list, LayoutInflater inflater, boolean showCheckBox) {
        mRestarauntList = list;
        mInflater = inflater;

        mShowCheckBox =showCheckBox;
    }

    @Override
    public int getCount() {
        return mRestarauntList.size();
    }


    public Object getItem(int position) {
        return mRestarauntList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewItem item;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item, null);
            item = new ViewItem();


            item.RestarauntImageView = (ImageView) convertView
                    .findViewById(R.id.ImageViewItem);

            item.RestarauntTitle = (TextView) convertView
                    .findViewById(R.id.TextViewItem);

            item.RestarauntQuantity = (TextView) convertView
                    .findViewById(R.id.textViewQuantity);



            convertView.setTag(item);
        } else {
            item = (ViewItem) convertView.getTag();
        }

        Restaraunt curRestaraunt = mRestarauntList.get(position);

        item.RestarauntImageView.setImageDrawable(curRestaraunt.RestarauntImage);
        item.RestarauntTitle.setText(curRestaraunt.Name);
        if (!mShowQuantity) {
            // Hid the view
            item.RestarauntQuantity.setVisibility(View.GONE);
        }
        // Show the quantity in the cart or not
        return convertView;
    }

    private class ViewItem {
        ImageView RestarauntImageView;
        TextView RestarauntTitle;
        TextView RestarauntQuantity;

    }

}
