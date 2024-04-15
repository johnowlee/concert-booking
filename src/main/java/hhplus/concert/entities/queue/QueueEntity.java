package hhplus.concert.entities.queue;

import hhplus.concert.entities.user.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "queue")
public class QueueEntity {

    @Id
    @Column(name = "queue_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    private long position;
    private LocalDateTime updateAt;

    @Enumerated(EnumType.STRING)
    private QueueStatus status;


}
