package com.example.projetfestival.entity;

import javax.persistence.*;

@Entity
@Table(name="artist")
public class Artist {
    public long getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="name")
    private String name;



    @Column(name="pass_time")
    private int pass_time;

    public int getPass_time() {
        return pass_time;
    }

    public void setPass_time(int pass_time) {
        this.pass_time = pass_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Artist(){}

    public Artist(String name, int pass_time) {
        this.name = name;
        this.pass_time=pass_time;
    }
}
