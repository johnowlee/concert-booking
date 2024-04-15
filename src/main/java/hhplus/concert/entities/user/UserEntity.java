package hhplus.concert.entities.user;

import hhplus.concert.entities.balance.BalanceHistoryEntity;
import hhplus.concert.entities.booking.BookingEntity;
import hhplus.concert.entities.payment.PaymentEntity;
import hhplus.concert.entities.queue.QueueEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;
    private long balance;

    @OneToMany(mappedBy = "userEntity")
    private List<QueueEntity> queueEntity = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity")
    private List<BalanceHistoryEntity> balanceHistories = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity")
    private List<BookingEntity> bookingEntities = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity")
    private List<PaymentEntity> paymentEntities = new ArrayList<>();

    public static UserEntity createUser(String name, long balance) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(name);
        userEntity.setBalance(balance);
        return userEntity;
    }
}
