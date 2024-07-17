package model;

public class DeleteCourierResponse {
    private Boolean ok;

    public DeleteCourierResponse() {
    }

    public DeleteCourierResponse(Boolean ok) {
        this.ok = ok;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }
}
