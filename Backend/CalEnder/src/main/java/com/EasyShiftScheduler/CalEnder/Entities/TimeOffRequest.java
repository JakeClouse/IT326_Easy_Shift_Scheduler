package com.EasyShiftScheduler.CalEnder.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "time_off_request")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeOffRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    // Private variables
    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Column
    private String reason;

    @Column
    private boolean approved;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user_that_requested;

    @Override
    public String toString(){
        String s = "Id: " + id + "\nstartDate: " + startDate + "\nendDate: " + endDate + "\nreason: " + reason + "\napproved: " + approved + "\nuserId: " + ((user_that_requested != null) ? user_that_requested.getId() : "null");
        return s;
    }

    @Override
    public boolean equals(Object o){
        if (o == this){
            return false;
        }

        if (!(o instanceof TimeOffRequest)){
            return false;
        }

        TimeOffRequest u = (TimeOffRequest)o;

        return Long.compare(id, u.getId()) == 0;
    }

}
