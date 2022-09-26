package com.bushro.common.core.enums;

public interface IMessagePairs<K, V> {
    /**
     * 返回枚举项的 code
     * */
    K code();

    /**
     * 返回枚举项的 message
     * */
    V message();
}
