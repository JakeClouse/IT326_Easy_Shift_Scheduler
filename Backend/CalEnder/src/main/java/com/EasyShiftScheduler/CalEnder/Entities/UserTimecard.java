package com.EasyShiftScheduler.CalEnder.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Table(name = "user_timecard")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserTimecard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "user_timecard")
    private List<Punch> punch_times;

    @OneToOne(mappedBy = "user_timecard")
    private User user;

    @Column()
    private int worked_hours;

}