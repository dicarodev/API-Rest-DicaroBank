package com.dicaro.dicarobank.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    /**
     * Method to handle exceptions in the controller layer and return an error response to the client with the status code.
     * @param ex the exception to handle
     * @param body the body to use for the response
     * @param headers the headers to use for the response
     * @param statusCode the status code to use for the response
     * @param request the current request
     * @return the error response
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatusCode statusCode, WebRequest request) {
        ApiError apiError = new ApiError(statusCode, ex.getMessage()); // Error model with status code and message
        return ResponseEntity.status(statusCode).headers(headers).body(apiError); // Return error response
    }

    /*private ResponseEntity<ApiError> buildErrorResponseEntity(HttpStatusCode statusCode, String messagge){
        return ResponseEntity.status(statusCode)
                .body(ApiError.builder()
                        .status(statusCode)
                        .message(messagge)
                        .build());
    }*/
}
