package co.dust.smspasuruan.view;

/**
 * Created by irsal on 5/18/17.
 */

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.Calendar;

public class MyBetterSpinner extends AppCompatAutoCompleteTextView implements AdapterView.OnItemClickListener {

    private static final int MAX_CLICK_DURATION = 200;
    private long startClickTime;
    private boolean isPopup;

    public MyBetterSpinner(Context context) {
        super(context);
        this.setOnItemClickListener(this);
    }

    public MyBetterSpinner(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        this.setOnItemClickListener(this);
    }

    public MyBetterSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
        this.setOnItemClickListener(this);
    }

    public boolean enoughToFilter() {
        return true;
    }

    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        // terkadang disini error, untuk beberapa hp, lenovo, dkk
        try {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);

            if (focused) {
                this.performFiltering("", 0);
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//            InputMethodManager imm = (InputMethodManager) this.getContext().getSystemService("input_method");
                imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
                this.setKeyListener(null);
                this.dismissDropDown();
            } else {
                this.isPopup = false;
            }
        } catch (Exception ignored) {

        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.isEnabled()) {
            return false;
        } else {
            switch (event.getAction()) {
                case 0:
                    this.startClickTime = Calendar.getInstance().getTimeInMillis();
                    break;
                case 1:
                    long clickDuration = Calendar.getInstance().getTimeInMillis() - this.startClickTime;
                    if (clickDuration < 200L) {
                        if (this.isPopup) {
                            this.dismissDropDown();
                            this.isPopup = false;
                        } else {
                            this.requestFocus();
                            this.showDropDown();
                            this.isPopup = true;
                        }
                    }
            }

            return super.onTouchEvent(event);
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        this.isPopup = false;
    }

    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        Drawable dropdownIcon = new IconicsDrawable(this.getContext())
                .icon(GoogleMaterial.Icon.gmd_expand_more)
                .sizeDp(10);
//        Drawable dropdownIcon = ContextCompat.getDrawable(this.getContext(), R.drawable.ic_expand_more_black_18dp);

        if (dropdownIcon != null) {
            right = dropdownIcon;
            dropdownIcon.mutate().setAlpha(128);
        }

        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }
}

