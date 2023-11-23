package com.example.dembuocchan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import com.squareup.picasso.Picasso;

public class User extends AppCompatActivity {
    Button btnUpdate, btnUpdate2;
    private List<EditText> editTexts;
    EditText edtName, edtDateOfBirth, edtGender, edtAddress, edtEmail, edtSDT, edtHight, edtWeight, edtNhiptim, edtHuyetap;

    // Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private static final int PICK_IMAGE_REQUEST = 1;
    ImageView imageView;
    private int checksua=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        AnhXa();
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        btnUpdate2.setVisibility(View.GONE);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUpdate.setVisibility(View.GONE);
                btnUpdate2.setVisibility(View.VISIBLE);
                enableAllPlainText();
                checksua=1;
            }
        });

        btnUpdate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUpdate2.setVisibility(View.GONE);
                btnUpdate.setVisibility(View.VISIBLE);
                unenableAllPlainText();
                // Update user profile data in Firebase Realtime Database
                updateUserData();
                checksua=0;
            }
        });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checksua==1){
                    openGallery();}
                    else {Toast.makeText(User.this, "hay an nut sua truoc", Toast.LENGTH_SHORT).show();}
                }
            });

        // Hiển thị thông tin hồ sơ người dùng khi mở ứng dụng
        displayUserData();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            // Gọi hàm xử lý ảnh (ví dụ: hiển thị ảnh trong ImageView)
            handleImage(uri);
        }
    }

    private void handleImage(Uri uri) {
        // Hiển thị ảnh trong ImageView

        imageView.setImageURI(uri);

        // Lưu ảnh vào Firebase Storage (ví dụ)
        uploadImageToFirebase(uri);
    }

    private void uploadImageToFirebase(Uri uri) {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String userId = user.getUid();
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            String imageFileName = userId+".jpg";
            StorageReference imageRef = storageRef.child("profile_images/" + imageFileName);
            imageRef.putFile(uri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Lấy đường dẫn tới ảnh sau khi tải lên thành công
                        imageRef.getDownloadUrl().addOnSuccessListener(uried -> {
                            String imageUrl = uried.toString();
                            // Lưu đường dẫn ảnh vào cơ sở dữ liệu (ví dụ: Realtime Database)
                            saveImageUrlToDatabase(imageUrl);
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Xử lý khi có lỗi xảy ra trong quá trình tải lên
                    });
        }

    }

    private void saveImageUrlToDatabase(String imageUrl) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = databaseReference.child(userId).child("hoso");

            // Cập nhật dữ liệu người dùng trong Firebase Realtime Database
            userRef.child("url").setValue(imageUrl);
        }
    }
    private void loadImageFromFirebaseStorage(String imageUrl) {
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

    public void AnhXa(){
        btnUpdate=findViewById(R.id.btnUpdate);
        btnUpdate2=findViewById(R.id.btnUpdate2);

        editTexts = new ArrayList<>();

        edtName=findViewById(R.id.editTextName);
        edtDateOfBirth=findViewById(R.id.editTextDateofbrith);
        edtGender=findViewById(R.id.editTextGender);
        edtAddress=findViewById(R.id.editTextAddress);
        edtEmail=findViewById(R.id.editTextEmail);
        edtSDT=findViewById(R.id.editTextSDT);
        edtHight=findViewById(R.id.editTextHight);
        edtWeight=findViewById(R.id.editTextWeight);
        edtNhiptim=findViewById(R.id.editTextNhiptim);
        edtHuyetap=findViewById(R.id.editTextHuyetap);
        editTexts.add(edtName);
        editTexts.add(edtDateOfBirth);
        editTexts.add(edtGender);
        editTexts.add(edtAddress);
        editTexts.add(edtEmail);
        editTexts.add(edtSDT);
        editTexts.add(edtHight);
        editTexts.add(edtWeight);
        editTexts.add(edtNhiptim);
        editTexts.add(edtHuyetap);

        imageView = findViewById(R.id.imageView);
    }
    private void displayUserData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = databaseReference.child(userId).child("hoso");
            // Lắng nghe sự thay đổi trong dữ liệu người dùng
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Lấy dữ liệu từ Firebase và hiển thị lên các EditText tương ứng
                        String imageUrl =dataSnapshot.child("url").getValue(String.class); // Lấy đường dẫn ảnh từ cơ sở dữ liệu (ví dụ: Realtime Database)
                        loadImageFromFirebaseStorage(imageUrl);
                        edtName.setText(dataSnapshot.child("name").getValue(String.class));
                        edtDateOfBirth.setText(dataSnapshot.child("date").getValue(String.class));
                        edtGender.setText(dataSnapshot.child("gender").getValue(String.class));
                        edtAddress.setText(dataSnapshot.child("address").getValue(String.class));
                        edtEmail.setText(dataSnapshot.child("email").getValue(String.class));
                        edtSDT.setText(dataSnapshot.child("phone").getValue(String.class));
                        edtHight.setText(String.valueOf(dataSnapshot.child("hight").getValue(Integer.class))+" .cm");
                        edtWeight.setText(String.valueOf(dataSnapshot.child("weight").getValue(Integer.class))+" .kg");
                        edtNhiptim.setText(String.valueOf(dataSnapshot.child("nhiptim").getValue(Integer.class))+" .lần/giây");
                        edtHuyetap.setText(dataSnapshot.child("huyetap").getValue(String.class)+" .mmhg");
                    }else {
                        Toast.makeText(User.this, "khong ton tai", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu từ Firebase
                    Toast.makeText(User.this, "Lỗi đọc dữ liệu", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void enableAllPlainText() {
        for (EditText editText : editTexts) {
            editText.setEnabled(true);
        }
    }

    private void unenableAllPlainText() {
        for (EditText editText : editTexts) {
            editText.setEnabled(false);
        }
    }

    private void updateUserData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = databaseReference.child(userId).child("hoso");

            // Cập nhật dữ liệu người dùng trong Firebase Realtime Database
            userRef.child("name").setValue(edtName.getText().toString());
            userRef.child("date").setValue(edtDateOfBirth.getText().toString());
            userRef.child("gender").setValue(edtGender.getText().toString());
            userRef.child("address").setValue(edtAddress.getText().toString());
            userRef.child("email").setValue(edtEmail.getText().toString());
            userRef.child("phone").setValue(edtSDT.getText().toString());
            userRef.child("hight").setValue(Float.parseFloat(String.valueOf(edtHight.getText()).replaceAll("[^0-9.]", "")));
            userRef.child("weight").setValue(Float.parseFloat(String.valueOf(edtWeight.getText()).replaceAll("[^0-9.]", "")));
            userRef.child("nhiptim").setValue(Float.parseFloat(String.valueOf(edtNhiptim.getText()).replaceAll("[^0-9.]", "")));
            userRef.child("huyetap").setValue(edtHuyetap.getText().toString().replaceAll("[^0-9/]", ""));
        }
    }
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }
    public void onBackPressed() {
        // Khi nút "Back" được nhấn, gọi finish() để đóng Activity
        super.onBackPressed();
        finish();
    }
}
