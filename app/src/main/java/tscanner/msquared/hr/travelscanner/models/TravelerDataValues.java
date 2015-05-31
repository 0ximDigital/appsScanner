package tscanner.msquared.hr.travelscanner.models;

/**
 * Created by Mihael on 31.5.2015..
 */
public class TravelerDataValues {

    private String name;
    private String surname;

    private String dateOfBirth;
    private String idNumber;

    public TravelerDataValues(String dateOfBirth, String idNumber, String name, String surname) {
        this.dateOfBirth = dateOfBirth;
        this.idNumber = idNumber;
        this.name = name;
        this.surname = surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
