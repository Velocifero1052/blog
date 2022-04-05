package ru.bagration.spring.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.bagration.spring.utils.LangContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Component
public class LangInterceptor implements HandlerInterceptor {

    private static final String langHeaderName = "lang";

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response, @NotNull Object handler){
        String lang = request.getHeader(langHeaderName);
        LangContext.setLang(Objects.requireNonNullElse(lang, "ru"));
        return true;
    }

}
