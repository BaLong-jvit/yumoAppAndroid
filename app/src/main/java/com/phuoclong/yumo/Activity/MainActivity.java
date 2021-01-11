package com.phuoclong.yumo.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.phuoclong.yumo.AsyncTask.LoadFragmentHome;
import com.phuoclong.yumo.Model.AccountCTV;
import com.phuoclong.yumo.Model.GioHang;
import com.phuoclong.yumo.R;
import com.phuoclong.yumo.fragment.FragmentMainActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    public static Random random;
    public static boolean isUser = false;
    public static AccountCTV account;
    public static ArrayList<GioHang> danhSachHangduocChon;
    public static int soluongsanpham;
    public static final int GET_COUNT=1001;
    static Handler mhandler;
    @SuppressLint("StaticFieldLeak")
    public static FrameLayout frameLayout_trangChu,frameLayout_activityThuong;
    FragmentMainActivity fragmentMainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        frameLayout_trangChu.setVisibility(View.VISIBLE);
        frameLayout_activityThuong.setVisibility(View.INVISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentMainActivity = new FragmentMainActivity();
        new LoadFragmentHome(getApplicationContext(),frameLayout_trangChu,fragmentManager).execute(fragmentMainActivity);
    }
    public void frag_main(View view){
        fragmentMainActivity.frag_main(view);
    }
    @SuppressLint("HandlerLeak")
    private void AnhXa() {
        frameLayout_trangChu = (FrameLayout) findViewById(R.id.framelayout_trangChu);
        frameLayout_activityThuong=(FrameLayout) findViewById(R.id.framelayout_activiti_thuong);
        if (danhSachHangduocChon ==null){
            danhSachHangduocChon = new ArrayList<>();
        }
        mhandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case GET_COUNT:
                        MainActivity.soluongsanpham = msg.arg1;
                        break;
                    default:
                        break;
                }
            }
        };
    }
    public static String formartMoney(float myNumber) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
       return currencyVN.format(myNumber);
    }
    public static class ThreadGetCount implements Runnable{
        @Override
        public void run() {
                int ok = 0;
                Message message = new Message();
                if (danhSachHangduocChon != null) {
                    for (int i = 0; i < danhSachHangduocChon.size(); i++) {
                        ok += danhSachHangduocChon.get(i).getSoluongSp();
                    }
                    message.what = GET_COUNT;
                    message.arg1 = ok;
                } else {
                    message.what = 1000;
                    message.arg1 = 0;
                }
                mhandler.sendMessage(message);
        }
    }

}

