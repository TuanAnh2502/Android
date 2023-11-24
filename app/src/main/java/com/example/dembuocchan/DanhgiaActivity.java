package com.example.dembuocchan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
public class DanhgiaActivity extends AppCompatActivity {
    Button send;
    EditText edtDanhgia;
    RatingBar rtbStar;
    private String username="rong";
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private  DatabaseReference dbRinfor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String idchuongtrinh = intent.getStringExtra("idchuongtrinh");
        setContentView(R.layout.activity_danhgia);
        send=findViewById(R.id.buttonSend);
        edtDanhgia=findViewById(R.id.editTextDanhgia);
        rtbStar=findViewById(R.id.ratingBar);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("chuongtrinhluyentap");
        dbRinfor=FirebaseDatabase.getInstance().getReference("User");
        rtbStar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                /*Toast.makeText(DanhgiaActivity.this, "so sao"+(int)rtbStar.getRating(),Toast.LENGTH_SHORT).show()*/;
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check=edtDanhgia.getText().toString().toLowerCase().trim();
                Toast.makeText(DanhgiaActivity.this, check, Toast.LENGTH_SHORT).show();
                if (ContentValidator.kiemTraNoiDungKhongHopLe(check)) {
                    Toast.makeText(DanhgiaActivity.this, "Nội dung không hợp lệ", Toast.LENGTH_SHORT).show(); return;
                }
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    String userId = user.getUid();
                    DatabaseReference userRef = dbRinfor.child(userId).child("hoso");
                    // Lắng nghe sự thay đổi trong dữ liệu người dùng
                    userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // Lấy dữ liệu từ Firebase
                                username=dataSnapshot.child("name").getValue(String.class);
                                addComment(idchuongtrinh);

                            }else {
                                Toast.makeText(DanhgiaActivity.this, "Hay cap nhat ho so cua ban", Toast.LENGTH_SHORT).show();
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
        });
        displayCommentData(idchuongtrinh);
    }

    public interface OnUrlReceivedListener {
        void onUrlReceived(String url);
    }
    private void geturl(String id, OnUrlReceivedListener listener){
        DatabaseReference userRef = dbRinfor.child(id).child("hoso");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String url = dataSnapshot.child("url").getValue(String.class);
                    listener.onUrlReceived(url);
                } else {
                    Toast.makeText(DanhgiaActivity.this, "Hay cap nhat ho so cua ban", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DanhgiaActivity.this, "Lỗi đọc dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addComment(String chuongtrinh){
        int sosao=(int)rtbStar.getRating();
        if(sosao<1){
            Toast.makeText(DanhgiaActivity.this, "Vui lòng nhập số sao"+(int)rtbStar.getRating(),Toast.LENGTH_SHORT).show();
            return;
        }

        LinearLayout danhgia=findViewById(R.id.xemdanhgia);
        View userReviewLayout = LayoutInflater.from(DanhgiaActivity.this).inflate(R.layout.danhgiaform, null);
        TextView userNameTextView = userReviewLayout.findViewById(R.id.textView19);
        userNameTextView.setText(username); // Thay tên người dùng tương ứng
        RatingBar userRating=userReviewLayout.findViewById(R.id.ratingBar3);
        userRating.setRating(sosao);
        EditText userReviewEditText = userReviewLayout.findViewById(R.id.editTextText2);
        String userReviewText = edtDanhgia.getText().toString(); // Thay nội dung đánh giá tương ứng
        userReviewEditText.setText(userReviewText);
        TextView dateTextView = userReviewLayout.findViewById(R.id.textView20);
        dateTextView.setText("Ngày: " + getCurrentDate()); // Sử dụng phương thức để lấy ngày tháng hiện tại
        danhgia.addView(userReviewLayout, 0);
        createComment(chuongtrinh);
        edtDanhgia.setText("");
        rtbStar.setRating(0);
    }

    private void displayCommentData(String chuongtrinh) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            DatabaseReference userRef = databaseReference.child(chuongtrinh).child("comment");
            Query query = userRef.orderByChild("datetime");
            // Lắng nghe sự thay đổi trong dữ liệu người dùng
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Lấy dữ liệu từ Firebase và hiển thị lên các EditText tương ứng
                        LinearLayout danhgia=findViewById(R.id.xemdanhgia);
                        for (DataSnapshot commentSnapshot : dataSnapshot.getChildren()) {
                            String parentName = commentSnapshot.getKey().toString();
                            View userReviewLayout = LayoutInflater.from(DanhgiaActivity.this).inflate(R.layout.danhgiaform, null);
                            //String imageUrl =dataSnapshot.child("url").getValue(String.class);
                            ImageView imageView = userReviewLayout.findViewById(R.id.daidien);
                            geturl(parentName, new OnUrlReceivedListener() {
                                @Override
                                public void onUrlReceived(String url) {
                                    // Thực hiện công việc với giá trị url ở đây
                                    // Ví dụ: Hiển thị hình ảnh
                                    loadImageFromFirebaseStorage(url,imageView);
                                }
                            });
                            //loadImageFromFirebaseStorage("https://firebasestorage.googleapis.com/v0/b/dembuocchan.appspot.com/o/profile_images%2FPxgg5Ci9BLTlwVGxuOZN7YrEhfu2.jpg?alt=media&token=9c1349bd-e542-475d-a941-138a61d43646",imageView);
                            TextView userNameTextView = userReviewLayout.findViewById(R.id.textView19);
                            userNameTextView.setText(commentSnapshot.child("name").getValue(String.class)); // Thay tên người dùng tương ứng
                            RatingBar userRating = userReviewLayout.findViewById(R.id.ratingBar3);
                            userRating.setRating(commentSnapshot.child("sao").getValue(Integer.class));
                            EditText userReviewEditText = userReviewLayout.findViewById(R.id.editTextText2);
                            String userReviewText = commentSnapshot.child("content").getValue(String.class); // Thay nội dung đánh giá tương ứng
                            userReviewEditText.setText(userReviewText);
                            TextView dateTextView = userReviewLayout.findViewById(R.id.textView20);
                            dateTextView.setText(commentSnapshot.child("datetime").getValue(String.class));
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
    private void createComment(String chuongtrinh) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = databaseReference.child(chuongtrinh).child("comment").child(userId);
            int sosao=(int)rtbStar.getRating();
            // Cập nhật dữ liệu người dùng trong Firebase Realtime Database
            userRef.child("name").setValue(username);
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
    public void onBackPressed() {
        // Khi nút "Back" được nhấn, gọi finish() để đóng Activity
        super.onBackPressed();
        finish();
    }
    private void loadImageFromFirebaseStorage(String imageUrl,ImageView imageView) {
        // Sử dụng thư viện Picasso để tải ảnh và hiển thị nó trong ImageView
        if (imageUrl != null && !imageUrl.isEmpty()) {
            // Sử dụng thư viện Picasso để tải ảnh và hiển thị nó trong ImageView
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.admin) // Đặt ảnh giả mạo nếu không tải được ảnh từ Firebase
                    .error(R.drawable.loi) // Đặt ảnh khi có lỗi xảy ra trong quá trình tải ảnh
                    .into(imageView);
        } else {
            // Nếu imageUrl không có giá trị, sử dụng ảnh "loi"
            imageView.setImageResource(R.drawable.loi);
        }
    }
}
