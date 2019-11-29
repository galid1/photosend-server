package com.photosend.photosendserver01.domains.location.service;

import com.photosend.photosendserver01.domains.location.domain.location.LocationEntity;
import com.photosend.photosendserver01.domains.location.domain.location.LocationRepository;
import com.photosend.photosendserver01.domains.location.presentation.request_response.LocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public List<LocationResponse> getLocationList() {
        return locationRepository
                .findAll()
                .stream()
                .map(locationEntity -> {
                    return toLocationResponse(locationEntity);
                })
                .collect(Collectors.toList());
    }

    private LocationResponse toLocationResponse(LocationEntity locationEntity) {
        return LocationResponse
                .builder()
                .name(locationEntity.getName())
                .locationImagePath(locationEntity.getLocationImagePath())
                .locationList(locationEntity.getLocationList())
                .build();
    }
}
