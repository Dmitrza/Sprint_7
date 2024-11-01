package ru.praktikum_services.qa_scooter.courier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CourierId {

    private String id;

    public CourierId(String id) {
        this.id = id;
    }

    public CourierId() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
