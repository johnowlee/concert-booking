package hhplus.concert.domain.queue.model;

import hhplus.concert.domain.user.models.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    public static Queue createQueue(String id, User user, long position, QueueStatus status) {
        return builder()
                .id(id)
                .user(user)
                .position(position)
                .updateAt(LocalDateTime.now())
                .status(status)
                .build();
    }
}
