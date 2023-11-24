package com.example.dembuocchan.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.dembuocchan.CaiDat.CaiDat;
import com.example.dembuocchan.CaiDat.CaiDatAdapter;
import com.example.dembuocchan.ChinhSachBaoMatActivity;
import com.example.dembuocchan.DieuKhoanDichVuActivity;
import com.example.dembuocchan.DoiPasswordActivity;
import com.example.dembuocchan.GioiThieuActivity;
import com.example.dembuocchan.Login;
import com.example.dembuocchan.R;
import com.example.dembuocchan.User;

import java.util.ArrayList;
import java.util.List;

public class CaiDatFragment extends Fragment {
//    private Switch switchDarkMode;
    private View caidatView;
    private ListView listView;
    private List<CaiDat> caiDatList;
    private CaiDatAdapter adapter;
    private Button btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        caidatView = inflater.inflate(R.layout.fragment_cai_dat, container, false);
//        switchDarkMode = caidatView.findViewById(R.id.switch_DarkMode);
        btnLogout = caidatView.findViewById(R.id.btnLogout);

        caiDatList = new ArrayList<>();
        caiDatList.add(new CaiDat("Hồ sơ", R.drawable.icon_setting_profile, new ProfileClickListener()));
        caiDatList.add(new CaiDat("Đổi mật khẩu", R.drawable.icon_setting_password,new  PasswordClickListener()));
        caiDatList.add(new CaiDat("Trợ giúp", R.drawable.icon_setting_help,new HelpClickListener()));
        caiDatList.add(new CaiDat("Điều khoản dịch vụ", R.drawable.icon_setting_service,  new TermsClickListener()));
        caiDatList.add(new CaiDat("Chính sách bảo mật", R.drawable.icon_setting_privacy, new PrivacyClickListener()));
        caiDatList.add(new CaiDat("Giới thiệu ứng dụng", R.drawable.icon_setting_intro, new AboutClickListener()));

        adapter = new CaiDatAdapter(caiDatList,getActivity());
        listView = caidatView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CaiDat item = caiDatList.get(position);
                item.getClickListener().onClick(view);
            }
        });
        xulusukien();

        return caidatView;
    }

    private void xulusukien() {
        // xử lý sự kiện cài đặt sáng tối
//        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            }
////            recreate(); // Tái khởi động hoạt động để áp dụng thay đổi chế độ tối
//        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xoa thong tin dang nhap
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("username"); // Xóa thông tin đăng nhập, ví dụ: username
                editor.remove("password"); // Xóa thông tin đăng nhập, ví dụ: password
                editor.apply();

                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private class ProfileClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), User.class);
            startActivity(intent);
        }
    }

    private class HelpClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    private class PasswordClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), DoiPasswordActivity.class);
            startActivity(intent);
        }
    }

    private class TermsClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), DieuKhoanDichVuActivity.class);
            startActivity(intent);
        }
    }

    private class PrivacyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), ChinhSachBaoMatActivity.class);
            startActivity(intent);
        }
    }

    private class AboutClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), GioiThieuActivity.class);
            startActivity(intent);
        }
    }
}