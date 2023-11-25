package com.example.dembuocchan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ThongBaoActivity extends AppCompatActivity {
    private Button btnDong;
    private AlertDialog notificationDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);
        btnDong = findViewById(R.id.buttonDismiss);

        xulysukien();
    }

    private void xulysukien() {
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongBaoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}