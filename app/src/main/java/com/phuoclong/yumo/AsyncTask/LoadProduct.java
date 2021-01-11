package com.phuoclong.yumo.AsyncTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phuoclong.yumo.Adapter.ProductAdapter;
import com.phuoclong.yumo.Model.Product;
import com.phuoclong.yumo.Until.Server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LoadProduct extends AsyncTask<Integer,Void, ArrayList<Product>> {
    private ArrayList<Product> listProduct;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView recyclerView_sanphammoi;
    private ProductAdapter productAdapter;
    private LinearLayoutManager mLayoutManager;
    public LoadProduct(ArrayList<Product> listProduct, Context context, RecyclerView recyclerView_sanphammoi,ProductAdapter productAdapter) {
        this.listProduct = listProduct;
        this.context = context;
        this.recyclerView_sanphammoi = recyclerView_sanphammoi;
        this.productAdapter = productAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @SuppressLint("WrongThread")
    @Override
    protected ArrayList<Product> doInBackground(Integer... integers) {
        TaoMangSanPhamMoi(integers[0],listProduct);
        return listProduct;
    }

    private void TaoMangSanPhamMoi(int page, ArrayList<Product> list) {
        int Min = 20 * page;
        StringBuilder stringBuilderQuery = new StringBuilder("SELECT t1.IDLoaiSanPham,t1.IDNhomHangHoa,t1.MaLoaiSanPham,t1.MaBarcode,t1.TenLoaiSanPham," +
                "t1.TenLoaiSanPhamKhongDau,t1.idBoSuuTap,t1.ChieuCao,t1.HoaHongCTV,t1.GHTKMin,t1.GHTKMax,t1.GiaBanSanPham,t1.GiaBanSi,t1.GiaBanLe,t1.TinhTrang," +
                "t1.HinhAnh,t1.MoTaNgan,t1.MoTaChiTiet, t2.TenThuongHieu  FROM dbo.LoaiSanPham t1 INNER JOIN dbo.tb_ThuongHieu t2 ON t2.idThuongHieu = t1.idThuongHieu " +
                "ORDER BY IDLoaiSanPham desc OFFSET "+ Min +"ROWS FETCH NEXT 20 ROWS ONLY");
        Server server=new Server();
        Connection connect=server.connection();
        Statement statement1;
        ResultSet resultSet;
        try {
            if (connect !=null) {
                statement1 = connect.createStatement();
                resultSet = statement1.executeQuery(stringBuilderQuery.toString());
                if(resultSet !=null && resultSet.getFetchSize()>0){
                    while (resultSet.next()){
                        list.add(new Product(resultSet.getInt("IDLoaiSanPham")+"",resultSet.getInt("IDNhomHangHoa")+"",
                                        resultSet.getString("MaLoaiSanPham"),resultSet.getString("MaBarcode"),resultSet.getString("TenLoaiSanPham"),
                                        resultSet.getString("TenLoaiSanPhamKhongDau"),resultSet.getInt("idBoSuuTap")+"",
                                        resultSet.getString("TenThuongHieu"),resultSet.getString("ChieuCao"), resultSet.getFloat("HoaHongCTV"),
                                        resultSet.getFloat("GHTKMin"),resultSet.getFloat("GHTKMax"),resultSet.getFloat("GiaBanSanPham"),
                                        resultSet.getFloat("GiaBanSi"),resultSet.getFloat("GiaBanLe"),resultSet.getString("TinhTrang"),
                                        resultSet.getString("HinhAnh"),resultSet.getString("MoTaNgan"),resultSet.getString("MoTaChiTiet")
                                )
                        );
                    }
                }
                connect.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Product> products) {
        super.onPostExecute(products);
        productAdapter.notifyDataSetChanged();
        recyclerView_sanphammoi.setAdapter(productAdapter);
        mLayoutManager = new GridLayoutManager(context, 2);
        recyclerView_sanphammoi.setHasFixedSize(true);
        recyclerView_sanphammoi.setLayoutManager(mLayoutManager);
    }
}
