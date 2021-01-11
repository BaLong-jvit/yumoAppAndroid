package com.phuoclong.yumo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phuoclong.yumo.Activity.MainActivity;
import com.phuoclong.yumo.Model.Product;
import com.phuoclong.yumo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context context;
    private int layout_item;
    private ArrayList<Product> listProduct;
    private OnCallBack listener;

    public ProductAdapter(Context context, int layout_item, ArrayList<Product> listProduct, OnCallBack listener) {
        this.context = context;
        this.layout_item = layout_item;
        this.listProduct = listProduct;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layout_item,parent,false);
        return new ViewHolder(itemView);
    }
    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, gia, giacu, daban, thuonghieu, bonus;
        ImageView avatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name =(TextView)itemView.findViewById(R.id.txt_tensanpham);
            gia = (TextView) itemView.findViewById(R.id.gia_SanPham);
            giacu = (TextView) itemView.findViewById(R.id.gia_SanPhamCu);
            daban = (TextView) itemView.findViewById(R.id.SanPhamDaBan);
            avatar= (ImageView) itemView.findViewById(R.id.imBtn_hinhanh);
            thuonghieu = (TextView) itemView.findViewById(R.id.text_thuonghieu);
            bonus = (TextView) itemView.findViewById(R.id.bonus);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(getPosition());
                }
            });
        }
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product=listProduct.get(position);
        String ten_sanpham = (product.getTenLoaiSanPham().length()<22)?product.getTenLoaiSanPham()
                :product.getTenLoaiSanPham().substring(0,22)+"...";
        MainActivity.random = new Random();
        int a = MainActivity.random.nextInt(10)*100+MainActivity.random.nextInt(10)*10+MainActivity.random.nextInt(10);
        holder.name.setText(ten_sanpham);
        holder.gia.setText(MainActivity.formartMoney(product.getGiaBanLe()));
//        "+ MainActivity.formartMoney(Math.round(product.getGiaBanSanPham())+"")+"
        String giacu = MainActivity.formartMoney(product.getGiaBanSanPham());
        SpannableString spannableString = new SpannableString(giacu);
        spannableString.setSpan(new StrikethroughSpan(),0,giacu.length(),0);
        holder.giacu.setText(spannableString);
        holder.daban.setText(a+"");
        String thuonghieu = (product.getThuongHieu().length()<10)?product.getThuongHieu()
                :product.getThuongHieu().substring(0,10)+"...";
        holder.thuonghieu.setText(thuonghieu);
        Picasso.with(context).load(product.getHinhAnh())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.avatar);
        if(MainActivity.isUser) {
            holder.bonus.setText( "Bonus: "+MainActivity.formartMoney(product.getHoaHongCTV())+"đ");
        }else {
            holder.bonus.setText("");
        }
    }
    public interface OnCallBack{
        void onItemClicked(int position);
    }
}