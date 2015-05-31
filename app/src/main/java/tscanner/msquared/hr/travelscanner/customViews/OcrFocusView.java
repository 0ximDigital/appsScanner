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

    private Rect leftMaskRect;
    private Rect topMaskRect;
    private Rect bottomMaskRect;
    private Rect rightMaskRect;

    private Paint paint;

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

    private void initMaskRects(Rect focusRect, Canvas canvas){
        this.leftMaskRect = new Rect(0, 0, focusRect.left, canvas.getHeight());
        this.topMaskRect = new Rect(focusRect.left, 0, focusRect.right, focusRect.top);
        this.bottomMaskRect = new Rect(focusRect.left, focusRect.bottom, focusRect.right, canvas.getHeight());
        this.rightMaskRect = new Rect(focusRect.right, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void setPaint(RectColor color){
        switch (color){
            default:
                this.paint.setColor(this.context.getResources().getColor(R.color.travelScanner_ocr_mask_gray));
        }
    }

    public Bitmap cropFocusedBitmap(Bitmap frame){
        int width = frame.getWidth();
        int height = frame.getHeight();
        Bitmap out = Bitmap.createBitmap(frame, (int)(width * 0.1), (int)(height * 0.6), (int)(width * 0.8), (int)(height * 0.3));
        return out;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(this.focusRect == null){
            this.focusRect = this.getFocusRect(canvas);
            this.initMaskRects(this.focusRect, canvas);
        }

        canvas.drawRect(leftMaskRect.left, leftMaskRect.top, leftMaskRect.right, leftMaskRect.bottom, paint);
        canvas.drawRect(topMaskRect.left, topMaskRect.top, topMaskRect.right, topMaskRect.bottom, paint);
        canvas.drawRect(bottomMaskRect.left, bottomMaskRect.top, bottomMaskRect.right, bottomMaskRect.bottom, paint);
        canvas.drawRect(rightMaskRect.left, rightMaskRect.top, rightMaskRect.right, rightMaskRect.bottom, paint);

    }

}
