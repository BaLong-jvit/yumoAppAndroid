package com.phuoclong.yumo.Model;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.phuoclong.yumo.Activity.MainActivity;
import com.phuoclong.yumo.R;
import com.phuoclong.yumo.Until.Server;
import com.phuoclong.yumo.fragment.FragmentMainActivity;
import com.phuoclong.yumo.fragment.Fragment_GioHang;
import com.phuoclong.yumo.fragment.Fragment_Product;
import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChonSanPham extends BottomSheetDialogFragment {
    ImageView imgsp;
    TextView txt_soluongsp, txt_tensanpham, txt_giasanpham;
    Button   bntmn,bntpl,bntdone;
    EditText edtsoluong;
    private Button[] btnStyle = new Button[4];
    private Button btnStyle_unfocus;
    private int[] btnStyle_id = {R.id.btn_kieu1, R.id.btn_kieu2, R.id.btn_kieu3, R.id.btn_kieu4};
    private Button[] btnSize = new Button[8];
    private Button btnSize_unfocus;
    private int[] btnSize_id = {R.id.btn_36, R.id.btn_37, R.id.btn_38, R.id.btn_39,R.id.btn_40, R.id.btn_41, R.id.btn_42, R.id.btn_43};
    int a=0;
    int b=0;
    boolean Style = false;
    boolean Size = false;
    Product product;
    Bundle bundle = new Bundle();
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment_GioHang fragment_gioHang;
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View contextView = inflater.inflate(R.layout.layout_chonsp,null);
        Anhxa(contextView);
        assert getArguments() != null;
        product = (Product) getArguments().getSerializable("sanphamDuocChon");
        if(getArguments().getBoolean("thanhtoan")){
            bntdone.setText("MUA NGAY");
        }else bntdone.setText("THÊM VÀO GIỎ HÀNG");
        Picasso.with(getContext()).load(product.getHinhAnh()).into(imgsp);
//        txt_soluongsp.setText("Sản phẩm đang có:\n "+ getArguments().getInt("soluongsanpham_conlai"));
        txt_tensanpham.setText(product.getTenLoaiSanPham());
        txt_giasanpham.setText(MainActivity.formartMoney(product.getGiaBanLe()));

        ControlAvtivity(contextView);
        return contextView;
    }

    private void ControlAvtivity(final View contextView) {
        checkStyle(contextView);
        checkSize(contextView);
        bntmn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(edtsoluong.getText().toString())>1){
                    edtsoluong.setText((Integer.parseInt(edtsoluong.getText().toString())-1)+"");
                }else edtsoluong.setText("1");
            }
        });
        bntpl.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(edtsoluong.getText().toString())<100){
                    edtsoluong.setText((Integer.parseInt(edtsoluong.getText().toString())+1)+"");
                }else edtsoluong.setText("100");
            }
        });
        bntdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a==0||b==0){
                    String nhacnho = (a==0&&b==0)?"Vui lòng chọn kích cỡ và kiểu!":(b==0)?"Vui lòng chọn kích cỡ!":"Vui lòng chọn kiểu!";
                    Toast.makeText(getContext(),nhacnho,Toast.LENGTH_SHORT).show();
                }else {
                    String idngdar = "0";

                    int soLuong = Integer.parseInt(edtsoluong.getText().toString());
                    if (MainActivity.danhSachHangduocChon.size() > 0){
                        boolean thoat = false;
                        for(int i = 0; i<MainActivity.danhSachHangduocChon.size();i++){
                            if(MainActivity.danhSachHangduocChon.get(i).getIdSP().equals(product.getIDloaiSanPham())
                                    && a==MainActivity.danhSachHangduocChon.get(i).getMau()
                                    && b==MainActivity.danhSachHangduocChon.get(i).getSize())
                            {
                                int soluongmoi = ((MainActivity.danhSachHangduocChon.get(i).getSoluongSp()+soLuong) >=100)?100 :(MainActivity.danhSachHangduocChon.get(i).getSoluongSp()+soLuong);
                                MainActivity.danhSachHangduocChon.get(i).setSoluongSp(soluongmoi);
                                MainActivity.danhSachHangduocChon.get(i).setTongThanhToan(soluongmoi*product.getGiaBanLe());
                                thoat = true;
                            }
                        }
                        if (thoat == false) {

                            MainActivity.danhSachHangduocChon.add(new GioHang(
                                    product.getIDloaiSanPham(),
                                    product.getHinhAnh(),
                                    product.getTenLoaiSanPham(),
                                    idngdar, soLuong, product.getGiaBanLe(),
                                    product.getHoaHongCTV(),  b,a,
                                    soLuong*product.getGiaBanLe())
                            );
                        }
                    } else {
                        MainActivity.danhSachHangduocChon.add(new GioHang(
                                product.getIDloaiSanPham(),
                                product.getHinhAnh(),
                                product.getTenLoaiSanPham(),
                                idngdar, soLuong, product.getGiaBanLe(),
                                product.getHoaHongCTV(),  b, a,
                                soLuong*product.getGiaBanLe())
                        );
                    }
                    if (getArguments().getBoolean("thanhtoan")) {
                        dismiss();
                        bundle.putString("activity","ProductActivity");
                        fragmentManager =getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragment_gioHang = new Fragment_GioHang();
                        fragment_gioHang.setArguments(bundle);
                        fragmentTransaction.add(R.id.framelayout_activiti_thuong,fragment_gioHang,"gio hang");
                        fragmentTransaction.commit();
                    } else {
                        dismiss();
                    }
                    Fragment_Product.soluonghang.setText(FragmentMainActivity.getcounthang(MainActivity.danhSachHangduocChon)+"");
                }
            }
        });

    }

    private String getidsanpham(String idLoaiSanPham, int a, int b) {
        StringBuilder idsanpham=null;
        Server server = new Server();
        Connection connection = server.connection();
        if(connection!=null){
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT IDSanPham FROM SanPham WHERE IDLoaiSanPham ="+idLoaiSanPham+" AND IDSize ="+b+" AND IDMauSac= "+a);
                if(resultSet != null){
                    while (resultSet.next()){
                        idsanpham = new StringBuilder(resultSet.getString("IDSanPham"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return idsanpham.toString();
    }

    private void checkSize(View contextView) {
        for(int i = 0; i < btnSize.length; i++){
            btnSize[i] = (Button) contextView.findViewById(btnSize_id[i]);
            btnSize[i].setBackgroundColor(Color.rgb(207, 207, 207));
            btnSize[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.btn_36 :
                            if(setFocusSize(btnSize_unfocus, btnSize[0])){
                                b=1;
                                hienSoSpConLai(b,a);
                            }else b=0;
                            break;
                        case R.id.btn_37 :
                            if(setFocusSize(btnSize_unfocus, btnSize[1])){
                                b=2;
                                hienSoSpConLai(b,a);
                            }else b=0;
                            break;
                        case R.id.btn_38 :
                            if(setFocusSize(btnSize_unfocus, btnSize[2])){
                                b=3;
                                hienSoSpConLai(b,a);
                            }else b=0;
                            break;
                        case R.id.btn_39 :
                            if(setFocusSize(btnSize_unfocus, btnSize[3])){b=4;hienSoSpConLai(b,a);}else b=0;
                            break;
                        case R.id.btn_40 :
                            if(setFocusSize(btnSize_unfocus, btnSize[4])){b=5;hienSoSpConLai(b,a);}else b=0;
                            break;
                        case R.id.btn_41 :
                            if(setFocusSize(btnSize_unfocus, btnSize[5])){b=6;hienSoSpConLai(b,a);}else b=0;
                            break;
                        case R.id.btn_42 :
                            if(setFocusSize(btnSize_unfocus, btnSize[6])){b=7;hienSoSpConLai(b,a);}else b=0;
                            break;
                        case R.id.btn_43 :
                            if(setFocusSize(btnSize_unfocus, btnSize[7])){b=8;hienSoSpConLai(b,a);}else b=0;
                            break;
                    }
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    private void hienSoSpConLai(int b, int a) {
        if(a==0||b==0){
            txt_soluongsp.setText("Mời chọn SIZE và Màu Sắc!");
        }else {
            String query = "SELECT SoLuong FROM dbo.SoLuongSanPhamThucTe t1 INNER JOIN dbo.SanPham t2 " +
                    "ON t2.IDSanPham = t1.IDSanPham WHERE t2.IDSize ="+b+" AND t2.IDMauSac = "+a;
            int soluong=0;
            Server server = new Server();
            Connection connection = server.connection();
            if(connection!=null) {
                try {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    if (resultSet != null) {
                        while (resultSet.next()) {
                            soluong = resultSet.getInt("SoLuong");
                        }
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            txt_soluongsp.setText("Số lượng sản phẩm còn: "+soluong);
        }
    }

    private void checkStyle(final View contextView) {
        for(int i = 0; i < btnStyle.length; i++){
            btnStyle[i] = (Button) contextView.findViewById(btnStyle_id[i]);
            btnStyle[i].setBackgroundColor(Color.rgb(207, 207, 207));
            btnStyle[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.btn_kieu1 :
                            if(setFocusStyle(btnStyle_unfocus, btnStyle[0])){a =1;hienSoSpConLai(b,a);} else a=0;
                            break;
                        case R.id.btn_kieu2 :
                            if(setFocusStyle(btnStyle_unfocus, btnStyle[1])){a =2;hienSoSpConLai(b,a);} else a=0;
                            break;
                        case R.id.btn_kieu3 :
                            if(setFocusStyle(btnStyle_unfocus, btnStyle[2])){a =3;hienSoSpConLai(b,a);} else a=0;
                            break;
                        case R.id.btn_kieu4 :
                            if(setFocusStyle(btnStyle_unfocus, btnStyle[3])){a =4;hienSoSpConLai(b,a);} else a=0;
                            break;
                    }
                }
            });
        }
    }

    private boolean setFocusStyle(Button btn_unfocus, Button btn_focus){
        if (btn_unfocus == null){
            btn_focus.setTextColor(Color.rgb(255, 255, 255));
            btn_focus.setBackgroundColor(Color.rgb(3, 106, 150));
            Style = true;
        }else if(btn_unfocus==btn_focus){
            btn_focus.setTextColor(Color.rgb(49, 50, 51));
            btn_focus.setBackgroundColor(Color.rgb(207, 207, 207));
            Style = false;
        }else {
            btn_unfocus.setTextColor(Color.rgb(49, 50, 51));
            btn_unfocus.setBackgroundColor(Color.rgb(207, 207, 207));
            btn_focus.setTextColor(Color.rgb(255, 255, 255));
            btn_focus.setBackgroundColor(Color.rgb(3, 106, 150));
            Style = true;
        }
        this.btnStyle_unfocus = btn_focus;
        return Style;
    }

    private boolean setFocusSize(Button btn_unfocus, Button btn_focus){

        if (btn_unfocus == null){
            btn_focus.setTextColor(Color.rgb(255, 255, 255));
            btn_focus.setBackgroundColor(Color.rgb(3, 106, 150));
            Size = true;
        }else if(btn_unfocus==btn_focus){
            btn_focus.setTextColor(Color.rgb(49, 50, 51));
            btn_focus.setBackgroundColor(Color.rgb(207, 207, 207));
            Size = false;
        }else {
            btn_unfocus.setTextColor(Color.rgb(49, 50, 51));
            btn_unfocus.setBackgroundColor(Color.rgb(207, 207, 207));
            btn_focus.setTextColor(Color.rgb(255, 255, 255));
            btn_focus.setBackgroundColor(Color.rgb(3, 106, 150));
            Size = true;
        }
        this.btnSize_unfocus = btn_focus;
        return Size;
    }
    private void Anhxa(View contextView) {
        imgsp = (ImageView) contextView.findViewById(R.id.img_hinh_sanpham);
        txt_soluongsp =(TextView) contextView.findViewById(R.id.txt_sp_conlai);
        bntmn = (Button) contextView.findViewById(R.id.btn_minus);
        bntpl = (Button) contextView.findViewById(R.id.btn_plus);
        bntdone = (Button) contextView.findViewById(R.id.btn_hoantat);
        edtsoluong = (EditText) contextView.findViewById(R.id.edt_soluongcanmua);
        txt_giasanpham = (TextView) contextView.findViewById(R.id.txt_gia);
        txt_tensanpham = (TextView) contextView.findViewById(R.id.txt_tensanpham);
    }
}
