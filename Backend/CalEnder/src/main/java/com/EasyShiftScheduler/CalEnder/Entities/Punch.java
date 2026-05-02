package com.EasyShiftScheduler.CalEnder.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PUNCH")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="timecard_id")
    private UserTimecard user_timecard;

    @Override
    public String toString(){
        String s = "Id: " + id + " PunchTime: " + punch_time + " Reason: " + reason + " TimecardID: " + user_timecard.getId();
        return s;
    }

}


