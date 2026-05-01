package com.EasyShiftScheduler.CalEnder.Entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "timecard_id", referencedColumnName = "id")
    private UserTimecard user_timecard;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "availability_schedule_id", referencedColumnName = "id")
    private UserAvailabilitySchedule availability_schedule;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "work_schedule_id", referencedColumnName = "id")
    private UserWorkSchedule work_schedule;

    @OneToMany(mappedBy = "user_that_requested", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DroppedShift> dropped_shifts = new ArrayList<>();

    @OneToMany(mappedBy = "user_that_requested", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeOffRequest> time_off_requests = new ArrayList<>();

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

    @Override
    public String toString(){
        String s = "Id: " + id + " Username: " + username + " roles: " + roles + " compensation rate: " + compensation_rate + " timecard: " + timecard.getId() + " availability_schedule: " + availability_schedule.getId() + " work_schedule: " + work_schedule.getId();
        s += "Dropped Shifts: ";
        for(DroppedShift shift : dropped_shifts){
            if (dropped_shifts.indexOf(shift) == dropped_shifts.size() - 1){
                s += shift + "\n";
            }
            else{
                s += shift + ", ";
            }
            s += shift.getId() + ", ";
        }
        s += "Time Off Requests: ";
        for(TimeOffRequest time : time_off_requests){
            if (time_off_requests.indexOf(time) == time_off_requests.size() - 1){
                s += time + "\n";
            }
            else{
                s += time + ", ";
            }
        }
        return s;
    }
}
