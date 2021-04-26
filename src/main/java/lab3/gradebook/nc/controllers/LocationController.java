package lab3.gradebook.nc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lab3.gradebook.nc.controllers.utils.CustomFormatResponseBody;
import lab3.gradebook.nc.model.services.CheckSubtypes;
import lab3.gradebook.nc.model.db.DAOException;
import lab3.gradebook.nc.model.db.DaoLocation;
import lab3.gradebook.nc.model.entities.Location;
import lab3.gradebook.nc.model.entities.LocationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/api/locations")
public class LocationController {
    private DaoLocation daoLocation;
    private CustomFormatResponseBody customFormatResponseBody;
    private CheckSubtypes checkSubtypes;

    @Autowired
    public LocationController(
            DaoLocation daoLocation,
            CustomFormatResponseBody customFormatResponseBody,
            CheckSubtypes checkSubtypes) {
        this.daoLocation = daoLocation;
        this.customFormatResponseBody = customFormatResponseBody;
        this.checkSubtypes = checkSubtypes;
    }

    @GetMapping
    @ResponseBody
    public String allLocations() throws DAOException, JsonProcessingException {
        List<Location> locationList = daoLocation.getAll();
        return customFormatResponseBody.buildResponse(true, locationList);
    }

    @GetMapping("withoutType/{type}")
    @ResponseBody
    public String allLocationsWithoutLocType(@PathVariable String type)
            throws DAOException, JsonProcessingException {
        try {
            List<Location> locationList =
                    daoLocation.getAllWithoutType(LocationType.valueOf(type));
            return customFormatResponseBody.buildResponse(true, locationList);
        } catch (IllegalArgumentException e) {
            return customFormatResponseBody.buildResponse(false,
                    "Incorrect location type");
        }
    }

    @GetMapping("{id}")
    @ResponseBody
    public String getById(@PathVariable int id)
            throws JsonProcessingException, DAOException {
        Location location = daoLocation.getById(id);
        return location != null
                ? customFormatResponseBody.buildResponse(true, location)
                : customFormatResponseBody.buildResponse(
                        false,
                        "Cannot find location by id");
    }

    @GetMapping("{id}/chain")
    @ResponseBody
    public String getParentLocations(@PathVariable int id)
            throws DAOException, JsonProcessingException {
        List<Location> locationList = daoLocation.getParentLocations(id);
        return locationList.size() != 0
                ? customFormatResponseBody.buildResponse(true, locationList)
                : customFormatResponseBody.buildResponse(
                        false,
                        "Cannot find chain locations by id");
    }

    @GetMapping("lowerType/{locType}")
    @ResponseBody
    public String subtypes(@PathVariable String locType)
            throws JsonProcessingException {
        if (locType.trim().equals("-")) {
            return customFormatResponseBody.buildResponse(true, "COUNTRY");
        }
        try {
            LocationType locationType =
                    checkSubtypes.lowerType(LocationType.valueOf(locType));
            if (locationType == null) {
                throw new IllegalArgumentException();
            }
            return customFormatResponseBody
                    .buildResponse(true, locationType);
        } catch (IllegalArgumentException e) {
            return customFormatResponseBody
                    .buildResponse(false, "Not exist a subtype");
        }
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public String delete(@PathVariable int id)
            throws JsonProcessingException {
        try {
            daoLocation.delete(id);
            return customFormatResponseBody
                    .buildResponse(true, "Location deleted successfully");
        } catch (DAOException e) {
            return customFormatResponseBody
                    .buildResponse(false, "Location deleted unsuccessfully");
        }
    }

    @PutMapping
    public ResponseEntity<?> edit(@RequestBody Location location)
            throws JsonProcessingException {
        try {
            daoLocation.edit(location);
            return ResponseEntity.ok(
                    customFormatResponseBody.buildResponse(
                            true,
                            "Location edited successfully"));
        } catch (DAOException e) {
            return ResponseEntity.status(400).body(
                    customFormatResponseBody.buildResponse(
                            false,
                            "Location edited unsuccessfully"));
        }
    }

    @PostMapping
    @ResponseBody
    public String add(@RequestBody Location location)
            throws JsonProcessingException {
        try {
            daoLocation.add(location);
            return customFormatResponseBody
                    .buildResponse(true, "Location added successfully");
        } catch (DAOException e) {
            System.out.println(e.getMessage());
            return customFormatResponseBody
                    .buildResponse(false, "Location added unsuccessfully");
        }
    }
}
