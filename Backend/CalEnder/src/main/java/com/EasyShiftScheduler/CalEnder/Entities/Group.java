package com.EasyShiftScheduler.CalEnder.Entities;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="USER_GROUPS")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToMany(mappedBy = "groups")
    private List<User> users = new ArrayList<>();

    @Override
    public String toString(){
        String s = "Id: " + id + "User Ids: ";
        for (User u : users){
            if (users.indexOf(u) == users.size() - 1){
                s += u.getId() + "\n";
            }
            else{
                s += u.getId() + ", ";
            }
        }
        return s;
    }

}
