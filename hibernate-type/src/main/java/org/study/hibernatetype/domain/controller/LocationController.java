package org.study.hibernatetype.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.study.hibernatetype.domain.dto.FreeLocationRequest;
import org.study.hibernatetype.domain.dto.LocationRequest;
import org.study.hibernatetype.domain.entity.Location;
import org.study.hibernatetype.domain.service.LocationService;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/register")
    public Location register(@RequestBody LocationRequest locationRequest) {
        return locationService.register(locationRequest);
    }

    @PostMapping("/register/free")
    public Location registerFree(@RequestBody FreeLocationRequest freeLocationRequest) {
        return locationService.registerFree(freeLocationRequest);
    }
}
