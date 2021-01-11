package com.phuoclong.yumo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phuoclong.yumo.Model.Category;
import com.phuoclong.yumo.R;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Category> listCategory;

    public CategoryAdapter(Context context, int layout, ArrayList<Category> listCategory) {
        this.context = context;
        this.layout = layout;
        this.listCategory = listCategory;
    }

    @Override
    public int getCount() {
        return listCategory.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolderCategory{
        ImageView ctgrAvatar;
        TextView ctgrName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderCategory categoryHolder;
        if(convertView == null){
            categoryHolder = new ViewHolderCategory();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout,null);
            categoryHolder.ctgrAvatar = (ImageView) convertView.findViewById(R.id.img_category);
            categoryHolder.ctgrName = (TextView) convertView.findViewById(R.id.txt_categoryName);
            convertView.setTag(categoryHolder);
        }else {
            categoryHolder =(ViewHolderCategory) convertView.getTag();
        }
        categoryHolder.ctgrName.setText(listCategory.get(position).getCtgrName());
        Picasso.with(context).load(listCategory.get(position).getCtgrImage())
                .into(categoryHolder.ctgrAvatar);
        return convertView;
    }
}
