package tscanner.msquared.hr.travelscanner.models.restModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mihael on 29.5.2015..
 */
public class Traveler {

    public final static String GENDER_MALE = "M";
    public final static String GENDER_FEMALE = "F";

    @SerializedName("purchase_id")
    @Expose
    private Integer purchaseId;
    @Expose
    private String surname;
    @Expose
    private String name;
    @Expose
    private String gender;
    @Expose
    private Integer age;
    @Expose
    private Integer id;

    public Traveler(Integer age, String gender, Integer id, String name, Integer purchaseId, String surname) {
        this.age = age;
        this.gender = gender;
        this.id = id;
        this.name = name;
        this.purchaseId = purchaseId;
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "id: " + id + ", " + name + " - " + surname + " - " + gender + " - " + age + ", purchaseId - " + purchaseId;
    }
}
