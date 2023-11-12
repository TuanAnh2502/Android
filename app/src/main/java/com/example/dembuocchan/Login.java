package com.example.dembuocchan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private Button btnRegister;
    private  Button btnLogin;
    private EditText edtlgEmail,edtlgPassword;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        AnhXa();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

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
                DangNhap();

            }
        });
    }
    private void AnhXa(){
        edtlgEmail=findViewById(R.id.editTextTextEmailAddress);
        edtlgPassword=findViewById(R.id.editTextTextPassword);
        btnRegister=findViewById(R.id.btnRegister);
        btnLogin=findViewById(R.id.btnLogin);
    }
    private void DangNhap(){
        String email=edtlgEmail.getText().toString().trim();
        String password=edtlgPassword.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Login.this, "Đăng nhập không thành công",Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }
    private void reload() { }
    private void updateUI(FirebaseUser user) {

    }
}
