package com.example.dembuocchan;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity{
    private     EditText edtEmail,edtPassword,edtCFPassword;
    private     RadioButton rbtnCheck;
    private     Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtEmail = findViewById(R.id.editTextEmail);
        edtPassword = findViewById(R.id.editTextPassword);
        edtCFPassword = findViewById(R.id.editTextCoifimPassword);
        rbtnCheck = findViewById(R.id.radioButton);
        btnRegister = findViewById(R.id.btnRegister1);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra điều kiện đồng ý điều khoản
                if (!rbtnCheck.isChecked()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng đồng ý điều khoản", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra email
                String email = edtEmail.getText().toString();
                if (!email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
                    Toast.makeText(RegisterActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra mật khẩu
                String password = edtPassword.getText().toString();
                String confirmPassword = edtCFPassword.getText().toString();
                if (password.length() < 8) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu phải dài ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Đăng ký tài khoản
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
