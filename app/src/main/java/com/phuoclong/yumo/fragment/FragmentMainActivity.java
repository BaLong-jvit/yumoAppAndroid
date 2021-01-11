package com.phuoclong.yumo.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.phuoclong.yumo.Activity.LoginActivity;
import com.phuoclong.yumo.Activity.MainActivity;
import com.phuoclong.yumo.Activity.RegesterActivity;
import com.phuoclong.yumo.AsyncTask.LoadCategory;
import com.phuoclong.yumo.Model.Category;
import com.phuoclong.yumo.Model.GioHang;
import com.phuoclong.yumo.R;
import com.phuoclong.yumo.Until.CheckConnection;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import static com.phuoclong.yumo.Activity.MainActivity.account;
import static com.phuoclong.yumo.Activity.MainActivity.isUser;

public class FragmentMainActivity extends Fragment {
    private Button button_call, button_regester;
    private ImageButton imageButton_Home, imageButton_Giohang, imageButton_lichsu, imageButton_thongbao, imageButton_account;
    private ImageView avatar;
    private TextView txtName;
    private TextView txtType;
    @SuppressLint("StaticFieldLeak")
    public static TextView countsoluong, countThongbao;
    public static Random random;
    private Toolbar toolbar;
    private NavigationView navi_menuhome;
    private LinearLayout layout_account;
    private ListView listView_menu;
    private DrawerLayout drawerLayout;
    private ArrayList<Category> listCategory = new ArrayList<>();
    private Bundle bundle = new Bundle();
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FragmentHome fragmentHome;
    Fragment fragment = null;
    Fragment_GioHang fragment_gioHang;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_activity,container,false);
        AnhXa(view);
        if(CheckConnection.haveNetworkConnection(getContext())){
            cacText();
            control(view);

            { // tạo Menu
                Actionbar_ToolBar();
                TaoImageAccount();
                {
                    new LoadCategory(listCategory,getContext(),listView_menu).execute("SELECT IDNhomHangHoa, TenNhomHangHoa, LinkAnh, An, GhiChu, idCapCha FROM dbo.NhomHangHoa");
                }
            }

            {
                fragmentManager =getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentHome = new FragmentHome();
                fragmentTransaction.add(R.id.frame_fragment, fragmentHome);
                fragmentTransaction.commit();
                imageButton_Home.setBackgroundResource(R.drawable.iconhome_click);
            }

        }else {
            CheckConnection.showToast_Short(getContext(),"Kết nối Internet thất bại!\nPlease check your network!");
        }
        return view;
    }

    public void frag_main(View view){
        fragmentManager =getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (view.getId()){
            case R.id.imgBtnHome:
                fragment = new FragmentHome();
                ButtonUnclick();
                fragmentTransaction.add(R.id.frame_fragment,fragment);
                fragmentTransaction.commit();
                imageButton_Home.setBackgroundResource(R.drawable.iconhome_click);
                break;
            case R.id.imgBtnlichsu:
                if(isUser ==false){
                    Toast.makeText(getContext(),"Đăng nhập để xem lịch sử!",Toast.LENGTH_SHORT).show();
                }else {
                    fragment = new FragmentHistory();
                    ButtonUnclick();
                    fragmentTransaction.add(R.id.frame_fragment, fragment);
                    fragmentTransaction.commit();
                    imageButton_lichsu.setBackgroundResource(R.drawable.iconchatclick);
                }
                break;
            case R.id.imgBtnThongbaoMain:
                fragment = new FragmentNotification();
                ButtonUnclick();
                fragmentTransaction.add(R.id.frame_fragment,fragment);
                fragmentTransaction.commit();
                imageButton_thongbao.setBackgroundResource(R.drawable.iconnotification_click);
                break;
            case R.id.imgBtn_Acc:
                if(isUser==false){
                    btnRegester_showAlertDialog();
                }else {
                    fragment = new FragmentAccount();
                    ButtonUnclick();
                    fragmentTransaction.add(R.id.frame_fragment,fragment);
                    fragmentTransaction.commit();
                    imageButton_account.setBackgroundResource(R.drawable.user_click);
                }
                break;
            case R.id.btnRegester:
                if(isUser==false){
                    btnRegester_showAlertDialog();
                }else {
                    fragment = new FragmentAccount();
                    ButtonUnclick();
                    fragmentTransaction.add(R.id.frame_fragment,fragment);
                    fragmentTransaction.commit();
                    imageButton_account.setBackgroundResource(R.drawable.user_click);
                }
                break;
            case R.id.account_menu:
                if(isUser==false){
                    btnRegester_showAlertDialog();
                }else {
                    fragment = new FragmentAccount();
                    ButtonUnclick();
                    fragmentTransaction.add(R.id.frame_fragment,fragment);
                    fragmentTransaction.commit();
                    imageButton_account.setBackgroundResource(R.drawable.user_click);
                }
                drawerLayout.closeDrawers();
                break;
        }

    }

    private void ButtonUnclick() {
        imageButton_Home.setBackgroundResource(R.drawable.iconhome);
        imageButton_Giohang.setBackgroundResource(R.drawable.icon_cart);
        imageButton_lichsu.setBackgroundResource(R.drawable.iconchat);
        imageButton_thongbao.setBackgroundResource(R.drawable.iconnotification);
        imageButton_account.setBackgroundResource(R.drawable.usericon);
    }

    private void control(View view) {
        button_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCall_showAlertDialog();
            }
        });
        imageButton_Giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("activity","home");
                fragmentManager =getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragment_gioHang = new Fragment_GioHang();
                fragment_gioHang.setArguments(bundle);
                fragmentTransaction.add(R.id.framelayout_activiti_thuong,fragment_gioHang,"gio hang");
                fragmentTransaction.addToBackStack("gio hang");
                ButtonUnclick();
                imageButton_Home.setBackgroundResource(R.drawable.iconhome_click);
                MainActivity.frameLayout_trangChu.setVisibility(View.INVISIBLE);
                MainActivity.frameLayout_activityThuong.setVisibility(View.VISIBLE);
                fragmentTransaction.commit();
            }
        });
        countsoluong.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                bundle.putString("activity","home");
                fragmentManager =getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragment_gioHang = new Fragment_GioHang();
                fragment_gioHang.setArguments(bundle);
                fragmentTransaction.replace(R.id.framelayout_activiti_thuong,fragment_gioHang,"gio hang");
                fragmentTransaction.addToBackStack("gio hang");
                ButtonUnclick();
                imageButton_Home.setBackgroundResource(R.drawable.iconhome_click);
                MainActivity.frameLayout_trangChu.setVisibility(View.INVISIBLE);
                MainActivity.frameLayout_activityThuong.setVisibility(View.VISIBLE);
                fragmentTransaction.commit();
            }
        });
        listView_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawers();
                bundle.clear();
                bundle.putInt("idCategory",position+1);
                bundle.putString("tenCategory",listCategory.get(position).getCtgrName());
                bundle.putString("activity","home");
                fragmentManager =getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragment = new FragmentProductType();
                fragment.setArguments(bundle);
                ButtonUnclick();
                imageButton_Home.setBackgroundResource(R.drawable.iconhome_click);
                fragmentTransaction.add(R.id.framelayout_activiti_thuong,fragment,"danh muc san pham");
                MainActivity.frameLayout_trangChu.setVisibility(View.INVISIBLE);
                MainActivity.frameLayout_activityThuong.setVisibility(View.VISIBLE);
                fragmentTransaction.commit();
            }
        });
    }

    private void cacText() {
        countsoluong.setText(getcounthang(MainActivity.danhSachHangduocChon)+"");
        if(isUser==true){
            button_regester.setText("Tài khoản");

        }
    }

    public static int getcounthang(ArrayList<GioHang> danhSachHangduocChon) {
        int ok = 0;
        if(danhSachHangduocChon!=null){
            for (int i=0;i<danhSachHangduocChon.size();i++){
                ok = ok+danhSachHangduocChon.get(i).getSoluongSp();
            }
        }
        return ok;
    }

    private void TaoImageAccount() {
        if(isUser == false){
            txtName.setText("Vui lòng đăng nhập hoặc đăng ký");
            Picasso.with(getContext()).load("https://i.imgur.com/6lubZhc.png")
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(avatar);
        }else {

            txtName.setText(account.getTenCongTacVien());
            txtType.setText((account.isCTV()==1)?"Cộng tác viên":"Chưa xác thực");
            Picasso.with(getContext()).load(account.getLinkAnh())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(avatar);
        }
    }

    private void Actionbar_ToolBar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void btnCall_showAlertDialog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        builder.setTitle("Thông báo");
        builder.setMessage("Gọi: +08.6909.0303");
        builder.setCancelable(true);
        builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        builder.setNegativeButton("Sửa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                String dilt= "tel:"+"0869090303";
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dilt)));
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void btnRegester_showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Trở thành thành viên của YuMo");
        builder.setPositiveButton("Đăng Nhập", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getContext(), LoginActivity.class);

                startActivity(intent);
            }
        });
        builder.setNegativeButton("Đăng Ký", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getContext(), RegesterActivity.class);

                startActivity(intent);
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    @SuppressLint("WrongViewCast")
    private void AnhXa(View view){
        button_call = (Button) view.findViewById(R.id.btnPhoneNumber);
        button_regester = (Button) view.findViewById(R.id.btnRegester);
        imageButton_Home = (ImageButton) view.findViewById(R.id.imgBtnHome);
        imageButton_Giohang = (ImageButton) view.findViewById(R.id.imgBtnGiohangMain);
        imageButton_lichsu = (ImageButton) view.findViewById(R.id.imgBtnlichsu);
        imageButton_thongbao = (ImageButton) view.findViewById(R.id.imgBtnThongbaoMain);
        imageButton_account = (ImageButton) view.findViewById(R.id.imgBtn_Acc);
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drwl_drawerlayout);
        toolbar = (Toolbar) view.findViewById(R.id.toolb_menu);
        navi_menuhome = (NavigationView) view.findViewById(R.id.navi_menu);
        listView_menu = (ListView) view.findViewById(R.id.list_menu);
        txtName = (TextView) view.findViewById(R.id.txt_displayName);
        txtType = (TextView) view.findViewById(R.id.txt_accountType);
        avatar = (ImageView) view.findViewById(R.id.img_accountAvatar);
        countsoluong = (TextView) view.findViewById(R.id.txt_hang_trong_gioMain);
        countThongbao = (TextView) view.findViewById(R.id.txt_thongbao_Main);
        layout_account = (LinearLayout) view.findViewById(R.id.account_menu);
    }
}
