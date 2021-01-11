package com.phuoclong.yumo.AsyncTask;

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

public class LoadCollection extends AsyncTask<Integer, Void , ArrayList<Product>> {
    Context context;
    ArrayList<Product> listProduct;
    RecyclerView recyclerView;
    int idCollection;
    ProductAdapter productAdapter;
    LinearLayoutManager mLayoutManager;

    public LoadCollection(Context context, ArrayList<Product> listProduct, RecyclerView recyclerView, int idCollection, ProductAdapter productAdapter) {
        this.context = context;
        this.listProduct = listProduct;
        this.recyclerView = recyclerView;
        this.idCollection = idCollection;
        this.productAdapter = productAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Product> doInBackground(Integer... integers) {
        taoListCollection(integers[0],listProduct);
        return listProduct;
    }

    private void taoListCollection(Integer integer, ArrayList<Product> listProduct) {
        int Min = integer * 20;
        String query = "SELECT tb1.IDLoaiSanPham,tb1.IDNhomHangHoa,tb1.MaLoaiSanPham,tb1.MaBarcode,tb1.TenLoaiSanPham," +
                "tb1.TenLoaiSanPhamKhongDau,tb1.idBoSuuTap,tb1.ChieuCao,tb1.HoaHongCTV,tb1.GHTKMin,tb1.GHTKMax,tb1.GiaBanSanPham,tb1.GiaBanSi,tb1.GiaBanLe,tb1.TinhTrang," +
                "tb1.HinhAnh,tb1.MoTaNgan,tb1.MoTaChiTiet,tb2.TenThuongHieu FROM dbo.LoaiSanPham tb1 INNER JOIN dbo.tb_ThuongHieu tb2 ON tb2.idThuongHieu = tb1.idThuongHieu " +
                "WHERE tb1.idBoSuuTap = "+idCollection+" ORDER BY IDLoaiSanPham desc OFFSET "+Min+" ROWS FETCH NEXT 20 ROWS ONLY";
        Server server=new Server();
        Connection connect=server.connection();
        try {
            if (connect !=null) {
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if(resultSet !=null && resultSet.getFetchSize()>0) {
                    while (resultSet.next()) {
                        listProduct.add(new Product(resultSet.getInt("IDLoaiSanPham") + "", resultSet.getInt("IDNhomHangHoa") + "",
                                        resultSet.getString("MaLoaiSanPham"), resultSet.getString("MaBarCode"), resultSet.getString("TenLoaiSanPham"),
                                        resultSet.getString("TenLoaiSanPhamKhongDau"), resultSet.getInt("idBoSuuTap") + "",
                                        resultSet.getString("TenThuongHieu"), resultSet.getString("ChieuCao"), resultSet.getFloat("HoaHongCTV"),
                                        resultSet.getFloat("GHTKMin"), resultSet.getFloat("GHTKMax"), resultSet.getFloat("GiaBanSanPham"),
                                        resultSet.getFloat("GiaBanSi"), resultSet.getFloat("GiaBanLe"), resultSet.getString("TinhTrang"),
                                        resultSet.getString("HinhAnh"), resultSet.getString("MoTaNgan"), resultSet.getString("MoTaChiTiet")
                                )
                        );

                    }
                    connect.close();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPostExecute(ArrayList<Product> productArrayList) {
        super.onPostExecute(productArrayList);
        productAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(productAdapter);
        mLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
    }
}
