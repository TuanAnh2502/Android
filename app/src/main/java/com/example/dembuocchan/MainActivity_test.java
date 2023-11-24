package com.example.dembuocchan;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity_test extends AppCompatActivity{
    Button hoso,danhgia;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        AnhXa();
        hoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_test.this, User.class);
                startActivity(intent);
            }
        });
        danhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_test.this, DanhgiaActivity.class);
                startActivity(intent);
            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.CaiDat){
                    Intent intentHoso = new Intent(MainActivity_test.this, User.class);
                    startActivity(intentHoso);
                }
                return false;
                }
        });
    }
    public void AnhXa(){
        hoso=findViewById(R.id.button);
        danhgia=findViewById(R.id.button2);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

}