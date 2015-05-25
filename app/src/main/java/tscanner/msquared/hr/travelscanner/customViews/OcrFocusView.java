package tscanner.msquared.hr.travelscanner.customViews;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Mihael on 25.5.2015..
 */
public class OcrFocusView extends View {

    private final String TAG = this.getClass().getName();

    public OcrFocusView(Context context) {
        super(context);
    }

    public OcrFocusView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OcrFocusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
