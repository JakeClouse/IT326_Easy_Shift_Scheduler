package com.EasyShiftScheduler.CalEnder.Entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_timecard")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserTimecard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "user_timecard", fetch = FetchType.EAGER)
    private List<Punch> punch_times;

    @OneToOne(mappedBy = "user_timecard", fetch = FetchType.EAGER)
    private User user;

    @Column()
    private int worked_hours;

    @Override
    public String toString(){
        String s = "Id: " + this.id + "Worked_Hours: " + worked_hours + "Punches: ";
        for (int i = 0; i < punch_times.size(); i++){
            if (i == punch_times.size() - 1){
                s += punch_times.get(i).getId();
            }
            else{
                s += punch_times.get(i).getId() + ", "; 
            }
        }
        return (s);
    }

}