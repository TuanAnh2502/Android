package com.example.dembuocchan;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class User extends AppCompatActivity {
    Button btnUpdate,btnUpdate2;
    private List<EditText> editTexts;
    EditText edtName,edtDateofbirth,edtGender,edtAddress,edtEmail,edtSDT,edtHight,edtWeight,edtNhiptim,edtHuyetap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uer);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnUpdate2=findViewById(R.id.btnUpdate2);
        editTexts = new ArrayList<>();
        edtName=findViewById(R.id.editTextName);
        edtDateofbirth=findViewById(R.id.editTextDateofbrith);
        edtGender=findViewById(R.id.editTextGender);
        edtAddress=findViewById(R.id.editTextAddress);
        edtEmail=findViewById(R.id.editTextEmail);
        edtSDT=findViewById(R.id.editTextSDT);
        edtHight=findViewById(R.id.editTextHight);
        edtWeight=findViewById(R.id.editTextWeight);
        edtNhiptim=findViewById(R.id.editTextNhiptim);
        edtHuyetap=findViewById(R.id.editTextHuyetap);
        editTexts.add(edtName);
        editTexts.add(edtDateofbirth);
        editTexts.add(edtGender);
        editTexts.add(edtAddress);
        editTexts.add(edtEmail);
        editTexts.add(edtSDT);
        editTexts.add(edtHight);
        editTexts.add(edtWeight);
        editTexts.add(edtNhiptim);
        editTexts.add(edtHuyetap);
        btnUpdate2.setVisibility(View.GONE);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUpdate.setVisibility(View.GONE);
                btnUpdate2.setVisibility(View.VISIBLE);
                enableAllPlainText();
            }
        });
        btnUpdate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUpdate2.setVisibility(View.GONE);
                btnUpdate.setVisibility(View.VISIBLE);
                unenableAllPlainText();
            }
        });

    }
    public void enableAllPlainText() {
        // Lấy tất cả các phần tử EditText trong giao diện người dùng
        for (EditText editText : editTexts) {
            editText.setEnabled(true);// Đặt trạng thái enabled của tất cả các phần tử thành true
        }
    }
    public void unenableAllPlainText() {
        // Lấy tất cả các phần tử EditText trong giao diện người dùng
        for (EditText editText : editTexts) {
            editText.setEnabled(false);// Đặt trạng thái enabled của tất cả các phần tử thành false
        }
    }
}
