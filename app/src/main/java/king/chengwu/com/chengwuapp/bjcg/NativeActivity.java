package king.chengwu.com.chengwuapp.bjcg;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import chengwu.com.chengwuapp.R;
import cn.passguard.PassGuardEdit;
import cn.passguard.doAction;

public class NativeActivity extends Activity implements OnClickListener {
    private PassGuardEdit edit1 = null;
    private PassGuardEdit edit2 = null;
    private Button toWeb = null;
    private TextView tvShow = null;
    private Button clear = null;
    private Button getLenth = null;
    private Button getMd5 = null;

    private Button getAESCiphertext = null;
    private Button getRSAAESCiphertext = null;

    private Button getSM2Ciphertext = null;
    private Button getSM3Ciphertext = null;
    private Button getSM4Ciphertext = null;


    private Builder mAlert = null;

    static {
        System.loadLibrary("PassGuard");
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);
        initView();
        initPassGuard();
        addListener();
        final WebView webView = findViewById(R.id.webview);
        webView.loadUrl("https://vip.trc.com/trc_app/index");
        webView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        });
    }

    private void initView() {
        edit1 = (PassGuardEdit) findViewById(R.id.passguardedit1);
        edit2 = (PassGuardEdit) findViewById(R.id.passguardedit2);
        toWeb = (Button) findViewById(R.id.native_to_web);

        tvShow = (TextView) findViewById(R.id.tv_show);

        clear = (Button) findViewById(R.id.clear);
        getLenth = (Button) findViewById(R.id.btn_getlength);
        getMd5 = (Button) findViewById(R.id.btn_getmd5);

        getAESCiphertext = (Button) findViewById(R.id.btn_aes_ciphertext);
        getRSAAESCiphertext = (Button) findViewById(R.id.btn_rsaaes_ciphertext);

        getSM2Ciphertext = (Button) findViewById(R.id.btn_sm2_ciphertext);
        getSM3Ciphertext = (Button) findViewById(R.id.btn_sm3_ciphertext);
        getSM4Ciphertext = (Button) findViewById(R.id.btn_sm4_ciphertext);
    }

    private void initPassGuard() {
        PassGuardEdit
                //		.setLicense("WTlaQ3hIeFB4ektha1lEZEhPK0VSb1kySjF0UkhjK0c4b0cwSnpiRDYzNVY4Nm1rZkQrZ1dnbkFEQ2F6aDE4ZUQyWUxNYzVjU2YydG1UVGxiUmhIVzZmL21MVGJ4a0RaRldnUzRoSXB3L2RCY1RUZWptcW5NWTJ5dzBha3ZLQ0UzR0p0ekgvYU9vamkydzcwUzY0NHBCMng1WWU0RCtRdkpTc2xDa1FsTllZPXsiaWQiOjAsInR5cGUiOiJwcm9kdWN0IiwicGFja2FnZSI6WyJjbi5wYXNzZ3VhcmQiXSwiYXBwbHluYW1lIjpbIuWvhueggeaOp+S7tiJdLCJwbGF0Zm9ybSI6Mn0=");
                .setLicense("TDgvVUVnWHFHUUl5aS9NRUowWE1hZ0RHczZBQ1o0NmtrWXhiYlVNenFZcTlydHBhRnFPbXBCSm5oYzh0TGplbXJkK0t5WEx1QnB2N095ZUdaN0ZNRWx5cjQ2bE5wZEtyMFFIc3J5bHFxc1RFTnV2dTkvanlSNnVzQzlWTXc1VkYwVFhjcHJ6OTlDWWFwVFhRWkpkRUk0TXlwaXJ5a1pPVUVvbDdTOWNLb2xJPXsiaWQiOjAsInR5cGUiOiJ0ZXN0IiwicGxhdGZvcm0iOjIsIm5vdGJlZm9yZSI6IjIwMTgwMTE2Iiwibm90YWZ0ZXIiOiIyMDE4MDQxNiJ9");
        edit1.setEncrypt(false);
        edit1.useNumberPad(true);
        edit2.setCipherKey("abcdefghijklmnopqrstuvwxyz123456");
        edit2.setPublicKey("3081890281810092d9d8d04fb5f8ef9b8374f21690fd46fdbf49b40eeccdf416b4e2ac2044b0cfe3bd67eb4416b26fd18c9d3833770a526fd1ab66a83ed969af74238d6c900403fc498154ec74eaf420e7338675cad7f19332b4a56be4ff946b662a3c2d217efbe4dc646fb742b8c62bfe8e25fd5dc59e7540695fa8b9cd5bfd9f92dfad009d230203010001");
        edit2.setEccKey("cbe6fbafb20fb69fa035fdeb43c6e11065e28edf9d9dc1b0c008571b3657f432|bf27b68d1c7b354e0abc391bdb96e5cb2ff860b97c200e6694f885f6f5bf8973");
        edit2.setMaxLength(6);
        edit1.setClip(false);
        edit1.setButtonPress(true);
        edit2.setClip(false);
        edit2.setButtonPress(true);
        edit2.setButtonPressAnim(true);
        edit2.setWatchOutside(true);
//		edit2.setReorder(PassGuardEdit.KEY_CHAOS_PRESS_KEY);
        edit2.setInputRegex("[a-zA-Z0-9@_\\.]");

        doAction action = new doAction() {
            @Override
            public void doActionFunction() {
                Toast.makeText(
                        getApplicationContext(),
                        edit2.isKeyBoardShowing() ? "KeyBoardShow"
                                : "KeyBoardHide", Toast.LENGTH_LONG).show();
                if (edit2.isKeyBoardShowing()) {
                    int b = edit2.getKeyboardHeight();
                    tvShow.setText("高度为：" + "" + b);
                }
            }
        };

        edit2.setKeyBoardHideAction(action);
        edit2.setKeyBoardShowAction(action);
        edit1.initPassGuardKeyBoard();
        edit2.initPassGuardKeyBoard();
    }

    private void addListener() {
        toWeb.setOnClickListener(this);
        tvShow.setOnClickListener(this);
        clear.setOnClickListener(this);
        getLenth.setOnClickListener(this);
        getMd5.setOnClickListener(this);
        getAESCiphertext.setOnClickListener(this);
        getRSAAESCiphertext.setOnClickListener(this);
        getSM2Ciphertext.setOnClickListener(this);
        getSM3Ciphertext.setOnClickListener(this);
        getSM4Ciphertext.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String msg = null;
        int id = v.getId();
        switch (id) {
            case R.id.native_to_web:
//			Intent intent = new Intent(NativeActivity.this, WebActivity.class);
//			startActivity(intent);
                break;
            case R.id.clear:
                edit2.clear();
                break;
            case R.id.btn_getlength:
                int length = edit2.getLength();
                tvShow.setText("长度为：" + length);
                break;
            case R.id.btn_getmd5:
                msg = edit2.getMD5();
                showDialog("MD5值为：", msg);
                msg = null;
                break;
            case R.id.btn_aes_ciphertext:
                msg = edit2.getAESCiphertext();
                showDialog("AES密文：", msg);
                msg = null;
                break;
            case R.id.btn_rsaaes_ciphertext:
                msg = edit2.getRSAAESCiphertext();
                showDialog("RSA+AES密文：", msg);
                msg = null;
                break;
            case R.id.btn_sm2_ciphertext:
                msg = edit2.getSM2Ciphertext();
                showDialog("SM2密文：", msg);
                msg = null;
                break;
            case R.id.btn_sm3_ciphertext:
                msg = edit2.getSM3Ciphertext();
                showDialog("SM3密文：", msg);
                msg = null;
                break;
            case R.id.btn_sm4_ciphertext:
                msg = edit2.getSM4Ciphertext();
                showDialog("SM4密文：", msg);
                msg = null;
                break;
            default:
                break;
        }
    }

    public void showDialog(String title, String msg) {
        if (mAlert == null) {
            mAlert = new Builder(NativeActivity.this);
            mAlert.setPositiveButton("确定", null);
            mAlert.setNegativeButton("取消", null);
        }
        if (title != null && msg != null) {
            mAlert.setTitle(title);
            mAlert.setMessage(msg);
        } else {
            mAlert.setTitle(title);
            mAlert.setMessage("null");
        }
        mAlert.show();
    }

}