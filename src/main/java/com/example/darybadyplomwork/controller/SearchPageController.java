package com.example.darybadyplomwork.controller;

import com.example.darybadyplomwork.entity.Announcement;
import com.example.darybadyplomwork.entity.enums.AnnounceStatus;
import com.example.darybadyplomwork.entity.enums.AppType;
import com.example.darybadyplomwork.entity.enums.Status;
import com.example.darybadyplomwork.service.AnnounceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@Controller
public class SearchPageController {
    @Autowired
    private AnnounceService service;

    @GetMapping("/search")
    public String AnnounceList(Pageable pageable,
                               Model model, Optional<String> city,
                               Optional<Integer> mincost, Optional<Integer> maxcost,
                               Optional<Integer> minrooms, Optional<Integer> maxrooms,
                               Optional<Integer> minarea, Optional<Integer> maxarea,
                               Optional<AppType> type, Optional<Status> status
                               ) {
       Page<Announcement> page =  service.findPageOfAnnouncements(pageable,city,mincost,maxcost,
                minrooms,maxrooms,minarea,maxarea,type,status,Optional.of(AnnounceStatus.ACCEPTED));
       model.addAttribute("announces",page.getContent());
       model.addAttribute("status",status.orElse(Status.SELL));
       return "search_page.html";
    }
}
