package com.programele.nibble;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class RecipesAdapter extends BaseAdapter {

    ArrayList<Recipe> dataList;
    Activity activity;


    public RecipesAdapter(ArrayList<Recipe> d, Activity a)
    {
        dataList = d;
        activity = a;
    }

    @Override
    public int getCount() {
        if (dataList != null)
        {
            return dataList.size();
        }
        return 0;
    }

    public long getItemId (int position) { return position; }

    public Object getItem(int position)
    {
        if(dataList != null) {
            return dataList.get(position);
        }
        return null;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        View vi = convertView;
        if(vi == null)
        {
            LayoutInflater li = LayoutInflater.from(activity);
            vi = li.inflate(R.layout.recipe_list_layout, null);
        }


        TextView title = (TextView) vi.findViewById(R.id.teextRecipeTitleText);
        ImageView image = (ImageView) vi.findViewById(R.id.imageView);
        TextView ingredients = (TextView) vi.findViewById(R.id.textRecipeIngredientsText);
        TextView description = (TextView) vi.findViewById(R.id.textRecipeDescriptionText);
        Recipe re = dataList.get(position);




        title.setText(re.getTitle());
        String photo = re.getPhoto();
        Uri photoUri = Uri.parse(photo);
        Picasso.with(activity).load(photoUri).into(image);
        ingredients.setText(re.getIngredients_list());
        description.setText(re.getDescription());

        return vi;
    }
}
