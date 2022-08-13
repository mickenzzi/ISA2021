package application.model.dto;

import application.model.User;

public class CottageDTO {

    private Long id;
    private String name;
    private String address;
    private String description;
    private String room;
    private String term;
    private String price;
    private String info;
    private String termin;
    private User userCottage;

    public CottageDTO() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getRoom() {
        return room;
    }

    public String getTerm() {
        return term;
    }

    public String getPrice() {
        return price;
    }

    public String getInfo() {
        return info;
    }

    public String getTermin() {
        return termin;
    }

    public User getUserCottage() {
        return userCottage;
    }
}
