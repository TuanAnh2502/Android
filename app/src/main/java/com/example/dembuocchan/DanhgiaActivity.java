package com.example.dembuocchan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import java.util.Date;
import java.text.SimpleDateFormat;
public class DanhgiaActivity extends AppCompatActivity {
    Button send;
    EditText edtDanhgia;
    RatingBar rtbStar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhgia);
        send=findViewById(R.id.buttonSend);
        edtDanhgia=findViewById(R.id.editTextDanhgia);
        rtbStar=findViewById(R.id.ratingBar);
        rtbStar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(DanhgiaActivity.this, "so sao"+(int)rtbStar.getRating(),Toast.LENGTH_SHORT).show();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sosao=(int)rtbStar.getRating();
                if(sosao<1){
                    Toast.makeText(DanhgiaActivity.this, "Vui lòng nhập số sao"+(int)rtbStar.getRating(),Toast.LENGTH_SHORT).show();
                    return;
                }
                LinearLayout danhgia=findViewById(R.id.xemdanhgia);
                View userReviewLayout = LayoutInflater.from(DanhgiaActivity.this).inflate(R.layout.danhgiaform, null);
                TextView userNameTextView = userReviewLayout.findViewById(R.id.textView19);
                userNameTextView.setText("Tuấn Anh"); // Thay tên người dùng tương ứng
                RatingBar userRating=userReviewLayout.findViewById(R.id.ratingBar3);
                userRating.setRating(sosao);
                EditText userReviewEditText = userReviewLayout.findViewById(R.id.editTextText2);
                String userReviewText = edtDanhgia.getText().toString(); // Thay nội dung đánh giá tương ứng
                userReviewEditText.setText(userReviewText);
                TextView dateTextView = userReviewLayout.findViewById(R.id.textView20);
                dateTextView.setText("Ngày: " + getCurrentDate()); // Sử dụng phương thức để lấy ngày tháng hiện tại
                danhgia.addView(userReviewLayout, 0);
                edtDanhgia.setText("");
                rtbStar.setRating(0);
            }
        });
    }
    public String getCurrentDate() {
        // Tạo một đối tượng Date để lấy ngày giờ hiện tại
        Date currentDate = new Date();

        // Định dạng ngày tháng theo ý muốn, ví dụ: "dd/MM/yyyy HH:mm:ss"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        // Sử dụng định dạng để chuyển đổi ngày giờ thành chuỗi
        String formattedDate = dateFormat.format(currentDate);

        return formattedDate;
    }
}
