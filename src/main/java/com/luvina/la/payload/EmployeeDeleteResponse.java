package com.luvina.la.payload;

import lombok.Data;

/**
 * EmployeeDeleteResponse chứa thông tin trả về sau khi xóa nhân viên.
 */
@Data
public class EmployeeDeleteResponse {
    private int code;
    private Long employeeId;
    private Message message;

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
