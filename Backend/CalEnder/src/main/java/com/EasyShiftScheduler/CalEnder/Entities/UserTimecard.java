package com.EasyShiftScheduler.CalEnder.Entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user_timecard", fetch = FetchType.EAGER)
    private List<Punch> punch_times = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user_timecard", fetch = FetchType.EAGER)
    private User user;

    @Column()
    private double worked_hours;

    @Override
    public String toString(){
        String s = "Id: " + this.id + "\nWorked_Hours: " + worked_hours + "\nPunches: ";
        if (punch_times == null){
            return s;
        }
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

    @Override
    public boolean equals(Object o){
        if (o == this){
            return false;
        }

        if (!(o instanceof UserTimecard)){
            return false;
        }

        UserTimecard u = (UserTimecard)o;

        return Long.compare(id, u.getId()) == 0;
    }

}