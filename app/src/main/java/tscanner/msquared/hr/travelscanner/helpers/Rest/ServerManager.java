package tscanner.msquared.hr.travelscanner.helpers.Rest;

import android.util.Log;
import android.view.SurfaceHolder;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import tscanner.msquared.hr.travelscanner.helpers.Rest.ApiConstants;
import tscanner.msquared.hr.travelscanner.helpers.Rest.DeleteRestService;
import tscanner.msquared.hr.travelscanner.helpers.Rest.GetRestService;
import tscanner.msquared.hr.travelscanner.helpers.Rest.PostRestService;
import tscanner.msquared.hr.travelscanner.helpers.Rest.PutRestService;
import tscanner.msquared.hr.travelscanner.models.restModels.AppUser;
import tscanner.msquared.hr.travelscanner.models.restModels.Purchase;
import tscanner.msquared.hr.travelscanner.models.restModels.ResponseMessage;
import tscanner.msquared.hr.travelscanner.models.restModels.TravelDestination;
import tscanner.msquared.hr.travelscanner.models.restModels.Traveler;

/**
 * Created by Mihael on 29.5.2015..
 */
public class ServerManager {

    private final String TAG = this.getClass().getSimpleName();

    private GetRestService getRestService;
    private PostRestService postRestService;
    private PutRestService putRestService;
    private DeleteRestService deleteRestService;

    private Gson gson;

    public ServerManager() {
        this.gson = new Gson();
    }

    public interface Callback<T>{
        void requestResult(T t);
    }

    // ### AppUser REST

    public void getAllAppUsers(final Callback<List<AppUser>> appUsersCallback){
        String response = null;
        if(this.getRestService == null){
            this.getRestService = new GetRestService(null);
        }
        this.getRestService.setUrl(this.getURLRequest(ApiConstants.dohvatSvihUsera));
        try {
            this.getRestService.setGetRestServiceListener(new GetRestService.GetRestServiceListener() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, response);
                    if(response == null){
                        appUsersCallback.requestResult(null);
                    }
                    AppUser[] appUsersArray = gson.fromJson(response, AppUser[].class);
                    appUsersCallback.requestResult(Arrays.asList(appUsersArray));
                }
            });
            this.getRestService.executeRequest();
        }
        catch (Exception e){
            e.printStackTrace();
            appUsersCallback.requestResult(null);
        }
    }

    public void getAppUserById(Integer id, final Callback<AppUser> appUserCallback){
        String response = null;
        if(this.getRestService == null){
            this.getRestService = new GetRestService(null);
        }
        this.getRestService.setUrl(this.getURLRequestWithIdParameter(ApiConstants.dohvatUseraSaId, id));
        try{
            this.getRestService.setGetRestServiceListener(new GetRestService.GetRestServiceListener() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, response);
                    if(response == null){
                        appUserCallback.requestResult(null);
                    }
                    response = response.replaceAll("\\[|\\]", "");
                    AppUser appUser = gson.fromJson(response, AppUser.class);
                    appUserCallback.requestResult(appUser);
                }
            });
            this.getRestService.executeRequest();
        }
        catch (Exception e){
            e.printStackTrace();
            appUserCallback.requestResult(null);
        }
    }

    public void getAllSalesmanUsers(final Callback<List<AppUser>> appUsersCallback){
        String response = null;
        if(this.getRestService == null){
            this.getRestService = new GetRestService(null);
        }
        this.getRestService.setUrl(this.getURLRequest(ApiConstants.dohvatSalesmanUsera));
        try {
            this.getRestService.setGetRestServiceListener(new GetRestService.GetRestServiceListener() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, response);
                    if(response == null){
                        appUsersCallback.requestResult(null);
                    }
                    AppUser[] appUsersArray = gson.fromJson(response, AppUser[].class);
                    appUsersCallback.requestResult(Arrays.asList(appUsersArray));
                }
            });
            this.getRestService.executeRequest();
        }
        catch (Exception e){
            e.printStackTrace();
            appUsersCallback.requestResult(null);
        }
    }

    public void getAllNonSalesmanUsers(final Callback<List<AppUser>> appUsersCallback){
        String response = null;
        if(this.getRestService == null){
            this.getRestService = new GetRestService(null);
        }
        this.getRestService.setUrl(this.getURLRequest(ApiConstants.dohvatObicnihUsera));
        try {
            this.getRestService.setGetRestServiceListener(new GetRestService.GetRestServiceListener() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, response);
                    if(response == null){
                        appUsersCallback.requestResult(null);
                    }
                    AppUser[] appUsersArray = gson.fromJson(response, AppUser[].class);
                    appUsersCallback.requestResult(Arrays.asList(appUsersArray));
                }
            });
            this.getRestService.executeRequest();
        }
        catch (Exception e){
            e.printStackTrace();
            appUsersCallback.requestResult(null);
        }
    }

    // POST
    public void addNewAppUser(AppUser user, final Callback<ResponseMessage> messageCallback){
        if(this.postRestService == null){
            this.postRestService = new PostRestService(null, null);
        }
        this.postRestService.setUrl(this.getURLRequest(ApiConstants.postAppUser));
        this.postRestService.setJson(gson.toJson(user));
        try {
            this.postRestService.setPostRestServicelistener(new PostRestService.PostRestServicelistener() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, response);
                    if(response == null){
                        messageCallback.requestResult(new ResponseMessage(null));
                    }
                    ResponseMessage message = gson.fromJson(response, ResponseMessage.class);
                    messageCallback.requestResult(message);
                }
            });
            this.postRestService.execute();
        }
        catch (Exception e){
            e.printStackTrace();
            messageCallback.requestResult(new ResponseMessage(null));
        }
    }

    // ### TravelDestination REST

    public void getAllTravelDestinations(final Callback<List<TravelDestination>> travelDestinationsCallback){
        String response = null;
        if(this.getRestService == null){
            this.getRestService = new GetRestService(null);
        }
        this.getRestService.setUrl(this.getURLRequest(ApiConstants.dohvatSvihDestinacija));
        try {
            this.getRestService.setGetRestServiceListener(new GetRestService.GetRestServiceListener() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, response);
                    if(response == null){
                        travelDestinationsCallback.requestResult(null);
                    }
                    TravelDestination[] travelDestinations = gson.fromJson(response, TravelDestination[].class);
                    travelDestinationsCallback.requestResult(Arrays.asList(travelDestinations));
                }
            });
            this.getRestService.executeRequest();
        }
        catch (Exception e){
            e.printStackTrace();
            travelDestinationsCallback.requestResult(null);
        }
    }

    public void getTravelDestinationById(Integer id, final Callback<TravelDestination> travelDestinationCallback){
        String response = null;
        if(this.getRestService == null){
            this.getRestService = new GetRestService(null);
        }
        this.getRestService.setUrl(this.getURLRequestWithIdParameter(ApiConstants.dohvatDestinacijeSaId, id));
        try{
            this.getRestService.setGetRestServiceListener(new GetRestService.GetRestServiceListener() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, response);
                    if(response == null){
                        travelDestinationCallback.requestResult(null);
                    }
                    response = response.replaceAll("\\[|\\]", "");
                    TravelDestination travelDestination = gson.fromJson(response, TravelDestination.class);
                    travelDestinationCallback.requestResult(travelDestination);
                }
            });
            this.getRestService.executeRequest();
        }
        catch (Exception e){
            e.printStackTrace();
            travelDestinationCallback.requestResult(null);
        }
    }

    // ### Purchases REST

    public void getAllPurchases(final Callback<List<Purchase>> purchasesCallback){
        String response = null;
        if(this.getRestService == null){
            this.getRestService = new GetRestService(null);
        }
        this.getRestService.setUrl(this.getURLRequest(ApiConstants.dohvatiSvePurchase));
        try {
            this.getRestService.setGetRestServiceListener(new GetRestService.GetRestServiceListener() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, response);
                    if(response == null){
                        purchasesCallback.requestResult(null);
                    }
                    Purchase[] purchases = gson.fromJson(response, Purchase[].class);
                    purchasesCallback.requestResult(Arrays.asList(purchases));
                }
            });
            this.getRestService.executeRequest();
        }
        catch (Exception e){
            e.printStackTrace();
            purchasesCallback.requestResult(null);
        }
    }

    public void getPurchaseById(Integer id, final Callback<Purchase> purchaseCallback){
        String response = null;
        if(this.getRestService == null){
            this.getRestService = new GetRestService(null);
        }
        this.getRestService.setUrl(this.getURLRequestWithIdParameter(ApiConstants.dohvatPurchaseaSaId, id));
        try{
            this.getRestService.setGetRestServiceListener(new GetRestService.GetRestServiceListener() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, response);
                    if(response == null){
                        purchaseCallback.requestResult(null);
                    }
                    response = response.replaceAll("\\[|\\]", "");
                    Purchase purchase = gson.fromJson(response, Purchase.class);
                    purchaseCallback.requestResult(purchase);
                }
            });
            this.getRestService.executeRequest();
        }
        catch (Exception e){
            e.printStackTrace();
            purchaseCallback.requestResult(null);
        }
    }

    /**
     * Returns all purchases by user..
     * If user is regular.. all his purchases
     * If user is salesman, method returns all purchases he sold so far
     * @param id
     * @return
     */
    public void getAllPurchasesFromUserId(Integer id, final Callback<List<Purchase>> purchasesCallback){
        String response = null;
        if(this.getRestService == null){
            this.getRestService = new GetRestService(null);
        }
        this.getRestService.setUrl(this.getURLRequestWithIdParameter(ApiConstants.dohvatPurchaseaOdUseraSaId, id));
        try{
            this.getRestService.setGetRestServiceListener(new GetRestService.GetRestServiceListener() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, response);
                    if(response == null){
                        purchasesCallback.requestResult(null);
                    }
                    Purchase[] purchases = gson.fromJson(response, Purchase[].class);
                    purchasesCallback.requestResult(Arrays.asList(purchases));
                }
            });
            this.getRestService.executeRequest();
        }
        catch (Exception e){
            e.printStackTrace();
            purchasesCallback.requestResult(null);
        }
    }

    // ### Travelers REST

    public void getAllTravelers(final Callback<List<Traveler>> travelersCallback){
        String response = null;
        if(this.getRestService == null){
            this.getRestService = new GetRestService(null);
        }
        this.getRestService.setUrl(this.getURLRequest(ApiConstants.dohvatSvihTravelera));
        try {
            this.getRestService.setGetRestServiceListener(new GetRestService.GetRestServiceListener() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, response);
                    if(response == null){
                        travelersCallback.requestResult(null);
                    }
                    Traveler[] travelers = gson.fromJson(response, Traveler[].class);
                    travelersCallback.requestResult(Arrays.asList(travelers));
                }
            });
            this.getRestService.executeRequest();
        }
        catch (Exception e){
            e.printStackTrace();
            travelersCallback.requestResult(null);
        }
    }

    public void getTravelerById(Integer id, final Callback<Traveler> travelerCallback){
        String response = null;
        if(this.getRestService == null){
            this.getRestService = new GetRestService(null);
        }
        this.getRestService.setUrl(this.getURLRequestWithIdParameter(ApiConstants.dohvatTraveleraSaId, id));
        try{
            this.getRestService.setGetRestServiceListener(new GetRestService.GetRestServiceListener() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, response);
                    if(response == null){
                        travelerCallback.requestResult(null);
                    }
                    response = response.replaceAll("\\[|\\]", "");
                    Traveler traveler = gson.fromJson(response, Traveler.class);
                    travelerCallback.requestResult(traveler);
                }
            });
            this.getRestService.executeRequest();
        }
        catch (Exception e){
            e.printStackTrace();
            travelerCallback.requestResult(null);
        }
    }

    /**
     * Returns traveleres that were together scanned
     * @param id
     * @return
     */
    public void getTravelersWithPurchaseId(Integer id, final Callback<List<Traveler>> travelersCallback){
        String response = null;
        if(this.getRestService == null){
            this.getRestService = new GetRestService(null);
        }
        this.getRestService.setUrl(this.getURLRequestWithIdParameter(ApiConstants.dohvatTravelersaSaPurchaseId, id));
        try{
            this.getRestService.setGetRestServiceListener(new GetRestService.GetRestServiceListener() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, response);
                    if(response == null){
                        travelersCallback.requestResult(null);
                    }
                    Traveler[] travelers = gson.fromJson(response, Traveler[].class);
                    travelersCallback.requestResult(Arrays.asList(travelers));
                }
            });
            this.getRestService.executeRequest();
        }
        catch (Exception e){
            e.printStackTrace();
            travelersCallback.requestResult(null);
        }
    }

    private String getURLRequest(String request){
        return ApiConstants.serverPrefix + request + ApiConstants.returnFormat;
    }

    private String getURLRequestWithIdParameter(String request, int id){
        return ApiConstants.serverPrefix + request + id + ApiConstants.returnFormat;
    }

}
