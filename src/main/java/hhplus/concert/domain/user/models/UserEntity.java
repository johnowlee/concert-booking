package hhplus.concert.domain.user.models;

import hhplus.concert.domain.balance.models.BalanceHistoryEntity;
import hhplus.concert.domain.booking.model.BookingEntity;
import hhplus.concert.domain.payment.model.PaymentEntity;
import hhplus.concert.domain.queue.model.QueueEntity;
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
