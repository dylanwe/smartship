package com.smartship.backend.app.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.smartship.backend.app.exceptions.NotAcceptableException;
import com.smartship.backend.app.exceptions.NotFoundException;
import com.smartship.backend.app.exceptions.UnauthorizedException;
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
import java.util.Optional;
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
     * Retrieve a list of all dashboards from the repository.
     *
     * @return a list of all dashboards
     */
    @GetMapping
    public ResponseEntity<List<Dashboard>> findAllDashboards() {
        List<Dashboard> dashboards = dashboardRepository.findAll();
        return ResponseEntity.ok(dashboards);
    }

    /**
     * Create a new dashboard for a user.
     *
     * @param jwTokenInfo JWT token information containing user information
     * @return a newly created Dashboard with 201 Created HTTP status
     * @throws NotAcceptableException if user already has a dashboard
     */
    @PostMapping
    public ResponseEntity<Dashboard> createDashboard(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo) {
        // Retrieve user from JWT token
        User foundUser = getUser(jwTokenInfo);

        // Make sure user doesn't already have a dashboard
        if (dashboardRepository.existsByUserId(foundUser.getId())) {
            throw new NotAcceptableException("User with id=%d already has a dashboard".formatted(foundUser.getId()));
        }

        // Create and save to repo
        Dashboard dashboard = dashboardRepository.save(new Dashboard(foundUser));

        // Build location header
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dashboard.getId()).toUri();

        // Return newly created dashboard in response
        return ResponseEntity.created(location).body(dashboard);
    }


    /**
     * Delete a dashboard for a user.
     *
     * @param jwTokenInfo JWT token information containing user information
     * @return a deleted Dashboard with 200 OK HTTP status
     * @throws NotFoundException if dashboard for the user is not found
     */
    @DeleteMapping
    public ResponseEntity<Dashboard> deleteDashboardByUserId(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo) {
        // Find dashboard by user id
        Dashboard foundDashboard = dashboardRepository.findByUserId(jwTokenInfo.userId())
                .orElseThrow(() -> new NotFoundException(String.format("Dashboard with id %s is not found", jwTokenInfo.userId())));

        // Delete the dashboard from repository
        dashboardRepository.delete(foundDashboard);

        // Return deleted dashboard in response
        return ResponseEntity.ok(foundDashboard);
    }


    /**
     * Retrieve a dashboard by id.
     *
     * @param id id of the dashboard
     * @return a dashboard with 200 OK HTTP status
     * @throws NotFoundException if dashboard with given id is not found
     */
    @GetMapping(path = "{id}")
    public ResponseEntity<Dashboard> getDashboardById(@PathVariable Long id) {
        // Find dashboard by id
        Dashboard foundDashboard = dashboardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Dashboard with id %s is not found", id)));

        // Return found dashboard in response
        return ResponseEntity.ok(foundDashboard);
    }

    /**
     * Retrieve a dashboard by user id.
     *
     * @param userId id of the user
     * @return a dashboard with 200 OK HTTP status
     * @throws NotFoundException if user or dashboard with given user id is not found
     */
    @GetMapping(path = "user/{userId}")
    public ResponseEntity<Dashboard> getDashboardByUserId(@PathVariable Long userId, @RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo) {

        // Find user by id
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with userid %s is not found", userId)));

        //check if the user id in the path variable matches the user id in the JWT
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        // Get or create user dashboard
        Dashboard dashboard = dashboardRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Dashboard newDashboard = new Dashboard(user);
                    user.setDashboard(newDashboard);
                    return dashboardRepository.save(newDashboard);
                });

        return ResponseEntity.ok(dashboard);
    }


    /**
     * Update layout of a dashboard by replacing it with new items.
     *
     * @param id    id of the dashboard
     * @param items new items to update the layout
     * @return a set of new DashboardItem with 200 OK HTTP status
     * @throws NotFoundException if dashboard with given id is not found
     */
    @PutMapping(path = "{id}")
    @Transactional
    @JsonView(CustomJson.Shallow.class)
    public ResponseEntity<Set<DashboardItem>> updateLayout(@PathVariable Long id, @RequestBody DashboardItem... items) {
        // Find dashboard by id
        Dashboard dashboard = dashboardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Dashboard with id %d is not found", id)));

        // Delete existing layout
        dashboardItemRepository.deleteAll(dashboardItemRepository.findDashboardItemByDashboard(dashboard));

        // Add new items to layout
        for (DashboardItem item : items) {
            dashboard.addToLayout(dashboardItemRepository.save(item));
        }
        // Save the updated dashboard
        dashboardRepository.save(dashboard);

        // Return new layout in response
        return ResponseEntity.ok().body(dashboard.getLayout());
    }

    /**
     * Retrieve SensorData between two timestamps for a given Sensor Id
     *
     * @param id   Sensor Id for which data is being fetched
     * @param from starting timestamp in millis
     * @param to   ending timestamp in millis
     * @return a list of SensorData with 200 OK HTTP status
     */
    @GetMapping("widget/{id}")
    public ResponseEntity<List<SensorData>> findDataBetweenDates(@PathVariable String id, String from, String to) {
        return ResponseEntity.ok().body(sensorDataRepository.findSensorDataByShipSensorIdAndTimeBetweenOrderByTimeAsc(id, Long.valueOf(from), Long.valueOf(to)));
    }

    /**
     * Retrieve a user by user id
     *
     * @param jwTokenInfo JWT token containing user id
     * @return a user wrapped in a User object
     * @throws NotFoundException if user with given id is not found
     */
    private User getUser(JWTokenInfo jwTokenInfo) {
        return userRepository.findById(jwTokenInfo.userId())
                .orElseThrow(() -> new NotFoundException(String.format(
                        "User with id %s wasn't found",
                        jwTokenInfo.userId()
                )));
    }

}

