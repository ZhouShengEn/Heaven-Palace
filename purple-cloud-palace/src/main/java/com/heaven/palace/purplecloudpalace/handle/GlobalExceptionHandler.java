package com.heaven.palace.purplecloudpalace.handle;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import com.heaven.palace.jasperpalace.base.exception.auth.AuthenticationException;
import com.heaven.palace.jasperpalace.base.response.BaseResponse;
import com.heaven.palace.jasperpalace.base.response.GlobalRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * 公共全局异常处理类
 *
 * @author zhoushengen
 * @date 2023/11/22
 */
@Slf4j
@RestControllerAdvice({"com.heaven.palace"})
public class GlobalExceptionHandler {
    /**
     * 处理自定义的业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public BaseResponse bizExceptionHandler(BusinessException e) {
        log.error("发生业务异常！原因是：", e);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage(e.getMessage());
        baseResponse.setStatusCode(e.getStatusCode());
        return baseResponse;
    }

    /**
     * 处理空指针异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    public BaseResponse nullPointerExceptionHandler(NullPointerException e) {
        log.error("发生空指针异常！原因是：", e);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("发生空指针异常！");
        baseResponse.setStatusCode(500);
        return baseResponse;
    }

    /**
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常，详情继续往下看代码
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public BaseResponse BindExceptionHandler(BindException e) {
        log.error("请求参数格式错误异常！原因是：", e);
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage(message);
        baseResponse.setStatusCode(500);
        return baseResponse;
    }

    /**
     * 处理@RequestParam(required=true)参数未传报错异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public BaseResponse MissingParamExceptionHandler(MissingServletRequestParameterException e) {
        log.error("请求参数错误异常！原因是：", e);
        String message = "请求参数" + e.getParameterName() + "不能为空";
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage(message);
        baseResponse.setStatusCode(500);
        return baseResponse;
    }

    /**
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResponse ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        log.error("请求参数格式错误异常！原因是：", e);
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage(message);
        baseResponse.setStatusCode(500);
        return baseResponse;
    }

    /**
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("请求参数格式错误异常！原因是：", e);
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage(message);
        baseResponse.setStatusCode(500);
        return baseResponse;
    }

    /**
     * 处理文件上传过大异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public BaseResponse maxUploadSizeExceededExceptionHandler(MaxUploadSizeExceededException e) {
        log.error("文件过大异常！原因是：", e);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("文件过大异常！");
        baseResponse.setStatusCode(500);
        return baseResponse;
    }

    /**
     * 处理参数转换异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpMessageConversionException.class)
    public GlobalRestResponse httpMessageNotReadableExceptionHandler(HttpMessageConversionException e) {
        log.error("参数转换异常！原因是：", e);
        GlobalRestResponse<Object> globalRestResponse = new GlobalRestResponse();
        globalRestResponse.setMessage("参数转换异常！原因是：" + e.getMessage());
        globalRestResponse.setStatusCode(500);
        Throwable cause = e.getCause();
        if (cause instanceof InvalidFormatException) {
            globalRestResponse.setMessage("参数转换异常！原因是：" + ((InvalidFormatException) cause).getOriginalMessage());
            globalRestResponse.setData(new HashMap() {
                private static final long serialVersionUID = -3939368368275206373L;

                {
                    put("value", ((InvalidFormatException) cause).getValue());
                    put("targetType", ((InvalidFormatException) cause).getTargetType().getSimpleName());
                }
            });
        }
        return globalRestResponse;
    }

    /**
     * 处理唯一性约束异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = DuplicateKeyException.class)
    public BaseResponse duplicateKeyExceptionHandler(DuplicateKeyException e) {
        log.error("发生唯一性约束异常！原因是：", e);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("发生唯一性约束异常！");
        baseResponse.setStatusCode(500);
        return baseResponse;
    }

    /**
     * 处理数据库异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = DataAccessException.class)
    public BaseResponse dataAccessExceptionHandler(DataAccessException e) {
        log.error("发生数据库异常！原因是：", e);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("发生数据库异常！请联系客服人员。");
        baseResponse.setStatusCode(500);
        return baseResponse;
    }

    @ExceptionHandler(value = RestClientException.class)
    public BaseResponse restClientExceptionHandler(RestClientException e) {
        log.error("调用外部接口异常！原因是：", e);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("调用外部接口异常！请联系客服人员。");
        baseResponse.setStatusCode(500);
        return baseResponse;
    }

    /**
     * 处理认证异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public BaseResponse exceptionHandler(HttpServletResponse response, AuthenticationException e) {
        log.error("认证异常！原因是：", e);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return new BaseResponse(e.getStatusCode(), e.getMessage());
    }

    /**
     * 处理其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public BaseResponse exceptionHandler(Exception e) {
        log.error("服务器内部未知异常！原因是：", e);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("服务器内部未知异常！请联系客服人员。");
        baseResponse.setStatusCode(500);
        return baseResponse;
    }

}
