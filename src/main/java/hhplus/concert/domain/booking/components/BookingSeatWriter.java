package hhplus.concert.domain.booking.components;

import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.booking.repositories.BookingSeatWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingSeatWriter {

    private final BookingSeatWriterRepository bookingSeatWriterRepository;

    public List<BookingSeat> saveBookingSeats(List<BookingSeat> bookingSeats) {
        return bookingSeatWriterRepository.saveAll(bookingSeats);
    }
}
