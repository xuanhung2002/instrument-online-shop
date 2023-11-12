package com.shop.controller;

import com.shop.config.VNPayConfig;
import com.shop.dto.PaymentResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.shop.config.VNPayConfig.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @GetMapping("/create_VNPay_payment")
    public ResponseEntity<?> createVNPaypayment(HttpServletRequest req, @RequestParam(value = "amount") Long amount) throws UnsupportedEncodingException {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
//        long amount = Integer.parseInt(req.getParameter("amount"))*100;
//        String bankCode = req.getParameter("bankCode");

        String vnp_TxnRef = getRandomNumber(8);
        String vnp_IpAddr = getIpAddress(req);

        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = hmacSHA512(secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnp_PayUrl + "?" + queryUrl;

        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        paymentResponseDTO.setURL(paymentUrl);
        paymentResponseDTO.setStatus("OK");
        paymentResponseDTO.setMessage("Successfully");

        return ResponseEntity.status(HttpStatus.OK).body(paymentResponseDTO);
    }


//    @PostMapping("/vnpay-notify")
//    public ResponseEntity<String> processVnPayResponse(@RequestBody Map<String, String> requestBody, HttpServletRequest request) throws UnsupportedEncodingException {
//        // Điều này giả sử bạn đã cấu hình URL IPN từ VnPay để trỏ vào "/vnpay-notify" trong ứng dụng Spring Boot của bạn.
//
//        // Begin process return from VNPAY
//        Map<String, String> fields = new HashMap<>();
//        Enumeration<String> params = request.getParameterNames();
//        while (params.hasMoreElements()) {
//            String fieldName = params.nextElement();
//            String fieldValue = request.getParameter(fieldName);
//            fields.put(fieldName, fieldValue);
//        }
//
//        String vnpSecureHash = requestBody.get("vnp_SecureHash");
//        if (fields.containsKey("vnp_SecureHashType")) {
//            fields.remove("vnp_SecureHashType");
//        }
//        if (fields.containsKey("vnp_SecureHash")) {
//            fields.remove("vnp_SecureHash");
//        }
//        String signValue = VNPayConfig.hashAllFields(fields); // Implement this method to generate the secure hash.
//
//        if (signValue.equals(vnpSecureHash)) {
//            boolean checkOrderId = true; // Giá trị của vnp_TxnRef tồn tại trong CSDL của merchant
//            boolean checkAmount = true; // Kiểm tra số tiền thanh toán do VNPAY phản hồi(vnp_Amount/100) với số tiền của đơn hàng merchant tạo thanh toán: giả sử số tiền kiểm tra là đúng.
//            boolean checkOrderStatus = true; // Giả sử PaymnentStatus = 0 (pending) là trạng thái thanh toán của giao dịch khởi tạo chưa có IPN.
//
//            if (checkOrderId) {
//                if (checkAmount) {
//                    if (checkOrderStatus) {
//                        if ("00".equals(requestBody.get("vnp_ResponseCode"))) {
//                            // Xử lý/Cập nhật tình trạng giao dịch thanh toán "Thành công"
//                            return ResponseEntity.ok("{\"RspCode\":\"00\",\"Message\":\"Confirm Success\"}");
//                        } else {
//                            // Xử lý/Cập nhật tình trạng giao dịch thanh toán "Không thành công"
//                            return ResponseEntity.ok("{\"RspCode\":\"00\",\"Message\":\"Confirm Success\"}");
//                        }
//                    } else {
//                        // Trạng thái giao dịch đã được cập nhật trước đó
//                        return ResponseEntity.ok("{\"RspCode\":\"02\",\"Message\":\"Order already confirmed\"}");
//                    }
//                } else {
//                    // Số tiền không trùng khớp
//                    return ResponseEntity.ok("{\"RspCode\":\"04\",\"Message\":\"Invalid Amount\"}");
//                }
//            } else {
//                // Mã giao dịch không tồn tại
//                return ResponseEntity.ok("{\"RspCode\":\"01\",\"Message\":\"Order not Found\"}");
//            }
//        } else {
//            // Sai checksum
//            return ResponseEntity.ok("{\"RspCode\":\"97\",\"Message\":\"Invalid Checksum\"}");
//        }
//    }
}
