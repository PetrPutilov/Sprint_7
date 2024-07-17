package model;

import java.util.List;

public class GetOrdersResponse {
    private List<Object> orders;
    private PageInfo pageInfo;
    private List<Object> availableStations;

    public GetOrdersResponse() {
    }

    public GetOrdersResponse(List<Object> orders, PageInfo pageInfo, List<Object> availableStations) {
        this.orders = orders;
        this.pageInfo = pageInfo;
        this.availableStations = availableStations;
    }

    public List<Object> getOrders() {
        return orders;
    }

    public void setOrders(List<Object> orders) {
        this.orders = orders;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<Object> getAvailableStations() {
        return availableStations;
    }

    public void setAvailableStations(List<Object> availableStations) {
        this.availableStations = availableStations;
    }
}
