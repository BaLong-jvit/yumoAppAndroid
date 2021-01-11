package com.phuoclong.yumo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.phuoclong.yumo.Activity.MainActivity;
import com.phuoclong.yumo.Adapter.HistoryAdapter;
import com.phuoclong.yumo.Model.LichSu;
import com.phuoclong.yumo.R;
import com.phuoclong.yumo.Until.Server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FragmentHistory extends Fragment {
    ListView listView_lichsumuahang;
    TextView textView_thongbao_lichsu;
    ArrayList<LichSu> danhsach_damuahang;
    HistoryAdapter historyAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lichsu, container,false);
        AnhXa(view);
        danhsach_damuahang = new ArrayList<>();
        if(taoListDanhSachDaMua(danhsach_damuahang).size()>1){
            textView_thongbao_lichsu.setVisibility(View.INVISIBLE);
            listView_lichsumuahang.setVisibility(View.VISIBLE);
            historyAdapter = new HistoryAdapter(getContext(),R.layout.item_history,danhsach_damuahang);
            listView_lichsumuahang.setAdapter(historyAdapter);
        }else {
            textView_thongbao_lichsu.setVisibility(View.VISIBLE);
            listView_lichsumuahang.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    private void AnhXa(View view) {
        listView_lichsumuahang = (ListView) view.findViewById(R.id.list_history);
        textView_thongbao_lichsu = (TextView) view.findViewById(R.id.thongbao_lichsumuahang);
    }

    private ArrayList<LichSu> taoListDanhSachDaMua(ArrayList<LichSu> danhsach_damuahang) {
        Server server = new Server();
        Connection connection = server.connection();
        Statement statement;
        ResultSet resultSet;
        if(connection!=null) {
            try {
                statement = connection.createStatement();
                String query = "SELECT * FROM dbo.PhieuXuatKhoChoCTV WHERE IDCTV=" + MainActivity.account.getIDCTV() + "ORDER BY IDPhieuXuatChoCTV desc";
                resultSet = statement.executeQuery(query);
                if (resultSet != null) {
                    while (resultSet.next()) {
                        danhsach_damuahang.add(new LichSu(resultSet.getInt(
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
                        )
                        );
                    }
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return danhsach_damuahang;
    }
}
