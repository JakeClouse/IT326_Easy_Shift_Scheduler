package com.EasyShiftScheduler.CalEnder.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="USER")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String roles;

    @Column
    private double compensation_rate;

    @Enumerated(EnumType.ORDINAL)
    private UserType user_type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "timecard_id")
    private UserTimecard timecard;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "availability_schedule_id")
    private UserAvailabilitySchedule availability_schedule;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "work_schedule_id")
    private UserWorkSchedule work_schedule;

    @OneToMany(mappedBy = "user_that_requested")
    private List<TimeOffRequest> time_off_requests;

    @ManyToMany
    @JoinTable(
            name="user_group",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="group_id")
    )
    private List<Group> groups = new ArrayList<>();


    public User(String username, String password) {
        this.username = username;
        this.password = password;

    }
}
