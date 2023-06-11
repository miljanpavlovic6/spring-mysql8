package com.app.exception;
import com.app.exception.custom.AchievementNotFoundForGameException;
import com.app.support.HttpResponse;
import com.app.exception.custom.AchievementNotFoundException;
import com.app.exception.custom.AchievementsExistException;
import com.app.exception.custom.GameNotFoundException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.util.Objects;
import static org.springframework.http.HttpStatus.*;
import static com.app.constant.Constant.*;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {

    public static final String ERROR_PATH = "/error";

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(AchievementsExistException.class)
    public ResponseEntity<HttpResponse> achievementsExistException(AchievementsExistException e){
        LOGGER.error(e.getMessage());
        return createHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<HttpResponse> gameNotFoundException(GameNotFoundException e){
        LOGGER.error(e.getMessage());
        return createHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(AchievementNotFoundException.class)
    public ResponseEntity<HttpResponse> achievementNotFoundException(AchievementNotFoundException e){
        LOGGER.error(e.getMessage());
        return createHttpResponse(BAD_REQUEST, e.getMessage());
    }

        @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<HttpResponse> noHandlerFoundException(NoHandlerFoundException e) {
        return createHttpResponse(BAD_REQUEST, NO_MAPPING_FOR_URL);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }


    @ExceptionHandler(AchievementNotFoundForGameException.class)
    public ResponseEntity<HttpResponse> achievementNotFoundForGameException(AchievementNotFoundForGameException e){
        LOGGER.error(e.getMessage());
        return createHttpResponse(NO_CONTENT, e.getMessage());
    }


    @RequestMapping(ERROR_PATH)
    public ResponseEntity<HttpResponse> notFound404() {
        LOGGER.info("There is no maping for this address - GameApp");
        return createHttpResponse(NOT_FOUND, NO_MAPPING_FOR_URL);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
    }

}
