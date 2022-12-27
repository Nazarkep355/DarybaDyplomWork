package com.example.darybadyplomwork.dto;

import com.example.darybadyplomwork.entity.enums.AppType;
import com.example.darybadyplomwork.entity.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnnounceDTO {
    private Status status;
    private String city;
    private String address;
    private String house;
    private int rooms;
    private AppType type;
    private int area;
    private int floor;
    private int total;
}
