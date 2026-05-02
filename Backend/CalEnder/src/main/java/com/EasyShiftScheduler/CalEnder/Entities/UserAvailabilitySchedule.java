package com.EasyShiftScheduler.CalEnder.Entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Override
    public String toString(){
        String s = "Id: " + id;
        for (LocalDateTime time : availability_schedule){
            if (availability_schedule.indexOf(time) == availability_schedule.size() - 1){
                s += time + "\n";
            }
            else{
                s += time + ", ";
            }
        }
        return s;
    }
}
