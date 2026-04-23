package com.EasyShiftScheduler.CalEnder.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PUNCH")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Punch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column
    public LocalDateTime punch_time;

    @Column
    public String reason;

    @ManyToOne
    @JoinColumn(name="timecard_id")
    private UserTimecard user_timecard;

}


