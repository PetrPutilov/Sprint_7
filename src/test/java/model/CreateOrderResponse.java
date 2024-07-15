package model;

public class CreateOrderResponse {
    private Integer track;

    public CreateOrderResponse() {
    }

    public CreateOrderResponse(Integer track) {
        this.track = track;
    }

    public Integer getTrack() {
        return track;
    }

    public void setTrack(Integer track) {
        this.track = track;
    }
}
