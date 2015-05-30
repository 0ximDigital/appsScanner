package tscanner.msquared.hr.travelscanner.helpers.Rest;


public final class ApiConstants {

    public static final String serverPrefix = "https://lumipex.me";

    public static final String returnFormat = ".json";

    // GET

    public static final String dohvatSvihUsera = "/Traveler/api/data/app_users";
    public static final String dohvatUseraSaId = "/Traveler/api/data/app_user/id/";
    public static final String dohvatSalesmanUsera = "/Traveler/api/data/app_users/is_salesman/true";
    public static final String dohvatObicnihUsera = "/Traveler/api/data/app_users/is_salesman/false";

    public static final String dohvatSvihDestinacija = "/Traveler/api/data/travel_destinations";
    public static final String dohvatDestinacijeSaId = "/Traveler/api/data/travel_destination/id/";

    public static final String dohvatiSvePurchase = "/Traveler/api/data/purchases";
    public static final String dohvatPurchaseaSaId = "/Traveler/api/data/purchase/id/";
    public static final String dohvatPurchaseaOdUseraSaId = "/Traveler/api/data/purchases/user_id/";

    public static final String dohvatSvihTravelera = "/Traveler/api/data/travelers";
    public static final String dohvatTraveleraSaId = "/Traveler/api/data/traveler/id/";
    public static final String dohvatTravelersaSaPurchaseId = "/Traveler/api/data/travelers/purchase_id/";     // Svi koji su bili negdje (Hawaii)

    // POST

    public static final String postAppUser = "/Traveler/api/data/app_user";
    public static final String postPurchase = "/Traveler/api/data/purchase";
    public static final String postTraveler = "/Traveler/api/data/traveler";

    // PUT

    public static final String putAppUser = "/Traveler/api/data/app_user/";

    // DELETE

    public static final String deleteAppUserWithId = "/Traveler/api/data/app_user/";

}

