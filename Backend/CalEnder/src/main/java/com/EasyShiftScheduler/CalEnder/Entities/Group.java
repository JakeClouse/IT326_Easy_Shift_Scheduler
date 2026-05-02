package com.EasyShiftScheduler.CalEnder.Entities;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
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

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
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
