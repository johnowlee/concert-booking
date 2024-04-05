package hhplus.concert.domain.queue.model;

import hhplus.concert.domain.user.model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
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


}
