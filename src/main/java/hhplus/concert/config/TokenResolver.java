package hhplus.concert.config;

import hhplus.concert.core.queue.domain.service.QueueQueryService;
import hhplus.concert.representer.api.queue.request.QueueTokenRequest;
import hhplus.concert.representer.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static hhplus.concert.representer.exception.code.TokenErrorCode.NOT_FOUND_TOKEN;
import static hhplus.concert.representer.exception.code.TokenErrorCode.WAITING_TOKEN;

@Slf4j
@RequiredArgsConstructor
public class TokenResolver implements HandlerMethodArgumentResolver {

    private final QueueQueryService queueQueryService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(QueueTokenRequest.class);
    }

    @Override
    public QueueTokenRequest resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader("Queue-Token");

        if (queueQueryService.isWaitingToken(token)) {
            throw new RestApiException(WAITING_TOKEN);
        }
        if (!queueQueryService.isActiveToken(token)) {
            throw new RestApiException(NOT_FOUND_TOKEN);
        }
        return new QueueTokenRequest(token);
    }
}
