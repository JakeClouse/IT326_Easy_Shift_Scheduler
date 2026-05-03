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
@Table(name = "dropped_shift")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DroppedShift {
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
    private boolean pickedUp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user_that_requested;

    @Override
    public String toString(){
        String s = "Id: " + id + "\nstartDate: " + startDate + "\nendDate: " + endDate + "\nreason: " + reason +  "\nuserId: " + user_that_requested.getId();
        return s;
    }

    @Override
    public boolean equals(Object o){
        if (o == this){
            return false;
        }

        if (!(o instanceof DroppedShift)){
            return false;
        }

        DroppedShift u = (DroppedShift)o;

        return Long.compare(id, u.getId()) == 0;
    }



}
