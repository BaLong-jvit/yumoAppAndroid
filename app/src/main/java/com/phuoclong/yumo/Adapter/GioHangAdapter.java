package com.phuoclong.yumo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.phuoclong.yumo.Activity.MainActivity;
import com.phuoclong.yumo.Model.GioHang;
import com.phuoclong.yumo.R;
import com.phuoclong.yumo.Until.Server;
import com.phuoclong.yumo.fragment.Fragment_GioHang;
import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<GioHang> listGioHang;

    public GioHangAdapter(Context context, int layout, ArrayList<GioHang> listGioHang) {
        this.context = context;
        this.layout = layout;
        this.listGioHang = listGioHang;
    }

    @Override
    public int getCount() {
        return listGioHang.size();
    }

    @Override
    public Object getItem(int position) {
        return listGioHang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolderGioHang{
        public ImageView imageHinhanh;
        public TextView txt_tenSanpham, txt_giasanpham, txt_size, txt_style;
        public Button btn_minus, btn_plus;
        public TextView textValues;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderGioHang viewHolderGioHang = null;
        if (convertView==null){
            viewHolderGioHang = new ViewHolderGioHang();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            viewHolderGioHang.txt_tenSanpham = convertView.findViewById(R.id.txt_tenSanpham);
            viewHolderGioHang.txt_giasanpham = convertView.findViewById(R.id.txt_tonggiaSP);
            viewHolderGioHang.imageHinhanh = convertView.findViewById(R.id.im_hinhSanpham);
            viewHolderGioHang.btn_minus = convertView.findViewById(R.id.btn_minusgio);
            viewHolderGioHang.btn_plus = convertView.findViewById(R.id.btn_plusgio);
            viewHolderGioHang.textValues = convertView.findViewById(R.id.txt_soluongcanmuagio);
            viewHolderGioHang.txt_size = convertView.findViewById(R.id.size);
            viewHolderGioHang.txt_style = convertView.findViewById(R.id.style);
            convertView.setTag(viewHolderGioHang);
        }else {
            viewHolderGioHang = (ViewHolderGioHang) convertView.getTag();
        }
        viewHolderGioHang.txt_tenSanpham.setText((listGioHang.get(position).getTenSp().length()>25)?listGioHang.get(position).getTenSp().substring(0,25)+"...":listGioHang.get(position).getTenSp());
        viewHolderGioHang.txt_giasanpham.setText(MainActivity.formartMoney(listGioHang.get(position).getTongThanhToan()));
        viewHolderGioHang.txt_size.setText(laySize(listGioHang.get(position).getSize()));
        viewHolderGioHang.txt_style.setText(layMau(listGioHang.get(position).getMau()));
        Picasso.with(context).load(listGioHang.get(position).getHinhAnhSp())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(viewHolderGioHang.imageHinhanh);
        viewHolderGioHang.textValues.setText(listGioHang.get(position).getSoluongSp()+"");
        int soluong= listGioHang.get(position).getSoluongSp();
        if(soluong >=100){
            viewHolderGioHang.btn_plus.setVisibility(View.INVISIBLE);
            viewHolderGioHang.btn_minus.setVisibility(View.VISIBLE);
        }else if(soluong <= 1){
            viewHolderGioHang.btn_minus.setVisibility(View.INVISIBLE);
            viewHolderGioHang.btn_plus.setVisibility(View.VISIBLE);
        }else {
            viewHolderGioHang.btn_plus.setVisibility(View.VISIBLE);
            viewHolderGioHang.btn_minus.setVisibility(View.VISIBLE);
        }
        final ViewHolderGioHang finalViewHolderGioHang = viewHolderGioHang;
        viewHolderGioHang.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluongmoi = Integer.parseInt(finalViewHolderGioHang.textValues.getText().toString())+1;
                if(soluongmoi>99){
                    finalViewHolderGioHang.btn_plus.setVisibility(View.INVISIBLE);
                    finalViewHolderGioHang.btn_minus.setVisibility(View.VISIBLE);
                    finalViewHolderGioHang.textValues.setText(soluongmoi+"");
                }else {
                    finalViewHolderGioHang.btn_plus.setVisibility(View.VISIBLE);
                    finalViewHolderGioHang.btn_minus.setVisibility(View.VISIBLE);
                    finalViewHolderGioHang.textValues.setText(soluongmoi+"");
                }
                float thanhToanMoi = soluongmoi * MainActivity.danhSachHangduocChon.get(position).getDonGia();
                MainActivity.danhSachHangduocChon.get(position).setSoluongSp(soluongmoi);
                MainActivity.danhSachHangduocChon.get(position).setTongThanhToan(thanhToanMoi);
                finalViewHolderGioHang.txt_giasanpham.setText(MainActivity.formartMoney(MainActivity.danhSachHangduocChon.get(position).getTongThanhToan()));
                float tongtien =0;
                for (int i=0;i<MainActivity.danhSachHangduocChon.size();i++){
                    tongtien += MainActivity.danhSachHangduocChon.get(i).getTongThanhToan();
                }
                Fragment_GioHang.txt_thanhtoan.setText(MainActivity.formartMoney(tongtien));
            }
        });

        viewHolderGioHang.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluongmoi = Integer.parseInt(finalViewHolderGioHang.textValues.getText().toString())-1;
                if(soluongmoi<2){
                    finalViewHolderGioHang.btn_plus.setVisibility(View.VISIBLE);
                    finalViewHolderGioHang.btn_minus.setVisibility(View.INVISIBLE);
                    finalViewHolderGioHang.textValues.setText(soluongmoi+"");
                }else {
                    finalViewHolderGioHang.btn_plus.setVisibility(View.VISIBLE);
                    finalViewHolderGioHang.btn_minus.setVisibility(View.VISIBLE);
                    finalViewHolderGioHang.textValues.setText(soluongmoi+"");
                }
                float thanhToanMoi = soluongmoi * MainActivity.danhSachHangduocChon.get(position).getDonGia();
                MainActivity.danhSachHangduocChon.get(position).setSoluongSp(soluongmoi);
                MainActivity.danhSachHangduocChon.get(position).setTongThanhToan(thanhToanMoi);
                finalViewHolderGioHang.txt_giasanpham.setText(MainActivity.formartMoney(MainActivity.danhSachHangduocChon.get(position).getTongThanhToan()));
                float tongtien =0;
                for (int i=0;i<MainActivity.danhSachHangduocChon.size();i++){
                    tongtien += MainActivity.danhSachHangduocChon.get(i).getTongThanhToan();
                }
                Fragment_GioHang.txt_thanhtoan.setText(MainActivity.formartMoney(tongtien));
            }
        });
        return convertView;
    }
    private String laySize(int size) {
        String Query="SELECT TenSize FROM dbo.Size WHERE IDSize="+size;
        StringBuilder sizereturn=null;
        Server server= new Server();
        Connection connection = server.connection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Query);
            if (resultSet!=null){
                while (resultSet.next()){
                    sizereturn =new StringBuilder(resultSet.getString("TenSize"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sizereturn.toString();
    }
    private String layMau(int mau) {
        String Query="SELECT TenMauSac FROM dbo.MauSac WHERE IDMauSac="+mau;
        StringBuilder MauSacReturn=null;
        Server server= new Server();
        Connection connection = server.connection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Query);
            if (resultSet!=null){
                while (resultSet.next()){
                    MauSacReturn = new StringBuilder(resultSet.getString("TenMauSac"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return MauSacReturn.toString();
    }
}
