package com.smartship.backend.app.rest;

import com.smartship.backend.app.exceptions.NotFoundException;
import com.smartship.backend.app.models.Dashboard;
import com.smartship.backend.app.models.DashboardItem;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.models.Widget;
import com.smartship.backend.app.repositories.DashboardRepository;
import com.smartship.backend.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
     * Get all dashboards
     *
     * @return Array of dashboards
     */
    @GetMapping(path = "")
    public ResponseEntity<List<Dashboard>> findAllDashboards() {
        List<Dashboard> dashboards = dashboardRepository.findAll();

        return ResponseEntity.ok().body(dashboards);
    }

    /**
     * Create a new dashboard
     *
     * @param user
     * @return created layout
     */
    @PostMapping(path = "")
    public ResponseEntity<Dashboard> createDashboard(@RequestBody User user) {

        Dashboard dashboardLayout = dashboardRepository.save(new Dashboard(user));

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dashboardLayout.getId()).toUri();

        return ResponseEntity.created(location).body(dashboardLayout);
    }


    /**
     * Delete a dashboard by user id
     *
     * @param id user id
     * @return deleted dashboard
     */
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Dashboard> deleteDashboardById(@PathVariable Long id) {

        Dashboard foundDashboard = dashboardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Dashboard with id %s wasn't found", id)));

        dashboardRepository.deleteById(id);

        return ResponseEntity.ok().body(foundDashboard);
    }

    /**
     * Find dashboard by user id
     *
     * @param id user id
     * @return layout object
     */
    @GetMapping(path = "{id}")
    public ResponseEntity<Dashboard> getDashboardByUserId(@PathVariable Long id) {

        Dashboard foundDashboard = dashboardRepository.findByUserId(id)
                .orElseThrow(() -> new NotFoundException(String.format("Dashboard with userid %s wasn't found", id)));

        return ResponseEntity.ok().body(foundDashboard);
    }


    /**
     * Add a Dashboard item to layout
     *
     * @param id     dashboard id
     * @param widget Widget Object
     * @return
     */
    @PostMapping(path = "{id}")
    public ResponseEntity<DashboardItem> addDashboardItem(@PathVariable Long id, @RequestBody Widget widget) {

        DashboardItem item = new DashboardItem(0, 0, widget.getDefaultHeight(), widget.getDefaultWidth(), widget);

        Dashboard dashboard = dashboardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Dashboard with id %s wasn't found", id)));

        dashboard.addToLayout(item);


        return ResponseEntity.ok().body(item);
    }
//
//    /**
//     * Get a Dashboard items or get a specific item
//     *
//     * @param id     dashboard id
//     * @param
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
//
//
//    }

    @PutMapping(path = "{id}")
    public ResponseEntity<DashboardItem> updateDashboardItem(@RequestBody Long id, @RequestParam Long itemId, @RequestBody Object values) {

        Dashboard dashboard = dashboardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Dashboard with id %s wasn't found", id)));

        DashboardItem item = dashboard.findItemById(itemId);

        item = (DashboardItem) values;

        return ResponseEntity.ok().body(item);
    }
}
