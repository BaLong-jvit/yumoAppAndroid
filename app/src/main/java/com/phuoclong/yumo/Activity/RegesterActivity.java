package com.phuoclong.yumo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegesterActivity extends AppCompatActivity {


    EditText username,disname,passw1,pass2,phone,mail;
    TextView txt_checkUser,txt_checkPass1,txt_check_pass2,txt_checkPhone;
    CheckBox accept;
    Button btn1,btn2;
    ImageView userNameItem, pass1Item,pass2Item, mailItem,phoneItem ;
    Toolbar toolbar;
    boolean user=false,acceptRule=false, display=false, password= false, comfinpass=false, acceptmail=false,acceptphone=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);
        AnhXa();
       if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            checkUserName();
            checkDisplayName();
            checkPassword();
            checkConfrimPassword();
            checkMail();
            checkPhone();
            accept.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) acceptRule=true; else  acceptRule=false;
                }
            });
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(user && display && password && comfinpass && acceptmail && acceptphone && acceptRule){
                        MainActivity.account = new AccountCTV(taoID(),taoCode(),disname.getText().toString().trim(),
                                null,phone.getText().toString().trim(),mail.getText().toString().trim(),0,null,null,
                                null,null,null,null,null,null,null,null,
                                null,null,username.getText().toString().trim(),passw1.getText().toString().trim(),1,null,
                                0,null,null,null,null,null,null,null);
                        Server server=new Server();
                        Connection connect=server.connection();
                        Statement st1;
                        if(connect!=null){
                            String query = "INSERT INTO  dbo.CTV ( MaCongTacVien, TenCTV, SDT, Email, ChietKhau, TaiKhoan, MatKhau, TrangThai, " +
                                    " isCTV ) VALUES  ( N'"+MainActivity.account.getMaCongTacVien()+ "', N'"
                                    + MainActivity.account.getTenCongTacVien()+"' , N'"+MainActivity.account.getSoDienThoai()+"' , N'"+MainActivity.account.getEmail()+"',"
                                    +MainActivity.account.getChietKhau()+", N'"+MainActivity.account.getTaiKhoan()+"', N'"+MainActivity.account.getMatKhau()+"', "
                                    +MainActivity.account.isTrangThai()+", "+MainActivity.account.isCTV()+")";
                            try {
                                st1 = connect.createStatement();
                                st1.executeQuery(query);
                                connect.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(RegesterActivity.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegesterActivity.this, MainActivity.class);
                            MainActivity.isUser = true;
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            RegesterActivity.this.startActivity(intent);
                        }
                    }else {
                        Toast.makeText(RegesterActivity.this,"Vui lòng điền dầy đủ thông tin \n" +
                                "và chấp nhận điều khoản.",Toast.LENGTH_LONG).show();
                    }
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RegesterActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    RegesterActivity.this.startActivity(intent);
                }
            });
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    private String taoCode() {
        StringBuilder code = null;
        Server server = new Server();
        Connection connection = server.connection();
        Statement statement;
        ResultSet resultSet;
        String Query = "SELECT MAX(IDCTV) FROM dbo.CTV";
        if (connection!=null){
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(Query);
                if (resultSet!=null){
                    while (resultSet.next()){
                        for (int i = (((resultSet.getInt(1)+1)+"").trim()).length();i<5;i++){
                            code = new StringBuilder("0").append(code);
                        }
                        code = new StringBuilder("CTV").append(code).append(resultSet.getInt(1)+1);
                    }
                    connection.close();
                }else {
                    code = new StringBuilder("CTV00001");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return code.toString();
    }

    private String taoID() {
        StringBuilder id=null;

        Server server = new Server();
        Connection connection = server.connection();
        Statement statement;
        ResultSet resultSet;
        String Query = "SELECT MAX(IDCTV) FROM dbo.CTV";
        if (connection!=null){
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(Query);
                if (resultSet!=null){
                    while (resultSet.next()){
                        id =new StringBuilder((resultSet.getInt(1)+1)+"");
                    }
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id.toString();
    }

    private void checkDisplayName() {
        disname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!disname.getText().toString().trim().equals("")){
                    display=true;
                }else {
                    display =false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void checkPhone() {
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pnb=phone.getText().toString();
                String phoneFormat= "^[0]"+"[0-9]"+"[0-9]"+"[0-9]"+"[0-9]"+"[0-9]"+"[0-9]"+"[0-9]"+"[0-9]"+"[0-9]";
                if(pnb.matches(phoneFormat)){
                    if(!checkPhoneSame(pnb)){
                        phoneItem.setBackgroundResource(R.drawable.incorrect);
                        txt_checkPhone.setText("Số điện thoại bị trùng.");
                        acceptphone=false;
                    }else {
                        phoneItem.setBackgroundResource(R.drawable.correct);
                        txt_checkPhone.setText("Số điện thoại được cháp nhận.");
                        acceptphone=true;
                    }
                }else{
                    phoneItem.setBackgroundResource(R.drawable.incorrect);
                    txt_checkPhone.setText("Mười chữ số và bắt đâu bằng \"0\".");
                    acceptphone=false;
                }
            }
        });
    }

    private boolean checkPhoneSame(String pnb) {
        boolean checkPhoneSame=false;
        Server server=new Server();
        Connection connect=server.connection();
        Statement st1;
        ResultSet rs;
        if (connect != null) {
            try {
                st1 = connect.createStatement();
                String query = "SELECT SDT FROM dbo.CTV WHERE SDT = '" + pnb + "'";
                rs = st1.executeQuery(query);
                if (rs != null) {
                    if (!rs.next()){checkPhoneSame = true;}
                    connect.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return checkPhoneSame;
    }

    private void checkMail() {
        mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String formatMail = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
                String em=mail.getText().toString();
                if(em.matches(formatMail)){
                    mailItem.setBackgroundResource(R.drawable.correct);
                    acceptmail=true;
                }else {mailItem.setBackgroundResource(R.drawable.incorrect);acceptmail=false;}
            }
        });
    }

    private void checkConfrimPassword() {
        pass2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pw=passw1.getText().toString();
                String pwcf=pass2.getText().toString();
                if(pw.equals(pwcf)){
                    txt_check_pass2.setText("Password trùng khớp.");
                    pass2Item.setBackgroundResource(R.drawable.correct);
                    comfinpass=true;
                }else {
                    txt_check_pass2.setText("Password không trùng khớp.");
                    pass2Item.setBackgroundResource(R.drawable.incorrect);
                    comfinpass=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void checkPassword() {
        passw1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pw1=passw1.getText().toString();
                if(checkPass(pw1)==3){
                    txt_checkPass1.setText("PassWord hợp lệ.");
                    pass1Item.setBackgroundResource(R.drawable.correct);
                    password=true;
                }else if(checkPass(pw1)==2){
                    txt_checkPass1.setText("Không được chứa ký tự đặc biệt.");
                    pass1Item.setBackgroundResource(R.drawable.incorrect);
                    password=false;
                }else  if(checkPass(pw1)==1){
                    txt_checkPass1.setText("Cần số (0-9), thường (a-z) và hoa(A-Z).");
                    pass1Item.setBackgroundResource(R.drawable.incorrect);
                    password=false;
                }else {
                    txt_checkPass1.setText("Tối thiểu 8-17 ký tự.");
                    pass1Item.setBackgroundResource(R.drawable.incorrect);
                    password = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private int checkPass(String pw1) {
        int a;
        Pattern capital = Pattern.compile("[A-Z]");
        Pattern lowercase = Pattern.compile("[a-z]");
        Pattern number = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile("[!@#$%^&\"*()\\s_+=|<>`?{}\\\\//\\[\\]\\^\\,\\;\\:\\'\\~-]");

        Matcher hasCapital = capital.matcher(pw1);
        Matcher hasLowercase = lowercase.matcher(pw1);
        Matcher hasNumber = number.matcher(pw1);
        Matcher hasSpecial = special.matcher(pw1);
        if(pw1.length()>=8 && pw1.length()<=17)
        {
           if( hasCapital.find() && hasLowercase.find() && hasNumber.find()){
               if(hasSpecial.find()){
                   a=2; // chua ky tu dac biet
               }else a=3;//hop le
           }else a=1;//thiếu loại ký tu
        }else a=0;//toi thieu 8 ky tu
        return a;
    }

    private void checkUserName() {
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String un =cleardau(username.getText().toString());

                int a = checkKytu(un);
                if(a==0) {
                    txt_checkUser.setText("UserName chứa ký tự đặc biệt.");
                    userNameItem.setBackgroundResource(R.drawable.incorrect);
                    user=false;
                }else if(a==1) {
                    txt_checkUser.setText("UserName có ít nhất 8-17 ký tự.");
                    userNameItem.setBackgroundResource(R.drawable.incorrect);
                    user=false;
                }else if(a==2) {
                    txt_checkUser.setText("UserName bị trùng.");
                    userNameItem.setBackgroundResource(R.drawable.incorrect);
                    user=false;
                }else if(a==3) {
                    txt_checkUser.setText("UserName có thể sử dụng.");
                    userNameItem.setBackgroundResource(R.drawable.correct);
                    user=true;
                }else {
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public static String cleardau(String unikeyInput) {
        String unikey = unikeyInput;
        unikey.replaceAll("[áàảãạăắằẳẵặâấầẩẫậ]","a");
        unikey.replaceAll("[ÁÀẠẢÃÂẤẦẬẨẪĂẮẰẶẲẴ]","A");
        unikey.replaceAll("[éèẻẽẹếềểễê]","e");
        unikey.replaceAll("[ÉÈẸẺẼÊẾỀỆỂỄ]","E");
        unikey.replaceAll("[ÓÒỌỎÕÔỐỒỘỔỖƠỚỜỢỞỠ]","O");
        unikey.replaceAll("[óòỏõọốồổỗộớờởỡợôơ]","o");
        unikey.replaceAll("[úùủũụứừửữựư]","u");
        unikey.replaceAll("[ÚÙỤỦŨƯỨỪỰỬỮ]","U");
        unikey.replaceAll("[ýỳỷỹỵ]","y");
        unikey.replaceAll("[ÝỲỴỶỸ]","Y");
        unikey.replaceAll("[íìỉĩị]","i");
        unikey.replaceAll("[ÍÌỊỈĨ]","I");
        unikey.replaceAll("[đ]","d");
        unikey.replaceAll("[Đ]","D");
        return unikey;
    }

    private int checkKytu(String un) {
        Pattern special = Pattern.compile("[!@#$%^&\"*()\\s_+=|<>`?{}\\\\//\\[\\]\\^\\,\\;\\:\\'\\~-]");
        Matcher hasSpecial = special.matcher(un);
        int val=-1;
        if (!hasSpecial.find()) {
            boolean checkUserNameSame=false;
            if ((un.length() >= 8 && un.length() <= 17)) {
                Server server=new Server();
                Connection connect=server.connection();
                Statement st1;
                ResultSet rs;
                if (connect != null) {
                    try {
                        st1 = connect.createStatement();
                        String query = "SELECT TaiKhoan FROM dbo.CTV WHERE TaiKhoan = '" + un + "'";
                        rs = st1.executeQuery(query);
                        if (rs != null) {
                            if (rs.next()){
                                checkUserNameSame = true;
                            }connect.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(checkUserNameSame){
                    val =2;//ok
                }else val=3; // bi trung
            } else {
                val= 1; //neu chua du ky tu
            }
        }else {
            val = 0; // neu co ku ty dac biet
        }
        return val;
    }
    private void AnhXa() {
        username = (EditText) findViewById(R.id.etxt_userNameRegester);
        disname = (EditText) findViewById(R.id.etxt_tenHienThi);
        passw1 = (EditText) findViewById(R.id.etxt_passWord);
        pass2 = (EditText) findViewById(R.id.etxt_confirmPassWord);
        phone = (EditText) findViewById(R.id.etxt_phoneNumber);
        mail = (EditText) findViewById(R.id.etxt_e_mail);
        txt_checkPass1 = (TextView) findViewById(R.id.tV_greatPassword);
        txt_check_pass2 =(TextView) findViewById(R.id.tV_confpassword);
        txt_checkUser =(TextView) findViewById(R.id.txt_userProblem);
        txt_checkPhone = (TextView) findViewById(R.id.tV_checkPhone);
        accept = (CheckBox) findViewById(R.id.chBox_dieuKhoan);
        btn1 =(Button) findViewById(R.id.btn_regester_regester);
        btn2 = (Button) findViewById(R.id.btn_login_regester);
        userNameItem= (ImageView) findViewById(R.id.img_check_user);
        pass1Item = (ImageView) findViewById(R.id.img_check_pass);
        pass2Item = (ImageView) findViewById(R.id.img_check_pass2);
        mailItem =(ImageView) findViewById(R.id.img_check_mail);
        phoneItem = (ImageView) findViewById(R.id.img_check_phone);
        toolbar = (Toolbar) findViewById(R.id.toolb_dangky);
    }
}
