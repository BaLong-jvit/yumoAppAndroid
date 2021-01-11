package com.phuoclong.yumo.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.phuoclong.yumo.Activity.MainActivity;
import com.phuoclong.yumo.Adapter.GioHangAdapter;
import com.phuoclong.yumo.AsyncTask.LoadGioHang;
import com.phuoclong.yumo.R;

public class Fragment_GioHang extends Fragment {
    TextView txt_msgBox_giohangtrong;
    public static TextView txt_thanhtoan;
    ListView listView;
    Button magiamgia, muangay, tieptuc;
    Toolbar toolbar;
    GioHangAdapter gioHangAdapter;
    Bundle bundle = new Bundle();
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment_GioHang fragment_gioHang;
    Fragment_Chondiachi fragment_chondiachi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_giohang,container,false);
        AnhXa(view);
        ActionToolbar();
        if(MainActivity.danhSachHangduocChon.size()<1){
            txt_msgBox_giohangtrong.setVisibility(View.VISIBLE);
            txt_msgBox_giohangtrong.setText("Giỏ hàng đang trống");
            listView.setVisibility(View.INVISIBLE);
        }else {
            float tongtien =0;
            for (int i=0;i<MainActivity.danhSachHangduocChon.size();i++){
                tongtien = tongtien + MainActivity.danhSachHangduocChon.get(i).getTongThanhToan();
            }
            txt_msgBox_giohangtrong.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
            new LoadGioHang(getContext(),MainActivity.danhSachHangduocChon,R.layout.layout_sanpham_giohang,gioHangAdapter,listView).execute(true);
            txt_thanhtoan.setText(MainActivity.formartMoney(tongtien));
            clickOnItemListView();
            ControlButton();
        }
        return view;
    }
    private void clickOnItemListView() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Xóa");
                builder.setMessage("Xóa sản phâm?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.danhSachHangduocChon.remove(position);
                        if(MainActivity.danhSachHangduocChon.size()<=0){
                            txt_msgBox_giohangtrong.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.INVISIBLE);
                            txt_msgBox_giohangtrong.setText("Giỏ hàng đang trống");
                        }else {
                            gioHangAdapter.notifyDataSetChanged();
                        }
                        float tongtien =0;
                        for (int i=0;i<MainActivity.danhSachHangduocChon.size();i++){
                            tongtien += MainActivity.danhSachHangduocChon.get(i).getTongThanhToan();
                        }
                        txt_thanhtoan.setText(MainActivity.formartMoney(tongtien));
                    }

                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            }
        });
    }
   // gioHangAdapter = new GioHangAdapter(getContext(),R.layout.layout_sanpham_giohang,MainActivity.danhSachHangduocChon);
     //               listView.setAdapter(gioHangAdapter);

    private void ControlButton() {
        tieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishGiohangActivity();
            }
        });
        muangay.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                if(MainActivity.danhSachHangduocChon.size()>0){
                    fragmentManager = getFragmentManager();
                    fragmentTransaction=fragmentManager.beginTransaction();
                    fragment_chondiachi = new Fragment_Chondiachi();
                    fragmentTransaction.add(R.id.framelayout_activiti_thuong,fragment_chondiachi,"chon dia chi");
                    fragmentTransaction.addToBackStack("chon dia chi");
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(getContext(),"Giỏ hàng đang trống!",Toast.LENGTH_LONG);
                }
            }
        });
    }

    private void finishGiohangActivity() {
        bundle = getArguments();
        String Activity = bundle.getString("activity");
        switch (Activity){
            case "home":
                FragmentMainActivity.countsoluong.setText(FragmentMainActivity.getcounthang(MainActivity.danhSachHangduocChon)+"");
                fragmentManager=getFragmentManager();
                fragmentTransaction =fragmentManager.beginTransaction();
                fragment_gioHang = (Fragment_GioHang) getFragmentManager().findFragmentByTag("gio hang");
                fragmentTransaction.remove(fragment_gioHang);
                MainActivity.frameLayout_trangChu.setVisibility(View.VISIBLE);
                MainActivity.frameLayout_activityThuong.setVisibility(View.INVISIBLE);
                fragmentTransaction.commit();
                break;
            case "ProductActivity":
                Fragment_Product.soluonghang.setText(FragmentMainActivity.getcounthang(MainActivity.danhSachHangduocChon)+"");
                fragmentManager=getFragmentManager();
                fragmentTransaction =fragmentManager.beginTransaction();
                fragment_gioHang = (Fragment_GioHang) getFragmentManager().findFragmentByTag("gio hang");
                fragmentTransaction.remove(fragment_gioHang);
                fragmentTransaction.commit();
                break;
            case "ProductTypeActivity":
                FragmentProductType.txt_soluonghang_category.setText(FragmentMainActivity.getcounthang(MainActivity.danhSachHangduocChon)+"");
                fragmentManager=getFragmentManager();
                fragmentTransaction =fragmentManager.beginTransaction();
                fragment_gioHang = (Fragment_GioHang) getFragmentManager().findFragmentByTag("gio hang");
                fragmentTransaction.remove(fragment_gioHang);
                fragmentTransaction.commit();
                break;
            case "CollectionActivity":
                FragmentCollection.soluong.setText(FragmentMainActivity.getcounthang(MainActivity.danhSachHangduocChon)+"");
                fragmentManager=getFragmentManager();
                fragmentTransaction =fragmentManager.beginTransaction();
                fragment_gioHang = (Fragment_GioHang) getFragmentManager().findFragmentByTag("gio hang");
                fragmentTransaction.remove(fragment_gioHang);
                fragmentTransaction.commit();
                break;
        }

    }

    private void ActionToolbar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishGiohangActivity();
            }
        });
    }
    private void AnhXa(View view) {
        txt_msgBox_giohangtrong = (TextView) view.findViewById(R.id.msgBox_giohangtrong);
        listView = (ListView) view.findViewById(R.id.list_sanphamgiohang);
        magiamgia = (Button) view.findViewById(R.id.btn_magiamgia);
        muangay = (Button) view.findViewById(R.id.btn_dathang);
        tieptuc = (Button) view.findViewById(R.id.btn_tieptucmuahang);
        toolbar = (Toolbar) view.findViewById(R.id.tb_giohang);
        txt_thanhtoan = (TextView) view.findViewById(R.id.txt_total_money);
    }
}
