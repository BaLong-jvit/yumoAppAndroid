package com.phuoclong.yumo.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.phuoclong.yumo.Activity.MainActivity;
import com.phuoclong.yumo.Adapter.ProductAdapter;
import com.phuoclong.yumo.AsyncTask.LoadCollection;
import com.phuoclong.yumo.Model.Product;
import com.phuoclong.yumo.R;

import java.util.ArrayList;


public class FragmentCollection extends Fragment implements ProductAdapter.OnCallBack {
    RecyclerView recyclerView_bst;
    //khai bao adapter cho san pham moi
    ArrayList<Product> thongTinSanPhamMois;
    ProductAdapter product_in_collectionAdapter;
    LinearLayoutManager mLayoutManager;
    public static TextView soluong;
    TextView txt_tenbosuutap;
    ImageButton cards;
    Toolbar toolbar;
    ProgressBar progressBar;
    mHandler mHandler = new mHandler();
    Bundle bundle = new Bundle();
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean isLoading = false;
    int page = 0;
    boolean limitData=false;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FragmentCollection fragmentCollection;
    Fragment_Product fragment_product;
    Fragment_GioHang fragment_gioHang;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection,container,false);
        AnhXa(view);
        AcctionToolBar();
        bundle = getArguments();
        int load_int_collection = bundle.getInt("idbosuutap", -1);
        txt_tenbosuutap.setText(bundle.getString("tenbosuutap"));
        thongTinSanPhamMois=new ArrayList<>();
        soluong.setText(FragmentMainActivity.getcounthang(MainActivity.danhSachHangduocChon)+"");
        progressBar.setVisibility(View.INVISIBLE);
        product_in_collectionAdapter = new ProductAdapter(getContext(),R.layout.product_layout,thongTinSanPhamMois,this);
        new LoadCollection(getContext(),thongTinSanPhamMois,recyclerView_bst,load_int_collection,product_in_collectionAdapter).execute(page);
        recyclerView_bst.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        control();

        return view;
    }
    private void AcctionToolBar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentMainActivity.countsoluong.setText(FragmentMainActivity.getcounthang(MainActivity.danhSachHangduocChon)+"");
                fragmentManager=getFragmentManager();
                fragmentTransaction =fragmentManager.beginTransaction();
                fragmentCollection = (FragmentCollection) getFragmentManager().findFragmentByTag("bo suu tap");
                fragmentTransaction.remove(fragmentCollection);
                fragmentTransaction.commit();
                MainActivity.frameLayout_activityThuong.setVisibility(View.INVISIBLE);
                MainActivity.frameLayout_trangChu.setVisibility(View.VISIBLE);
            }
        });
    }

    private void control() {
        soluong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("activity","CollectionActivity");
                fragmentManager =getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragment_gioHang = new Fragment_GioHang();
                fragment_gioHang.setArguments(bundle);
                fragmentTransaction.add(R.id.framelayout_activiti_thuong,fragment_gioHang,"gio hang");
                fragmentTransaction.addToBackStack("gio hang");
                fragmentTransaction.commit();
            }
        });
        cards.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                bundle.putString("activity","CollectionActivity");
                fragmentManager =getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragment_gioHang = new Fragment_GioHang();
                fragment_gioHang.setArguments(bundle);
                fragmentTransaction.add(R.id.framelayout_activiti_thuong,fragment_gioHang,"gio hang");
                fragmentTransaction.addToBackStack("gio hang");
                fragmentTransaction.commit();
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

    @SuppressLint("HandlerLeak")
    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                new LoadCollection(getContext(),thongTinSanPhamMois,recyclerView_bst,bundle.getInt("idbosuutap"),product_in_collectionAdapter).execute(++page);
                progressBar.setVisibility(View.INVISIBLE);
                isLoading=false;
            }
        }
    }

    private void AnhXa(View view) {
        recyclerView_bst = (RecyclerView) view.findViewById(R.id.rclv_sanphambt);
        soluong = (TextView) view.findViewById(R.id.txt_hang_trong_gioMain2);
        txt_tenbosuutap = (TextView) view.findViewById(R.id.txt_tenbosuutap);
        cards = (ImageButton) view.findViewById(R.id.imgBtnGiohangcoll);
        toolbar = (Toolbar) view.findViewById(R.id.imgbtn_comeback);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_loadMore_collection);
    }

    @Override
    public void onItemClicked(int position) {
        bundle.clear();
        bundle.putString("activity", "CollectionActivity");
        bundle.putSerializable("productDuocChon",thongTinSanPhamMois.get(position));
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment_product = new Fragment_Product();
        fragment_product.setArguments(bundle);
        fragmentTransaction.add(R.id.framelayout_activiti_thuong,fragment_product,"firt product");
        fragmentTransaction.addToBackStack("product");
        fragmentTransaction.commit();
    }
}
