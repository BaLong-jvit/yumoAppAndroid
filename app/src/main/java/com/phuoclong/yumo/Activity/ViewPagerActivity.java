package com.phuoclong.yumo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.phuoclong.yumo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewPagerActivity extends AppCompatActivity {

    private static ArrayList<String> listHinhAnh;
    static int Hinh_thu;
    private SectionsPagerAdapterfull mSectionsPagerAdapter;

    ViewPager viewPager;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        Intent intent = getIntent();
        listHinhAnh = new ArrayList<>();
        listHinhAnh = intent.getStringArrayListExtra("listHinhAnh");
        Hinh_thu = intent.getIntExtra("HinhDaChon",-1);
        mSectionsPagerAdapter = new SectionsPagerAdapterfull(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById( R.id.vpg_hinhanhFulScreen);
        viewPager.setAdapter(mSectionsPagerAdapter);
        toolbar = (Toolbar) findViewById(R.id.tb_pager);
        ActiontoolBar(toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void ActiontoolBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static class PlaceholderFragmentfull extends Fragment {

        private static final String IMAGE  = "Hinh_thu";

        private ArrayList<String> DanhSachHinhanh;
        private int hinh_thu;

        private ImageView imageView;

        public PlaceholderFragmentfull(ArrayList<String> danhsachhinhanh, int hinh_thu) {
            this.DanhSachHinhanh=danhsachhinhanh;
            this.hinh_thu=hinh_thu;
        }

        // Method static dạng singleton, cho phép tạo fragment mới, lấy tham số đầu vào để cài đặt màu sắc.
        public static PlaceholderFragmentfull newInstance(int hinhthu) {
           PlaceholderFragmentfull fragment = new PlaceholderFragmentfull(listHinhAnh,Hinh_thu);
            Bundle args = new Bundle();
            args.putInt(IMAGE, hinhthu);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragmentfull_main, container, false);
            imageView = (ImageView) rootView.findViewById(R.id.section_label1);
//            int hinhanh;
//            if(getArguments().getInt(IMAGE)>listHinhAnh.size()){
//                hinhanh = getArguments().getInt(IMAGE);
//            }else hinhanh = getArguments().getInt(IMAGE);
            //trong list, bắt đầu từ số 0
            Picasso.with(getContext()).load(listHinhAnh.get(getArguments().getInt(IMAGE))).into(imageView);
            return rootView;
        }
    }
    public class SectionsPagerAdapterfull extends FragmentPagerAdapter {

        public SectionsPagerAdapterfull(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // vì position cũng bắt đầu từ số 0.
            return PlaceholderFragmentfull.newInstance(position);
        }
        @Override
        public int getCount() {
            return listHinhAnh.size();
        }
    }
}
