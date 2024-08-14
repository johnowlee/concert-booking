package hhplus.concert.config;

import hhplus.concert.api.queue.controller.request.QueueTokenRequest;
import hhplus.concert.domain.queue.support.TokenValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
public class TokenResolver implements HandlerMethodArgumentResolver {

    private final TokenValidator tokenValidator;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(QueueTokenRequest.class);
    }

    @Override
    public QueueTokenRequest resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader("Queue-Token");
        tokenValidator.validateToken(token);
        return QueueTokenRequest.from(token);
    }
}
