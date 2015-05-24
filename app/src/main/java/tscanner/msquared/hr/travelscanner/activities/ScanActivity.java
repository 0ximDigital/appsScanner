package tscanner.msquared.hr.travelscanner.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import tscanner.msquared.hr.travelscanner.R;


public class ScanActivity extends Activity {

    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        this.resultTextView = (TextView) findViewById(R.id.txtResult);

        this.resultTextView.setText(this.detectText(BitmapFactory.decodeResource(getResources(), R.drawable.test_numbers)));
    }

    public String detectText(Bitmap bitmap) {

        //TessDataManager.initTessTrainedData(this);
        TessBaseAPI tessBaseAPI = new TessBaseAPI();

        try {
            this.copyTestDataFromAssets();
        }
        catch (Exception e){
            e.printStackTrace();
            Log.i("FAIL", "well.. f*** it");
        }

        String path = getFilesDir() + "/";

        tessBaseAPI.setDebug(true);
        tessBaseAPI.init(path, "eng"); //Init the Tess with the trained data file, with english language

        //For example if we want to only detect numbers
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "1234567890");
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&*()_+=-qwertyuiop[]}{POIU" +
                "YTREWQasdASDfghFGHjklJKLl;L:'\"\\|~`xcvXCVbnmBNM,./<>?");


        tessBaseAPI.setImage(bitmap);

        Log.i("TIME", "1");
        String result = tessBaseAPI.getUTF8Text();
        Log.i("TIME", "2");

        Log.d("SCANNING", "Got data: " + result);
        tessBaseAPI.end();

        return result;
    }

    private void copyTestDataFromAssets() throws IOException {
        InputStream stream = null;
        OutputStream output = null;

        File internalTessDataDir = new File(getFilesDir() + "/tessdata");
        if(internalTessDataDir.exists()){
            Log.i("TESS", "tessdata folder already exists");
            return;
        }

        internalTessDataDir.mkdir();

        Log.i("TESS", "copying tessdata files");
        for(String fileName : this.getAssets().list("tessdata"))
        {
            stream = this.getAssets().open("tessdata/" + fileName);
            output = new BufferedOutputStream(new FileOutputStream(getFilesDir() + "/tessdata/" + fileName));

            byte data[] = new byte[1024];
            int count;

            while((count = stream.read(data)) != -1)
            {
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            stream.close();

            stream = null;
            output = null;
        }
    }
}
