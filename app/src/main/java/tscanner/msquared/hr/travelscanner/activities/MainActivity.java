package tscanner.msquared.hr.travelscanner.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.Collections;
import java.util.List;

import tscanner.msquared.hr.travelscanner.InternetConnectionCheck;
import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.adapters.TravelDestinationsAdapter;
import tscanner.msquared.hr.travelscanner.helpers.Rest.ServerManager;
import tscanner.msquared.hr.travelscanner.models.restModels.TravelDestination;

public class MainActivity extends Activity {

    private final String TAG = this.getClass().getSimpleName();

    private Button goToDestInfo;

    private RecyclerView recyclerView;

    private ServerManager serverManager;
    private LoadToast loadToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InternetConnectionCheck check = new InternetConnectionCheck(this);
        check.setCheckCallback(new InternetConnectionCheck.OnCheckCallback() {
            @Override
            public void onCheck(boolean hasConnection) {
                referenceViews();
                fetchAllTravelDestinations();
            }
        });
        check.checkConnection();
    }

    private void referenceViews(){
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler);
        this.recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(manager);

        this.goToDestInfo = (Button)findViewById(R.id.button2);

        goToDestInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DestinationInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchAllTravelDestinations(){
        if(serverManager == null){
            serverManager = new ServerManager();
        }
        if(loadToast == null){
            loadToast = new LoadToast(this);
            loadToast.setTranslationY(200);
        }
        loadToast.setText("Fetching destinations..").show();
        serverManager.getAllTravelDestinations(new ServerManager.Callback<TravelDestination[]>() {
            @Override
            public void requestResult(TravelDestination[] travelDestinations) {
                if (travelDestinations != null) {
                    recyclerView.setAdapter(new TravelDestinationsAdapter(MainActivity.this, travelDestinations));
                    recyclerView.getAdapter().notifyDataSetChanged();
                    loadToast.success();
                } else {
                    loadToast.error();
                }
            }
        });
    }

}
