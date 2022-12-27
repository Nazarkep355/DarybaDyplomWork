package com.example.darybadyplomwork.service;

import com.example.darybadyplomwork.dto.AnnounceDTO;
import com.example.darybadyplomwork.entity.Announcement;
import com.example.darybadyplomwork.entity.City;
import com.example.darybadyplomwork.entity.Residence;
import com.example.darybadyplomwork.entity.User;
import com.example.darybadyplomwork.entity.enums.AnnounceStatus;
import com.example.darybadyplomwork.entity.enums.AppType;
import com.example.darybadyplomwork.entity.enums.Status;
import com.example.darybadyplomwork.repos.AnnouncementRepository;
import com.example.darybadyplomwork.repos.ResidenceRepository;
import com.example.darybadyplomwork.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class AnnounceService {
    @Autowired
    private AnnouncementRepository aRepository;
    @Autowired
    private UserRepository uRepository;
    @Autowired
    private ResidenceRepository rRepository;
    @Autowired
    private CityService cityService;
    public Page<Announcement> findByPageable(Pageable pageable){
        if(pageable.getPageSize()>20){
            pageable = PageRequest.of(pageable.getPageNumber(),20,pageable.getSort());
        }
        if(!pageable.isPaged()){
            pageable = PageRequest.of(pageable.getPageNumber(),20,pageable.getSort());
        }
        return aRepository.findByAStatus(AnnounceStatus.ACCEPTED,pageable);
    }
    public Page<Announcement> findByCity(String city, Pageable pageable){
        if(pageable.getPageSize()>20){
            pageable = PageRequest.of(pageable.getPageNumber(),20,pageable.getSort());
        }
        if(!pageable.isPaged()){
            pageable = PageRequest.of(pageable.getPageNumber(),20,pageable.getSort());
        }
        return aRepository.findByResidenceCityAndAStatus(city,AnnounceStatus.ACCEPTED,pageable);
    }
    public void createAnnounce(Status status, String cityName, AppType type,
                             String address, String house, int rooms, int area, int floor,
                             int total, List<String> photos, User user){
        City city = cityService.findCityByName(cityName);
        Date now =Date.from(Instant.now());
        Residence residence = new Residence();
        residence.setCity(city);
        residence.setFullArea(area);
        residence.setStreet(address);
        residence.setHouseNumber(house);
        residence.setFloor(floor);
        residence.setTotalFloors(total);
        residence.setRoomNumber(rooms);
        residence = rRepository.save(residence);
        Announcement announcement = new Announcement();
        announcement.setResidence(residence);
        announcement.setAppType(type);
        announcement.setStatus(status);
        announcement.setCreateDate(now);
        announcement.setOwner(user);
        announcement.setRefreshDate(now);
        announcement.setPhoto(photos);
        announcement.setAStatus(AnnounceStatus.CREATED);
        aRepository.save(announcement);

    }
    public void createAnnounceWithDTO(AnnounceDTO dto,List<String> photos,User user){
        createAnnounce(dto.getStatus(),dto.getCity(),dto.getType(),
                dto.getAddress(),dto.getHouse(),dto.getRooms(),
                dto.getArea(),dto.getFloor(),dto.getTotal(),photos,user);
    }
}