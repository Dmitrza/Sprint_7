package ru.praktikum_services.qa_scooter.order_list;

public class PageInfo {

    private int page;
    private int total;
    private int limit;

    public PageInfo(int page, int total, int limit) {
        this.page = page;
        this.total = total;
        this.limit = limit;
    }

    public PageInfo(){}

}
