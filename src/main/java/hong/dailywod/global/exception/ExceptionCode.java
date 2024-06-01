package hong.dailywod.global.exception;

import java.text.MessageFormat;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    SUCCESS("S000", "성공"),

    SYSTEM_ERROR("E000", "시스템 에러"),
    SYSTEM_ERROR_DAILY_WOD_NOT_FOUND("E001", "Daily WOD를 찾을 수 없음"),

    FAIL_INVALID_PARAMETER("F001", "올바르지 않은 파라미터"),
    FAIL_INVALID_REQUEST("F002", "올바르지 않은 요청"),
    FAIL_NOT_FOUND_DATA("F003", "존재하지 않는 데이터"),
    FAIL_DUPLICATED_DATA("F004", "중복되는 데이터"),
    FAIL_UNIQUE_CONSTRAINT_VIOLATED("F999", "이미 존재하는 데이터");

    private String code;
    private String message;

    ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}({1}, {2})", this.name(), this.code, this.message);
    }
}
