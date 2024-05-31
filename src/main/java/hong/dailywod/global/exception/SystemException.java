package hong.dailywod.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SystemException extends RuntimeException {

    private String code;
    private String message;

    public SystemException(ExceptionCode exceptionCode) {
        super(exceptionCode.toString());
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }

    public SystemException(ExceptionCode exceptionCode, String customMessage) {
        super(exceptionCode.toString());
        this.code = exceptionCode.getCode();
        this.message = customMessage;
    }
}
