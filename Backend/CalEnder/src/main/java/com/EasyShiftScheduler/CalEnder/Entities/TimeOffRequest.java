package com.EasyShiftScheduler.CalEnder.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "time_off_request")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeOffRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    // Private variables
    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Column
    private String reason;

    @Column
    private boolean approved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user_that_requested;

}
