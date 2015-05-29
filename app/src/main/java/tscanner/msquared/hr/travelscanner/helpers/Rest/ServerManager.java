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

    public void getAllAppUsers(final Callback<List<AppUser>> appUsers){
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
                        appUsers.requestResult(null);
                    }
                    AppUser[] appUsersArray = gson.fromJson(response, AppUser[].class);
                    appUsers.requestResult(Arrays.asList(appUsersArray));
                }
            });
            this.getRestService.executeRequest();
        }
        catch (Exception e){
            e.printStackTrace();
            appUsers.requestResult(null);
        }
    }

    public AppUser getAppUserById(Integer id){
        throw new UnsupportedOperationException("TODO - Implement this !");
    }

    public List<AppUser> getAllSalesmanUsers(){
        throw new UnsupportedOperationException("TODO - Implement this !");
    }

    public List<AppUser> getAllNonSalesmanUsers(){
        throw new UnsupportedOperationException("TODO - Implement this !");
    }

    public List<TravelDestination> getAllTravelDestiantions(){
        throw new UnsupportedOperationException("TODO - Implement this !");
    }

    public TravelDestination getTravelDestinationById(Integer id){
        throw new UnsupportedOperationException("TODO - Implement this !");
    }

    public List<Purchase> getAllPurchases(){
        throw new UnsupportedOperationException("TODO - Implement this !");
    }

    public Purchase getPurchaseById(Integer id){
        throw new UnsupportedOperationException("TODO - Implement this !");
    }

    public List<Purchase> getAllPurchasesFromUserId(Integer id){
        throw new UnsupportedOperationException("TODO - Implement this !");
    }

    public List<Traveler> getAllTravelers(){
        throw new UnsupportedOperationException("TODO - Implement this !");
    }

    public Traveler getTravelerById(Integer id){
        throw new UnsupportedOperationException("TODO - Implement this !");
    }

    public List<Traveler> getTravelersWithPurchaseId(Integer id){
        throw new UnsupportedOperationException("TODO - Implement this !");
    }

    private String getURLRequest(String request){
        return ApiConstants.serverPrefix + request + ApiConstants.returnFormat;
    }
}
