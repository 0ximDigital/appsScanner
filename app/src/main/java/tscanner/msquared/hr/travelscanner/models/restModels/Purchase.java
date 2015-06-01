package tscanner.msquared.hr.travelscanner.models.restModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mihael on 29.5.2015..
 */
public class Purchase {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("purchase_date")
    @Expose
    private String purchaseDate;
    @SerializedName("destination_id")
    @Expose
    private Integer destinationId;
    @Expose
    private Integer id;
    @SerializedName("purchase_signature")
    @Expose
    private String purchaseSignature;

    public Purchase(Integer destinationId, Integer id, String purchaseDate, Integer userId, String signature) {
        this.destinationId = destinationId;
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.userId = userId;
        this.purchaseSignature = signature;
    }

    public Integer getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPurchaseSignature() {
        return purchaseSignature;
    }

    public void setPurchaseSignature(String purchaseSignature) {
        this.purchaseSignature = purchaseSignature;
    }

    @Override
    public String toString() {
        return "id: " + id + ", userId - " + userId + ", destiantionId - " + destinationId + ", - " + purchaseDate + ", " + purchaseSignature;
    }
}
