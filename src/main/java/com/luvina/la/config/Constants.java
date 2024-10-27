package com.luvina.la.config;

public class Constants {

    private Constants() {
    }

    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    public static final boolean IS_CROSS_ALLOW = true;

    public static final String JWT_SECRET = "Luvina-Academe";
    public static final long JWT_EXPIRATION = 160 * 60 * 60; // 7 day

    // config endpoints public
    public static final String[] ENDPOINTS_PUBLIC = new String[] {
            "/",
            "/login/**",
            "/error/**"
    };

    // config endpoints for USER role
    public static final String[] ENDPOINTS_WITH_ROLE = new String[] {
            "/user/**"
    };

    // user attributies put to token
    public static final String[] ATTRIBUTIES_TO_TOKEN = new String[] {
            "employeeId",
            "employeeName",
            "employeeLoginId",
            "employeeEmail"
    };

    // Error messages
    public static final String ER001 = "ER001";
    public static final String ER002 = "ER002";
    public static final String ER003 = "ER003";
    public static final String ER004 = "ER004";
    public static final String ER005 = "ER005";
    public static final String ER006 = "ER006";
    public static final String ER007 = "ER007";
    public static final String ER008 = "ER008";
    public static final String ER009 = "ER009";
    public static final String ER010 = "ER010";
    public static final String ER011 = "ER011";
    public static final String ER012 = "ER012";
    public static final String ER013 = "ER013";
    public static final String ER014 = "ER014";
    public static final String ER015 = "ER015";
    public static final String ER016 = "ER016";
    public static final String ER017 = "ER017";
    public static final String ER018 = "ER018";
    public static final String ER019 = "ER019";
    public static final String ER020 = "ER020";
    public static final String ER021 = "ER021";
    public static final String ER022 = "ER022";
    public static final String ER023 = "ER023";

    // Success messages
    public static final String MSG001 = "MSG001";
    public static final String MSG002 = "MSG002";
    public static final String MSG003 = "MSG003";
    public static final String MSG004 = "MSG004";
    public static final String MSG005 = "MSG005";

    //
    public static final String HTTP_STATUS_OK = "200"; // Request has succeeded
    public static final String HTTP_STATUS_INTERNAL_ERROR = "500"; // Internal server error

}
