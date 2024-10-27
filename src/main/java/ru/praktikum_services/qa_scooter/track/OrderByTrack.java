package ru.praktikum_services.qa_scooter.track;

public class OrderByTrack {

    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private int track;
    private int status;
    private String[] color;
    private String comment;
    private String cancelled;
    private String finished;
    private String inDelivery;
    private String courierFirstName;
    private String createdAt;
    private String updatedAt;

    public OrderByTrack(int id, String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, int track, int status, String[] color, String comment, String cancelled, String finished, String inDelivery, String courierFirstName, String createdAt, String updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.track = track;
        this.status = status;
        this.color = color;
        this.comment = comment;
        this.cancelled = cancelled;
        this.finished = finished;
        this.inDelivery = inDelivery;
        this.courierFirstName = courierFirstName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public OrderByTrack() {
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public int getTrack() {
        return track;
    }

    public int getStatus() {
        return status;
    }

    public String[] getColor() {
        return color;
    }

    public String getComment() {
        return comment;
    }

    public String getCancelled() {
        return cancelled;
    }

    public String getFinished() {
        return finished;
    }

    public String getInDelivery() {
        return inDelivery;
    }

    public String getCourierFirstName() {
        return courierFirstName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
