package com.EasyShiftScheduler.CalEnder.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TimeOffRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long time_off_request_id;

    // Private variables
    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Column
    private String reason;

    @Column
    private boolean approved;


    @ManyToOne
    @JoinColumn(name="user_id")
    private User user_that_requested;

    public TimeOffRequest() {
    }

    public TimeOffRequest(Long time_off_request_id, LocalDateTime startDate, LocalDateTime endDate, String reason, boolean approved, User user_that_requested) {
        this.time_off_request_id = time_off_request_id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.approved = approved;
        this.user_that_requested = user_that_requested;
    }

    public Long getTime_off_request_id() {
        return time_off_request_id;
    }

    public void setTime_off_request_id(Long time_off_request_id) {
        this.time_off_request_id = time_off_request_id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public User getUser_that_requested() {
        return user_that_requested;
    }

    public void setUser_that_requested(User user_that_requested) {
        this.user_that_requested = user_that_requested;
    }
}
