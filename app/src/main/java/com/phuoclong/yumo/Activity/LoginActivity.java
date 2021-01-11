package com.phuoclong.yumo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.phuoclong.yumo.Model.AccountCTV;
import com.phuoclong.yumo.R;
import com.phuoclong.yumo.Until.CheckConnection;
import com.phuoclong.yumo.Until.Server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {

    Button btn_dangnhap, btn_dangky;
    CheckBox checkBoxRemember;
    EditText edtUser, edtPassword;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_dangnhap = (Button) findViewById(R.id.btn_login_login);//dang nhap
        btn_dangky =(Button) findViewById(R.id.btn_regester_login); //dang ky
        checkBoxRemember = (CheckBox) findViewById(R.id.chBox_rememberAccount);
        edtUser = (EditText) findViewById(R.id.etxt_userNameLogin);
        edtPassword = (EditText) findViewById(R.id.etxt_passWordLogin);
        toolbar =(Toolbar) findViewById(R.id.toolb_dangnhap);
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            btn_dangnhap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String user = edtUser.getText().toString().trim();
                    String pass = edtPassword.getText().toString().trim();
                    if (user.length()> 0 &&  pass.length()>0 ) {
                        Server server = new Server();
                        Connection conn = server.connection();
                        Statement statement;
                        ResultSet resultSet;
                        if (conn != null) {
                            String Query = "SELECT IDCTV,MaCongTacVien,TenCTV,DiaChi,SDT,Email,ChietKhau,GhiChu,Loai,idTinhTP,idQuanHuyen,idPhuongXa,idCTVCha,STKNganHang,TenNganHang," +
                                    "LoaiKhachHang,NgaySinh,MST,GioiTinh,TaiKhoan,MatKhau,TrangThai,idNhanVienCSKH,isCTV,LinkAnh,TenTKNganHang,Quyen,uidZalo,idZalo,OAuthCodeZalo," +
                                    "NgayCapOauthCodeZalo FROM dbo.CTV WHERE TaiKhoan = '" + user + "' AND MatKhau = '" + pass + "' ";
                            try {
                                statement = conn.createStatement();
                                resultSet = statement.executeQuery(Query);
                                if (resultSet != null) {
                                    while (resultSet.next()){
                                        MainActivity.account=new AccountCTV(resultSet.getInt("IDCTV")+"",
                                                resultSet.getString("MaCongTacVien"),resultSet.getString("TenCTV"),
                                                resultSet.getString("DiaChi"),resultSet.getString("SDT"),
                                                resultSet.getString("Email"),resultSet.getFloat("ChietKhau"),
                                                resultSet.getString("GhiChu"),resultSet.getString("Loai"),
                                                resultSet.getInt("idTinhTP")+"",resultSet.getInt("idQuanHuyen")+"",
                                                resultSet.getInt("idPhuongXa")+"",resultSet.getInt("idCTVCha")+"",
                                                resultSet.getString("STKNganHang"),resultSet.getString("TenNganHang"),resultSet.getString("LoaiKhachHang"),
                                                resultSet.getString("NgaySinh"),resultSet.getString("MST"),
                                                resultSet.getString("GioiTinh"),resultSet.getString("TaiKhoan"),resultSet.getString("MatKhau"),
                                                resultSet.getInt("TrangThai"),resultSet.getInt("idNhanVienCSKH")+"",
                                                resultSet.getInt("isCTV"),resultSet.getString("LinkAnh"),resultSet.getString("TenTKNganHang"),
                                                resultSet.getString("Quyen"),resultSet.getString("uidZalo"),resultSet.getString("idZalo"),
                                                resultSet.getString("OAuthCodeZalo"),resultSet.getString("NgayCapOauthCodeZalo"));
                                    }
                                    conn.close();
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            if(MainActivity.account==null){
                                Toast.makeText(LoginActivity.this, "Tài khoản hoặc Mật khẩu không chính xác!", Toast.LENGTH_LONG).show();
                            }else {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                MainActivity.isUser = true;
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    }else Toast.makeText(LoginActivity.this,"Vui lòng nhập thông tin đăng nhập!",Toast.LENGTH_LONG).show();
                }
            });
            btn_dangky.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(LoginActivity.this,RegesterActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    LoginActivity.this.startActivity(intent);
                }
            });
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }else {
            CheckConnection.showToast_Short(getApplicationContext(),"Kết nối Internet thất bại!\nPlease check your network!");
        }
    }
}
