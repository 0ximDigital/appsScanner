package tscanner.msquared.hr.travelscanner.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

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


public class ScanActivity extends Activity {

    private TextView resultTextView;
    private OcrCameraView ocrCameraView;
    private OcrFocusView ocrFocusView;

    private FrameLayout rootFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        this.rootFrameLayout = (FrameLayout) findViewById(R.id.rootFrameLayout);

        this.resultTextView = (TextView) findViewById(R.id.txtResult);
        this.ocrCameraView = (OcrCameraView) findViewById(R.id.ocrView);

        this.ocrFocusView = (OcrFocusView) findViewById(R.id.ocrFocusView);

        this.ocrCameraView.setOcrFocusView(this.ocrFocusView);
        this.ocrCameraView.setParent(this.rootFrameLayout);
    }
}
