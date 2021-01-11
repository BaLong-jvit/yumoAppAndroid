package com.phuoclong.yumo.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
import androidx.viewpager.widget.ViewPager;

import com.phuoclong.yumo.Activity.MainActivity;
import com.phuoclong.yumo.Adapter.ProductAdapter;
import com.phuoclong.yumo.AsyncTask.LoadHinhAnhSanPham;
import com.phuoclong.yumo.AsyncTask.LoadProductTheoThuongHieu;
import com.phuoclong.yumo.Model.ChonSanPham;
import com.phuoclong.yumo.Model.Product;
import com.phuoclong.yumo.R;
import com.phuoclong.yumo.Until.CheckConnection;

import java.util.ArrayList;

public class Fragment_Product extends Fragment implements ProductAdapter.OnCallBack{
    @SuppressLint("StaticFieldLeak")
    public static TextView soluonghang;
    private Product product;
     static ArrayList<String> listHinhAnh;
    private ViewPager mViewPager;
    private TextView tensanpham, thuonghieu, gia, giacu, daban,mota;
    LinearLayoutManager mLayoutManager;
    private ArrayList<Product> listProduct = new ArrayList<>();
    private ProductAdapter productAdapter;
    private RecyclerView recy_splienquan;
    private boolean limitData=false;
    private Button btn_addtocart, btn_buy,btn_xemthem,btn_thugon;
    private ImageButton giohang;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment_Product fragment_product;
    private Fragment_GioHang fragment_gioHang;
    private boolean isLoading = false;
    private int page = 0;
    private mHandler mHandler;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private Bundle bundle = new Bundle();
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product,container,false);
        bundle = getArguments();
        assert bundle != null;
        product = (Product) bundle.getSerializable("productDuocChon");
        if(CheckConnection.haveNetworkConnection(getContext())) {
            AnhXa(view);
            ActionToolbar();
            listHinhAnh = new ArrayList<>();
            listHinhAnh.add(product.getHinhAnh());
//            taoProduct(Id);
            soluonghang.setText(MainActivity.soluongsanpham+"");
           new LoadHinhAnhSanPham(listHinhAnh,getContext(),mViewPager).execute("SELECT LinkHinh, isAnhChinh FROM tb_HinhAnhLoaiSP WHERE IDLoaiSanPham = "+ product.getIDloaiSanPham());
            //tạo xong list hinh anh
            TaoThongTinSanPham(product);
            // tao recyclerview
            mHandler = new mHandler();
            progressBar.setVisibility(View.INVISIBLE);
            productAdapter = new ProductAdapter(getContext(),R.layout.product_layout,listProduct,this);
            new LoadProductTheoThuongHieu(listProduct,getContext(),recy_splienquan,product.getThuongHieu(),productAdapter).execute(page);
            recy_splienquan.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
            clickControl();
        }else CheckConnection.showToast_Short(getContext(),"Kết nối Internet thất bại!\nPlease check your network!");
        return view;
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
                    new LoadProductTheoThuongHieu(listProduct,getContext(),recy_splienquan,product.getThuongHieu(),productAdapter).execute(++page);
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
            }
            super.handleMessage(msg);
        }
    }
    private void ActionToolbar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishProductActivity();
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void finishProductActivity() {
        bundle = getArguments();
        assert bundle != null;
        String activity = bundle.getString("activity");
        assert activity != null;
        switch (activity)
        {
            case "home":
                fragmentManager=getFragmentManager();
                fragmentTransaction =fragmentManager.beginTransaction();
                fragment_product = (Fragment_Product) getFragmentManager().findFragmentByTag("product");
                fragmentTransaction.remove(fragment_product);
                FragmentMainActivity.countsoluong.setText(FragmentMainActivity.getcounthang(MainActivity.danhSachHangduocChon)+"");
                MainActivity.frameLayout_trangChu.setVisibility(View.VISIBLE);
                MainActivity.frameLayout_activityThuong.setVisibility(View.INVISIBLE);
                fragmentTransaction.commit();
                break;
            case "CollectionActivity":
                FragmentCollection.soluong.setText(FragmentMainActivity.getcounthang(MainActivity.danhSachHangduocChon)+"");
                fragmentManager=getFragmentManager();
                fragmentTransaction =fragmentManager.beginTransaction();
                fragment_product = (Fragment_Product) getFragmentManager().findFragmentByTag("product");
                fragmentTransaction.remove(fragment_product);
                fragmentTransaction.commit();
                break;
            case "ProductActivity":

//                assert getFragmentManager() != null;
//                getFragmentManager().getBackStackEntryCount()>0) {
                getFragmentManager().popBackStack();
                Fragment_Product.soluonghang.setText(FragmentMainActivity.getcounthang(MainActivity.danhSachHangduocChon)+"");
                if(getFragmentManager().getBackStackEntryCount()<1){
                    MainActivity.frameLayout_trangChu.setVisibility(View.VISIBLE);
                    MainActivity.frameLayout_activityThuong.setVisibility(View.INVISIBLE);
                }
                break;
            case "ProductTypeActivity":
                FragmentProductType.txt_soluonghang_category.setText(FragmentMainActivity.getcounthang(MainActivity.danhSachHangduocChon)+"");
                fragmentManager=getFragmentManager();
                fragmentTransaction =fragmentManager.beginTransaction();
                fragment_product = (Fragment_Product) getFragmentManager().findFragmentByTag("firt product");
                fragmentTransaction.remove(fragment_product);
                fragmentTransaction.commit();
                break;
            default:
                break;
        }
    }
    private void clickControl() {
        giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("activity","ProductActivity");
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragment_gioHang = new Fragment_GioHang();
                fragment_gioHang.setArguments(bundle);
                fragmentTransaction.add(R.id.framelayout_activiti_thuong,fragment_gioHang,"gio hang");
                fragmentTransaction.addToBackStack("gio hang");
                fragmentTransaction.commit();
            }
        });
        soluonghang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("activity","ProductActivity");
                fragmentManager =getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragment_gioHang = new Fragment_GioHang();
                fragment_gioHang.setArguments(bundle);
                fragmentTransaction.add(R.id.framelayout_activiti_thuong,fragment_gioHang,"gio hang");
                fragmentTransaction.addToBackStack("gio hang");
                fragmentTransaction.commit();
            }
        });


        btn_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v=btn_addtocard
                ChonSanPham chonSanPham = new ChonSanPham();
                fragmentManager=getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                bundle.putSerializable("sanphamDuocChon",product);
//                bundle.putString("idSanpham",product.getIDloaiSanPham());
//                bundle.putString("hinhsanpham",product.getHinhAnh());
//                bundle.putString("tenSanpham",product.getTenLoaiSanPham());
//                bundle.putFloat("gia",product.getGiaBanLe());
//                bundle.putFloat("HoaHong", product.getHoaHongCTV());
                bundle.putBoolean("thanhtoan", false);
                chonSanPham.setArguments(bundle);
//                chonSanPham.show(getContext().getSupportFragmentManager(),chonSanPham.getTag());
                chonSanPham.show(getFragmentManager(),chonSanPham.getTag());
            }
        });
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v=btn_buy
                ChonSanPham chonSanPham = new ChonSanPham();
                fragmentManager=getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                bundle.putSerializable("sanphamDuocChon",product);
                bundle.putBoolean("thanhtoan", true);
                chonSanPham.setArguments(bundle);
                chonSanPham.show(getFragmentManager(),chonSanPham.getTag());
            }
        });
        btn_xemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mota.setText(product.getMotaChiTiet());
                btn_xemthem.setVisibility(View.INVISIBLE);
                btn_thugon.setVisibility(View.VISIBLE);
            }
        });
        btn_thugon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mota.setText(product.getMoTaNgan());
                btn_thugon.setVisibility(View.INVISIBLE);
                btn_xemthem.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public void onItemClicked(int position) {
        bundle = new Bundle();
        bundle.putString("activity", "ProductActivity");
        fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        bundle.putSerializable("productDuocChon",listProduct.get(position));
        fragment_product = new Fragment_Product();
        fragment_product.setArguments(bundle);
        fragmentTransaction.add(R.id.framelayout_activiti_thuong,fragment_product,"product");
        fragmentTransaction.addToBackStack("product");
        fragmentTransaction.commit();

    }
    @SuppressLint({"NewApi", "SetTextI18n"})
    private void TaoThongTinSanPham(Product product) {
        tensanpham.setText(product.getTenLoaiSanPham());
        gia.setText(MainActivity.formartMoney(product.getGiaBanLe()));
        SpannableString spannableString = new SpannableString( MainActivity.formartMoney(product.getGiaBanSanPham()));
        spannableString.setSpan(new StrikethroughSpan(),0,MainActivity.formartMoney(product.getGiaBanSanPham()).length(),0);
        giacu.setText(spannableString);
        if(MainActivity.isUser) {
            daban.setText("Bonus: "+MainActivity.formartMoney(product.getHoaHongCTV()));
        }else {
            daban.setText("");
        }
        mota.setText(product.getMoTaNgan());
        btn_xemthem.setVisibility(View.VISIBLE);
        btn_thugon.setVisibility(View.INVISIBLE);
        thuonghieu.setText(product.getThuongHieu());
    }


    @SuppressLint({"NewApi", "WrongViewCast"})
    private void AnhXa(View view){
        tensanpham = (TextView) view.findViewById(R.id.txt_productName);
        gia = (TextView) view.findViewById(R.id.productPrice);
        giacu = (TextView) view.findViewById(R.id.old_productPrice);
        daban =(TextView) view.findViewById(R.id.DaBan);
        mota = (TextView) view.findViewById(R.id.txt_mota);
        thuonghieu =(TextView) view.findViewById(R.id.txt_thuonghieu);
        recy_splienquan = (RecyclerView) view.findViewById(R.id.rcv_sanphamtuongtu);
        soluonghang = (TextView) view.findViewById(R.id.txt_hang_trong_gio_Product);
        giohang = (ImageButton) view.findViewById(R.id.imgBtnGiohang_Product);
        btn_addtocart = (Button) view.findViewById(R.id.btn_addToCart);
        btn_buy = (Button) view.findViewById(R.id.btn_Buy);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_loadMore_product);
        btn_xemthem= (Button) view.findViewById(R.id.btn_xemthem);
        toolbar = (Toolbar) view.findViewById(R.id.tb_product);
        btn_thugon=(Button) view.findViewById(R.id.btn_thugon);
    }
}
