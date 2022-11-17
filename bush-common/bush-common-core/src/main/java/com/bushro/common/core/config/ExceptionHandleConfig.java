package com.bushro.common.core.config;

import com.bushro.common.core.enums.MessageEnum;
import com.bushro.common.core.exception.BusinessException;
import com.bushro.common.core.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * @author luo.qiang
 * @date 2022/10/30
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandleConfig {

    /**
     * 自定义系统业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public R handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        return R.failed(e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public R handlerNoFoundException(Exception e) {
        log.error("404:", e);
        return R.failed(404, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public R<String> handleRuntimeException(IllegalArgumentException e) {
        log.error("参数不合法:", e);
        return R.failed(e.getMessage());
    }

    /**
     * jsr303参数校验异常
     */
    @ExceptionHandler({BindException.class})
    public R<String> exception(BindException e) {
        log.error("参数校验异常:", e);
        return R.failed(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 方法参数校验
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("方法参数校验:" + e.getMessage(), e);
        String errorMessage = String.join(",", e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
        return R.failed(errorMessage);
    }


    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public R nullPointerExceptionHandler(NullPointerException e) {
        log.error("空指针异常:", e);
        return R.failed("空指针异常:" + e.getMessage());
    }

    /**
     * 类型转换异常
     */
    @ExceptionHandler(ClassCastException.class)
    public R classCastExceptionHandler(ClassCastException e) {
        log.error("类型转换异常:", e);
        return R.failed("类型转换异常:" + e.getMessage());
    }

    /**
     * 数组越界异常
     */
    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public R arrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException e) {
        log.error("数组越界异常:", e);
        return R.failed("数组越界异常:" + e.getMessage());
    }

    /**
     * 异常处理，兜底的
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception exception) {
        log.error(exception.getMessage(), exception);
        return R.failed(MessageEnum.ERROR.message());
    }

}
