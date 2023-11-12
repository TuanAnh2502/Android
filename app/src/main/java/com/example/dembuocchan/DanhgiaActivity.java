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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.text.SimpleDateFormat;
public class DanhgiaActivity extends AppCompatActivity {
    Button send;
    EditText edtDanhgia;
    RatingBar rtbStar;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhgia);
        send=findViewById(R.id.buttonSend);
        edtDanhgia=findViewById(R.id.editTextDanhgia);
        rtbStar=findViewById(R.id.ratingBar);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("chuongtrinhluyentap");
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
                createComment();
                edtDanhgia.setText("");
                rtbStar.setRating(0);

            }
        });
        displayCommentData();
    }
    private void displayCommentData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = databaseReference.child("chuongtrinh1").child("comment");
            // Lắng nghe sự thay đổi trong dữ liệu người dùng
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Lấy dữ liệu từ Firebase và hiển thị lên các EditText tương ứng
                        LinearLayout danhgia=findViewById(R.id.xemdanhgia);
                        for (DataSnapshot commentSnapshot : dataSnapshot.getChildren()) {
                            View userReviewLayout = LayoutInflater.from(DanhgiaActivity.this).inflate(R.layout.danhgiaform, null);
                            TextView userNameTextView = userReviewLayout.findViewById(R.id.textView19);
                            userNameTextView.setText(commentSnapshot.child("name").getValue(String.class)); // Thay tên người dùng tương ứng
                            RatingBar userRating = userReviewLayout.findViewById(R.id.ratingBar3);
                            userRating.setRating(commentSnapshot.child("sao").getValue(Integer.class));
                            EditText userReviewEditText = userReviewLayout.findViewById(R.id.editTextText2);
                            String userReviewText = commentSnapshot.child("content").getValue(String.class); // Thay nội dung đánh giá tương ứng
                            userReviewEditText.setText(userReviewText);
                            TextView dateTextView = userReviewLayout.findViewById(R.id.textView20);
                            dateTextView.setText("Ngày: " + commentSnapshot.child("datetime").getValue(String.class));
                            danhgia.addView(userReviewLayout,0);
                        }
                    }else {
                        Toast.makeText(DanhgiaActivity.this, "khong ton tai", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu từ Firebase
                    Toast.makeText(DanhgiaActivity.this, "Lỗi đọc dữ liệu", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void createComment() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = databaseReference.child("chuongtrinh1").child("comment").child(userId);
            int sosao=(int)rtbStar.getRating();
            // Cập nhật dữ liệu người dùng trong Firebase Realtime Database
            userRef.child("name").setValue(mAuth.getCurrentUser().getDisplayName());
            userRef.child("datetime").setValue(getCurrentDate().toString());
            userRef.child("content").setValue(edtDanhgia.getText().toString());
            userRef.child("sao").setValue(sosao);
        }
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
