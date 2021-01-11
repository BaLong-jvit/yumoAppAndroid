package com.phuoclong.yumo.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.phuoclong.yumo.Activity.MainActivity;
import com.phuoclong.yumo.Model.LichSu;
import com.phuoclong.yumo.R;
import com.phuoclong.yumo.fragment.Fragment_Product;
import com.phuoclong.yumo.fragment.Fragment_chiTiet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<LichSu> danhsach_damua;

    public HistoryAdapter(Context context, int layout, ArrayList<LichSu> danhsach_damua) {
        this.context = context;
        this.layout = layout;
        this.danhsach_damua = danhsach_damua;
    }

    @Override
    public int getCount() {
        return danhsach_damua.size();
    }

    @Override
    public Object getItem(int position) {
        return danhsach_damua.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolderHistory  {
        public Button chitiet,datlai;
        public TextView textView_ten, textView_gia, textView_soluong, textView_tinhtrang;
        public ImageView imageView_hinh;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderHistory viewHolderHistory = null;
        if (convertView==null){
            viewHolderHistory = new ViewHolderHistory();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            viewHolderHistory.chitiet = (Button) convertView.findViewById(R.id.btn_chitiet);
            viewHolderHistory.datlai = (Button) convertView.findViewById(R.id.btn_datlai);
            viewHolderHistory.imageView_hinh = (ImageView) convertView.findViewById(R.id.img_sanphamdamua);
            viewHolderHistory.textView_ten = (TextView) convertView.findViewById(R.id.txt_tensanphamdamua);
            viewHolderHistory.textView_gia = (TextView) convertView.findViewById(R.id.txt_giaSanphamdamua);
            viewHolderHistory.textView_soluong = (TextView) convertView.findViewById(R.id.txt_soluong);
            viewHolderHistory.textView_tinhtrang = (TextView) convertView.findViewById(R.id.txt_trangthai_thanhtoan);
            convertView.setTag(viewHolderHistory);
        }else {
            viewHolderHistory = (ViewHolderHistory) convertView.getTag();
        }
        String displayName= (danhsach_damua.get(position).getTenSanPham().length()>40)?danhsach_damua.get(position).getTenSanPham().substring(0,40)
                :danhsach_damua.get(position).getTenSanPham();
        String tinhtrang = danhsach_damua.get(position).getTrangThaiVanDon();
        viewHolderHistory.textView_ten.setText(displayName);
        viewHolderHistory.textView_gia.setText(MainActivity.formartMoney( danhsach_damua.get(position).getTongTienDonHang()));
        viewHolderHistory.textView_soluong.setText(danhsach_damua.get(position).getSoluong()+"");
        viewHolderHistory.textView_tinhtrang.setText(tinhtrang);
        Picasso.with(context).load(danhsach_damua.get(position).getHinhAnhSp()).into(viewHolderHistory.imageView_hinh);
        viewHolderHistory.datlai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("activity", "home");
                bundle.putString("SanPhamCoID", danhsach_damua.get(position).getIdSanPham());
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment_Product fragment_product = new Fragment_Product();
                fragment_product.setArguments(bundle);
                fragmentTransaction.add(R.id.framelayout_activiti_thuong,fragment_product,"firt product");
                fragmentTransaction.addToBackStack("product");
                MainActivity.frameLayout_trangChu.setVisibility(View.INVISIBLE);
                MainActivity.frameLayout_activityThuong.setVisibility(View.VISIBLE);
                fragmentTransaction.commit();


            }
        });
        viewHolderHistory.chitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("idDonhang", danhsach_damua.get(position).getIDPhieuXuatChoCTV());
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment_chiTiet fragment_chiTiet = new Fragment_chiTiet();
                fragment_chiTiet.setArguments(bundle);
                fragmentTransaction.add(R.id.framelayout_activiti_thuong,fragment_chiTiet);
                fragmentTransaction.addToBackStack("product");
                MainActivity.frameLayout_trangChu.setVisibility(View.INVISIBLE);
                MainActivity.frameLayout_activityThuong.setVisibility(View.VISIBLE);
                fragmentTransaction.commit();
//                Intent intent = new Intent(context, ChitietActivity.class);
//                intent.putExtra("idDonhang", lichsu.getIDPhieuXuatChoCTV());
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
