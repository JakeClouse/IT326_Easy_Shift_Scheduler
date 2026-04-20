package com.EasyShiftScheduler.CalEnder.Entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="USER_GROUPS")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long group_id;

    @ManyToMany
    @JoinTable(
            name="group_user",
            joinColumns = @JoinColumn(name="group_id"),
            inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private List<User> group_users;

    public Group() {
    }

    public Group(Long group_id, List<User> group_users) {
        this.group_id = group_id;
        this.group_users = group_users;
    }

    public Long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

    public List<User> getGroup_users() {
        return group_users;
    }

    public void setGroup_users(List<User> group_users) {
        this.group_users = group_users;
    }
}
