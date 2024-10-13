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
    public static final String ER001 = "「画面項目名」を入力してください";
    public static final String ER002 = "「画面項目名」を入力してください";
    public static final String ER003 = "「画面項目名」は既に存在しています。";
    public static final String ER004 = "「画面項目名」は存在していません。";
    public static final String ER005 = "「画面項目名」をxxx形式で入力してください";
    public static final String ER006 = "xxxx桁以内の「画面項目名」を入力してください";
    public static final String ER007 = "「画面項目名」をxxx＜＝桁数、＜＝xxx桁で入力してください";
    public static final String ER008 = "「画面項目名」に半角英数を入力してください";
    public static final String ER009 = "「画面項目名」をカタカナで入力してください";
    public static final String ER010 = "「画面項目名」をひらがなで入力してください";
    public static final String ER011 = "「画面項目名」は無効になっています。";
    public static final String ER012 = "「失効日」は「資格交付日」より未来の日で入力してください。";
    public static final String ER013 = "該当するユーザは存在していません。";
    public static final String ER014 = "該当するユーザは存在していません。";
    public static final String ER015 = "システムエラーが発生しました。";
    public static final String ER016 = "「アカウント名」または「パスワード」は不正です。";
    public static final String ER017 = "「パスワード（確認」が不正です。";
    public static final String ER018 = "「画面上の項目名」は半角で入力してください。";
    public static final String ER019 = "[アカウント名]は(a-z, A-Z, 0-9 と _)の桁のみです。最初の桁は数字ではない。";
    public static final String ER020 = "管理者ユーザを削除することはできません。";
    public static final String ER021 = "ソートは (ASC, DESC) でなければなりません。";
    public static final String ER022 = "ページが見つかりません。";
    public static final String ER023 = "システムエラーが発生しました。";

    // Success messages
    public static final String MSG001 = "ユーザの登録が完了しました。";
    public static final String MSG002 = "ユーザの更新が完了しました。";
    public static final String MSG003 = "ユーザの削除が完了しました。";
    public static final String MSG004 = "削除しますが、よろしいでしょうか。";
    public static final String MSG005 = "検索条件に該当するユーザが見つかりません。";
}
