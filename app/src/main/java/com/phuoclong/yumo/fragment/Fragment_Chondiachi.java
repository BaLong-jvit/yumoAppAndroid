package com.phuoclong.yumo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.phuoclong.yumo.Activity.MainActivity;
import com.phuoclong.yumo.Model.GioHang;
import com.phuoclong.yumo.R;
import com.phuoclong.yumo.Until.Server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Fragment_Chondiachi extends Fragment {
    private Toolbar toolbar;
    private EditText hoten, sodt, diachi, email;
    private Button xacnhan;
    private Spinner tinhthanhpho, quanhuyen, phuongxa,hinhthucthanhtoan;
    private ArrayList<String> listTinh_ThanhPho, listQuan_Huyen, listPhuong_Xa, thanhtoan;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chondiachi,container,false);
        AnhXa(view);
        actionBar();
        taoDanhSachtinhthanhpho();
        hinhThucThanhToan();
        ControlButton();
        return view;
    }

    private void hinhThucThanhToan() {
        thanhtoan = new ArrayList<>();
        thanhtoan.add("Chọn hình thức thanh toán");
        Server server = new Server();
        Connection connection = server.connection();
        if(connection != null){
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT TenPhuongThucThanhToan FROM tb_PhuongThucThanhToan");
                if ( resultSet!=null){
                    while (resultSet.next()){
                        thanhtoan.add(resultSet.getString("TenPhuongThucThanhToan"));
                    }
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,thanhtoan);
        hinhthucthanhtoan.setAdapter(arrayAdapter);
    }

    private void ControlButton() {
        tinhthanhpho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Server server = new Server();
                Connection connection = server.connection();
                Statement statement;
                ResultSet resultSet;
                String QueryQuanhuyen = "SELECT Ten FROM tb_QuanHuyen WHERE idTinhTP = (SELECT id FROM tb_TinhTP WHERE Ten like N'"+
                        tinhthanhpho.getSelectedItem().toString()+"') ";
                listQuan_Huyen = new ArrayList<>();
                listQuan_Huyen.add("Chọn Quận/Huyện");
                if(connection!=null){
                    try {
                        statement = connection.createStatement();
                        resultSet = statement.executeQuery(QueryQuanhuyen);
                        if(resultSet!=null){
                            while (resultSet.next()){
                                listQuan_Huyen.add(resultSet.getString("Ten"));
                            }
                            connection.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,listQuan_Huyen);
                quanhuyen.setAdapter(arrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        quanhuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Server server = new Server();
                Connection connection = server.connection();
                Statement statement;
                ResultSet resultSet;
                String QueryPhuongXa = "SELECT Ten FROM tb_PhuongXa WHERE idQuanHuyen = (SELECT id FROM tb_QuanHuyen WHERE Ten like N'"+
                        quanhuyen.getSelectedItem().toString()+"')";
                listPhuong_Xa = new ArrayList<>();
                listPhuong_Xa.add("Chọn Phường/Xã");
                if(connection!=null){
                    try {
                        statement = connection.createStatement();
                        resultSet = statement.executeQuery(QueryPhuongXa);
                        if(resultSet!=null){
                            while (resultSet.next()){
                                listPhuong_Xa.add(resultSet.getString("Ten"));
                            }
                            connection.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,listPhuong_Xa);
                phuongxa.setAdapter(arrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!hoten.getText().toString().trim().equals("")
                        &&!sodt.getText().toString().trim().equals("")
                        &&!tinhthanhpho.getSelectedItem().toString().equals("Chọn Tỉnh/Thành Phố")
                        &&!quanhuyen.getSelectedItem().toString().equals("Chọn Quận/Huyện")
                        &&!phuongxa.getSelectedItem().toString().equals("Chọn Phường/Xã")
                        &&!diachi.getText().toString().trim().equals("")
                        &&!hinhthucthanhtoan.getSelectedItem().toString().equals("Chọn hình thức thanh toán")
                ){
                    String idctv = (MainActivity.isUser)?MainActivity.account.getIDCTV():"0";
                    ArrayList<GioHang> danhsachLoaiSanPham =new ArrayList<>();
                    ArrayList<String> idPhieuxuat = new ArrayList<>();
                    danhsachLoaiSanPham.add(MainActivity.danhSachHangduocChon.get(0));
                    for (int i = 1; i< MainActivity.danhSachHangduocChon.size(); i++) {
                        boolean add = true;
                        for (int j = danhsachLoaiSanPham.size() - 1; j > 0; j--) {
                            if (danhsachLoaiSanPham.get(j).getIdSP().equals(MainActivity.danhSachHangduocChon.get(i).getIdSP())
                            ) {
                                danhsachLoaiSanPham.get(j).setSoluongSp(MainActivity.danhSachHangduocChon.get(j).getSoluongSp() + MainActivity.danhSachHangduocChon.get(i).getSoluongSp());
                                danhsachLoaiSanPham.get(j).setTongThanhToan(MainActivity.danhSachHangduocChon.get(i).getTongThanhToan() + MainActivity.danhSachHangduocChon.get(j).getTongThanhToan());
                                add = false;
                            }

                        }
                        if (add) {
                            danhsachLoaiSanPham.add(MainActivity.danhSachHangduocChon.get(i));
                        }
                    }
                    for (int i = 0; i<danhsachLoaiSanPham.size();i++) {
                        float phivanchuyen=(tinhthanhpho.getSelectedItem().toString().equals("Thành phố Hồ Chí Minh"))?20000:35000;
                        idPhieuxuat.add(getMaPhieuXuat());
                        String QueryPhieuxuat = "INSERT INTO dbo.PhieuXuatChoCTV ( MaPhieuXuatChoCTV ,NgayLap , IDCTV , GiamGia ,TenNguoiNhanHang , " +
                                "SDTNguoiNhanHang , EmailNguoiNhan , idTinhNhan ,idQuanNhan , idXaNhan , DiaChiNhan , PhiVanChuyen , DonViVanChuyen , " +
                                "MaVanDon , MaGiamGiaCoDinh , MaGiamGiaPhiShip , TongTienDH , TongHoaHongCTV , TongTienThanhToan)" +
                                "VALUES  ( N'" + idPhieuxuat.get(i) + "' , (SELECT CONVERT(VARCHAR(10), getdate(), 103)) ," + idctv + ", 0.0 , N'" +
                                hoten.getText().toString().trim() + "' , N'" + sodt.getText().toString().trim() + "' ,N'" + email.getText().toString().trim() + "' , " +
                                "(SELECT id FROM tb_TinhTP WHERE Ten like N'" + tinhthanhpho.getSelectedItem().toString() + "') , " +
                                "(SELECT id FROM tb_QuanHuyen WHERE Ten like N'" + quanhuyen.getSelectedItem().toString() + "') , " +
                                "(SELECT id FROM tb_PhuongXa WHERE Ten like N'" + phuongxa.getSelectedItem().toString() + "') , N'" +
                                diachi.getText().toString().trim() + "' , "+phivanchuyen+", N'' ,  N'' , N'' ,  N'' , " +
                                danhsachLoaiSanPham.get(i).getTongThanhToan() + " , " + danhsachLoaiSanPham.get(i).getHoaHong() + " , 0.0)";
                        saveBill(QueryPhieuxuat);
                    }
                    for (int i = 0; i<MainActivity.danhSachHangduocChon.size();i++){
                        String QueryChitiet="INSERT INTO dbo.ChiTietPhieuXuatChoCTV ( IDPhieuXuatChoCTV , IDSanPham , SoLuong ,DonGia ,ThanhTien ,IDLoaiSanPham ,IDMauSac ,IDSize ,HoaHongCTV )" +
                                "VALUES  ( "+getIDphieuxuat(i,danhsachLoaiSanPham,idPhieuxuat)+" , "+getidSanPham(MainActivity.danhSachHangduocChon.get(i).getIdSP())+" ,  "+MainActivity.danhSachHangduocChon.get(i).getSoluongSp()+
                                " , "+MainActivity.danhSachHangduocChon.get(i).getDonGia()+" , "+MainActivity.danhSachHangduocChon.get(i).getSoluongSp()* MainActivity.danhSachHangduocChon.get(i).getDonGia() +" , "+
                                MainActivity.danhSachHangduocChon.get(i).getIdSP()+" , "+MainActivity.danhSachHangduocChon.get(i).getMau() +" , "+MainActivity.danhSachHangduocChon.get(i).getSize()+" , "+
                                MainActivity.danhSachHangduocChon.get(i).getHoaHong() * MainActivity.danhSachHangduocChon.get(i).getSoluongSp()+" )";
                         String querySoluongThucte="  UPDATE dbo.SoLuongSanPhamThucTe SET SoLuong= (SoLuong - "+MainActivity.danhSachHangduocChon.get(i).getSoluongSp()+
                                ") WHERE IDSanPham = "+MainActivity.danhSachHangduocChon.get(i).getIdSP();
                        saveBill(querySoluongThucte);
                        saveBill(QueryChitiet);
                    }

                    MainActivity.danhSachHangduocChon.clear();
                    MainActivity.frameLayout_activityThuong.removeAllViewsInLayout();
                    MainActivity.frameLayout_activityThuong.setVisibility(View.INVISIBLE);
                    MainActivity.frameLayout_trangChu.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(getContext(),"Vui lòng nhập đầy dủ thông tin", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private String getIDphieuxuat(int index,ArrayList<GioHang> danhsach2,ArrayList<String> idphieuxuat) {
        StringBuilder idphieu = null;
        for (int i=0;i<danhsach2.size();i++){
            if(MainActivity.danhSachHangduocChon.get(index).getIdSP().equals(danhsach2.get(i).getIdSP())){
                idphieu = new StringBuilder(idphieuxuat.get(i));
            }
        }
        return idphieu.toString();
    }

    private String getidSanPham(String idSP) {
        StringBuilder idSanPham = null;
        Server server = new Server();
        Connection connection = server.connection();
        try {
            Statement statement= connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT IDSanPham FROM dbo.SanPham WHERE  IDLoaiSanPham="+idSP);
            if (connection!=null){
                if (resultSet!=null){
                    idSanPham =new StringBuilder(resultSet.getInt("IDSanPham"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idSanPham.toString();
    }

    private void saveBill(String query) {
        Server server = new Server();
        Connection connection = server.connection();
        Statement statement;
        ResultSet resultSet;
        if (connection != null) {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);
                if (resultSet != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private String getMaPhieuXuat() {
        StringBuilder billCode=null;
        Server server = new Server();
        Connection connection = server.connection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MAX(IDPhieuXuatChoCTV) FROM dbo.PhieuXuatChoCTV");
            if (resultSet!=null){
                while (resultSet.next()){
                    if(resultSet.getRow()>0) {
                        for (int i = ((resultSet.getInt(1) + 1) + "").trim().length(); i < 5; i++) {
                            billCode = new StringBuilder("0").append(billCode);
                        }
                        billCode = new StringBuilder("BILL").append(billCode).append((resultSet.getInt(1) + 1));
                    }else {
                        billCode = new StringBuilder("BILL00001");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return billCode.toString();
    }

    private void taoDanhSachtinhthanhpho() {
        Server server = new Server();
        Connection connection = server.connection();
        Statement statement;
        ResultSet resultSet;
        String QueryTinh_ThanhPho = "SELECT Ten FROM tb_TinhTP";
        listTinh_ThanhPho = new ArrayList<>();
        listTinh_ThanhPho.add("Chọn Tỉnh/Thành Phố");
        if(connection != null){
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(QueryTinh_ThanhPho);
                if(resultSet!=null){
                    while (resultSet.next()){
                        listTinh_ThanhPho.add(resultSet.getString("Ten"));
                    }
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,listTinh_ThanhPho);
        tinhthanhpho.setAdapter(arrayAdapter);
    }

    private void actionBar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment_Chondiachi fragment_chondiachi = (Fragment_Chondiachi) fragmentManager.findFragmentByTag("chon dia chi");
                fragmentTransaction.remove(fragment_chondiachi);
                fragmentTransaction.commit();
            }
        });
    }

    private void AnhXa(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolb_chondiachi);
        hoten = (EditText) view.findViewById(R.id.edt_hovaten);
        sodt = (EditText) view.findViewById(R.id.edt_sdtngNhan);
        diachi = (EditText) view.findViewById(R.id.edt_diachicuthe);
        xacnhan = (Button) view.findViewById(R.id.btn_chondiachi);
        tinhthanhpho = (Spinner) view.findViewById(R.id.spinner_tinhthanhpho);
        quanhuyen = (Spinner) view.findViewById(R.id.spinner_quanhuyen);
        phuongxa = (Spinner) view.findViewById(R.id.spinner_phuongxa);
        hinhthucthanhtoan = (Spinner) view.findViewById(R.id.spinner_hinhthucthanhtoan);
        email = (EditText) view.findViewById(R.id.edt_email);
    }
}
