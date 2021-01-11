package com.phuoclong.yumo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phuoclong.yumo.Model.Collection;
import com.phuoclong.yumo.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class CollectionAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Collection> danhsach_Collection;

    public CollectionAdapter(Context context, int layout, ArrayList<Collection> danhsach_Collection) {
        this.context = context;
        this.layout = layout;
        this.danhsach_Collection = danhsach_Collection;
    }

    @Override
    public int getCount() {
        return danhsach_Collection.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolderCollection{
        TextView txt_nameCollection, txt_soluong;
        ImageView img_avatarCollection;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderCollection holderCollection;
        if (convertView==null){
            holderCollection = new ViewHolderCollection();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView= layoutInflater.inflate(layout,null);
            holderCollection.txt_nameCollection = convertView.findViewById(R.id.txt_tenBST);
            holderCollection.txt_soluong= convertView.findViewById(R.id.txt_sanPhamBSTdangCo);
            holderCollection.img_avatarCollection = convertView.findViewById(R.id.imgBtn_hinhBST);
            convertView.setTag(holderCollection);
        }else {
            holderCollection = (ViewHolderCollection) convertView.getTag();
        }
        holderCollection.txt_nameCollection.setText(danhsach_Collection.get(position).getName_collection());
        holderCollection.txt_soluong.setText(danhsach_Collection.get(position).getSoLuong()+"");
        Picasso.with(context).load(danhsach_Collection.get(position).getAvatar_collection())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holderCollection.img_avatarCollection);
        return convertView;
    }
}
