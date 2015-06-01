package tscanner.msquared.hr.travelscanner.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import net.steamcrafted.loadtoast.LoadToast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.adapters.TravelDestinationsAdapter;
import tscanner.msquared.hr.travelscanner.helpers.PrefsHelper;
import tscanner.msquared.hr.travelscanner.helpers.Rest.ServerManager;
import tscanner.msquared.hr.travelscanner.models.restModels.AppUser;
import tscanner.msquared.hr.travelscanner.models.restModels.Purchase;
import tscanner.msquared.hr.travelscanner.models.restModels.ResponseMessage;
import tscanner.msquared.hr.travelscanner.models.restModels.TravelDestination;
import tscanner.msquared.hr.travelscanner.models.restModels.Traveler;

/**
 * Created by Matej on 5/31/2015.
 */
public class InfoFragment extends android.support.v4.app.Fragment {


    public static final String TITLE = "User info";

    private TextView userTravelPoints;
    private TextView userNumberOfTravels;
    private TextView userNumberOfTravelers;
    private RecyclerView recyclerView;

    private ServerManager serverManager;
    private PrefsHelper prefsHelper;
    private Gson gson;
    private AppUser appUser;

    private LoadToast loadToast;

    private Purchase[] usersPurchases;
    private List<Traveler> usersTravelers;
    private List<TravelDestination> usersDestinations;

    public static InfoFragment newInstance(){
        return new InfoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v=inflater.inflate(R.layout.fragment_info, container, false);
        this.recyclerView = (RecyclerView)v.findViewById(R.id.recycler);
        this.recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        this.recyclerView.setLayoutManager(manager);

        this.loadToast = new LoadToast(getActivity());
        this.loadToast.setTranslationY(300);

        this.userTravelPoints = (TextView) v.findViewById(R.id.txtTravelerPoints);
        this.userNumberOfTravels = (TextView) v.findViewById(R.id.txtTravelsNumber);
        this.userNumberOfTravelers = (TextView) v.findViewById(R.id.txtTravelersNumber);

        usersTravelers = new ArrayList<Traveler>();
        usersDestinations = new ArrayList<TravelDestination>();

        this.prefsHelper = new PrefsHelper(getActivity());
        this.gson = new Gson();

        this.populateUserData();
        return v;
    }

    private void populateUserData(){
        String appUserData = this.prefsHelper.getString(PrefsHelper.LOGGED_IN_USER_APPUSER_DATA, null);
        if(appUserData != null){
            this.appUser = gson.fromJson(appUserData, AppUser.class);
            if(this.appUser != null){
                this.userTravelPoints.setText("" + this.appUser.getTravelPoints());
            }
            crunchUserData();
        }
    }

    private void crunchUserData(){
        fetchPurchasesMadeByUserWithId(this.appUser.getId());
    }

    private void fetchPurchasesMadeByUserWithId(int id){
        if(serverManager == null){
            serverManager = new ServerManager();
        }
        serverManager.getAllPurchasesFromUserId(id, new ServerManager.Callback<List<Purchase>>() {
            @Override
            public void requestResult(List<Purchase> purchases) {
                if (purchases != null) {
                    usersPurchases = purchases.toArray(new Purchase[purchases.size()]);
                    parsePurchaseAtIndex(-1);
                } else {
                    loadToast.error();
                }
            }
        });
    }

    private void parsePurchaseAtIndex(int i){
        i++;
        if(this.usersPurchases.length > i){
            fetchDestinationById(this.usersPurchases[i].getDestinationId(), i);
        }
        else{
            this.userNumberOfTravels.setText("" + this.usersPurchases.length);
            this.userNumberOfTravelers.setText("" + this.usersTravelers.size());
            recyclerView.setAdapter(new TravelDestinationsAdapter(getActivity(), this.usersDestinations.toArray(new TravelDestination[usersDestinations.size()])));
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    private void fetchDestinationById(int id,final int depth){
        if(serverManager == null){
            serverManager = new ServerManager();
        }
        serverManager.getTravelDestinationById(id, new ServerManager.Callback<TravelDestination>() {
            @Override
            public void requestResult(TravelDestination travelDestination) {
                if (travelDestination != null) {
                    usersDestinations.add(travelDestination);

                } else {
                }
                fetchTravelersWitchPurchaseId(usersPurchases[depth].getId(), depth);
            }
        });
    }

    private void fetchTravelersWitchPurchaseId(int id, final int depth){
        if(serverManager == null){
            serverManager = new ServerManager();
        }
        serverManager.getTravelersWithPurchaseId(id, new ServerManager.Callback<List<Traveler>>() {
            @Override
            public void requestResult(List<Traveler> travelers) {
                if (travelers != null) {
                    usersTravelers.addAll(travelers);
                } else {
                }
                parsePurchaseAtIndex(depth);
            }
        });
    }

}

