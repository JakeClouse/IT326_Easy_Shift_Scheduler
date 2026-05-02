package com.EasyShiftScheduler.CalEnder.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dropped_shift")
@Data
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
        String s = "Id: " + id + "startDate: " + startDate + " endDate: " + endDate + " reason: " + reason +  " userId: " + user_that_requested.getId();
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
