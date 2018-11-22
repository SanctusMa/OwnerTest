package king.chengwu.com.chengwuapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import chengwu.com.chengwuapp.R;


public class DialogLoadingView extends Dialog {
    private Runnable runnable = null;

    public DialogLoadingView(Context context) {
        super(context, R.style.CommonLoadingDialogStyle);
        init(context);
    }

    public DialogLoadingView(Context context, Runnable runnable) {
        super(context, R.style.CommonLoadingDialogStyle);
        this.runnable = runnable;
        init(context);
    }

    public Runnable getRunnable() {
        return runnable;
    }

    private void init(final Context context) {
//        this.setCancelable(false);
        View dialog = LayoutInflater.from(context).inflate(R.layout.dialog_circle_loading, null);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(dialog);
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Binder binder;
}
