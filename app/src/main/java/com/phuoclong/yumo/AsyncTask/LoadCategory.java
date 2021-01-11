package com.phuoclong.yumo.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.phuoclong.yumo.Adapter.CategoryAdapter;
import com.phuoclong.yumo.Model.Category;
import com.phuoclong.yumo.R;
import com.phuoclong.yumo.Until.Server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;

public class LoadCategory extends AsyncTask<String,Void, ArrayList<Category>> {
    ArrayList<Category> listCategory;
    Context context;
    ListView listView;
    CategoryAdapter categoryAdapter;

    public LoadCategory(ArrayList<Category> listCategory, Context context, ListView listView) {
        this.listCategory = listCategory;
        this.context = context;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Category> doInBackground(String... strings) {
        Server server=new Server();
        Connection connect=server.connection();
        Statement st1;
        ResultSet rs;
        try {
            if (connect !=null) {
                st1 = connect.createStatement();
                //"SELECT IDNhomHangHoa, TenNhomHangHoa, LinkAnh, An, GhiChu, idCapCha FROM dbo.NhomHangHoa";
                rs = st1.executeQuery(strings[0]);
                if(rs !=null){
                    while (rs.next()){
                        listCategory.add(new Category(
                                        rs.getInt("IDNhomHangHoa")+"",
                                        rs.getString("TenNhomHangHoa"),
                                        rs.getString("LinkAnh"),
                                        rs.getBoolean("An"),
                                        rs.getString("GhiChu"),
                                        rs.getString("idCapCha")
                                )
                        );
                    }
                    connect.close();
                }else {
                    Toast.makeText(context,"không tạo được array collection", LENGTH_LONG).show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listCategory;
    }

    @Override
    protected void onPostExecute(ArrayList<Category> categories) {
        super.onPostExecute(categories);
        categoryAdapter =new CategoryAdapter(context, R.layout.category_item,listCategory);
        listView.setAdapter(categoryAdapter);
    }
}
