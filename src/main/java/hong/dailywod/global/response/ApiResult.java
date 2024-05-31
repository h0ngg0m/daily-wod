package hong.dailywod.global.response;

import hong.dailywod.global.exception.ExceptionCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResult<T> {

    private String code;
    private String message;
    private T data;

    public static ApiResult<?> of(ExceptionCode exceptionCode) {
        return ApiResult.of(exceptionCode, null);
    }

    public static <MT> ApiResult<MT> of(ExceptionCode exceptionCode, MT data) {
        return new ApiResult<>(exceptionCode.getCode(), exceptionCode.getMessage(), data);
    }

    public static <MT> ApiResult<MT> of(String code, String message) {
        return new ApiResult<>(code, message, null);
    }
}
