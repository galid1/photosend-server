package com.photosend.photosendserver01.domains.location.presentation;

import com.photosend.photosendserver01.domains.location.presentation.request_response.LocationResponse;
import com.photosend.photosendserver01.domains.location.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<LocationResponse> getLocationList() {
        return locationService.getLocationList();
    }
}
