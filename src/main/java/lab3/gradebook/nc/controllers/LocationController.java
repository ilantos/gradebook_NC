package lab3.gradebook.nc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lab3.gradebook.nc.controllers.utils.CustomFormatResponseBody;
import lab3.gradebook.nc.model.db.DAOException;
import lab3.gradebook.nc.model.db.DaoLocation;
import lab3.gradebook.nc.model.entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//TODO не выбрасывать DAOException ибо это в 500 ошибку превращается
// Сделать нормальный response. {"request":True, "message":{...}}
// + Сделать добавление места
// * Сделать более умное изменение. Чтобы можно было изменять id_parent, loc type
@Controller
@RequestMapping("/locations")
public class LocationController {
    private DaoLocation daoLocation;
    private CustomFormatResponseBody customFormatResponseBody;

    @Autowired
    public LocationController(DaoLocation daoLocation, CustomFormatResponseBody customFormatResponseBody) {
        this.daoLocation = daoLocation;
        this.customFormatResponseBody = customFormatResponseBody;
    }

    @GetMapping
    @ResponseBody
    public String allLocations() throws DAOException, JsonProcessingException {
        List<Location> locationList = daoLocation.getAll();
        return customFormatResponseBody.buildResponse(true, locationList);
    }

    @GetMapping("{id}")
    @ResponseBody
    public String getById(@PathVariable int id) throws JsonProcessingException, DAOException {
        Location location = daoLocation.getById(id);
        return location != null ?
                customFormatResponseBody.buildResponse(true, location) :
                customFormatResponseBody.buildResponse(
                        false,
                        "Cannot find location by id");
    }

    @GetMapping("{id}/chain")
    @ResponseBody
    public String getParentLocations(@PathVariable int id) throws DAOException, JsonProcessingException {
        List<Location> locationList = daoLocation.getParentLocations(id);
        return locationList.size() != 0 ?
                customFormatResponseBody.buildResponse(true, locationList) :
                customFormatResponseBody.buildResponse(
                        false,
                        "Cannot find chain locations by id");
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public String delete(@PathVariable int id) throws JsonProcessingException {
        try {
            daoLocation.delete(id);
            return customFormatResponseBody.buildResponse(true, "Location deleted successfully");
        } catch (DAOException e) {
            return customFormatResponseBody.buildResponse(false, "Location deleted unsuccessfully");
        }
    }

    @PutMapping
    public ResponseEntity<?> edit(@RequestBody Location location) throws JsonProcessingException {
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
    public String add(@RequestBody Location location) throws JsonProcessingException {
        try {
            daoLocation.add(location);
            return customFormatResponseBody.buildResponse(true, "Location added successfully");
        } catch (DAOException e) {
            return customFormatResponseBody.buildResponse(false, "Location added unsuccessfully");
        }
    }
}
