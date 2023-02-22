package com.example.darybadyplomwork.service;

import com.example.darybadyplomwork.dto.AnnounceDTO;
import com.example.darybadyplomwork.entity.Announcement;
import com.example.darybadyplomwork.entity.City;
import com.example.darybadyplomwork.entity.Residence;
import com.example.darybadyplomwork.entity.User;
import com.example.darybadyplomwork.entity.enums.*;
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
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Announcement findById(long id) {
        return aRepository.findById(id).orElseThrow();
    }

    public Page<Announcement> findByPageable(Pageable pageable) {
        if (pageable.getPageSize() > 20) {
            pageable = PageRequest.of(pageable.getPageNumber(), 20, pageable.getSort());
        }
        if (!pageable.isPaged()) {
            pageable = PageRequest.of(pageable.getPageNumber(), 20, pageable.getSort());
        }
        return aRepository.findByAStatus(AnnounceStatus.ACCEPTED, pageable);
    }

    public Page<Announcement> findByCity(String city, Pageable pageable) {
        if (pageable.getPageSize() > 20) {
            pageable = PageRequest.of(pageable.getPageNumber(), 20, pageable.getSort());
        }
        if (!pageable.isPaged()) {
            pageable = PageRequest.of(pageable.getPageNumber(), 20, pageable.getSort());
        }
        return aRepository.findByResidenceCityAndAStatus(city, AnnounceStatus.ACCEPTED, pageable);
    }

    public Page<Announcement> findPageOfAnnouncements(Pageable pageable, Optional<String> city,
                                               Optional<Integer> mincost, Optional<Integer> maxcost,
                                               Optional<Integer> minrooms, Optional<Integer> maxrooms,
                                               Optional<Integer> minarea, Optional<Integer> maxarea,
                                               Optional<AppType> type, Optional<Status> status
            ,Optional<AnnounceStatus> aStatus){
        String cityName = city.orElse("");
        int minCost = mincost.orElse(0);
        int maxCost = maxcost.orElse(Integer.MAX_VALUE);
        int minRooms = minrooms.orElse(0);
        int maxRooms = maxrooms.orElse(Integer.MAX_VALUE);
        int minArea = minarea.orElse(0);
        int maxArea = maxarea.orElse(Integer.MAX_VALUE);
        AppType appType = type.orElse(AppType.STUDIO);
        Status stat = status.orElse(Status.SELL);

        Page<Announcement> res = aRepository.findAnnouncementByAStatusAndCost(aStatus.orElse(AnnounceStatus.ACCEPTED),
                minCost,maxCost,cityName,minRooms,maxRooms,minArea,maxArea,appType,stat,pageable);

        return res;
    }
    public Page<Announcement> findCreatedAnnounces(Pageable pageable){
        if (pageable.getPageSize() > 20) {
            pageable = PageRequest.of(pageable.getPageNumber(), 20, pageable.getSort());
        }
        if (!pageable.isPaged()) {
            pageable = PageRequest.of(pageable.getPageNumber(), 20, pageable.getSort());
        }
        return aRepository.findByAStatus(AnnounceStatus.CREATED,pageable);
    }

    public void createAnnounce(Status status, String cityName, AppType type,
                               String address, String house, int rooms, int area, int floor,
                               int total, List<String> photos, State state, User user) {
        City city = cityService.findCityByName(cityName);
        Date now = Date.from(Instant.now());
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
        announcement.setState(state);
        announcement.setCreateDate(now);
        announcement.setOwner(user);
        announcement.setRefreshDate(now);
        announcement.setPhoto(photos);
        if (user.getRole() != UserRole.REALTOR) {
            announcement.setAStatus(AnnounceStatus.CREATED);
        } else {
            announcement.setAStatus(AnnounceStatus.ACCEPTED);
        }
        aRepository.save(announcement);

    }

    public void createAnnounceWithDTO(AnnounceDTO dto, List<String> photos, User user) {
        createAnnounce(dto.getStatus(), dto.getCity(), dto.getType(),
                dto.getAddress(), dto.getHouse(), dto.getRooms(),
                dto.getArea(), dto.getFloor(), dto.getTotal(), photos, dto.getState(), user);
    }
//    res.forEach(a->{
//        System.out.println("a.aStatus = ACCEPTED: "+(a.getAStatus()==AnnounceStatus.ACCEPTED)+"" +
//                " a.cost >= 0: " +(a.getCost()>=0 )+ " a.cost <= 7000: " +(a.getCost()<=7000) +
//                " a.residence.roomNumber <= 1000: " +(a.getResidence().getRoomNumber()<=1000) +
//                " a.residence.roomNumber >= 0: " + (a.getResidence().getRoomNumber()>=0) +
//                " a.residence.fullArea >= 0: "+(a.getResidence().getFullArea()>=0)+
//                " a.residence.fullArea <= 5000: "+ (a.getResidence().getFullArea()<= 5000)+
//                " a.appType = STUDIO: "+(a.getAppType()==AppType.STUDIO) +
//                " a.status = SELL: "+(a.getStatus()==Status.SELL));
//    });
}