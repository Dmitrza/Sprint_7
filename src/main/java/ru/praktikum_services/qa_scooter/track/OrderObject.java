package ru.praktikum_services.qa_scooter.track;

public class OrderObject {

    private OrderByTrack order;

    public OrderObject() {
    }

    public OrderObject(OrderByTrack order) {
        this.order = order;
    }

    public OrderByTrack getOrder() {
        return order;
    }
}
