package com.example.darybadyplomwork.repos;

import com.example.darybadyplomwork.entity.Announcement;
import com.example.darybadyplomwork.entity.enums.AnnounceStatus;
import com.example.darybadyplomwork.entity.enums.AppType;
import com.example.darybadyplomwork.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {



    @Query("select a from Announcement a where a.residence.city = ?1 and a.aStatus = ?2")
    Page<Announcement> findByResidenceCityAndAStatus(String city, AnnounceStatus aStatus, Pageable pageable);
    @Query("select a from Announcement a where a.aStatus = ?1")
    Page<Announcement> findByAStatus(AnnounceStatus aStatus, Pageable pageable);
    @Query("select a from Announcement a where a.status = ?1 and a.appType = ?2")
    Page<Announcement> findByStatus(Status status,AppType appType, Pageable pageable);

    @Query(value = "select a from Announcement a where a.aStatus = ?1" +
            " and a.cost >= ?2 and a.cost <= ?3 and a.residence.roomNumber >= ?5" +
            " and a.residence.roomNumber <= ?6  and a.residence.fullArea >= ?7 and " +
            " a.residence.fullArea <= ?8 and a.appType = ?9 and a.status = ?10  "
    +        " and (a.residence.city.enName like upper(concat('%', ?4, '%')) "
            +"or a.residence.city.ukName like upper(concat('%', ?4, '%')) or a.residence.city.enName=?4 or a.residence.city.ukName=?4) "

    )
    Page<Announcement> findAnnouncementByAStatusAndCost(AnnounceStatus announceStatus,
                                                        Integer mincost,
                                                        Integer maxcost,
                                                        String city,
                                                        Integer minrooms, Integer maxrooms,
                                                        Integer minarea, Integer maxarea,
                                                        AppType type, Status status, Pageable pageable);
}