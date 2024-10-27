package com.luvina.la.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    private int code;
    private Long employeeId;
    private Message message;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {
        private String code;
        private Object[] params;
    }
}
