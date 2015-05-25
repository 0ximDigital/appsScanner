package tscanner.msquared.hr.travelscanner.customViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import tscanner.msquared.hr.travelscanner.R;

/**
 * Created by Mihael on 25.5.2015..
 */
public class OcrFocusView extends View {

    private final String TAG = this.getClass().getName();
    private Context context;

    private int maskColor;
    private int frameColor;

    private int topLeftX;
    private int topLeftY;

    private Rect focusRect;
    private Paint paint;

    private ImageView previewImage;

    public enum RectColor {
        GREENISH,
        REEDISH,
        GRAYISH
    }

    public OcrFocusView(Context context) {
        super(context);
        this.initView(context);
    }

    public OcrFocusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    public OcrFocusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
    }

    private void initView(Context context){
        this.context = context;
        paint = new Paint();
        paint.setColor(context.getResources().getColor(R.color.travelScanner_ocr_mask_gray));
    }

    private Rect getFocusRect(Canvas canvas){
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        int left, right, top, bottom;

        this.topLeftX = left = (int) (width * 0.1);
        right = (int) (width * 0.9);
        this.topLeftY = top = (int) (height * 0.6);
        bottom = (int) (height * 0.9);

        return new Rect(left, top, right, bottom);
    }

    public void setPaint(RectColor color){      // TODO
        switch (color){
            default:
                this.paint.setColor(this.context.getResources().getColor(R.color.travelScanner_ocr_mask_gray));
        }
    }

    public Bitmap cropFocusedBitmap(Bitmap frame){
        Log.i(TAG, "Cropam");
        int width = frame.getWidth();
        int height = frame.getHeight();
        Bitmap out = Bitmap.createBitmap(frame, (int)(width * 0.1), (int)(height * 0.6), (int)(width * 0.8), (int)(height * 0.3));
        if(this.previewImage != null){
            this.previewImage.setImageBitmap(out);
        }
        return out;
    }

    public void setPreviewImageView(ImageView imageView){
        this.previewImage = imageView;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        this.focusRect = (this.focusRect == null) ? this.getFocusRect(canvas) : this.focusRect;
        canvas.drawRect(focusRect.left, focusRect.top, focusRect.right, focusRect.bottom, paint);

    }

}
