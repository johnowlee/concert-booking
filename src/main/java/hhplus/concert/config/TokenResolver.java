package hhplus.concert.config;

import hhplus.concert.representer.api.queue.request.QueueTokenRequest;
import hhplus.concert.domain.queue.service.QueueService;
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

    private final QueueService queueService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(QueueTokenRequest.class);
    }

    @Override
    public QueueTokenRequest resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader("Queue-Token");
        queueService.validateToken(token);
        return new QueueTokenRequest(token);
    }
}
