package zipview_server.config;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    EXIST_EMAIL(false,2001,"이미 존재하는 회원입니다."),
    INVALID_EMAIL(false,2002,"이메일 형식을 확인해주세요."),
    EMPTY_EMAIL(false,2003,"이메일을 입력해주세요."),
    EXIST_NICKNAME(false,2010,"이미 존재하는 닉네임입니다."),

    INVALID_PWD(false,2021,"비밀번호 형식을 확인해주세요."),




    /**
     * 3000 : Response 오류
     */


    /**
     * 4000 : Database, Server 오류
     */
    FAIL_ENCRYPT_PWD(false,4000,"비밀번호 암호화에 실패하였습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}