package com.example.dembuocchan;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dembuocchan.KeHoach.KeHoach;
import com.example.dembuocchan.UserPlan.UserPlan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class ChiTietKeHoachActivity extends AppCompatActivity {
    private ImageView imageView;
    private EditText ngaybatdau,thoigianthongbao;
    private TextView level,tvRateApp;
    private Button btnQuaylai,btnHoanthanh;
    private int hourOfDay, minutes;
    private DatabaseReference plansRef;
    private SharedPreferences sharedPreferences;
    private String emailUser = null;
    //
    private EditText edtNgayTapLuyen;
    private CharSequence[] weekdays = {"Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật"};
    private boolean[] checkedWeekdays;
    private String idchuongtrinh;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ke_hoach);
        mAuth = FirebaseAuth.getInstance();

        imageView = findViewById(R.id.image_planner);
        ngaybatdau = findViewById(R.id.ed_ngaybatdau);
        thoigianthongbao = findViewById(R.id.ed_thoigianthongbao);
        level = findViewById(R.id.tv_level);
        btnQuaylai = findViewById(R.id.btn_quaylai);
        btnHoanthanh = findViewById(R.id.btn_hoanthanh);
        tvRateApp = findViewById(R.id.tvRateApp);

        edtNgayTapLuyen = findViewById(R.id.edtNgayTapLuyen);
        checkedWeekdays = new boolean[weekdays.length];

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        xulysukien();

        //xu ly du lieu
        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        KeHoach keHoach = (KeHoach) bundle.get("object_Kehoach");
        idchuongtrinh= String.valueOf(keHoach.getId());
        imageView.setImageResource(keHoach.getResourceId());
        ngaybatdau.setText(keHoach.getTime());
        level.setText(keHoach.getLevel());

        // Lấy dữ liệu email truyền vào
        String email= mAuth.getCurrentUser().getEmail();
        int atIndex = email.indexOf("@");
        if (atIndex != -1) {
            emailUser = email.substring(0, atIndex);
        } else {
            return;
        }
        Toast.makeText(ChiTietKeHoachActivity.this,"email: "+emailUser, Toast.LENGTH_SHORT).show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        plansRef = database.getReference("user_plans").child(emailUser);
    }

    private void xulysukien() {
        edtNgayTapLuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWeekdaySelectionDialog();
            }
        });
        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietKeHoachActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnHoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cài đặt thông báo nhắc nhở
//                setNotification(hourOfDay, minutes);

                String startDate = ngaybatdau.getText().toString();
                String reminderTime = thoigianthongbao.getText().toString();
                String leVel = level.getText().toString();
                String preferredDays = edtNgayTapLuyen.getText().toString();


//                UserPlan plan1 = new UserPlan("2023-11-17", "08:00 AM", Arrays.asList("Monday", "Wednesday", "Friday"));
                UserPlan plan2 = new UserPlan(startDate,reminderTime,preferredDays,leVel);


                plansRef.setValue(plan2);
                Toast.makeText(ChiTietKeHoachActivity.this, "Kế hoạch đã được lưu", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        ngaybatdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        thoigianthongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
        tvRateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ints = new Intent(ChiTietKeHoachActivity.this, DanhgiaActivity.class);
                ints.putExtra("idchuongtrinh","chuongtrinh"+idchuongtrinh);
                startActivity(ints);
            }
        });
    }

    private void showWeekdaySelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn ngày trong tuần");
        builder.setMultiChoiceItems(weekdays, checkedWeekdays, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedWeekdays[which] = isChecked;
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateSelectedWeekdays();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void updateSelectedWeekdays() {
        StringBuilder selectedWeekdays = new StringBuilder();
        for (int i = 0; i < weekdays.length; i++) {
            if (checkedWeekdays[i]) {
                selectedWeekdays.append(weekdays[i]).append(", ");
            }
        }
        if (selectedWeekdays.length() > 0) {
            selectedWeekdays.delete(selectedWeekdays.length() - 2, selectedWeekdays.length());
        }
        edtNgayTapLuyen.setText(selectedWeekdays.toString());
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hours, int minute) {
                hourOfDay = hours;
                minutes = minute;
                String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                thoigianthongbao.setText(selectedTime);
                
            }
        },hour, minute, true);
        timePickerDialog.show();
    }

    /*private void setNotification(int hour, int minute) {
        // Tạo một đối tượng Calendar và đặt thời gian thông báo
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        // Tạo Intent để khởi động BroadcastReceiver để xử lý thông báo
        Intent intent = new Intent(this, ThongBaoActivity.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        // Lấy đối tượng AlarmManager và đặt thông báo
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }*/

    private void saveNotificationTime(int hourOfDay, int minute) {
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                ngaybatdau.setText(selectedDate);
            }
        },year,month,day);
        datePickerDialog.show();
    }
}