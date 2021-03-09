package ru.edjll.cinema.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @GetMapping(ERROR_PATH)
    public String getErrorPage(HttpServletRequest request, Map<String, Object> model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            model.put("errorCode", statusCode);
            model.put("message", HttpStatus.valueOf(statusCode).getReasonPhrase());
        }

        return "message";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
