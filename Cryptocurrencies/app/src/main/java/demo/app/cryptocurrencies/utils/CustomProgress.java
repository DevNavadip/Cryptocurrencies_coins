package demo.app.cryptocurrencies.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import demo.app.cryptocurrencies.R;

public class CustomProgress {
    public static CustomProgress customProgress = null;
    public static String TAG = CustomProgress.class.getSimpleName();
    private Dialog mDialog;

    public static CustomProgress getInstance() {
        if (customProgress == null) {
            customProgress = new CustomProgress();
        }
        return customProgress;
    }

    public void showProgress(Context context, String text) {
        if (mDialog == null) {
            mDialog = new Dialog(context);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.layout_progress_bar_dialog);
            ProgressBar mProgressBar = mDialog.findViewById(R.id.progress_bar);
            TextView textView = mDialog.findViewById(R.id.textViewTitle);
            if (!text.isEmpty()) {
                textView.setVisibility(View.VISIBLE);
                textView.setText(text);
            } else {
                textView.setVisibility(View.GONE);
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                Drawable wrapDrawable = DrawableCompat.wrap(mProgressBar.getIndeterminateDrawable());
                DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(context, R.color.black_5));
                mProgressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
            } else {
                mProgressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.black_5), PorterDuff.Mode.SRC_IN);
            }


            mProgressBar.setVisibility(View.VISIBLE);
            // you can change or add this line according to your need
            mProgressBar.setIndeterminate(true);
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        }
    }

    public void hideProgress() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
