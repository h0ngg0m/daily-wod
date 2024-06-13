package hong.dailywod.global.response;

import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import hong.dailywod.global.exception.ExceptionCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseFactory {

    public static <T> ResponseEntity<ApiResult<T>> created(T data) {
        return ResponseEntity.status(201).body(ApiResult.of(ExceptionCode.SUCCESS, data));
    }

    public static ResponseEntity<ApiResult<?>> ok() {
        return ResponseEntity.status(200).body(ApiResult.of(ExceptionCode.SUCCESS));
    }

    public static <T> ResponseEntity<ApiResult<T>> ok(T data) {
        return ResponseEntity.status(200).body(ApiResult.of(ExceptionCode.SUCCESS, data));
    }

    public static ResponseEntity<ApiResult<?>> ok(String code, String message) {
        Assert.notNull(code, "Parameter `code` must not be null");
        Assert.notNull(message, "Parameter `message` must not be null");
        return ResponseEntity.status(200).body(ApiResult.of(code, message));
    }

    public static ResponseEntity<ApiResult<?>> badRequest() {
        return ResponseEntity.status(400).body(ApiResult.of(ExceptionCode.SYSTEM_ERROR));
    }

    public static ResponseEntity<ApiResult<?>> badRequest(ExceptionCode exceptionCode) {
        Assert.notNull(exceptionCode, "Parameter `exceptionCode` must not be null");
        return ResponseEntity.status(400).body(ApiResult.of(exceptionCode));
    }

    public static ResponseEntity<ApiResult<?>> badRequest(String code, String message) {
        Assert.notNull(code, "Parameter `code` must not be null");
        Assert.notNull(message, "Parameter `message` must not be null");
        return ResponseEntity.status(400).body(ApiResult.of(code, message));
    }

    public static ResponseEntity<ApiResult<?>> notFound(ExceptionCode exceptionCode) {
        Assert.notNull(exceptionCode, "Parameter `exceptionCode` must not be null");
        return ResponseEntity.status(404).body(ApiResult.of(exceptionCode));
    }

    public static ResponseEntity<ApiResult<?>> unauthorized(ExceptionCode exceptionCode) {
        Assert.notNull(exceptionCode, "Parameter `exceptionCode` must not be null");
        return ResponseEntity.status(401).body(ApiResult.of(exceptionCode));
    }

    public static ResponseEntity<ApiResult<?>> unauthorized(String code, String message) {
        Assert.notNull(code, "Parameter `code` must not be null");
        Assert.notNull(message, "Parameter `message` must not be null");
        return ResponseEntity.status(401).body(ApiResult.of(code, message));
    }

    public static ResponseEntity<ApiResult<?>> forbidden(ExceptionCode exceptionCode) {
        Assert.notNull(exceptionCode, "Parameter `exceptionCode` must not be null");
        return ResponseEntity.status(403).body(ApiResult.of(exceptionCode));
    }

    public static ResponseEntity<ApiResult<?>> forbidden(String code, String message) {
        Assert.notNull(code, "Parameter `code` must not be null");
        Assert.notNull(message, "Parameter `message` must not be null");
        return ResponseEntity.status(403).body(ApiResult.of(code, message));
    }
}
