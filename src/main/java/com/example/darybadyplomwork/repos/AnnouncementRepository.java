package com.example.darybadyplomwork.repos;

import com.example.darybadyplomwork.entity.Announcement;
import com.example.darybadyplomwork.entity.enums.AnnounceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {



    @Query("select a from Announcement a where a.residence.city = ?1 and a.aStatus = ?2")
    Page<Announcement> findByResidenceCityAndAStatus(String city, AnnounceStatus aStatus, Pageable pageable);
    @Query("select a from Announcement a where a.aStatus = ?1")
    Page<Announcement> findByAStatus(AnnounceStatus aStatus, Pageable pageable);
}