package com.example.dembuocchan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private Button btnRegister;
    private  Button btnLogin;
    private EditText edtlgEmail,edtlgPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtlgEmail=findViewById(R.id.editTextTextEmailAddress);
        edtlgPassword=findViewById(R.id.editTextTextPassword);
        btnRegister=findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra dữ liệu đầu vào
                if (edtlgEmail.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Email không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edtlgPassword.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!edtlgEmail.getText().toString().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
                    Toast.makeText(Login.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edtlgPassword.getText().toString().length() < 8) {
                    Toast.makeText(Login.this, "Mật khẩu phải dài ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(Login.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
