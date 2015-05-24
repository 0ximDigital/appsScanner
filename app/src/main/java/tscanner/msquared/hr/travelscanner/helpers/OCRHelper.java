package tscanner.msquared.hr.travelscanner.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Mihael on 24.5.2015..
 */
public class OCRHelper {

    private Context context;

    public OCRHelper(Context context) {
        this.context = context;
        try {
            this.copyTestDataFromAssets();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String detectText(Bitmap bitmap) {

        TessBaseAPI tessBaseAPI = new TessBaseAPI();

        String path = this.context.getFilesDir() + "/";

        tessBaseAPI.setDebug(true);
        tessBaseAPI.init(path, "eng"); //Init the Tess with the trained data file, with english language

        //For example if we want to only detect numbers
        /*
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "1234567890");
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&*()_+=-qwertyuiop[]}{POIU" +
                "YTREWQasdASDfghFGHjklJKLl;L:'\"\\|~`xcvXCVbnmBNM,./<>?");
        */
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "1234567890qwertzuiopasdfghjklyxcvbnm<QWERTZUIOPASDFGHJKLYXCVBNM");

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

        File internalTessDataDir = new File(context.getFilesDir() + "/tessdata");
        if(internalTessDataDir.exists()){
            Log.i("TESS", "tessdata folder already exists");
            return;
        }

        internalTessDataDir.mkdir();

        Log.i("TESS", "copying tessdata files");
        for(String fileName : this.context.getAssets().list("tessdata"))
        {
            stream = this.context.getAssets().open("tessdata/" + fileName);
            output = new BufferedOutputStream(new FileOutputStream(this.context.getFilesDir() + "/tessdata/" + fileName));

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
