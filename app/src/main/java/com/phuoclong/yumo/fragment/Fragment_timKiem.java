package com.phuoclong.yumo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phuoclong.yumo.Activity.MainActivity;
import com.phuoclong.yumo.Adapter.ProductAdapter;
import com.phuoclong.yumo.Model.Product;
import com.phuoclong.yumo.R;
import com.phuoclong.yumo.Until.Server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Fragment_timKiem extends Fragment implements ProductAdapter.OnCallBack{
    private ArrayList<Product> sanpham_phuhop;
    private ProductAdapter productAdapter;
    private Toolbar toolbar;
    private ImageButton imageButton_giohang;
    public static TextView sosanpham_trongdonhang;
    private TextView thongbao,textView_tentimkiem;
    private RecyclerView recyclerView;
    LinearLayoutManager mlayout;
    Bundle bundle = new Bundle();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timkiem,container,false);
        bundle = getArguments();
        String chuoi = bundle.getString("tukhoa");
        String Query = " SELECT tb1.*,tb2.TenThuongHieu from dbo.LoaiSanPham tb1 " +
                "INNER JOIN dbo.tb_ThuongHieu tb2 ON tb2.idThuongHieu = tb1.idThuongHieu " +
                "WHERE TenLoaiSanPham like N'%"+ chuoi +"%' ORDER BY IDLoaiSanPham DESC";
        AnhXa(view);
        ActionToolbar();
        sanpham_phuhop = new ArrayList<>();
        taoSanPhamtimduoc(Query);
        textView_tentimkiem.setText("\'\'"+ chuoi +"\'\'");
        if (sanpham_phuhop.size()>0){
            thongbao.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }else {
            thongbao.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
        productAdapter =new ProductAdapter(getContext(),R.layout.product_layout, sanpham_phuhop,this);
        recyclerView.setAdapter(productAdapter);
        mlayout = new GridLayoutManager(getContext(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mlayout);
        Control();
        return view;
    }
    private void taoSanPhamtimduoc(String query) {
        Server server = new Server();
        Connection connection= server.connection();
        Statement statement;
        ResultSet resultSet;
        if ( connection != null){
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);
                if(resultSet!=null){
                    while (resultSet.next()){
                        sanpham_phuhop.add(new Product(resultSet.getInt("IDLoaiSanPham")+"",resultSet.getInt("IDNhomHangHoa")+"",
                                        resultSet.getString("MaLoaiSanPham"),resultSet.getString("MaBarCode"),resultSet.getString("TenLoaiSanPham"),
                                        resultSet.getString("TenLoaiSanPhamKhongDau"),resultSet.getInt("idBoSuuTap")+"",
                                        resultSet.getString("TenThuongHieu"),resultSet.getString("ChieuCao"), resultSet.getFloat("HoaHongCTV"),
                                        resultSet.getFloat("GHTKMin"),resultSet.getFloat("GHTKMax"),resultSet.getFloat("GiaBanSanPham"),
                                        resultSet.getFloat("GiaBanSi"),resultSet.getFloat("GiaBanLe"),resultSet.getString("TinhTrang"),
                                        resultSet.getString("HinhAnh"),resultSet.getString("MoTaNgan"),resultSet.getString("MoTaChiTiet")
                                )
                        );
                    }
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void Control() {

    }

    private void ActionToolbar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentMainActivity.countsoluong.setText(FragmentMainActivity.getcounthang(MainActivity.danhSachHangduocChon)+"");
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                Fragment_timKiem fragment_timKiem = (Fragment_timKiem) getFragmentManager().findFragmentByTag("tim kiem");
                fragmentTransaction.remove(fragment_timKiem);
                fragmentTransaction.commit();
                MainActivity.frameLayout_activityThuong.setVisibility(View.INVISIBLE);
                MainActivity.frameLayout_trangChu.setVisibility(View.VISIBLE);
            }
        });
    }

    private void AnhXa(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.tb_timkiem);
        imageButton_giohang = (ImageButton) view.findViewById(R.id.imgBtnGiohangtimkiem);
        sosanpham_trongdonhang = (TextView) view.findViewById(R.id.txt_hang_trong_giotimkiem);
        thongbao = (TextView) view.findViewById(R.id.text_thongbaokhongcosanphamphuhop);
        recyclerView = (RecyclerView) view.findViewById(R.id.rcl_sanphamtimkiem);
        textView_tentimkiem = (TextView) view.findViewById(R.id.tukhoatimkiem);
    }

    @Override
    public void onItemClicked(int position) {
        bundle = new Bundle();
        bundle.putString("activity", "home");
        bundle.putString("SanPhamCoID", sanpham_phuhop.get(position).getIDloaiSanPham()+"");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment_Product fragment_product = new Fragment_Product();
        fragment_product.setArguments(bundle);
        fragmentTransaction.add(R.id.framelayout_activiti_thuong,fragment_product);
        fragmentTransaction.addToBackStack("product");
        fragmentTransaction.commit();
        MainActivity.frameLayout_trangChu.setVisibility(View.INVISIBLE);
        MainActivity.frameLayout_activityThuong.setVisibility(View.VISIBLE);
    }
}
