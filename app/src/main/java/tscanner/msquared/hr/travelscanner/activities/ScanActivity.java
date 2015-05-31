package tscanner.msquared.hr.travelscanner.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.customViews.OcrCameraView;
import tscanner.msquared.hr.travelscanner.customViews.OcrFocusView;
import tscanner.msquared.hr.travelscanner.models.TravelerDataValues;


public class ScanActivity extends Activity {

    private OcrCameraView ocrCameraView;
    private OcrFocusView ocrFocusView;

    private FrameLayout rootFrameLayout;

    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        this.rootFrameLayout = (FrameLayout) findViewById(R.id.rootFrameLayout);

        this.ocrCameraView = (OcrCameraView) findViewById(R.id.ocrView);

        this.ocrFocusView = (OcrFocusView) findViewById(R.id.ocrFocusView);

        this.ocrCameraView.setOcrFocusView(this.ocrFocusView);
        this.ocrCameraView.setParent(this.rootFrameLayout);
        this.ocrCameraView.setCallback(new OcrCameraView.OcrViewDataCallback() {
            @Override
            public void onTravelerData(TravelerDataValues values) {
                Intent returnTravelerDataIntent = new Intent();
                returnTravelerDataIntent.putExtra(AddDestinationTravelersActivity.NEW_TRAVELER_SERIALIZED_DATA, gson.toJson(values));
                setResult(RESULT_OK, returnTravelerDataIntent);
                finish();
            }
        });
    }
}
