package tscanner.msquared.hr.travelscanner.models.restModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mihael on 29.5.2015..
 */
public class AppUser {

    @Expose
    private String username;
    @Expose
    private Integer id;
    @SerializedName("is_salesman")
    @Expose
    private Boolean isSalesman;
    @SerializedName("travel_points")
    @Expose
    private Integer travelPoints;
    @Expose
    private String email;

    private String password;

    public AppUser(String email, Integer id, Boolean isSalesman, Integer travelPoints, String username) {
        this.email = email;
        this.id = id;
        this.isSalesman = isSalesman;
        this.travelPoints = travelPoints;
        this.username = username;
    }

    public AppUser(String email, Integer id, Boolean isSalesman, String password, Integer travelPoints, String username) {
        this.email = email;
        this.id = id;
        this.isSalesman = isSalesman;
        this.password = password;
        this.travelPoints = travelPoints;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean isSalesman() {
        return isSalesman;
    }

    public void setIsSalesman(Boolean isSalesman) {
        this.isSalesman = isSalesman;
    }

    public Integer getTravelPoints() {
        return travelPoints;
    }

    public void setTravelPoints(Integer travelPoints) {
        this.travelPoints = travelPoints;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "id: " + id + ", " + username + ", " + email + ", isSalesman: " + isSalesman + ", travelPoints: " + travelPoints;
    }

}
