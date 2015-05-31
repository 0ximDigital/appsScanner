package tscanner.msquared.hr.travelscanner.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.steamcrafted.loadtoast.LoadToast;

/**
 * Created by Matej on 5/31/2015.
 */
public class InfoFragment extends android.support.v4.app.Fragment {


    public static final String TITLE = "User info";

    public static InfoFragment newInstance(){
        return new InfoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




}

