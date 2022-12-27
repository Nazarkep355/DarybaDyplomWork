package com.example.darybadyplomwork.entity;

import com.example.darybadyplomwork.entity.enums.AnnounceStatus;
import com.example.darybadyplomwork.entity.enums.AppType;
import com.example.darybadyplomwork.entity.enums.State;
import com.example.darybadyplomwork.entity.enums.Status;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;


import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "announces")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    private Date createDate;
    @Column
    private Date refreshDate;
    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;
    @ManyToOne
    @JoinColumn(name = "residence")
    private Residence residence;
    @ElementCollection
    private List<String> photo;
    @Column
    private String mainPhoto;
    @Column
    private State state;
    @Column
    private Status status;
    @Column
    private AppType appType;
    @Column
    private String additionalInfo;
    @Column
    private AnnounceStatus aStatus;

}
