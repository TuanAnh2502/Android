package com.example.dembuocchan;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DoiPasswordActivity extends AppCompatActivity {
    private EditText etOldPassword, etNewPassword, etConfirmPassword;
    private Button btnChangePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_password);
        etOldPassword = findViewById(R.id.etOldPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        xulysukien();
    }

    private void xulysukien() {
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = etOldPassword.getText().toString().trim();
                String newPassword = etNewPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                if (validateInput(oldPassword, newPassword, confirmPassword)) {
                    changePassword(oldPassword, newPassword);
                }
            }
        });
    }

    private boolean validateInput(String oldPassword, String newPassword, String confirmPassword) {
        if (TextUtils.isEmpty(oldPassword)) {
            etOldPassword.setError("Vui lòng nhập mật khẩu cũ");
            return false;
        }

        if (TextUtils.isEmpty(newPassword)) {
            etNewPassword.setError("Vui lòng nhập mật khẩu mới");
            return false;
        }

        if (!newPassword.equals(confirmPassword)) {
            etConfirmPassword.setError("Xác nhận mật khẩu không khớp");
            return false;
        }

        return true;
    }

    private void changePassword(String oldPassword, String newPassword) {
        //lấy đối tượng FirebaseUser đại diện cho người dùng hiện tại
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // AuthCredential đại diện cho thông tin xác thực của người dùng, bao gồm các thông tin như email, mật khẩu
            //  tạo đối tượng AuthCredential từ địa chỉ email và mật khẩu cũ được cung cấp
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
            // thực hiện quá trình xác thực lại người dùng với thông tin xác thực được cung cấp.
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(newPassword)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(DoiPasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(DoiPasswordActivity.this, "Lỗi đổi mật khẩu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(DoiPasswordActivity.this, "Xác thực thất bại. Vui lòng kiểm tra lại mật khẩu cũ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(DoiPasswordActivity.this, "Người dùng chưa đăng nhập", Toast.LENGTH_SHORT).show();
        }
    }
}