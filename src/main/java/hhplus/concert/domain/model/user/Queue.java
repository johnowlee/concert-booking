package hhplus.concert.domain.model.user;

import hhplus.concert.domain.model.enums.QueueStatus;
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
