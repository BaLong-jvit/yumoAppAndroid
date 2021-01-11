package com.phuoclong.yumo.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.phuoclong.yumo.Adapter.GioHangAdapter;
import com.phuoclong.yumo.Model.GioHang;

import java.util.ArrayList;

public class LoadGioHang extends AsyncTask<Boolean, Void, ArrayList<GioHang>> {

    public LoadGioHang(Context context, ArrayList<GioHang> danhsachGioHang, int layout, GioHangAdapter gioHangAdapter, ListView listView) {
        this.context = context;
        this.danhsachGioHang = danhsachGioHang;
        this.layout = layout;
        this.gioHangAdapter = gioHangAdapter;
        this.listView = listView;
    }

    Context context;
    ArrayList<GioHang> danhsachGioHang;
    int layout;
    GioHangAdapter gioHangAdapter;
    ListView listView;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }
    @Override
    protected ArrayList<GioHang> doInBackground(Boolean... booleans) {
        if(booleans[0])
        return danhsachGioHang;
        else return null;
    }




    @Override
    protected void onPostExecute(ArrayList<GioHang> gioHangs) {
        super.onPostExecute(gioHangs);
        gioHangAdapter = new GioHangAdapter(context,layout,danhsachGioHang);
        listView.setAdapter(gioHangAdapter);
    }
}
