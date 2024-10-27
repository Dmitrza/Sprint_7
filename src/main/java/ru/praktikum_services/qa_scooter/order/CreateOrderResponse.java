package ru.praktikum_services.qa_scooter.order;

public class CreateOrderResponse {

    private String track;

    public CreateOrderResponse(String track) {
        this.track = track;
    }

    public CreateOrderResponse() {
    }

    public String getTrack() {
        return track;
    }
}
