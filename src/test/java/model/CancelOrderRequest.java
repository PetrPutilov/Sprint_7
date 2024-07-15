package model;

public class CancelOrderRequest {
    private Integer track;

    public CancelOrderRequest() {
    }

    public CancelOrderRequest(Integer track) {
        this.track = track;
    }

    public Integer getTrack() {
        return track;
    }

    public void setTrack(Integer track) {
        this.track = track;
    }
}
