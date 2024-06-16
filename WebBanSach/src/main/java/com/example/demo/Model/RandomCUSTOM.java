package com.example.demo.Model;
import java.util.Random;
public class RandomCUSTOM {
	public static String generateRandomCode() {
        int randomNumber = new Random().nextInt(1000); // Lấy số ngẫu nhiên từ 0 đến 999
        long timestamp = System.currentTimeMillis(); // Lấy thời gian hiện tại (timestamp)

        String combinedNumber = String.valueOf(randomNumber) + String.valueOf(timestamp); // Ghép số ngẫu nhiên và timestamp lại với nhau

        return convertToCustomBase(combinedNumber);
    }

    public static String convertToCustomBase(String number) {
        String baseChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // Chuỗi các ký tự dùng cho hệ mới
        StringBuilder result = new StringBuilder();

        long num = Long.parseLong(number);

        // Chuyển đổi số từ hệ thập phân sang hệ mới
        while (num > 0) {
            int remainder = (int) (num % 36); // Lấy phần dư của số
            char baseChar = baseChars.charAt(remainder); // Lấy ký tự tương ứng từ hệ mới
            result.insert(0, baseChar); // Thêm ký tự vào đầu chuỗi kết quả
            num /= 36; // Chia số cho 36 để tiếp tục chuyển đổi
        }

        return result.toString();
    }
}
