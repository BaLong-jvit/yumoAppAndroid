package com.phuoclong.yumo.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phuoclong.yumo.Activity.MainActivity;
import com.phuoclong.yumo.Adapter.ProductAdapter;
import com.phuoclong.yumo.AsyncTask.LoadBoSuuTap;
import com.phuoclong.yumo.AsyncTask.LoadProduct;
import com.phuoclong.yumo.Model.Collection;
import com.phuoclong.yumo.Model.Product;
import com.phuoclong.yumo.R;

import java.util.ArrayList;

public class FragmentHome extends Fragment implements ProductAdapter.OnCallBack {
    private EditText timkiem;
    private ImageButton imageButton_search;
    private TextView imageButton_Yumomall,imageButton_discount, imageButton_lucky;
    private GridView gridView_collection;
    RecyclerView recyclerView_sanphammoi;
    private ArrayList<Collection> listCollection = new ArrayList<>();
    ProgressBar progressBar;
    ArrayList<Product> listProduct;
    LinearLayout linearLayout_contenthome;
    FragmentCollection fragmentCollection;
    ProductAdapter productAdapter;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    private mHandler mHandler = new mHandler();
    boolean isLoading = false;
    Bundle bundle = new Bundle();
    int page = 0;
    FragmentTransaction fragmentTransaction;
    Fragment_Product fragment_product;
    FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        AnhXa(view);
        {
            new LoadBoSuuTap(listCollection,getContext(),gridView_collection).execute("SELECT idBoSuuTap,MaBoSuuTap,TenBoSuuTap,LinkAnh,An,NgayTao FROM tb_BoSuuTap");
        }
        {
            listProduct = new ArrayList<>();
            progressBar.setVisibility(View.INVISIBLE);
            productAdapter =new ProductAdapter(getContext(),R.layout.product_layout, listProduct,  this);
            new LoadProduct(listProduct,getContext(),recyclerView_sanphammoi,productAdapter).execute(page);
            recyclerView_sanphammoi.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    mLayoutManager = (GridLayoutManager)recyclerView.getLayoutManager();
                    if(dy > 0){ // check cuộn xuống
                        visibleItemCount = mLayoutManager.getChildCount();
                        totalItemCount = mLayoutManager.getItemCount();
                        pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                     //  Toast.makeText(getContext(),""+visibleItemCount+" "+totalItemCount+" "+pastVisiblesItems ,Toast.LENGTH_SHORT).show();
                        if(((visibleItemCount + pastVisiblesItems) >= totalItemCount - 1)&&(totalItemCount != 0)&&(isLoading==false)){
                            Toast.makeText(getContext(),"ok vo dau roi",Toast.LENGTH_SHORT).show();
                            isLoading = true;
                            progressBar.setVisibility(View.VISIBLE);
                            ThreadData threadData = new ThreadData();
                            threadData.start();

                        }
                    }
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
        }
        Control();
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
    @SuppressLint("HandlerLeak")
    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what)
            {
                case 0:
                    break;
                case 1:
                    isLoading=false;
                    new LoadProduct(listProduct,getContext(),recyclerView_sanphammoi,productAdapter).execute(++page);
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
            }
            super.handleMessage(msg);
        }
    }
    private void Control() {
        gridView_collection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bundle.clear();
                bundle.putInt("idbosuutap", position + 1);
                bundle.putString("tenbosuutap",listCollection.get(position).getName_collection() );
                bundle.putString("activity", "home");
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentCollection = new FragmentCollection();
                fragmentCollection.setArguments(bundle);
                fragmentTransaction.replace(R.id.framelayout_activiti_thuong,fragmentCollection,"bo suu tap");
                MainActivity.frameLayout_trangChu.setVisibility(View.INVISIBLE);
                MainActivity.frameLayout_activityThuong.setVisibility(View.VISIBLE);
                fragmentTransaction.commit();
            }
        });
        imageButton_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chuoitim = timkiem.getText().toString().trim();
                bundle = new Bundle();
                bundle.putString("tukhoa",chuoitim);
                bundle.putString("activity", "home");
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment_timKiem fragment_timKiem = new Fragment_timKiem();
                fragment_timKiem.setArguments(bundle);
                fragmentTransaction.replace(R.id.framelayout_activiti_thuong,fragment_timKiem,"tim kiem");
                MainActivity.frameLayout_trangChu.setVisibility(View.INVISIBLE);
                MainActivity.frameLayout_activityThuong.setVisibility(View.VISIBLE);
                fragmentTransaction.commit();
            }
        });
    }
    @Override
    public void onItemClicked(int position) {
        bundle = new Bundle();
        bundle.putString("activity", "home");
//        bundle.putString("SanPhamCoID", listProduct.get(position).getIDloaiSanPham()+"");
        fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragment_product = new Fragment_Product();
        bundle.putSerializable("productDuocChon",listProduct.get(position));
        fragment_product.setArguments(bundle);
        fragmentTransaction.add(R.id.framelayout_activiti_thuong,fragment_product,"product");
        fragmentTransaction.addToBackStack("product");
        MainActivity.frameLayout_trangChu.setVisibility(View.INVISIBLE);
        MainActivity.frameLayout_activityThuong.setVisibility(View.VISIBLE);
        fragmentTransaction.commit();
    }


    private void AnhXa(View view) {
        timkiem = (EditText) view.findViewById(R.id.edtSearch);
        imageButton_Yumomall = (TextView) view.findViewById(R.id.iB_YumoMall);
        imageButton_search = (ImageButton) view.findViewById(R.id.imgBtn_Search);
        imageButton_discount = (TextView) view.findViewById(R.id.iB_DiscountCode);
        imageButton_lucky = (TextView) view.findViewById(R.id.iB_Vongquay);
        gridView_collection = (GridView) view.findViewById(R.id.gV_collection);
        recyclerView_sanphammoi = (RecyclerView) view.findViewById(R.id.rcV_sanPhamMoi);
        linearLayout_contenthome = (LinearLayout) view.findViewById(R.id.content_home);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_loadMore);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}