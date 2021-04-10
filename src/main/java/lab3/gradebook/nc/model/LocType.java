package lab3.gradebook.nc.model;

public enum LocType {
    COUNTRY (1, "Country"), CITY (2, "City"), SCHOOL(3, "School");
    private int id;
    private String value;
    LocType(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
