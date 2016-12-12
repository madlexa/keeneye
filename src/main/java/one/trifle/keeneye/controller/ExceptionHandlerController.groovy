package one.trifle.keeneye.controller

import one.trifle.keeneye.exception.RestException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class ExceptionHandlerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController);

    @ExceptionHandler(RestException)
    @ResponseBody
    String handleException(RestException e) {
        LOGGER.error("Ошибка: {}", e.getMessage(), e);
        return "Ошибка: " + e.getMessage();
    }
}
