package org.study.hibernatetype.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.study.hibernatetype.domain.dto.FreeLocationRequest;
import org.study.hibernatetype.domain.dto.LocationRequest;
import org.study.hibernatetype.domain.entity.Location;
import org.study.hibernatetype.domain.repository.LocationRepository;
import org.study.hibernatetype.domain.vo.City;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    @Transactional
    public Location register(LocationRequest locationRequest) {
        List<City.Division> divisionList = locationRequest.divisionList().stream()
                .map(divisionRequest -> new City.Division(divisionRequest.district()))
                .toList();
        City city = new City(locationRequest.city(), divisionList);
        Location location = Location.builder()
                .city(city)
                .build();
        return locationRepository.save(location);
    }

    @Transactional
    public Location registerFree(FreeLocationRequest freeLocationRequest) {
        Location location = Location.builder()
                .freeCity(freeLocationRequest.city())
                .build();
        return locationRepository.save(location);
    }
}
