package tscanner.msquared.hr.travelscanner.models.restModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mihael on 29.5.2015..
 */
public class TravelDestination {
    @Expose
    private String name;
    @Expose
    private String picture;
    @SerializedName("travel_by")
    @Expose
    private String travelBy;
    @Expose
    private Double price;
    @SerializedName("travel_points")
    @Expose
    private Integer travelPoints;
    @SerializedName("description_long")
    @Expose
    private String descriptionLong;
    @SerializedName("departure_date")
    @Expose
    private String departureDate;
    @Expose
    private Integer duration;
    @SerializedName("description_short")
    @Expose
    private String descriptionShort;
    @Expose
    private Integer id;

    @Override
    public String toString() {
        return "id: " + id + ", " + name + " - " + price + " - " + travelPoints + " - " + travelBy;
    }

    public TravelDestination(String departureDate, String descriptionLong, String descriptionShort, Integer duration, Integer id, String name, String picture, Double price, String travelBy, Integer travelPoints) {
        this.departureDate = departureDate;
        this.descriptionLong = descriptionLong;
        this.descriptionShort = descriptionShort;
        this.duration = duration;
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.price = price;
        this.travelBy = travelBy;
        this.travelPoints = travelPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTravelBy() {
        return travelBy;
    }

    public void setTravelBy(String travelBy) {
        this.travelBy = travelBy;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTravelPoints() {
        return travelPoints;
    }

    public void setTravelPoints(Integer travelPoints) {
        this.travelPoints = travelPoints;
    }

    public String getDescriptionLong() {
        return descriptionLong;
    }

    public void setDescriptionLong(String descriptionLong) {
        this.descriptionLong = descriptionLong;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescriptionShort() {
        return descriptionShort;
    }

    public void setDescriptionShort(String descriptionShort) {
        this.descriptionShort = descriptionShort;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
