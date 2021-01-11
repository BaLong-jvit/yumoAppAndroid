package com.phuoclong.yumo.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.Toast;

import com.phuoclong.yumo.Adapter.CollectionAdapter;
import com.phuoclong.yumo.Model.Collection;
import com.phuoclong.yumo.R;
import com.phuoclong.yumo.Until.Server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;

public class LoadBoSuuTap extends AsyncTask<String, Void, ArrayList<Collection>> {
    ArrayList<Collection> listCollection;
    Context context;
    GridView gridView_collection;
    CollectionAdapter collectionAdapter;

    public LoadBoSuuTap(ArrayList<Collection> listCollection, Context context, GridView gridView_collection) {
        this.listCollection = listCollection;
        this.context = context;
        this.gridView_collection = gridView_collection;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Collection> doInBackground(String... strings) {
        Server server=new Server();
        Connection connect=server.connection();
        Statement st1;
        ResultSet rs;
        try {
            if (connect !=null) {
                st1 = connect.createStatement();
               // = "SELECT idBoSuuTap,MaBoSuuTap,TenBoSuuTap,LinkAnh,An,NgayTao FROM tb_BoSuuTap";
                rs = st1.executeQuery(strings[0]);
                if(rs !=null){
                    while (rs.next()){
                        int soluong=0;
                        //if(rs.getInt("SoLuong")!=null) soluong=rs.getInt("SoLuong");
                        listCollection.add(new com.phuoclong.yumo.Model.Collection(rs.getInt("idBoSuuTap")+"",rs.getString("MaBoSuuTap"),
                                        rs.getString("TenBoSuuTap"),soluong,rs.getString("LinkAnh"),
                                        rs.getBoolean("An"),rs.getString("NgayTao")
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
        return listCollection;
    }

    @Override
    protected void onPostExecute(ArrayList<Collection> collections) {
        super.onPostExecute(collections);
        collectionAdapter = new CollectionAdapter(context, R.layout.layout_collection, collections);
        gridView_collection.setAdapter(collectionAdapter);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
