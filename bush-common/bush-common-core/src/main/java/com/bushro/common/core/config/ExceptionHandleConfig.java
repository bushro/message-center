package com.bushro.common.core.config;

import com.bushro.common.core.enums.MessageEnum;
import com.bushro.common.core.exception.BusinessException;
import com.bushro.common.core.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
     * 表单参数验证失败
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String errorMessage = String.join(",", e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
        return R.failed(errorMessage);
    }

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
