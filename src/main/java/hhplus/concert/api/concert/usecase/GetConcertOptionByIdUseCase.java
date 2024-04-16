package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.concert.dto.response.ConcertOptionResponse;
import hhplus.concert.domain.concert.components.ConcertReader;
import hhplus.concert.domain.concert.models.ConcertOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hhplus.concert.api.concert.dto.ConcertDtoMapper.createSeatDtos;

@Service
@RequiredArgsConstructor
public class GetConcertOptionByIdUseCase {

    private final ConcertReader concertReader;

    public ConcertOptionResponse excute(Long id) {
        ConcertOption concertOption = concertReader.getConcertOptionById(id);
        return ConcertOptionResponse.from(
                    concertOption.getConcert().getTitle(),
                    concertOption.getConcertDateTime(),
                    concertOption.getConcert().getOrganizer(),
                    createSeatDtos(concertOption.getSeats())
                );
    }
}
