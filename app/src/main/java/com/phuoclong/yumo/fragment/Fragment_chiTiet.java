package com.phuoclong.yumo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.phuoclong.yumo.Activity.MainActivity;
import com.phuoclong.yumo.Model.LichSu;
import com.phuoclong.yumo.R;
import com.phuoclong.yumo.Until.Server;
import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Fragment_chiTiet extends Fragment {
    Toolbar toolbar;
    TextView textView_tensp, textView_thuonghieu, textView_gia, textView_giacu, textView_mota;
    ListView listView_trangthai;
    Button button_datlai;
    ImageView imageView_hinhanhsp;
    LichSu lichsu;
    Bundle bundle = new Bundle();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chitiet,container,false);
        AnhXa(view);
        ActionToolbar();
        bundle = getArguments();
        String IDPhieuXuatChoCTV = bundle.getString("idDonhang")+"";
        Server server = new Server();
        Connection connection = server.connection();
        Statement statement;
        ResultSet resultSet;
        if(connection!=null) {
            try {
                statement = connection.createStatement();
                String query = "SELECT t1.*, t2.IDSanPham, t3.HinhAnh,t2.SoLuong, t3.TenSanPham,t3.MoTaNgan, t3.MoTaChiTiet, t4.GiaBanSanPham, t4.GiaBanSi, t4.GiaBanLe, t5.TenThuongHieu FROM " +
                        "dbo.PhieuXuatChoCTV t1 INNER JOIN dbo.ChiTietPhieuXuatChoCTV t2 INNER JOIN dbo.SanPham t3 INNER JOIN dbo.LoaiSanPham t4 INNER JOIN dbo.tb_ThuongHieu t5 ON t2.IDPhieuXuatChoCTV = t1.IDPhieuXuatChoCTV " +
                        "ON t3.IDSanPham= t2.IDSanPham ON t4.IDLoaiSanPham = t3.IDLoaiSanPham ON t5.idThuongHieu = t4.idThuongHieu" +
                        "WHERE IDPhieuXuatChoCTV =" + IDPhieuXuatChoCTV;
                resultSet = statement.executeQuery(query);
                if (resultSet != null) {
                    while (resultSet.next()) {
                        lichsu = new LichSu(resultSet.getInt(
                                "IDPhieuXuatChoCTV")+"",
                                resultSet.getString("MaPhieuXuatChoCTV"),
                                resultSet.getDate("NgayLap").toString(),
                                resultSet.getString("GhiChu"),
                                resultSet.getInt("IDCTV")+"",
                                resultSet.getFloat("GiamGia"),
                                resultSet.getString("TenNguoiNhanHang"),
                                resultSet.getString("STDNguoiNhanHang"),
                                resultSet.getString("EmailNguoiNhan"),
                                resultSet.getInt("idTinhNhan"),
                                resultSet.getInt("idQuanNhan"),
                                resultSet.getInt("idXaNhan"),
                                resultSet.getString("DiaChiNhan"),
                                resultSet.getFloat("PhiVanChuyen"),
                                resultSet.getString("MaTrangThai"),
                                resultSet.getString("ThuKhac"),
                                resultSet.getFloat("ThanhToanTienMat"),
                                resultSet.getFloat("ThanhToanCHuyenKhoan"),
                                resultSet.getInt("idNguoiTao")+"",
                                resultSet.getString("Loai"),
                                resultSet.getBoolean("isDoiSoat"),
                                resultSet.getFloat("TienDoiSoat"),
                                resultSet.getBoolean("isNhanHang"),
                                resultSet.getString("DonViVanChuyen"),
                                resultSet.getString("MaVanDon"),
                                resultSet.getFloat("PhiTraVanChuyen"),
                                resultSet.getDate("NgayXuatHang").toString(),
                                resultSet.getString("TrangThaiVanDon"),
                                resultSet.getDate("NgayTrangThaiVanDon").toString(),
                                resultSet.getString("lyDoKhongThanhCong"),
                                resultSet.getString("MaDonHangDoi"),
                                resultSet.getInt("idTrangHang")+"",
                                resultSet.getString("MaGiamGiaCoDinh"),
                                resultSet.getString("MaGiamGiaPhiShip"),
                                resultSet.getDate("NgayChamSoc").toString(),
                                resultSet.getBoolean("DaChamSocXong"),
                                resultSet.getDate("NgayChamSocLai").toString(),
                                resultSet.getInt("idNguoiSua")+"",
                                resultSet.getDate("NgaySua").toString(),
                                resultSet.getInt("idLyDoHuy")+"",
                                resultSet.getFloat("TongTienDH"),
                                resultSet.getFloat("TongHoaHongCTV"),
                                resultSet.getBoolean("isDoiSoatHoaHongCTV"),
                                resultSet.getInt("idGianHang")+"",
                                resultSet.getInt("idGianHangCTV")+"",
                                resultSet.getBoolean("isWeb"),
                                resultSet.getString("QuyenCTV"),
                                resultSet.getBoolean("isDuyetSoQuy"),
                                resultSet.getInt("idNguoiDuyet")+"",
                                resultSet.getDate("NgayDuyet").toString(),
                                resultSet.getFloat("TongTienThanhToan"),
                                resultSet.getString("HinhAnh"),
                                resultSet.getString("TenSanPham"),
                                resultSet.getInt("SoLuong"),
                                resultSet.getFloat("GiaBanSanPham"),
                                resultSet.getFloat("GIaBanSi"),
                                resultSet.getFloat("GiaBanLe"),
                                resultSet.getString("TenThuongHieu"),
                                resultSet.getInt("idSanPham")+"",
                                resultSet.getString("MoTaNgan"),
                                resultSet.getString("MoTaChiTiet")
                        );
                    }
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Picasso.with(getContext()).load(lichsu.getHinhAnhSp()).into(imageView_hinhanhsp);
        textView_tensp.setText((lichsu.getTenSanPham().length()<22)?lichsu.getTenSanPham()
                :lichsu.getTenSanPham().substring(0,22)+"...");
        textView_gia.setText(MainActivity.formartMoney(lichsu.getGiaBanLe()));
        textView_giacu.setText(MainActivity.formartMoney(lichsu.getGiaBanSanPham()));

        textView_thuonghieu.setText(lichsu.getThuongHieu());

        textView_mota.setText(lichsu.getMoTaNgan());
        button_datlai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
    private void ActionToolbar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void AnhXa(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.tb_chitietsp);
        textView_tensp = (TextView) view.findViewById(R.id.txt_productName_chitiet);
        textView_thuonghieu = (TextView) view.findViewById(R.id.txt_thuonghieu_chitiet);
        textView_gia = (TextView) view.findViewById(R.id.productPrice_chitiet);
        textView_giacu = (TextView) view.findViewById(R.id.old_productPrice_chitiet);
        textView_mota = (TextView) view.findViewById(R.id.txt_mota_chitiet);
        listView_trangthai = (ListView) view.findViewById(R.id.list_trangthai_chitiet);
        button_datlai = (Button) view.findViewById(R.id.btn_datlai_chitiet);
        imageView_hinhanhsp = (ImageView) view.findViewById(R.id.hinh_chitiet);
    }
}
