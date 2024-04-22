package hhplus.concert.domain.queue.model;

import hhplus.concert.domain.concert.models.SeatBookingStatus;
import hhplus.concert.domain.user.models.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "queue")
public class Queue {

    @Id
    @Column(name = "queue_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private long position;
    private LocalDateTime updateAt;

    @Enumerated(EnumType.STRING)
    private QueueStatus status;

    @Builder
    private Queue(String id, User user, long position, LocalDateTime updateAt, QueueStatus status) {
        this.id = id;
        this.user = user;
        this.position = position;
        this.updateAt = updateAt;
        this.status = status;
    }

    public static Queue createQueue(User user, long position, QueueStatus status) {
        return builder()
                .id(generateUUID(user.getId()))
                .user(user)
                .position(position)
                .updateAt(LocalDateTime.now())
                .status(status)
                .build();
    }

    private static String generateUUID(Long userId) {
        return UUID.randomUUID() + "_" + userId;
    }

    public void changeQueueStatus(QueueStatus status) {
        this.status = status;
    }
}
