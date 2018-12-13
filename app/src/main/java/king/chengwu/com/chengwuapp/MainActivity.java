package king.chengwu.com.chengwuapp;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import chengwu.com.chengwuapp.R;
import king.chengwu.com.chengwuapp.test.AlarmActivity;
import king.chengwu.com.chengwuapp.test.ScrollViewActivity;
import king.chengwu.com.chengwuapp.test.TransparentScrollActivity;
import king.chengwu.com.chengwuapp.viewgroup.DetailProgressBar;

public class MainActivity extends AppCompatActivity {
    private String url = "https://h5.trc.com/trade/recharge";

    private EditText editText;
    private TextView tv1;
    private boolean show = false;
    private int timeCount = 12;
    private MediaPlayer mediaPlayer;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.mainWebview);

        m1();
        findViewById(R.id.progressBar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScrollViewActivity.class));
            }
        });
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TransparentScrollActivity.class));
            }
        });
        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AlarmActivity.class));
            }
        });

        webView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    new DatePickerDialog(MainActivity.this).show();
            }
        });

        System.out.println(keepTwoDecimal(3.146));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable-show:" + show);
            }
        }, 1000);

        show = true;
        System.out.println(show);

        String msg = "<font color=\"#f25a2b\">1小螺号<br/>嘀嘀嘀吹<br/>" + "</font>" + "海鸥听了展翅飞" + "小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "2小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "3小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "4小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "5小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞<br></br>"
                + "17小螺号嘀嘀嘀吹海鸥听了展翅飞小螺号嘀嘀嘀吹海鸥听了展翅飞小螺号嘀嘀嘀吹海鸥听了展翅飞小螺号嘀嘀嘀吹海鸥听了展翅飞"
                + "6小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "7小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "8小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "9小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "10小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "11小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "12小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "13小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "14小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "15小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "16小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "18小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "19小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "20小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "21小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "22小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "23小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "24小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞"
                + "25小螺号<br/>嘀嘀嘀吹<br/>海鸥听了展翅飞";
//        msg = "<br>25小螺号</br>嘀嘀嘀吹<br>海鸥听了展翅飞</br>";
//        Toast.makeText(this, Html.fromHtml(msg), Toast.LENGTH_LONG).show();
        msg = msg.replace("<br>", "").replace("</br>", "").replace("<br/>", "");
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

        getDeviceInfo(this);

        webView.loadUrl(url);
    }

    private void m1() {
        tv1 = (TextView) findViewById(R.id.tv1);
        int startIndex, stopIndex;
        SpannableStringBuilder spann;
        String s = "1、联系在线客服（服务时间9:00-21:00），提交相关信息；" +
                "\n2、请提供泰然城账户、姓名、身份证证号、原银行卡号、原银行卡预留手机号；" +
                "\n3、1-3个工作日内，将会有专人来电核实（请注意接听号码为0571-96056的电话，不接、拒接、核对错误将影响解绑）；" +
                "\n4、解绑成功后，您可以到当前页面绑定新的银行卡" +
                "\n哈嘿嘿";
        s = "《出借风险说明和禁止性行为》哈哈《泰然金融预约计划授权协议》";
        spann = new SpannableStringBuilder(s);
        startIndex = spann.length() - 30;
        stopIndex = spann.length() - 16;

        s = "《出借人风险提示》哈哈《出借风险说明和禁止性行为》哈哈《泰然金融预约计划授权协议》";
        spann = new SpannableStringBuilder(s);
        startIndex = spann.length() - 30;
        stopIndex = spann.length() - 16;

        s = "《借款协议范本》哈哈《出借风险说明和禁止性行为》";
        spann = new SpannableStringBuilder(s);
        startIndex = spann.length() - 24;
        stopIndex = spann.length() - 16;

        s = "《出借人风险提示》哈哈《借款协议范本》哈哈《出借风险说明和禁止性行为》";
        spann = new SpannableStringBuilder(s);
        startIndex = spann.length() - 24;
        stopIndex = spann.length() - 16;

        s = "《债权转让协议范本》哈哈《出借风险说明和禁止性行为》";
        spann = new SpannableStringBuilder(s);
        startIndex = spann.length() - 26;
        stopIndex = spann.length() - 16;

        s = "《出借人风险提示》哈哈《债权转让协议范本》哈哈《出借风险说明和禁止性行为》";
        spann = new SpannableStringBuilder(s);
        startIndex = spann.length() - 26;
        stopIndex = spann.length() - 16;


        spann.setSpan(new ClickableSpan() {

            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Asdasd", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.parseColor("#f25a2b"));
            }
        }, startIndex, stopIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        tv1.setMovementMethod(LinkMovementMethod.getInstance());
        tv1.setText(spann);
    }

    private void m2() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showNormalToast("asdasdasdasd");
            }
        });
        new Handler().post(null);

        final DetailProgressBar bar = (DetailProgressBar) findViewById(R.id.progressBar);
//        bar.setProgress(80);

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String strPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIKkHR3+u1EBucMFZKDG0dyfyUY+aGj+" +
//                        "okeBSD/KT88UunPAvfez+Rg3TKmOs3KFsEvR+4LtOALMk+SMAMAMll7e71KQGN1s9NIe3pvfX49L7/" +
//                        "zxC23x18zYIKXA9mU3/Ph52CKLqXBRC/oqWxcSWayxnr8dewfSArxNcIpQRqSwIDAQAB";
//                String pwd = "Myh@123456";
//                String after = Encypt.encryptData(pwd, strPublicKey);

//                try {
//                    String path = "G:\\Desktop\\Pub_Key1494811415972.der";
//                    String pwd = BaseRSAUtil.getRSAPwd(BaseRSAUtil.RSAEncrypt("m111111".getBytes("UTF-8"), path));
//                    System.out.println(pwd);
//                    Log.e("after:", pwd);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

//                startActivity(new Intent(MainActivity.this, king.chengwu.com.chengwuapp.finger.yeah.MainActivity.class));
                bar.setProgress(100);
//                new DialogForFingnerprint(MainActivity.this).show();
            }
        });
        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String strPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIKkHR3+u1EBucMFZKDG0dyfyUY+aGj+" +
//                        "okeBSD/KT88UunPAvfez+Rg3TKmOs3KFsEvR+4LtOALMk+SMAMAMll7e71KQGN1s9NIe3pvfX49L7/" +
//                        "zxC23x18zYIKXA9mU3/Ph52CKLqXBRC/oqWxcSWayxnr8dewfSArxNcIpQRqSwIDAQAB";
//                String pwd = "Myh@123456";
//                String after = Encypt.encryptData(pwd, strPublicKey);


//                try {
//                    String path = "G:\\Desktop\\Pub_Key1494811415972.der";
//                    String pwd = BaseRSAUtil.getRSAPwd(BaseRSAUtil.RSAEncrypt("m111111".getBytes("UTF-8"), path));
//                    String privatePath = "G:\\Desktop\\private_der.key";
//                    String afterPwd = new String(BaseRSAUtil.decryptData(pwd.getBytes("UTF-8"), BaseRSAUtil.loadPrivateKey(privatePath)));
//                    System.out.println(afterPwd);
//                    Log.e("after:", afterPwd);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                BigDecimal bFeeRate = new BigDecimal(0.0036);
                BigDecimal bShare = new BigDecimal(9000);
                BigDecimal bDays = new BigDecimal(73);
                BigDecimal result = bFeeRate.multiply(bShare).multiply(bDays).divide(new BigDecimal(360));
                DecimalFormat format = new DecimalFormat("0.000");
                Toast.makeText(MainActivity.this, format.format(result), Toast.LENGTH_SHORT).show();
            }
        });
        editText = (EditText) findViewById(R.id.edit);
        editText.addTextChangedListener(watcher);


        findViewById(R.id.tv).setVisibility(View.GONE);
//        findViewById(R.id.tvCopy).setVisibility(View.GONE);
//        findViewById(R.id.tvCopy).setVisibility(View.VISIBLE);
        findViewById(R.id.tv).setVisibility(View.VISIBLE);

        timerInsteadTest();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e("onDestroy");
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String str = s.toString().trim().replace(" ", "");
            String result = "";
            if (str.length() >= 4) {
                editText.removeTextChangedListener(watcher);
                for (int i = 0; i < str.length(); i++) {
                    result += str.charAt(i);
                    if ((i + 1) % 4 == 0) {
                        result += " ";
                    }
                }
                if (result.endsWith(" ")) {
                    result = result.substring(0, result.length() - 1);
                }
                editText.setText(result);
                editText.addTextChangedListener(watcher);
                editText.setSelection(editText.getText().toString().length());//焦点到输入框最后位置
            }
        }
    };


    public static String keepTwoDecimal(@Nullable Double d) {
        String str = d + "";
        if (!str.contains(".")) {
            return d + ".00";
        }
        String arr[] = str.split("\\.");
        if (TextUtils.isEmpty(arr[1]))
            return d + ".00";
        else if (arr[1].length() == 1) {
            return arr[0] + "." + arr[1] + "0";
        } else if (arr[1].length() == 2)
            return str;
        else if (arr[1].length() > 2) {
            return arr[0] + "." + arr[1].substring(0, 2);
        }
        return str;
    }


    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            Log.e("device_id", "{\"device_id\":\"" + device_id + "\",\"mac\":\"" + mac + "\"}");
            System.out.println("{\"device_id\":\"" + device_id + "\",\"mac\":\"" + mac + "\"}");
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void timerInsteadTest() {
        ScheduledExecutorService service = new ScheduledExecutorService() {
            @NonNull
            @Override
            public ScheduledFuture<?> schedule(@NonNull Runnable command, long delay, @NonNull TimeUnit unit) {
                return null;
            }

            @NonNull
            @Override
            public <V> ScheduledFuture<V> schedule(@NonNull Callable<V> callable, long delay, @NonNull TimeUnit unit) {
                return null;
            }

            @NonNull
            @Override
            public ScheduledFuture<?> scheduleAtFixedRate(@NonNull Runnable command, long initialDelay, long period, @NonNull TimeUnit unit) {
                return null;
            }

            @NonNull
            @Override
            public ScheduledFuture<?> scheduleWithFixedDelay(@NonNull Runnable command, long initialDelay, long delay, @NonNull TimeUnit unit) {
                return null;
            }

            @Override
            public void shutdown() {

            }

            @NonNull
            @Override
            public List<Runnable> shutdownNow() {
                return null;
            }

            @Override
            public boolean isShutdown() {
                return false;
            }

            @Override
            public boolean isTerminated() {
                return false;
            }

            @Override
            public boolean awaitTermination(long timeout, @NonNull TimeUnit unit) throws InterruptedException {
                return false;
            }

            @NonNull
            @Override
            public <T> Future<T> submit(@NonNull Callable<T> task) {
                return null;
            }

            @NonNull
            @Override
            public <T> Future<T> submit(@NonNull Runnable task, T result) {
                return null;
            }

            @NonNull
            @Override
            public Future<?> submit(@NonNull Runnable task) {
                return null;
            }

            @NonNull
            @Override
            public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> tasks) throws InterruptedException {
                return null;
            }

            @NonNull
            @Override
            public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> tasks, long timeout, @NonNull TimeUnit unit) throws InterruptedException {
                return null;
            }

            @NonNull
            @Override
            public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
                return null;
            }

            @Override
            public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> tasks, long timeout, @NonNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }

            @Override
            public void execute(@NonNull Runnable command) {

            }
        };

        service.schedule(new Runnable() {
            @Override
            public void run() {

            }
        }, 5, TimeUnit.SECONDS);
    }
}
