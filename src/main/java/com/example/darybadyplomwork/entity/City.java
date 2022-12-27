package com.example.darybadyplomwork.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cities")
@Setter
@Getter
public class City {
    @Id
    private Long id;
    @Column
    private String ukName;
    @Column
    private String enName;

}