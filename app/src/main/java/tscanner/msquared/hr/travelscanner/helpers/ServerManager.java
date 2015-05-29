package tscanner.msquared.hr.travelscanner.helpers;

import com.google.gson.Gson;

import java.lang.reflect.Array;
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

    private final String TAG = this.getClass().getName();

    private GetRestService getRestService;
    private PostRestService postRestService;
    private PutRestService putRestService;
    private DeleteRestService deleteRestService;

    private Gson gson;

    public ServerManager() {
        this.gson = new Gson();
    }

    public List<AppUser> getAllAppUsers(){
        String response = null;
        if(this.getRestService == null){
            this.getRestService = new GetRestService(null);
        }
        this.getRestService.setUrl(ApiConstants.dohvatSvihUsera);
        try {
            response = this.getRestService.execute();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        if(response == null){
            return null;
        }

        AppUser[] appUsers = gson.fromJson(response, AppUser[].class);
        return Arrays.asList(appUsers);
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
}
