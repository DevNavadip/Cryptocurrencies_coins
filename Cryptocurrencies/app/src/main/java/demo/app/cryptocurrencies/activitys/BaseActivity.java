package demo.app.cryptocurrencies.activitys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

import demo.app.cryptocurrencies.utils.CustomProgress;

public abstract class BaseActivity extends AppCompatActivity {
    public CustomProgress customProgress;
    // for double click
    public double lastClickTime = 0;
    private Activity activity;

    public static final String CLOSE_TAG = "closeIcon";
    public static final String ARROW_TAG = "arrowIcon";

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    //set for image select
    // for image uploading functionality using bottomSheetDialog
    private BottomSheetDialog bottomSheetDialog;

    // for user profile upload add
    private int REQUEST_CAMERA = 198;
    private int SELECT_FILE = 199;
    private String selectedFilePath;

    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri; // file url to store image/video
    private String imagepath = "";
    private int imageCount = 0;
    private AppCompatActivity activityContext = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customProgress = CustomProgress.getInstance();
    }

    public void nextActivity(Class clas) {
        if (activity != null) {
            Intent intent = new Intent(activity, clas);
            startActivity(intent);
        }
    }

    public void nextActivityFinish(Class clas) {
        if (activity != null) {
            Intent intent = new Intent(activity, clas);
            startActivity(intent);
            activity.finish();
        }
    }

    public void nextActivityFinishWithClearAllTask(Class clas) {
        if (activity != null) {
            Intent intent = new Intent(activity, clas);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    public void nextActivityFinish(Class clas, String keyValue) {
        if (activity != null) {
            Intent intent = new Intent(activity, clas);
            intent.putExtra(keyValue, keyValue);
            startActivity(intent);
            finish();
        }
    }

    public void nextActivityFinish(Class clas, String key, Bundle keyValue) {
        if (activity != null) {
            Intent intent = new Intent(activity, clas);
            intent.putExtra(key, keyValue);
            startActivity(intent);
            finish();
        }
    }

    /**
     * @param isFull true if full screen
     */
    public void setFullScreen(Boolean isFull) {
        if (isFull) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * @param activity set context
     */
    public void setActivityContext(Activity activity) {
        this.activity = activity;
    }

    /**
     * show progress dialog
     */
    public void showProgress() {
        if (activity != null && !activity.isFinishing()) {
            if (customProgress != null) {
                customProgress.showProgress(activity, "");
            } else {
                customProgress = CustomProgress.getInstance();
                customProgress.showProgress(activity, "");
            }
        }
    }

    /**
     * @param text set progress text
     */
    public void showProgress(String text) {
        if (activity != null && !activity.isFinishing()) {
            if (customProgress != null) {
                customProgress.showProgress(activity, text);
            } else {
                customProgress = CustomProgress.getInstance();
                customProgress.showProgress(activity, text);
            }
        }
    }

    /**
     * hide progress dialog
     */
    public void hideProgress() {
        if (customProgress != null && activity != null && !activity.isFinishing()) {
            customProgress.hideProgress();
        } else {
            customProgress = CustomProgress.getInstance();
            customProgress.hideProgress();
        }
    }

    /**
     * check internet
     *
     * @return
     */
    public boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * @param message make toast
     */
    public void makeToast(String message) {
        if (activity != null) {
            Toast mToast;
            try {
                mToast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
                mToast.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * hide keyboard
     */
    public void hideKeyboard() {
        if (activity != null) {
            View view = activity.getCurrentFocus();
            if (view != null) {
                view.clearFocus();
                InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @SuppressLint("HardwareIds")
    public String getDeviceId() {
        if (activity != null) {
            return Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return "";
    }

    /**
     * @return get device name
     */
    public String getDeviceModel() {
        return Build.MODEL;

    }

    /**
     * @return get device manufacturer
     */
    public String getDeviceManufacture() {
        return Build.MANUFACTURER;
    }

    /**
     * @return get app version
     */
    public String getAppVersion() {
        if (activity != null) {
            PackageManager manager = activity.getPackageManager();
            PackageInfo info = null;
            try {
                info = manager.getPackageInfo(
                        activity.getPackageName(), 0);
                return info.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public String getString(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * set visibility gone
     */
    public void setVisibilityGoneAnim(final View visibleGone, int height) {
        collapse(visibleGone, height);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                visibleGone.setVisibility(View.GONE);
//            }
//        }, 350);
    }

    /**
     * set visibility visible
     */
    public void setVisibleAnim(View visible, int height) {
//        visible.setVisibility(View.VISIBLE);
        expand(visible, height);
    }

    private void expand(final View v, int height) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT,
                (height));
        final int targetHeight = (height);

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private void collapse(final View v, int height) {
//        final int initialHeight = v.getMeasuredHeight();
        final int initialHeight = height;

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public String checkNull(String data) {
        if (data == null) {
            return "";
        }
        return data;
    }

    public String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date());
    }
}
