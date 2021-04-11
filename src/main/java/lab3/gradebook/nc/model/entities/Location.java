package lab3.gradebook.nc.model.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private int id;
    private int parentLoc;
    private String title;
    private LocType locType;
}
