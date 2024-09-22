package org.lyflexi.backenddesensitization.result;

import lombok.Getter;

/**
 * @Description:
 * @Author: lyflexi
 * @project: debuginfo_jdkToFramework
 * @Date: 2024/8/15 13:41
 */
@Getter
public enum SystemResultType implements ResultType {

    SYSTEM_SUCCESS("000000", "handle.success"),

    SYSTEM_ERROR("-1", "system.error"),

    ;

    private String code;

    private String msg;

    SystemResultType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
