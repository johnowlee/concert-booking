package hhplus.concert.representer.api.concert;

import hhplus.concert.representer.api.concert.response.ConcertOptionResponse;
import hhplus.concert.representer.api.concert.response.ConcertResponse;
import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.domain.concert.models.ConcertOption;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConcertControllerMapper {

    public ConcertResponse toConcertResponse(Concert concert) {
        return new ConcertResponse(concert.getId(), concert.getTitle(), concert.getOrganizer());
    }
    public List<ConcertResponse> toConcertResponseList(List<Concert> concerts) {
        return concerts.stream()
                .map(this::toConcertResponse)
                .toList();
    }

    public ConcertOptionResponse toConcertOptionResponse(ConcertOption concertOption) {
        return new ConcertOptionResponse(concertOption.getId(), concertOption.getConcertDateTime(), concertOption.getPlace());
    }
    public List<ConcertOptionResponse> toConcertOptionResponseList(List<ConcertOption> concertOptions) {
        return concertOptions.stream()
                .map(this::toConcertOptionResponse)
                .toList();
    }
}
