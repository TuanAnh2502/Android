package com.example.dembuocchan;
import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword, edtCFPassword;
    private RadioButton rbtnCheck;
    private Button btnRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnhXa();
        mAuth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra điều kiện đồng ý điều khoản
                if (!rbtnCheck.isChecked()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng đồng ý điều khoản", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra email
                String email = edtEmail.getText().toString().trim();
                if (!email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
                    Toast.makeText(RegisterActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra mật khẩu
                String password = edtPassword.getText().toString().trim();
                String confirmPassword = edtCFPassword.getText().toString().trim();
                if (password.length() < 8) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu phải dài ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Đăng ký tài khoản
                DangKi();
            }
        });
    }

    private void AnhXa() {
        edtEmail = findViewById(R.id.editTextEmail);
        edtPassword = findViewById(R.id.editTextPassword);
        edtCFPassword = findViewById(R.id.editTextCoifimPassword);
        rbtnCheck = findViewById(R.id.radioButton);
        btnRegister = findViewById(R.id.btnRegister1);
    }

    private void DangKi() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Dang ki thanh cong", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    showProfileCreationDialog();
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        // Tài khoản không tồn tại, bạn có thể tiếp tục quá trình đăng ký.
                        // Thực hiện các hành động đăng ký tài khoản ở đây.
                    } catch (FirebaseAuthUserCollisionException e) {
                        // Tài khoản đã tồn tại, thông báo cho người dùng.
                        Toast.makeText(RegisterActivity.this, Html.fromHtml("Đăng ký không thành công<br/><b>Tài khoản đã tồn tại!</b>"), Toast.LENGTH_SHORT).show();
                        edtEmail.requestFocus();
                    } catch (Exception e) {
                        // Có lỗi khác xảy ra, bạn có thể xử lý tùy thuộc vào yêu cầu của bạn.
                        Log.e("RegisterActivity", "Đăng ký thất bại", e);
                    }
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {

    }

    private void showProfileCreationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tạo Hồ Sơ");
        builder.setMessage("Bạn có muốn tạo hồ sơ ngay bây giờ không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Nếu người dùng chọn "Có", chuyển đến trang tạo hồ sơ.
                Intent intent = new Intent(RegisterActivity.this, User.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Nếu người dùng chọn "Không", có thể không thực hiện bất kỳ hành động nào hoặc
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        finish();
    }
    public void onBackPressed() {
        // Khi nút "Back" được nhấn, gọi finish() để đóng Activity
        super.onBackPressed();
        finish();
    }
}
