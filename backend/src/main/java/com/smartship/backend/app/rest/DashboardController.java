package com.smartship.backend.app.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.smartship.backend.app.exceptions.NotAcceptableException;
import com.smartship.backend.app.exceptions.NotFoundException;
import com.smartship.backend.app.models.*;
import com.smartship.backend.app.repositories.*;
import com.smartship.backend.app.utility.JWTokenInfo;
import com.smartship.backend.app.views.CustomJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(path = "/api/v1/dashboards")
public class DashboardController {


    private final DashboardRepository dashboardRepository;
    private final UserRepository userRepository;
    private final DashboardItemRepository dashboardItemRepository;
    private final SensorDataRepository sensorDataRepository;

    @Autowired
    public DashboardController(DashboardRepository dashboardRepository, UserRepository userRepository, DashboardItemRepository dashboardItemRepository, SensorDataRepository sensorDataRepository) {
        this.dashboardRepository = dashboardRepository;
        this.userRepository = userRepository;
        this.dashboardItemRepository = dashboardItemRepository;
        this.sensorDataRepository = sensorDataRepository;
    }

    /**
     * Get all user dashboards
     *
     * @return Array of dashboards
     */
    @GetMapping
    public ResponseEntity<List<Dashboard>> findAllDashboards() {
        List<Dashboard> dashboards = dashboardRepository.findAll();
        return ResponseEntity.ok().body(dashboards);
    }

    /**
     * Create a new user dashboard
     *
     * @return dashboard object
     */
    @PostMapping
    public ResponseEntity<Dashboard> createDashboard(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo) {
        User foundUser = getUser(jwTokenInfo);

        // Make sure user doesn't already have a dashboard
        if (dashboardRepository.existsByUserId(foundUser.getId())) {
            throw new NotAcceptableException("User with id=%d already has a dashboard".formatted(foundUser.getId()));
        }

        // Create and save to repo
        Dashboard dashboard = dashboardRepository.save(new Dashboard(foundUser));

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dashboard.getId()).toUri();

        return ResponseEntity.created(location).body(dashboard);
    }


    /**
     * Delete user's dashboard
     * TODO move to user controller???
     *
     * @return deleted dashboard object
     */
    @DeleteMapping
    public ResponseEntity<Dashboard> deleteDashboardByUserId(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo) {
        Dashboard foundDashboard = dashboardRepository.findByUserId(jwTokenInfo.userId())
                .orElseThrow(() -> new NotFoundException(String.format("Dashboard with id %s is not found", jwTokenInfo.userId())));

        dashboardRepository.delete(foundDashboard);

        return ResponseEntity.ok().body(foundDashboard);
    }


    /**
     * Find dashboard by dashboard Id
     *
     * @param id dashboard id
     * @return Dashboard object
     */
    @GetMapping(path = "{id}")
    public ResponseEntity<Dashboard> getDashboardById(@PathVariable Long id) {

        Dashboard foundDashboard = dashboardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Dashboard with id %s is not found", id)));
        return ResponseEntity.ok().body(foundDashboard);
    }

    /**
     * Find dashboard by user id
     * TODO move to user controller???
     *
     * @return Dashboard object
     */
    @GetMapping(path = "user/{userId}")
    public ResponseEntity<Dashboard> getDashboardByUserId(@PathVariable Long userId) {

        Dashboard foundDashboard = dashboardRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Dashboard with userid %s is not found", userId)));
        return ResponseEntity.ok().body(foundDashboard);
    }


    @PutMapping(path = "{id}")
    @Transactional
    @JsonView(CustomJson.Shallow.class)
    public ResponseEntity<Set<DashboardItem>> updateLayout(@PathVariable Long id, @RequestBody DashboardItem... items) {

        Dashboard dashboard = dashboardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Dashboard with id %d is not found", id)));

        dashboardItemRepository.deleteAll(dashboardItemRepository.findDashboardItemByDashboard(dashboard));


        for (DashboardItem item : items) {
            dashboard.addToLayout(dashboardItemRepository.save(item));
        }
        dashboardRepository.save(dashboard);

        return ResponseEntity.ok().body(dashboard.getLayout());
    }


    @GetMapping("widget/{id}")
    public ResponseEntity<List<SensorData>> findDataBetweenDates(@PathVariable String id, String from, String to) {
        return ResponseEntity.ok().body(sensorDataRepository.findSensorDataByShipSensorIdAndTimeBetween(id, Long.valueOf(from), Long.valueOf(to)));
    }


    private User getUser(JWTokenInfo jwTokenInfo) {
        return userRepository.findById(jwTokenInfo.userId())
                .orElseThrow(() -> new NotFoundException(String.format(
                        "User with id %s wasn't found",
                        jwTokenInfo.userId()
                )));
    }

}

