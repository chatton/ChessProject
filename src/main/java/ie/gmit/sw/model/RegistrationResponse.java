package ie.gmit.sw.model;

public class RegistrationResponse {

    private Integer id;
    private String status;

    public RegistrationResponse(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

}
