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
        this.dateOfBirth = this.parseInputDate(dateOfBirth);
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

    private String parseInputDate(String inDate){
        if(inDate == null || inDate.length() != 6){
            return "Undefined";
        }
        String year = inDate.substring(0, 2);
        String month = inDate.substring(2, 4);
        String day = inDate.substring(4,6);

        try{
            int intYear = Integer.parseInt(year);
            String yearPrefix = (intYear < 15) ? "20" : "19";
            year = yearPrefix + year;
        }
        catch (Exception e){
        }

        return day + "." + month + "." + year;
    }
}
