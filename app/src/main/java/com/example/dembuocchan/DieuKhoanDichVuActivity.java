package com.example.dembuocchan;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DieuKhoanDichVuActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieu_khoan_dich_vu);
        textView = findViewById(R.id.tvdk);
        textView.setText("1. Sử dụng ứng dụng:\n" +
                "Bằng cách tải và sử dụng ứng dụng đếm bước chân này, bạn đồng ý tuân thủ các điều khoản và điều kiện dưới đây.\n" +
                "2. Yêu cầu sử dụng:\n" +
                "Ứng dụng đếm bước chân này chỉ được sử dụng cho mục đích cá nhân.\n" +
                "Bạn không được phép sử dụng ứng dụng cho bất kỳ mục đích phi pháp hoặc gây hại đến người khác.\n" +
                "3. Điều kiện sử dụng:\n" +
                "Ứng dụng đếm bước chân được cung cấp \"như đã có\" mà không có bất kỳ bảo đảm nào. Chúng tôi không chịu trách nhiệm về bất kỳ thiệt hại hoặc mất mát nào phát sinh từ việc sử dụng ứng dụng.\n" +
                "Chúng tôi không chịu trách nhiệm đối với bất kỳ thông tin cá nhân hoặc dữ liệu nào do bạn cung cấp trong quá trình sử dụng ứng dụng.\n" +
                "Bạn chịu trách nhiệm duy trì và bảo vệ thông tin đăng nhập và mật khẩu của mình để tránh truy cập trái phép vào tài khoản của bạn.\n" +
                "4. Quyền sở hữu:\n" +
                "Chúng tôi giữ quyền sở hữu tất cả các quyền trích dẫn, thương hiệu, tài sản trí tuệ và tài sản khác liên quan đến ứng dụng đếm bước chân này.\n" +
                "Bạn không được sao chép, sửa đổi, phân phối hoặc tái sản xuất bất kỳ phần nào của ứng dụng mà không có sự cho phép trước bằng văn bản từ chúng tôi.\n" +
                "5. Sửa đổi và chấm dứt:\n" +
                "Chúng tôi có quyền sửa đổi hoặc chấm dứt điều khoản dịch vụ này vào bất kỳ thời điểm nào và mà không cần thông báo trước.\n" +
                "Bạn có quyền chấm dứt việc sử dụng ứng dụng đếm bước chân này bất kỳ lúc nào.");
    }
}