package com.phuoclong.yumo.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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
import com.phuoclong.yumo.AsyncTask.LoadProductTyple;
import com.phuoclong.yumo.AsyncTask.LoadQuangCao;
import com.phuoclong.yumo.Model.Product;
import com.phuoclong.yumo.R;

import java.util.ArrayList;

public class FragmentProductType extends Fragment implements ProductAdapter.OnCallBack {
    Toolbar toolbar;
    ViewFlipper vflquangcao;
    ArrayList<String> hinhquangcao;
    TextView txt_tendanhmuc;
    ArrayList<Product> danhsachSanPham;
    ProductAdapter sanphamAdapter;
    RecyclerView rV_sanpham_category;
    private LinearLayoutManager mLayoutManager;
    ProgressBar progressBar;
    ImageButton cars_category;
    public static TextView txt_soluonghang_category;
    Bundle bundle = new Bundle();
    int page =0;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean isLoading = false;
    private mHandler mHandler;
    String idcategory="";

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_type, container, false);
        AnhXa(view);
        bundle = getArguments();
        idcategory = bundle.getInt("idCategory", -1) + "";
        txt_tendanhmuc.setText(bundle.getString("tenCategory"));
        ActionToolBar();
        hinhquangcao = new ArrayList<>();
        danhsachSanPham = new ArrayList<>();
        new LoadQuangCao(hinhquangcao,getContext(),vflquangcao).execute(idcategory);
        vflquangcao.setFlipInterval(5000);
        vflquangcao.setAutoStart(true);
        Animation animationslideIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        Animation animationslideOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        vflquangcao.setInAnimation(animationslideIn);
        vflquangcao.setOutAnimation(animationslideOut);
        sanphamAdapter = new ProductAdapter(getContext(), R.layout.product_layout, this.danhsachSanPham, this);
        new LoadProductTyple(danhsachSanPham,getContext(),sanphamAdapter,rV_sanpham_category,idcategory).execute(page);
//        taoSanPham(idcategory);
//        if(danhsachSanPham.size()<=0){
//            Toast.makeText(getContext(), "Sản phẩm chưa được kinh doanh", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            sanphamAdapter = new ProductAdapter(getContext(),R.layout.product_layout,danhsachSanPham,this);
//            rV_sanpham_category.setAdapter(sanphamAdapter);
//
//        }
        loadSanPhamMoi();
        txt_soluonghang_category.setText(FragmentMainActivity.getcounthang(MainActivity.danhSachHangduocChon) + "");
        ControlButton();
        return view;
    }

    private void loadSanPhamMoi() {
       rV_sanpham_category.addOnScrollListener(new RecyclerView.OnScrollListener() {
           @Override
           public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
               super.onScrollStateChanged(recyclerView, newState);
           }

           @Override
           public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
               super.onScrolled(recyclerView, dx, dy);
               mLayoutManager = (GridLayoutManager)recyclerView.getLayoutManager();
               if(dy > 0){ // check cuộn xuống
                   visibleItemCount = mLayoutManager.getChildCount();
                   totalItemCount = mLayoutManager.getItemCount();
                   pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                   if(((visibleItemCount + pastVisiblesItems) >= totalItemCount - 1) && (totalItemCount != 0)&&(isLoading==false)){
                       isLoading = true;
                       progressBar.setVisibility(View.VISIBLE);
                       ThreadData threadData = new ThreadData();
                       threadData.start();

                   }
               }
           }
       });
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            super.run();
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
        }
    }
    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what)
            {
                case 0:
                    break;
                case 1:
                    isLoading=false;
                    new LoadProductTyple(danhsachSanPham,getContext(),sanphamAdapter,rV_sanpham_category,idcategory).execute(++page);                    progressBar.setVisibility(View.INVISIBLE);
                    break;
            }
            super.handleMessage(msg);
        }
    }
    private void ControlButton() {
        cars_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                intent = new Intent(ProductTypeActivity.this, GiohangActivity.class);
//                intent.putExtra("activity", "ProductTypeActivity");
//                ProductTypeActivity.this.startActivity(intent);
                bundle.putString("activity","ProductTypeActivity");
                FragmentManager fragmentManager =getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment_GioHang fragment_gioHang = new Fragment_GioHang();
                fragment_gioHang.setArguments(bundle);
                fragmentTransaction.add(R.id.framelayout_activiti_thuong,fragment_gioHang,"gio hang");
                fragmentTransaction.addToBackStack("gio hang");
                MainActivity.frameLayout_trangChu.setVisibility(View.INVISIBLE);
                MainActivity.frameLayout_activityThuong.setVisibility(View.VISIBLE);
                fragmentTransaction.commit();
            }
        });

    }

//    private void taoSanPham(String idcategory) {
//        Server server = new Server();
//        Connection connection = server.connection();
//        Statement statement;
//        ResultSet resultSet;
//        String Query = "SELECT t1.*, t2.TenThuongHieu  FROM dbo.LoaiSanPham t1" +
//                "INNER JOIN dbo.tb_ThuongHieu t2 ON t2.idThuongHieu = t1.idThuongHieu " +
//                "WHERE t1.IDNhomHangHoa=" + idcategory + "ORDER BY IDLoaiSanPham desc";
//        if (connection != null) {
//            try {
//                statement = connection.createStatement();
//                resultSet = statement.executeQuery(Query);
//                if (resultSet != null) {
//                    while (resultSet.next()) {
//                        this.danhsachSanPham.add(new Product(resultSet.getInt("IDLoaiSanPham") + "", resultSet.getInt("IDNhomHangHoa") + "",
//                                        resultSet.getString("MaLoaiSanPham"), resultSet.getString("MaBarCode"), resultSet.getString("TenLoaiSanPham"),
//                                        resultSet.getString("TenLoaiSanPhamKhongDau"), resultSet.getInt("idBoSuuTap") + "",
//                                        resultSet.getString("TenThuongHieu"), resultSet.getString("ChieuCao"), resultSet.getFloat("HoaHongCTV"),
//                                        resultSet.getFloat("GHTKMin"), resultSet.getFloat("GHTKMax"), resultSet.getFloat("GiaBanSanPham"),
//                                        resultSet.getFloat("GiaBanSi"), resultSet.getFloat("GiaBanLe"), resultSet.getString("TinhTrang"),
//                                        resultSet.getString("HinhAnh"), resultSet.getString("MoTaNgan"), resultSet.getString("MoTaChiTiet")
//                                )
//                        );
//                    }
//                    connection.close();
//                }
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        if (this.danhsachSanPham.size() < 1) {
//            Toast.makeText(getContext(), "Chưa có sản phẩm được giới thiệu", Toast.LENGTH_LONG).show();
//        } else {
//            sanphamAdapter = new ProductAdapter(getContext(), R.layout.product_layout, this.danhsachSanPham, this);
//            rV_sanpham_category.setAdapter(sanphamAdapter);
//            mlayout = new GridLayoutManager(getContext(), 2);
//            rV_sanpham_category.setHasFixedSize(true);
//            rV_sanpham_category.setLayoutManager(mlayout);
//        }
//    }

//    private void taoQuangCao(String idcategory) {
//        Server server = new Server();
//        Connection connection = server.connection();
//        Statement statement;
//        ResultSet resultSet;
//        String query = "SELECT * FROM dbo.QuangCaoNhomHang WHERE IDNhomHangHoa =" + idcategory;
//        if (connection != null) {
//            try {
//                statement = connection.createStatement();
//                resultSet = statement.executeQuery(query);
//                if (resultSet != null) {
//                    while (resultSet.next()) {
//                        this.hinhquangcao.add(resultSet.getString("HinhAnh1"));
//                        this.hinhquangcao.add(resultSet.getString("HinhAnh2"));
//                        this.hinhquangcao.add(resultSet.getString("HinhAnh3"));
//                        this.hinhquangcao.add(resultSet.getString("HinhAnh4"));
//                        this.hinhquangcao.add(resultSet.getString("HinhAnh5"));
//                    }
//                }
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        for (int i = 0; i < this.hinhquangcao.size(); i++) {
//            ImageView imageView = new ImageView(getContext());
//            Picasso.with(getContext()).load(this.hinhquangcao.get(i)).into(imageView);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            vflquangcao.addView(imageView);
//        }
//        vflquangcao.setFlipInterval(5000);
//        vflquangcao.setAutoStart(true);
//        Animation animationslideIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
//        Animation animationslideOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
//        vflquangcao.setInAnimation(animationslideIn);
//        vflquangcao.setOutAnimation(animationslideOut);
//    }

    private void ActionToolBar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentMainActivity.countsoluong.setText(FragmentMainActivity.getcounthang(MainActivity.danhSachHangduocChon) + "");
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                FragmentProductType fragmentProductType = (FragmentProductType) getFragmentManager().findFragmentByTag("danh muc san pham");
                fragmentTransaction.remove(fragmentProductType);
                fragmentTransaction.commit();
                MainActivity.frameLayout_activityThuong.setVisibility(View.INVISIBLE);
                MainActivity.frameLayout_trangChu.setVisibility(View.VISIBLE);
            }
        });
    }

    private void AnhXa(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_category);
        vflquangcao = (ViewFlipper) view.findViewById(R.id.vFl_YomiMall);
        txt_tendanhmuc = (TextView) view.findViewById(R.id.txt_tendanhmuc);
        cars_category = (ImageButton) view.findViewById(R.id.imgBtnGiohang_category);
        txt_soluonghang_category = (TextView) view.findViewById(R.id.txt_hang_trong_gio_category);
        rV_sanpham_category = (RecyclerView) view.findViewById(R.id.rV_yomi_catgory);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_loadMore_product_typle);
    }

    @Override
    public void onItemClicked(int position) {
//        intent = new Intent(ProductTypeActivity.this, ProductActivity.class);
        //intent.putExtra("activity","ProductTypeActivity");
//        intent.putExtra("SanPhamCoID", danhsachSanPham.get(position).getId());
//        intent.putExtra("Table","table"+intent.getIntExtra("idCategory",-1));
        Toast.makeText(getContext(), "Sản phẩm chưa được kinh doanh", Toast.LENGTH_SHORT).show();
    }
}