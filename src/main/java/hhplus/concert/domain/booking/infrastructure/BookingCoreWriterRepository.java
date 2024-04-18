package hhplus.concert.domain.booking.infrastructure;

import hhplus.concert.domain.booking.repositories.BookingWriterRepository;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BookingCoreWriterRepository implements BookingWriterRepository {

    private final BookingJpaRepository bookingJpaRepository;
    private final BookingSeatJpaRepository bookingSeatJpaRepository;

    @Override
    public Booking bookConcert(Booking booking) {
        Booking bookingEntity = Booking.toBookingEntity(booking);
        return bookingJpaRepository.save(bookingEntity).toBooking();
    }

    @Override
    public List<BookingSeat> saveAllBookingSeat(List<BookingSeat> bookingSeats) {
        log.info("bookingseats ={}" , bookingSeats);
        //BookingSeatEntity 리스트 생성
        List<BookingSeat> bookingSeatEntities = bookingSeats.stream()
                .map(bs -> BookingSeat.toBookingSeatEntity(bs))
                .collect(Collectors.toList());

        return bookingSeatJpaRepository.saveAll(bookingSeatEntities).stream()
                .map(bse -> bse.toBookingSeat())
                .collect(Collectors.toList());
    }

    @Override
    public BookingSeat saveBookingSeat(BookingSeat bookingSeat) {
        return bookingSeatJpaRepository.save(BookingSeat.toBookingSeatEntity(bookingSeat)).toBookingSeat();
    }
}
