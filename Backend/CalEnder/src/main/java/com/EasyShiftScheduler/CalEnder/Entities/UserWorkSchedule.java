package com.EasyShiftScheduler.CalEnder.Entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "work_schedule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWorkSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ElementCollection
    public List<LocalDateTime> work_schedule;

    @OneToOne(mappedBy = "work_schedule", fetch = FetchType.EAGER)
    private User user;

    @Override
    public String toString(){
        String s = "Id: " + id;
        for (LocalDateTime time : work_schedule){
            if (work_schedule.indexOf(time) == work_schedule.size() - 1){
                s += time + "\n";
            }
            else{
                s += time + ", ";
            }
        }
        return s;
    }

    @Override
    public boolean equals(Object o){
        if (o == this){
            return false;
        }

        if (!(o instanceof UserWorkSchedule)){
            return false;
        }

        UserWorkSchedule u = (UserWorkSchedule)o;

        return Long.compare(id, u.getId()) == 0;
    }


}
