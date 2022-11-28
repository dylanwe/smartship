package com.smartship.backend.app.rest;

import com.smartship.backend.app.exceptions.NotAcceptableException;
import com.smartship.backend.app.exceptions.NotFoundException;
import com.smartship.backend.app.models.Dashboard;
import com.smartship.backend.app.models.DashboardItem;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.models.Widget;
import com.smartship.backend.app.repositories.DashboardRepository;
import com.smartship.backend.app.repositories.UserRepository;
import com.smartship.backend.app.utility.JWTokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/dashboards")
public class DashboardController {


    private final DashboardRepository dashboardRepository;
    private final UserRepository userRepository;

    @Autowired
    public DashboardController(DashboardRepository dashboardRepository, UserRepository userRepository) {
        this.dashboardRepository = dashboardRepository;
        this.userRepository = userRepository;
    }

    /**
     * Find all user dashboards
     * @return Array of dashboards
     */
    @GetMapping(path = "")
    public ResponseEntity<List<Dashboard>> findAllDashboards() {
        List<Dashboard> dashboards = dashboardRepository.findAll();
        return ResponseEntity.ok().body(dashboards);
    }

    /**
     * Create a new user dashboard
     * @return dashboard object
     */
    @PostMapping(path = "")
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
     * @return deleted dashboard object
     */
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Dashboard> deleteDashboardByUserId(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo) {
        Dashboard foundDashboard = dashboardRepository.findByUserId(jwTokenInfo.userId())
                .orElseThrow(() -> new NotFoundException(String.format("Dashboard with id %s is not found", jwTokenInfo.userId())));

        dashboardRepository.delete(foundDashboard);

        return ResponseEntity.ok().body(foundDashboard);
    }


    /**
     * Find dashboard by user id
     * @return Dashboard object
     */
    @GetMapping(path = "{id}")
    public ResponseEntity<Dashboard> getDashboardByUserId(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo) {
        Dashboard foundDashboard = dashboardRepository.findByUserId(jwTokenInfo.userId())
                .orElseThrow(() -> new NotFoundException(String.format("Dashboard with userid %s is not found", jwTokenInfo.userId())));
        return ResponseEntity.ok().body(foundDashboard);
    }


    public ResponseEntity<Dashboard> updateLayout(@PathVariable Long id, DashboardItem[] dashboardItems){
        Dashboard dashboard = dashboardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Dashboard with id %d is not found", id)));

        dashboard.addAllToLayout(dashboardItems);

        return ResponseEntity.ok().body(dashboard);
    }
//
//    /**
//     * Add component to dashboard layout
//     * @param id     dashboard id
//     * @param widget Widget Object
//     * @return
//     */
//    @PostMapping(path = "{id}")
//    public ResponseEntity<DashboardItem> addDashboardItem(@PathVariable Long id, @RequestBody Widget[] widgets) {
//
//        for (Widget widget : widgets) {
//
//        }
//        DashboardItem dashboardItem = new DashboardItem(0, 0, widget.getDefaultHeight(), widget.getDefaultWidth(), widget);
//
//        Dashboard dashboard = dashboardRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException(String.format("Dashboard with id=%s is not found", id)));
//
//        // link component to layout
//        dashboard.addToLayout(dashboardItem);
//
//
//        return ResponseEntity.ok().body(dashboardItem);
//    }


//    /**
//     * Get a Dashboard items or get a specific item
//     *
//     * @param id     dashboard id
//     * @return
//     */
//    @GetMapping(path = "{id}")
//    public Set<DashboardItem> guhaeiugha(@PathVariable Long id) {
//
//        Dashboard dashboard = dashboardRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException(String.format("Dashboard with id %s wasn't found", id)));
//
//
//        return dashboard.getLayout();
//    }




    private User getUser(JWTokenInfo jwTokenInfo) {
        return userRepository.findById(jwTokenInfo.userId())
                .orElseThrow(() -> new NotFoundException(String.format(
                        "User with id %s wasn't found",
                        jwTokenInfo.userId()
                )));
    }

}
