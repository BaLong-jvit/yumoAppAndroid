package com.phuoclong.yumo.AsyncTask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.phuoclong.yumo.Activity.ViewPagerActivity;
import com.phuoclong.yumo.R;
import com.phuoclong.yumo.Until.Server;
import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LoadHinhAnhSanPham extends AsyncTask<String,Void, ArrayList<String>> {
    static ArrayList<String> listHinhAnh;
    Context context;
    private ViewPager mViewPager;
    SectionsPagerAdapter mSectionsPagerAdapter;

    public LoadHinhAnhSanPham(ArrayList<String> listHinhAnh, Context context, ViewPager mViewPager) {
        this.listHinhAnh = listHinhAnh;
        this.context = context;
        this.mViewPager = mViewPager;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        Server server = new Server();
        Connection connection =server.connection();

     //   String Query = "SELECT LinkHinh, isAnhChinh FROM tb_HinhAnhLoaiSP WHERE IDLoaiSanPham = "+ Id;
        if(connection!= null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(strings[0]);
                if(resultSet!=null){
                    while (resultSet.next()){
                        if(resultSet.getInt("isAnhChinh")==0){
                            listHinhAnh.add(resultSet.getString("LinkHinh"));
                        }
                    }
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listHinhAnh;
    }


    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
        mSectionsPagerAdapter = new SectionsPagerAdapter(((AppCompatActivity)context).getSupportFragmentManager());
        mViewPager = (ViewPager) ((AppCompatActivity) context).findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) ((AppCompatActivity) context).findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }
    public static class PlaceholderFragment extends Fragment {

        private static final String IMAGE  = "Hinh_thu";
        ArrayList<String> danhsachhinhanh;
        private ImageView imageView;

        PlaceholderFragment(ArrayList<String> danhsachhinhanh) {
            this.danhsachhinhanh=danhsachhinhanh;
        }

        // Method static dạng singleton, cho phép tạo fragment mới, lấy tham số đầu vào để cài đặt màu sắc.
        static PlaceholderFragment newInstance(int hinhthu) {
            PlaceholderFragment fragment = new PlaceholderFragment(listHinhAnh);
            Bundle args = new Bundle();
            args.putInt(IMAGE, hinhthu);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            imageView = (ImageView) rootView.findViewById(R.id.section_label);

            //trong list, bắt đầu từ số 0
            Picasso.with(getContext()).load(listHinhAnh.get(getArguments().getInt(IMAGE))).into(imageView);

            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ViewPagerActivity.class);
                    intent.putExtra("HinhDaChon", getArguments().getInt(IMAGE));
                    intent.putExtra("listHinhAnh",listHinhAnh);
                    startActivity(intent);
                }
            });
            return rootView;
        }
    }
    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // vì position cũng bắt đầu từ số 0.
            return PlaceholderFragment.newInstance(position);
        }
        @Override
        public int getCount() {
            return listHinhAnh.size();
        }
    }

}
