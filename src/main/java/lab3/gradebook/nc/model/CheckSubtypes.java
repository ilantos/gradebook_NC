package lab3.gradebook.nc.model;

import lab3.gradebook.nc.model.entities.LocationType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CheckSubtypes implements InitializingBean {
    private NavigableMap<Integer, LocationType> types = new TreeMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        types.put(0, LocationType.COUNTRY);
        types.put(1, LocationType.CITY);
        types.put(2, LocationType.UNIVERSITY);
        types.put(3, LocationType.GROUP);
    }

    public Collection<LocationType> subtypeOf(LocationType locationType) {
        return types.tailMap(locationType.ordinal(), false).values()
                .stream()
                .skip(1)
                .collect(Collectors.toList());
    }

    public LocationType lowerType(LocationType locationType) {
        Map.Entry<Integer, LocationType> subtype = types.higherEntry(locationType.ordinal());
        return subtype != null ? subtype.getValue() : null;
    }
}
