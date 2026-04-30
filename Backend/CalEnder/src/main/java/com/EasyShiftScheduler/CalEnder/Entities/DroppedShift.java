package com.EasyShiftScheduler.CalEnder.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "dropped_shift")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DroppedShift {
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
    private boolean pickedUp;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user_that_requested;

}
