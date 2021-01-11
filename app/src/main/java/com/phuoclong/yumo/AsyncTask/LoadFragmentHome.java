package com.phuoclong.yumo.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.phuoclong.yumo.R;
import com.phuoclong.yumo.fragment.FragmentMainActivity;

public class LoadFragmentHome extends AsyncTask<FragmentMainActivity,Void, FragmentMainActivity> {
    ProgressDialog progressDialog;
    Context context;
    FrameLayout frameLayout;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    FragmentMainActivity fragmentMainActivity = new FragmentMainActivity();

    public LoadFragmentHome(Context context, FrameLayout frameLayout, FragmentManager fragmentManager) {
        this.context = context;
        this.frameLayout = frameLayout;
        this.fragmentManager=fragmentManager;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("...");
        progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        progressDialog.show();

    }

    @Override
    protected FragmentMainActivity doInBackground(FragmentMainActivity... fragmentMainActivities) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentMainActivity = fragmentMainActivities[0];
        return fragmentMainActivity;
    }

    @Override
    protected void onPostExecute(FragmentMainActivity fragmentMainActivity) {
        super.onPostExecute(fragmentMainActivity);
        progressDialog.dismiss();
        fragmentTransaction.add(R.id.framelayout_trangChu,fragmentMainActivity);
        fragmentTransaction.commit();
    }
}
