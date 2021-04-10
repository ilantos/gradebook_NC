package lab3.gradebook.nc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Location {
    private int id;
    private Location parentLoc;
    private String title;
    private LocType locType;
}
