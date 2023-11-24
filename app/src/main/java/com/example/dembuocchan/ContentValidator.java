package com.example.dembuocchan;

import android.icu.text.CaseMap;

import java.text.Normalizer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Pattern;

public class ContentValidator {
    public static boolean kiemTraNoiDungKhongHopLe(String noiDung) {
        String check;
        // Danh sách các từ vi phạm tiêu chuẩn
        String[] tuViPham = {"con me may", "ma tuy", "d c m m", "Mat day", "chui bay", "do khon", "do ngu", "do hen",
                "do vo hoc", "do vo van hoa", "do vo tich su", "do vo dung",
                "do vo trach nhiem", "do vo luong tam", "do vo liem si", "do vo dao duc",
                "do vo phep tac", "do vo ky luat", "do vo luan thuong dao ly", "do vo nhan dao", "do vo cam",
                "do vo tri vo giac", "do vo hon", "do vo nghia", "do vo gia tri","conmeme", "matuy",
                "dcmm", "matday", "chuibay", "dokhon", "dongu", "dohen",
                "dovohoc", "dovovanhoa", "dovotichsu", "dovodung",
                "dovotrachnhiem", "dovoluongtam", "dovoliemsi", "dovodaoduc",
                "dovopheptac", "dovokyluat", "dovoluanthuongdaoly", "dovonhanda", "dovocam",
                "dovotrivogiac", "dovohon", "dovonghia", "dovogiatri"};

        // Chuyển đổi nội dung và danh sách từ cần kiểm tra về chữ thường và không dấu
        check = removeAccents(noiDung).toLowerCase().trim();


        // Kiểm tra xem nội dung có chứa các từ vi phạm hay không
        for (String tu : tuViPham) {

            if (check.contains(tu)) {
                return true; // Nếu chứa từ vi phạm, coi là nội dung không hợp lệ
            }
        }

        // Nếu không có từ vi phạm, coi là nội dung hợp lệ
        return false;
    }

    private static String removeAccents(String input) {
        // Loại bỏ các ký tự đặc biệt và dấu cách
        return input.replaceAll("[\\p{InCombiningDiacriticalMarks}\\s]", "");
    }
}
