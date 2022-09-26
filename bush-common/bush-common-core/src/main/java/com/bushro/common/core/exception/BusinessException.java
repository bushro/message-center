package com.bushro.common.core.exception;

import com.bushro.common.core.enums.IMessagePairs;
import com.bushro.common.core.enums.MessageEnum;

/**
 * 业务异常
 *
 * @author ASAP Framework Team
 * @since 1.0
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 5441923856899380112L;

    private int code;
    private String message;

    public BusinessException(String message) {
        this(message, MessageEnum.BUSINESS_ERROR.code());
    }

    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(IMessagePairs<Integer, String> pairs) {
        super(pairs.message());
        this.code = pairs.code();
        this.message = pairs.message();
    }


    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
