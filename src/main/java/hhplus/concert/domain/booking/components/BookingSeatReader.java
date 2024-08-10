package hhplus.concert.domain.booking.components;

import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.booking.repositories.BookingSeatReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingSeatReader {

    private final BookingSeatReaderRepository bookingSeatReaderRepository;

    public List<BookingSeat> getBookingSeatsBySeatIds (List<Long> seatIds) {
        return bookingSeatReaderRepository.getBookingSeatsBySeatIds(seatIds);
    }
}
