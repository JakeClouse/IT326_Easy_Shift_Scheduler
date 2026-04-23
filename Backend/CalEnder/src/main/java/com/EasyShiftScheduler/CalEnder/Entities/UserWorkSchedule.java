package com.EasyShiftScheduler.CalEnder.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@Entity
@Data
@Table(name = "work_schedule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWorkSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ElementCollection
    public List<LocalDateTime> work_schedule;

    @OneToOne(mappedBy = "work_schedule")
    private User user;
}
