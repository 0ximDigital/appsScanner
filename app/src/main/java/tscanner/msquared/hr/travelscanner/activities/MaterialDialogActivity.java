package tscanner.msquared.hr.travelscanner.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;
import tscanner.msquared.hr.travelscanner.R;


/**
 * Created by Matej on 5/29/2015.
 */
public class MaterialDialogActivity extends Activity {
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_dialog_layout);

        /*lv=(ListView)findViewById(R.id.UserlistView);
        List<String> travelingList =new ArrayList<String>();
        travelingList.add("Mitar Miric");
        travelingList.add("Rade Lackovic");
        travelingList.add("Tanja Savic");

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.material_dialog_layout,R.id.textView,travelingList);
        lv.setAdapter(arrayAdapter);*/
        MaterialDialogStart();

    }



    public void MaterialDialogStart(){
        final MaterialDialog mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setTitle("Sudionici putovanja");

       /* LayoutInflater li=(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=li.inflate(R.layout.material_dialog_layout,null,false);
       */



        mMaterialDialog.setMessage("Popis sudionika!");
        mMaterialDialog.setPositiveButton("Nastavite kupnju", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();

            }
        });
        mMaterialDialog.setNegativeButton("Prekid", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();

            }
        });

        mMaterialDialog.show();
    }





}
