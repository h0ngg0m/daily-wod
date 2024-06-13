package hong.dailywod.global.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import hong.dailywod.global.response.ApiResult;
import hong.dailywod.global.response.ResponseFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    // 놓친 예외는 이곳에서 확인 후 추가
    @ExceptionHandler({Throwable.class})
    public ResponseEntity<ApiResult<?>> throwable(final Throwable e) {
        log.warn("[Throwable!!!] start ---");
        log.warn(e.getMessage(), e);
        // TODO: 개발자에게 알림 처리 추가
        return ResponseFactory.badRequest();
    }

    @ExceptionHandler({ClientBadRequestException.class})
    public ResponseEntity<ApiResult<?>> clientBadRequestException(
            final ClientBadRequestException e) {
        log.warn(e.getMessage(), e);
        if (e.getCode().equals(ExceptionCode.FAIL_UNAUTHORIZED.getCode())) {
            return ResponseFactory.unauthorized(e.getCode(), e.getMessage());
        }
        if (e.getCode().equals(ExceptionCode.FAIL_FORBIDDEN.getCode())) {
            return ResponseFactory.forbidden(e.getCode(), e.getMessage());
        }
        return ResponseFactory.badRequest(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({SystemException.class})
    public ResponseEntity<ApiResult<?>> systemException(final SystemException e) {
        log.warn(e.getMessage(), e);
        // TODO: 개발자에게 알림 처리 추가
        return ResponseFactory.ok(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiResult<?>> illegalArgumentException(final IllegalArgumentException e) {
        log.warn(e.getMessage(), e);
        return ResponseFactory.badRequest(ExceptionCode.FAIL_INVALID_PARAMETER);
    }

    @ExceptionHandler({NoResourceFoundException.class})
    public ResponseEntity<ApiResult<?>> noResourceFoundException(final NoResourceFoundException e) {
        log.warn(e.getMessage(), e);
        return ResponseFactory.notFound(ExceptionCode.FAIL_NOT_FOUND_DATA);
    }

    @ExceptionHandler({
        BindException.class,
        MethodArgumentTypeMismatchException.class,
        MissingServletRequestParameterException.class
    })
    public ResponseEntity<ApiResult<?>> bindException(final Exception e) {
        log.warn(e.getMessage(), e);
        return ResponseFactory.badRequest(ExceptionCode.FAIL_INVALID_PARAMETER);
    }

    @ExceptionHandler({
        HttpMediaTypeNotAcceptableException.class,
        HttpMediaTypeNotSupportedException.class,
        HttpRequestMethodNotSupportedException.class,
        HttpClientErrorException.class
    })
    public ResponseEntity<ApiResult<?>> httpMediaTypeNotAcceptableException(final Exception e) {
        log.warn(e.getMessage(), e);
        return ResponseFactory.badRequest(ExceptionCode.FAIL_INVALID_REQUEST);
    }

    @ExceptionHandler({DuplicateKeyException.class})
    public ResponseEntity<ApiResult<?>> duplicateKeyException(final DuplicateKeyException e) {
        log.warn(e.getMessage(), e);
        return ResponseFactory.badRequest(ExceptionCode.FAIL_UNIQUE_CONSTRAINT_VIOLATED);
    }
}
