package com.EasyShiftScheduler.CalEnder.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "availability_schedule")
public class UserAvailabilitySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ElementCollection
    public List<LocalDateTime> availability_schedule;

    @OneToOne(mappedBy = "availability_schedule")
    private User user;

    public UserAvailabilitySchedule() {

    }


    public List<LocalDateTime> getAvailability_schedule() {
        return availability_schedule;
    }

    public void setAvailability_schedule(List<LocalDateTime> availability_schedule) {
        this.availability_schedule = availability_schedule;
    }
}
