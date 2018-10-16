package uinbdg.skripsi.pesantren.Util;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.widget.NestedScrollView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Comp on 2/11/2018.
 */

public class Tools {
    public static int dpToPx(Context c, int dp) {
        Resources r = c.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static void nestedScrollTo(final NestedScrollView nested, final View targetView) {
        nested.post(new Runnable() {
            @Override
            public void run() {
                nested.scrollTo(500, targetView.getBottom());
            }
        });
    }

}
