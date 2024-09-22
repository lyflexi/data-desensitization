package org.lyflexi.backenddesensitization.result;

import lombok.Data;

/**
 * @Description:
 * @Author: lyflexi
 * @project: debuginfo_jdkToFramework
 * @Date: 2024/8/15 13:40
 */

@Data
public class Result<T> {
    private String code;
    private String message;
    private T data;

    public Result() {
    }

    public Result(ResultType resultType) {
        this.code = resultType.getCode();
        this.message = resultType.getMsg();
    }

    public Result(String code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public Result(ResultType resultType, T data) {
        this(resultType);
        this.data = data;
    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public static <T> Result<T> data(T data) {
        return new Result<>(SystemResultType.SYSTEM_SUCCESS, data);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(SystemResultType.SYSTEM_SUCCESS, data);
    }

    public static Result<String> successStringData(String data) {
        return new Result<>(SystemResultType.SYSTEM_SUCCESS, data);
    }


    public static <T> Result<T> success(String message) {
        return new Result<>(SystemResultType.SYSTEM_SUCCESS.getCode(), message);
    }
    public static <T> Result<T> fail(String message) {
        return new Result<>(SystemResultType.SYSTEM_ERROR.getCode(), message);
    }

    public static <T> Result<T> success() {
        return success(null);
    }


    public static <T> Result<T> result(ResultType resultType, T data) {
        return new Result<>(resultType, data);
    }

    public static <T> Result<T> result(ResultType resultType) {
        return result(resultType, null);
    }

    public static <T> Result<T> fail() {
        return new Result<>(SystemResultType.SYSTEM_ERROR);
    }

    public static <T> Result<T> fail(T data) {
        return new Result<>(SystemResultType.SYSTEM_ERROR.getCode(), data.toString());
    }

    public static <T> Result<T> failWithData(T data) {
        return new Result<>(SystemResultType.SYSTEM_ERROR.getCode(), null, data);
    }

    public boolean isSuccess () {
        return SystemResultType.SYSTEM_SUCCESS.getCode().equals(this.code);
    }

}
