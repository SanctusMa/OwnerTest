package king.chengwu.com.chengwuapp.finger.yeah;

import android.os.Handler;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.Log;

/**
 * Created by baniel on 7/21/16.
 */
public class MyAuthCallback extends FingerprintManagerCompat.AuthenticationCallback {

    private Handler handler = null;

    public MyAuthCallback(Handler handler) {
        super();

        this.handler = handler;
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        super.onAuthenticationError(errMsgId, errString);
        Log.i("Error", "errMsgId:" + errMsgId + "," + errString.toString());

        if (handler != null) {
            handler.obtainMessage(MainActivity.MSG_AUTH_ERROR, errMsgId, 0).sendToTarget();
        }
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        super.onAuthenticationHelp(helpMsgId, helpString);
        Log.e("Help", "helpMsgId:" + helpMsgId + "," + helpString.toString());
        if (handler != null) {
            handler.obtainMessage(MainActivity.MSG_AUTH_HELP, helpMsgId, 0).sendToTarget();
        }
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);

        if (handler != null) {
            handler.obtainMessage(MainActivity.MSG_AUTH_SUCCESS).sendToTarget();
        }
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        Log.d("Fail", "failed");

        if (handler != null) {
            handler.obtainMessage(MainActivity.MSG_AUTH_FAILED).sendToTarget();
        }
    }
}
