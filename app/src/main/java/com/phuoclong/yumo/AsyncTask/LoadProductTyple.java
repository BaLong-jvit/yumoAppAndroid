package com.phuoclong.yumo.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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

public class LoadProductTyple extends AsyncTask<Integer,Void, ArrayList<Product>> {
        ArrayList<Product> productArrayList;
    Context context;
    ProductAdapter productAdapter;
    RecyclerView recyclerView;
    String idcategory;
    LinearLayoutManager mlayout;


    public LoadProductTyple(ArrayList<Product> productArrayList, Context context, ProductAdapter productAdapter, RecyclerView recyclerView, String idcategory) {
        this.productArrayList = productArrayList;
        this.context = context;
        this.productAdapter = productAdapter;
        this.recyclerView = recyclerView;
        this.idcategory=idcategory;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Product> doInBackground(Integer... integers) {
        TaoSanPham(integers[0],productArrayList);
        return productArrayList;
    }

    private void TaoSanPham(Integer integer, ArrayList<Product> productArrayList) {
        int Min = 20 *integer;
        Server server = new Server();
        Connection connection = server.connection();
        Statement statement;
        ResultSet resultSet;
        String Query = "SELECT t1.*, t2.TenThuongHieu  FROM dbo.LoaiSanPham t1" +
                "INNER JOIN dbo.tb_ThuongHieu t2 ON t2.idThuongHieu = t1.idThuongHieu " +
                "WHERE t1.IDNhomHangHoa=" + idcategory + "ORDER BY IDLoaiSanPham desc OFFSET"+Min+" ROWS FETCH NEXT 20 ROWS ONLY";
        if (connection != null) {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(Query);
                if (resultSet != null) {
                    while (resultSet.next()) {
                        productArrayList.add(new Product(resultSet.getInt("IDLoaiSanPham") + "", resultSet.getInt("IDNhomHangHoa") + "",
                                        resultSet.getString("MaLoaiSanPham"), resultSet.getString("MaBarCode"), resultSet.getString("TenLoaiSanPham"),
                                        resultSet.getString("TenLoaiSanPhamKhongDau"), resultSet.getInt("idBoSuuTap") + "",
                                        resultSet.getString("TenThuongHieu"), resultSet.getString("ChieuCao"), resultSet.getFloat("HoaHongCTV"),
                                        resultSet.getFloat("GHTKMin"), resultSet.getFloat("GHTKMax"), resultSet.getFloat("GiaBanSanPham"),
                                        resultSet.getFloat("GiaBanSi"), resultSet.getFloat("GiaBanLe"), resultSet.getString("TinhTrang"),
                                        resultSet.getString("HinhAnh"), resultSet.getString("MoTaNgan"), resultSet.getString("MoTaChiTiet")
                                )
                        );
                    }
                    connection.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Product> productArrayList) {
        super.onPostExecute(productArrayList);
        if (productArrayList.size() < 1) {
            Toast.makeText(context, "Chưa có sản phẩm được giới thiệu", Toast.LENGTH_LONG).show();
        } else {
            productAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(productAdapter);
            mlayout = new GridLayoutManager(context, 2);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(mlayout);
        }
    }
}
