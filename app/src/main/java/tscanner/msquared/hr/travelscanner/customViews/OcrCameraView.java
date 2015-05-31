package tscanner.msquared.hr.travelscanner.customViews;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ToggleButton;
import android.widget.VideoView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.helpers.OCRHelper;

/**
 * Created by Mihael on 24.5.2015..
 */
public class OcrCameraView extends FrameLayout implements SurfaceHolder.Callback, Camera.AutoFocusCallback{

    private final String TAG = this.getClass().getName();
    private Context context;

    private VideoView videoView;
    private SurfaceHolder surfaceHolder;
    private Camera camera;

    private Camera.PreviewCallback cameraPreviewCallback = null;
    private Camera.Size optimalPreviewSize = null;
    private Camera.Parameters camParams;

    private ImageView flashButton;
    private ImageView focusButton;
    private ImageView scanButton;

    private boolean scanImage;

    private boolean hasCamera = false;
    private int cameraId;

    private boolean isFlashSupported;
    private boolean isFlashOn;

    private Bitmap frameInProcessing;
    private YuvImage yuvimage;

    private OCRHelper ocrHelper;

    private String decodedImageText;

    private OcrFocusView ocrFocusView;

    private MediaPlayer mediaPlayer;

    private OcrTextListener ocrTextListener;
    public interface OcrTextListener{
        void onDecodedText(String text);
    }

    public void setOcrTextListener(OcrTextListener ocrTextListener) {
        this.ocrTextListener = ocrTextListener;
    }

    public OcrCameraView(Context context) {
        super(context);
        this.initView(context);
    }

    public OcrCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    public OcrCameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
    }

    private void initView(Context context){
        this.context = context;
        inflate(this.context, R.layout.view_ocr_camera, this);
        this.referenceViews();
    }

    private void referenceViews(){
        this.mediaPlayer = MediaPlayer.create(this.context, R.raw.camera_shutter_click_effect);

        this.videoView = (VideoView) findViewById(R.id.videoView);
        this.flashButton = (ImageView) findViewById(R.id.btnFlash);

        this.flashButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFlashOn) {
                    turnFlashOff();
                    flashButton.setImageResource(R.drawable.ic_traveler_flash_off_icon);
                } else {
                    turnFlashOn();
                    flashButton.setImageResource(R.drawable.ic_traveler_flash_on_icon);
                }
            }
        });

        this.focusButton = (ImageView) findViewById(R.id.btnFocus);
        this.focusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.autoFocus(OcrCameraView.this);
            }
        });

        this.scanButton = (ImageView) findViewById(R.id.btnScan);
        this.scanButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                scanButton.setVisibility(INVISIBLE);
                scanImage = true;
            }
        });

        isFlashSupported = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        cameraPreviewCallback = new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] bytes, Camera camera) {
                if(scanImage){
                    scanButton.setVisibility(INVISIBLE);
                    scanImage = false;
                    Camera.Parameters parameters = camera.getParameters();

                    int width = optimalPreviewSize.width;
                    int height = optimalPreviewSize.height;

                    ByteArrayOutputStream outstr = new ByteArrayOutputStream();
                    Rect rect = new Rect(0, 0, width, height);
                    yuvimage = new YuvImage(bytes, ImageFormat.NV21, width, height, null);
                    yuvimage.compressToJpeg(rect, 100, outstr);
                    frameInProcessing = BitmapFactory.decodeByteArray(outstr.toByteArray(), 0, outstr.size());


                    decodedImageText = ocrHelper.detectText((ocrFocusView == null) ? frameInProcessing : ocrFocusView.cropFocusedBitmap(frameInProcessing));

                    if(ocrTextListener != null){
                        ocrTextListener.onDecodedText(decodedImageText);
                    }

                    scanButton.setVisibility(VISIBLE);
                }
            }
        };

        this.ocrHelper = new OCRHelper(this.context);

        PackageManager pm = context.getPackageManager();
        hasCamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
        if(hasCamera){
            cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
            initCamera();
        }
        else{
            // TODO prikaz odmah forme za upis podataka
        }
    }

    private void initCamera(){
        camera = Camera.open(cameraId);
        if(camera == null){
            Log.e(TAG, "Camera is null");
        }

        camParams = camera.getParameters();
        optimalPreviewSize = getOptimalSize(camParams.getSupportedPreviewSizes());

        if(optimalPreviewSize != null) {
            Log.i(TAG, "Setting optimal preview size");
            camParams.setPreviewSize(optimalPreviewSize.width, optimalPreviewSize.height);
        }

        if(isFlashOn){
            camParams.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        }
        else{
            camParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        }

        if (camParams.getSupportedFocusModes().contains(
                Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
            Log.i(TAG, "FOCUS_MODE_CONTINUOUS_VIDEO is supported");
            camParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            this.focusButton.setVisibility(GONE);
        }
        else if(camParams.getSupportedFocusModes().contains(
                Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)){
            Log.i(TAG, "FOCUS_MODE_CONTINUOUS_PICTURE is supported");
            camParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            this.focusButton.setVisibility(GONE);
        }
        else if(camParams.getSupportedFocusModes().contains(
                Camera.Parameters.FOCUS_MODE_AUTO)){
            Log.i(TAG, "FOCUS_MODE_AUTO is supported");
            camParams.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            this.focusButton.setVisibility(VISIBLE);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            camParams.setRecordingHint(true);
            Log.i(TAG, "Set recording hint true");
        }

        try {
            camera.setParameters(camParams);
        }
        catch (RuntimeException e){
            Log.e(TAG, "Failed to setup PREVIEW camera parameters, trying to recover");
            camera.setParameters(camera.getParameters());
        }

        camera.lock();

        surfaceHolder = videoView.getHolder();
        surfaceHolder.addCallback(this);

        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);

        try{
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            Log.i(TAG, "Preview set in initCamera");
        }
        catch (Exception e){
            Log.e(TAG, "Preview error");
            e.printStackTrace();
        }

        camera.setPreviewCallback(cameraPreviewCallback);
        Log.i(TAG, "Set camera preview callback");

    }

    public void setOcrFocusView(OcrFocusView focusView){
        this.ocrFocusView = focusView;
    }

    public void turnFlashOn() throws UnsupportedOperationException{
        if(! isFlashSupported){
            throw new UnsupportedOperationException("This device does not support camera flash");
        }
        else {
            camParams = camera.getParameters();
            camParams.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(camParams);
            isFlashOn = true;
        }
    }

    public void turnFlashOff() throws UnsupportedOperationException{
        if(! isFlashSupported){
            throw new UnsupportedOperationException("This device does not support camera flash");
        }
        else {
            camParams = camera.getParameters();
            camParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(camParams);
            isFlashOn = false;
        }
    }

    @Override
    public void onAutoFocus(boolean b, Camera camera) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try{
            camera.setPreviewDisplay(this.surfaceHolder);
            camera.startPreview();
        }
        catch (Exception e){
            Log.e(TAG, "Camera preview failed");
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.i(TAG, "Surface Destroyed");
        releaseCamera();
    }

    private void releaseCamera(){
        if(camera != null){
            try{
                camera.reconnect();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            camera.stopPreview();
            camera.setPreviewCallback(null);
            Log.i(TAG, "Unset camera preview callback");
            camera.release();
            camera = null;
        }
    }

    private Camera.Size getOptimalSize(List<Camera.Size> sizes) {

        int width, height;
        int maxWidth = 1280;  // -> 720p

        final double ASPECT_TOLERANCE = 0.2;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        if (android.os.Build.VERSION.SDK_INT >= 13){
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height = size.y;
        }
        else{
            width = display.getWidth();  // deprecated
            height = display.getHeight();
        }

        double targetRatio = (double) width / height;

        if (sizes == null) {
            return null;
        }

        if(width > maxWidth+20){
            int ratio = getClosestSupportedRatio(targetRatio);
            switch(ratio){
                case 1:
                    width = 1024;
                    height = 768;
                    break;
                case 2:
                    width = 1280;
                    height = 800;
                    break;
                case 3:
                    width = 1280;
                    height = 768;
                    break;
                case 4:
                    width = 1280;
                    height = 720;
                    break;
            }
        }

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;
        int targetHeight = height;
        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes)
        {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;
            if (Math.abs(size.height - targetHeight) < minDiff)
            {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null)
        {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff)
                {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        if (optimalSize != null) {
            Log.d("CameraActivity", "Using size: " + optimalSize.width + "w " + optimalSize.height + "h");
        }
        else{
            Log.e("CameraActivity", "optimalSize returned is null !");
        }
        return optimalSize;
    }

    private int getClosestSupportedRatio(double targetRatio){
        /*Return values:
            1 - 1.33
            2 - 1.6
            3 - 1.66
            4 - 1.77
         */
        double[] ratios = {1.33, 1.6, 1.66, 1.77};
        double minDiff = Double.MAX_VALUE;
        int retValue = 3;
        int i;
        for(i=0; i<ratios.length; i++){
            if(Math.abs(targetRatio - ratios[i]) < minDiff){
                minDiff = Math.abs(targetRatio - ratios[i]);
                retValue = i;
            }
        }
        return retValue+1;
    }
}
