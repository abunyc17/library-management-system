package cc.maid.lms.controller.advice;

import cc.maid.lms.exception.RequestException;
import cc.maid.lms.exception.RequestNotFoundException;
import cc.maid.lms.model.BaseResponse;
import cc.maid.lms.model.ResponseCodes;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;


import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;

/**
 * Created by Abubakar Adamu on 05/04/2024
 **/

@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class ApiAdvice {

    @ExceptionHandler({RequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse handleRequestException(RequestException e){
        BaseResponse baseResponse = new BaseResponse();
        String message = StringUtils.hasLength(e.getMessage()) ? e.getMessage() : ResponseCodes.BAD_REQUEST.getMessage();
        baseResponse.setResponseCode(ResponseCodes.BAD_REQUEST.getCode());
        baseResponse.setResponseMessage(message);
        return baseResponse;
    }

    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse handleBindingValidationException(BindException e){
        BaseResponse response = new BaseResponse();
        response.setResponseCode(ResponseCodes.BAD_REQUEST.getCode());
        String message = ResponseCodes.BAD_REQUEST.getMessage();
        List<ObjectError> errorList = e.getAllErrors();
        if(errorList.isEmpty()){
            message = errorList.get(0).getDefaultMessage();
        }
        response.setResponseMessage(message);
        return response;
    }

    @ExceptionHandler({InvalidParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse handleInvalidParameter(InvalidParameterException e){
        BaseResponse response = new BaseResponse();
        response.setResponseCode(ResponseCodes.BAD_REQUEST.getCode());
        response.setResponseMessage(e.getMessage());
        return response;
    }

    @ExceptionHandler({RequestNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResponse handNotFoundException(RequestNotFoundException e){
        BaseResponse response = new BaseResponse();
        response.setResponseCode(ResponseCodes.NOT_FOUND.getCode());
        String message =StringUtils.hasLength(e.getMessage()) ? e.getMessage() : ResponseCodes.NOT_FOUND.getMessage();
        response.setResponseMessage(message);
        return response;

    }
}
