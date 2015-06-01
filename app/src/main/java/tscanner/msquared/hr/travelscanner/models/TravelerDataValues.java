package tscanner.msquared.hr.travelscanner.models;

import java.security.spec.ECField;

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
        if(surname == null){
            String[] nameData = name.split("\\s+");
            if(nameData.length == 2){
                this.name = nameData[0];
                this.surname = nameData[1];
            }
        }
        else{
            this.name = name;
            this.surname = surname;
        }
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

    public int getAge(){
        if(this.dateOfBirth != null){
            try{
                String[] data = this.dateOfBirth.split("\\.");
                int birthYear = Integer.parseInt(data[2]);
                return 2015 - birthYear;
            }
            catch (Exception e){
                return 20;
            }
        }
        return 20;
    }

    public static String parseInputDate(String inDate){
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
