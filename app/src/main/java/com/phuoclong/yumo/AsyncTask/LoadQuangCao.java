package com.phuoclong.yumo.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.phuoclong.yumo.Until.Server;
import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LoadQuangCao extends AsyncTask<String,Void, ArrayList<String>> {
    ArrayList<String> listQuangCao;
    Context context;
    ViewFlipper viewFlipper;

    public LoadQuangCao(ArrayList<String> listQuangCao, Context context, ViewFlipper viewFlipper) {
        this.listQuangCao = listQuangCao;
        this.context = context;
        this.viewFlipper = viewFlipper;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        TaoQuangCao(strings[0],listQuangCao);
        return listQuangCao;
    }

    private void TaoQuangCao(String string, ArrayList<String> listQuangCao) {
        Server server = new Server();
        Connection connection = server.connection();
        Statement statement;
        ResultSet resultSet;
        String query = "SELECT * FROM dbo.QuangCaoNhomHang WHERE IDNhomHangHoa =" + string;
        if (connection != null) {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);
                if (resultSet != null) {
                    while (resultSet.next()) {
                        listQuangCao.add(resultSet.getString("HinhAnh1"));
                        listQuangCao.add(resultSet.getString("HinhAnh2"));
                        listQuangCao.add(resultSet.getString("HinhAnh3"));
                        listQuangCao.add(resultSet.getString("HinhAnh4"));
                        listQuangCao.add(resultSet.getString("HinhAnh5"));
                    }
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
        for (int i = 0; i < listQuangCao.size(); i++) {
            ImageView imageView = new ImageView(context);
            Picasso.with(context).load(listQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
    }
}
