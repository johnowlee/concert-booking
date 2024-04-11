package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.balance.dto.response.BalanceResponse;
import hhplus.concert.domain.concert.components.ConcertReader;
import hhplus.concert.domain.user.components.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetConcertsUseCase {

    private final ConcertReader concertReader;

    public BalanceResponse excute(Long userId) {
//        return concertReader.getConcerts();
        return null;
    }
}
