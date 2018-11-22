package king.chengwu.com.chengwuapp.finger;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.view.View;

import chengwu.com.chengwuapp.R;
import king.chengwu.com.chengwuapp.ThApplication;
import king.chengwu.com.chengwuapp.ToastUtil;

/**
 * Created by shmyh on 2017/10/18.
 */

public class FingerPrintActivity extends Activity {
    FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(ThApplication.application);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger);
        findViewById(R.id.txt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFingerprintOrNot();
            }
        });
        findViewById(R.id.txt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerFingerprintOrNot();
            }
        });
        findViewById(R.id.txt3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secureOrNot();
            }
        });
    }

    /**
     * 手机是否支持指纹,官网支持
     */
    private void supportFingerprintOrNot() {
        if (!fingerprintManager.isHardwareDetected()) {
            // no fingerprint sensor is detected, show dialog to tell user.
            ToastUtil.showNormalToast("Your phone dont support 'FingerPrint'");
        } else {
            ToastUtil.showNormalToast("yeah , you can use this action !");
        }
    }

    //手机是否录入指纹
    private void registerFingerprintOrNot() {
        if (!fingerprintManager.hasEnrolledFingerprints()) {
            // no fingerprint image has been enrolled.
            ToastUtil.showNormalToast
                    ("Your phone dont have even one 'FingerPrint' record.");
        } else {
            ToastUtil.showNormalToast("Yeah, you have registed FingerPrint");
        }
    }

    //代码检查当前设备是不是处于安全保护中
    private void secureOrNot() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (keyguardManager.isKeyguardSecure()) {
                // this device is secure.
                ToastUtil.showNormalToast("Your phone is safe.");
            } else {
                ToastUtil.showNormalToast("Your phone isnot safe.");
            }
        }
    }
}
