package com.phuoclong.yumo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.phuoclong.yumo.Activity.MainActivity;
import com.phuoclong.yumo.R;
import com.squareup.picasso.Picasso;

public class FragmentAccount extends Fragment {
    ImageView image_avatar;
    TextView textView_displayName, textView_accType, textView_TrangThai, textView_sodienthoai, textView_ngaySinh, textView_gioiTinh;
    Button button_dangXuat, button_lienKietNganHang;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container, false);
        AnhXa(view);
        textView_displayName.setText(MainActivity.account.getTenCongTacVien());
        textView_accType.setText((MainActivity.account.isCTV()==1)?"Cộng tác viên":"Chưa xác nhận");
        textView_TrangThai.setText(MainActivity.account.getLoai());
        textView_sodienthoai.setText("*******"+MainActivity.account.getSoDienThoai().substring(7,9));
        textView_ngaySinh.setText(MainActivity.account.getNgaySinh());
        Picasso.with(getContext()).load(MainActivity.account.getLinkAnh()).into(image_avatar);
        ConTrol();
        return view;
    }

    private void ConTrol() {
        button_dangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.account = null;
                MainActivity.isUser = false;
                Intent refresh = new Intent(getContext(),MainActivity.class);
                getContext().startActivity(refresh);
                getActivity().finish();
            }
        });
        button_lienKietNganHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentLienKet fragmentLienKet = new FragmentLienKet();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_fragment, fragmentLienKet);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void AnhXa(View view) {
        image_avatar = (ImageView) view.findViewById(R.id.img_account);
        textView_displayName = (TextView) view.findViewById(R.id.txt_displayName_acc);
        textView_accType = (TextView) view.findViewById(R.id.txt_accountType);
        button_dangXuat = (Button) view.findViewById(R.id.log_out);
        button_lienKietNganHang = (Button) view.findViewById(R.id.lienKetNganHang);
        textView_TrangThai = (TextView) view.findViewById(R.id.txt_trangThaiTaiKhoan);
        textView_sodienthoai = (TextView) view.findViewById(R.id.sdt);
        textView_ngaySinh = (TextView) view.findViewById(R.id.text_ngaysinh);
        textView_gioiTinh = (TextView) view.findViewById(R.id.gioitinh);
    }
}
