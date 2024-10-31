package ru.praktikum_services.qa_scooter.order_list;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListOfOrders {
    private List<OrderFromList> orders;
    private PageInfo pageInfo;
    private List<AvailableStation> availableStations;

    public ListOfOrders(List<OrderFromList> orders, PageInfo pageInfo, List<AvailableStation> availableStations) {
        this.orders = orders;
        this.pageInfo = pageInfo;
        this.availableStations = availableStations;
    }

    public ListOfOrders() {}

    public List<OrderFromList> getOrders() {
        return orders;
    }
}
