package hhplus.concert.entities.user;

import hhplus.concert.domain.user.models.User;
import hhplus.concert.entities.balance.BalanceHistoryEntity;
import hhplus.concert.entities.booking.BookingEntity;
import hhplus.concert.entities.payment.PaymentEntity;
import hhplus.concert.entities.queue.QueueEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
//@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue
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

    @Builder
    private UserEntity(Long id, String name, long balance,
                      List<QueueEntity> queueEntity,
                      List<BalanceHistoryEntity> balanceHistories,
                      List<BookingEntity> bookingEntities,
                      List<PaymentEntity> paymentEntities) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.queueEntity = queueEntity;
        this.balanceHistories = balanceHistories;
        this.bookingEntities = bookingEntities;
        this.paymentEntities = paymentEntities;
    }


    public void updateUserEntity(User user) {
        this.name = user.getName();
        this.balance = user.getBalance();
    }

    public User toUser() {
        return User.builder()
                .id(id)
                .name(name)
                .balance(balance)
                .build();
    }
}