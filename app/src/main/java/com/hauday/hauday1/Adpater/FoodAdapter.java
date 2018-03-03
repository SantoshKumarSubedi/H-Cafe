package com.hauday.hauday1.Adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hauday.hauday1.POJO.Food;
import com.hauday.hauday1.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Hauday on 7/30/2016.
 */
public class  FoodAdapter extends ArrayAdapter<Food> {
    // View lookup cache
    Context context;
    private static class ViewHolder {
        ImageView mFoodImage;
        TextView mname;
        TextView mprice;
    }

    public FoodAdapter(Context context, ArrayList<Food> foods) {
        super(context, R.layout.adopter_single_listview, foods);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Food food = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.adopter_single_listview, parent, false);
            viewHolder.mFoodImage=(ImageView) convertView.findViewById(R.id.adapter_food_list_image);
            viewHolder.mname = (TextView) convertView.findViewById(R.id.adaper_food_list_name_text_view);
            viewHolder.mprice = (TextView) convertView.findViewById(R.id.adapter_food_list_price_text_view);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object

        viewHolder.mname.setText(food.getName());
        viewHolder.mprice.setText(food.getPrice());
        Glide.with(context).load(food.getImage()).into(viewHolder.mFoodImage);
        // Return the completed view to render on screen
        return convertView;
    }
}