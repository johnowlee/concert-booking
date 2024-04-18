package hhplus.concert.domain.booking.components;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.booking.repositories.BookingWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingWriter {

    private final BookingWriterRepository bookingWriterRepository;

    public Booking bookConcert(Booking booking) {
        return bookingWriterRepository.bookConcert(booking);
    }

    public List<BookingSeat> saveAllBookingSeat(List<BookingSeat> bookingSeats) {
        return bookingWriterRepository.saveAllBookingSeat(bookingSeats);
    }

    public BookingSeat saveBookingSeat(BookingSeat bookingSeat) {
        return bookingWriterRepository.saveBookingSeat(bookingSeat);
    }
}
