package com.example.dembuocchan;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChinhSachBaoMatActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sach_bao_mat);
        textView = findViewById(R.id.mytextview);
        textView.setText("1. Thông tin thu thập:\n" +
                "Khi sử dụng ứng dụng đếm bước chân, chúng tôi có thể thu thập các thông tin cá nhân như tên, địa chỉ email và thông tin liên quan khác.\n" +
                "Chúng tôi cũng có thể thu thập thông tin về hoạt động sử dụng của bạn trong ứng dụng, bao gồm dữ liệu về bước chân và các thông số liên quan.\n" +
                "2. Sử dụng thông tin:\n" +
                "Chúng tôi sử dụng thông tin cá nhân được thu thập để cung cấp và cải thiện ứng dụng đếm bước chân.\n" +
                "Thông tin về hoạt động sử dụng của bạn có thể được sử dụng để phân tích và cải thiện trải nghiệm người dùng của ứng dụng.\n" +
                "3. Bảo mật thông tin:\n" +
                "Chúng tôi thực hiện các biện pháp bảo mật thích hợp để bảo vệ thông tin cá nhân của bạn khỏi mất mát, truy cập trái phép, tiết lộ, sửa đổi hoặc hủy bỏ.\n" +
                "Tuy nhiên, không có phương pháp truyền dữ liệu trên Internet hoặc phương thức lưu trữ điện tử nào là 100% an toàn và bảo mật. Chúng tôi không thể đảm bảo hoàn toàn an toàn của thông tin cá nhân của bạn.\n" +
                "4. Chia sẻ thông tin:\n" +
                "Chúng tôi không chia sẻ thông tin cá nhân của bạn với bên thứ ba trừ khi có sự đồng ý của bạn hoặc khi chúng tôi cần phải tuân thủ các quy định pháp luật hiện hành.\n" +
                "5. Liên hệ:\n" +
                "Nếu bạn có bất kỳ câu hỏi hoặc yêu cầu liên quan đến Chính sách Bảo mật này hoặc việc xử lý thông tin cá nhân của bạn, vui lòng liên hệ với chúng tôi theo thông tin liên hệ được cung cấp dưới đây.");
    }
}