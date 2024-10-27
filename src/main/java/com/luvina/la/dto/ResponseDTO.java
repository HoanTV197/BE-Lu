package com.luvina.la.dto;

import lombok.Data;

/**
 * ResponseDTO chứa thông tin phản hồi sau khi thực hiện các API.
 */
@Data
public class ResponseDTO {
    private int code; // HTTP Status Code
    private Object employeeId; // Dữ liệu trả về (nếu có)
    private Message message; // Thông tin thông báo hoặc lỗi

    public ResponseDTO(int code, Object employeeId, Message message) {
        this.code = code;
        this.employeeId = employeeId;
        this.message = message;
    }

    @Data
    public static class Message {
        private String code;
        private Object[] params;

        public Message(String code, Object[] params) {
            this.code = code;
            this.params = params;
        }
    }
}