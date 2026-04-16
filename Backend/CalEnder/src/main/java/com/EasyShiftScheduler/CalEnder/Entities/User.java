package com.EasyShiftScheduler.CalEnder.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String password;

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

    public User() {
    }

    public User(Long user_id, String username, String email, String password, double compensation_rate, UserType user_type, UserTimecard timecard, UserAvailabilitySchedule availability_schedule, UserWorkSchedule work_schedule, List<TimeOffRequest> time_off_requests, List<Group> groups) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.compensation_rate = compensation_rate;
        this.user_type = user_type;
        this.timecard = timecard;
        this.availability_schedule = availability_schedule;
        this.work_schedule = work_schedule;
        this.time_off_requests = time_off_requests;
        this.groups = groups;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public double getCompensation_rate() {
        return compensation_rate;
    }

    public void setCompensation_rate(double compensation_rate) {
        this.compensation_rate = compensation_rate;
    }

    public UserType getUser_type() {
        return user_type;
    }

    public void setUser_type(UserType user_type) {
        this.user_type = user_type;
    }

    public UserTimecard getTimecard() {
        return timecard;
    }

    public void setTimecard(UserTimecard timecard) {
        this.timecard = timecard;
    }

    public UserAvailabilitySchedule getAvailability_schedule() {
        return availability_schedule;
    }

    public void setAvailability_schedule(UserAvailabilitySchedule availability_schedule) {
        this.availability_schedule = availability_schedule;
    }

    public UserWorkSchedule getWork_schedule() {
        return work_schedule;
    }

    public void setWork_schedule(UserWorkSchedule work_schedule) {
        this.work_schedule = work_schedule;
    }

    public List<TimeOffRequest> getTime_off_requests() {
        return time_off_requests;
    }

    public void setTime_off_requests(List<TimeOffRequest> time_off_requests) {
        this.time_off_requests = time_off_requests;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
