package com.EasyShiftScheduler.CalEnder.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

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
    @JoinColumn(name = "timecard_id", referencedColumnName = "id")
    private UserTimecard timecard;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "availability_schedule_id", referencedColumnName = "id")
    private UserAvailabilitySchedule availability_schedule;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "work_schedule_id", referencedColumnName = "id")
    private UserWorkSchedule work_schedule;

    @OneToMany(mappedBy = "user_that_requested", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DroppedShift> dropped_shifts = new ArrayList<>();

    @OneToMany(mappedBy = "user_that_requested", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TimeOffRequest> time_off_requests = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
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
