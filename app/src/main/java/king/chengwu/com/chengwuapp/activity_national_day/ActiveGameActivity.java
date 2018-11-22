package king.chengwu.com.chengwuapp.activity_national_day;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Random;

import chengwu.com.chengwuapp.R;
import king.chengwu.com.chengwuapp.DialogLoadingView;
import king.chengwu.com.chengwuapp.LogUtil;

/**
 * Created by 金城武 on 2016/9/23.
 */
public class ActiveGameActivity extends Activity {
    private static final String ACTIVE_START_MODEL = "activeStartModel";
    private static final String ACTIVE_CAN_SHARE = "activeCanShare";

    private boolean afterShared;
    private FrameLayout rootView;
    private ImageView imageView;
    public TextView txtCoinCount;
    public TextView txtTimeCounter;
    //    public TextView txtShow;
    private ActiveGroup activeGroup;
    public Random random;
    public int countCoin;
    public int getCoin; //游戏结束时的T币数
    public int timeCount;
    public int viewCoinCountRight;
    public int viewCoinCountBottom;

    private View viewEnd;
    private ImageView imgEndTop;
    private TextView txtEndCount;
    private TextView txtEndTbDes;
    private View viewEndCenter;
    private ImageView imgEndRightBtn;
    private ImageView imgEndLeftBottom;
    private ImageView imgEndRightBottom;

    private static final int GAME_START = 1;
    private static final int GAME_RUNNING = 2;
    private static final int GAME_END = 3;
    private int gameStatus = GAME_START;
    private int progressLoading;
    private DialogLoadingView dialogLoadingView;
    private ActiveStartModel activeStartModel;
    private boolean canShare = false;
    private ActiveEndModel activeEndModel;
    private ActiveQueryModel queryModel;
    public Vibrator vibrator;
    public SoundPool soundPool;
    private MediaPlayer mediaPlayer;
    private boolean canPlayMedia = false;
    private Runnable runStartGame;
    private boolean runStartGameOn = false;
    private Runnable runEnd;
    private boolean runEndOn = false;

    public static void startActiveGameActivity(Activity activity, ActiveStartModel startModel, boolean canShare) {
        Intent intent = new Intent(activity, ActiveGameActivity.class);
        intent.putExtra(ACTIVE_START_MODEL, startModel);
        intent.putExtra(ACTIVE_CAN_SHARE, canShare);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = (FrameLayout) LayoutInflater.from(this).inflate(R.layout.active_game, null);
        setContentView(rootView);
        txtCoinCount = (TextView) findViewById(R.id.txtCoinCount);
        txtTimeCounter = (TextView) findViewById(R.id.txtTimeCounter);
        imageView = (ImageView) findViewById(R.id.imgCounterDown);

        activeStartModel = getIntent().getParcelableExtra(ACTIVE_START_MODEL);
        canShare = getIntent().getBooleanExtra(ACTIVE_CAN_SHARE, false);
        timeCount = activeStartModel.time;
        if (timeCount >= 10) {
            txtTimeCounter.setText("00:" + timeCount);
        } else {
            txtTimeCounter.setText("00:0" + timeCount);
        }
        //预加载倒计时资源文件，避免进入倒计时页面出现资源未加载完成的情况
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 5);
        soundPool.load(this, R.raw.count_down_time, 1);
        soundPool.load(this, R.raw.click_coin, 1);
        soundPool.load(this, R.raw.click_bomb, 1);
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        mediaPlayer = MediaPlayer.create(this, R.raw.active_bg);
        mediaPlayer.setLooping(true);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        findViewById(R.id.rootCount).setVisibility(View.GONE);
        findViewById(R.id.rootTimeCounter).setVisibility(View.GONE);
        txtCoinCount.setVisibility(View.GONE);
        txtTimeCounter.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        final View viewLoading = LayoutInflater.from(ActiveGameActivity.this).inflate(R.layout.active_loading, null);
        rootView.addView(viewLoading, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        final ProgressBar progressBar = (ProgressBar) viewLoading.findViewById(R.id.active_loading_bar);
        for (int interval = 0; interval < 2000; interval = interval + 20) {
            rootView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        progressBar.setProgress(++progressLoading);
                        if (progressLoading >= 100) {
                            rootView.removeView(viewLoading);
                            startCountingDown();
                        }
                    } catch (Exception e) {
                        LogUtil.e(e);
                    }
                }
            }, interval);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (canPlayMedia && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        if (afterShared) {
            ActivityStartPageActivity.startActivityStartPageActivity(ActiveGameActivity.this, true);
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActiveConfig.setActiveData(getCoin);
        soundPool.stop(1);
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            if (null != soundPool) {
                soundPool.stop(1);
                soundPool.stop(2);
                soundPool.stop(3);
                soundPool.release();
            }
            mediaPlayer.stop();
            mediaPlayer.release();
            if (runEndOn && runEnd != null) {
                rootView.removeCallbacks(runEnd);
            }
            if (runStartGameOn && runStartGame != null) {
                rootView.removeCallbacks(runStartGame);
            }
        } catch (IllegalStateException e) {
            LogUtil.e(e);
        } finally {
            super.onDestroy();
        }
    }

    private void startCountingDown() {
        findViewById(R.id.rootCount).setVisibility(View.VISIBLE);
        findViewById(R.id.rootTimeCounter).setVisibility(View.VISIBLE);
        txtCoinCount.setVisibility(View.VISIBLE);
        txtTimeCounter.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();
        soundPool.play(1, 0.5f, 0.5f, 0, 0, 1);

        timeCount = activeStartModel.time;
        runStartGame = new Runnable() {
            @Override
            public void run() {
                try {
                    startGame();
                } catch (Exception e) {
                    LogUtil.e(e);
                }
            }
        };
        rootView.postDelayed(runStartGame, 3500);
        runStartGameOn = true;
    }

    private void startGame() {
        canPlayMedia = true;
//        musicBind.musicStart();
        mediaPlayer.start();

        gameStatus = GAME_RUNNING;
        ActiveConfig.setActivePlayDate(new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis())); //本日玩过游戏
        ActiveConfig.setActiveDataSendSuccess(false); //开始玩的时候就把未发送数据至服务器至否，说明该局游戏数据还在本地
        rootView.removeView(imageView);
        new GameTimeCounter(timeCount * 1000, 1000).start();

        activeGroup = new ActiveGroup(this);
        rootView.addView(activeGroup, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        countCoin = activeStartModel.tb;
        int countBig = (int) (countCoin * activeStartModel.da);
        HashSet<Integer> hashSetBig = new HashSet<>();
        random = new Random();
        for (int i = 0; i < countBig; i++) {
            //hashset不接受相同的数字对象,下面保证for结束后hashset有足够的对象
            while (!hashSetBig.add(random.nextInt(timeCount - 2) + 2)) {
            }
        }

        int deltaX = (int) activeGroup.dip2px(45);
        viewCoinCountRight = deltaX;
        viewCoinCountBottom = deltaX;
        int countLittle = (int) (countCoin * activeStartModel.xiao);
        int intervalOfCreateLittle = timeCount * 1000 / countLittle;
        int countBomb = (int) (countCoin * activeStartModel.zhong);
        int intervalOfCreateBomb = timeCount * 1000 / countBomb;
        activeGroup.startRun(hashSetBig, intervalOfCreateLittle, intervalOfCreateBomb);
    }

    class GameTimeCounter extends CountDownTimer {
        public GameTimeCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            try {
                timeCount--;
                if (timeCount < 10) {
                    txtTimeCounter.setText("00:0" + timeCount);
                } else {
                    txtTimeCounter.setText("00:" + timeCount);
                }
            } catch (Exception e) {
                LogUtil.e(e);
            }
        }

        @Override
        public void onFinish() {
//            try {
//                txtTimeCounter.setText("00:00");
////            txtShow.setVisibility(View.GONE);
//                runEnd = new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            gameStatus = GAME_END;
//                            runEndOn = false;
//                            if (!TextUtils.isEmpty(ActiveConfig.getActivePlayDate()) && ActiveConfig.getActiveDataSendSuccess()) {
//                                //游戏切换到后台,点击图标重新却是重新进入应用，这时候会发送上次游戏的缓存数据，如果这个状态是true，则后台未取消的定时器就不用发送请求了
//                                //反之，定时器发送请求，则重新进入应用检查的时候就跳过发送请求的地方
//                                return;
//                            }
//                            if (null == dialogLoadingView) {
//                                dialogLoadingView = new DialogLoadingView(ActiveGameActivity.this);
//                            }
//                            dialogLoadingView.show();
//                            ActiveApi.getActiveEndData(getCoin, new Callback<ActiveEndModel>() {
//                                @Override
//                                public void onSuccess(ActiveEndModel endModel) {
//                                    activeEndModel = endModel;
//                                    ActiveConfig.setActiveDataSendSuccess(true);
//                                    ActiveApi.getActiveQueryData(new Callback<ActiveQueryModel>() {
//                                        @Override
//                                        public void onSuccess(ActiveQueryModel model) {
//                                            dialogLoadingView.dismiss();
//                                            queryModel = model;
//                                            whenGameEnd();
//                                        }
//
//                                        @Override
//                                        public void onFail(ServerResultCode serverResultCode, String errorMessage) {
//                                            dialogLoadingView.dismiss();
//                                            ToastUtil.showNormalToast(errorMessage);
//                                            whenGameEnd();
//                                            ActiveConfig.setActiveDataSendSuccess(false);
//                                        }
//                                    });
//                                }
//
//                                @Override
//                                public void onFail(ServerResultCode serverResultCode, String errorMessage) {
//                                    dialogLoadingView.dismiss();
//                                    ToastUtil.showNormalToast(errorMessage);
//                                    whenGameEnd();
//                                    ActiveConfig.setActiveDataSendSuccess(false);
//                                }
//                            });
//                        } catch (Exception e) {
//                            LogUtil.e(e);
//                        }
//                    }
//                };
//                rootView.postDelayed(runEnd, 3000);
//            } catch (Exception e) {
//                LogUtil.e(e);
//            }
//        }
        }

        private void whenGameEnd() {
            findViewById(R.id.rootCount).setVisibility(View.GONE);
            findViewById(R.id.rootTimeCounter).setVisibility(View.GONE);
            txtCoinCount.setVisibility(View.GONE);
            txtTimeCounter.setVisibility(View.GONE);
            rootView.removeView(activeGroup);
            if (null == viewEnd) {
                viewEnd = LayoutInflater.from(ActiveGameActivity.this).inflate(R.layout.active_end, null);
                imgEndTop = (ImageView) viewEnd.findViewById(R.id.active_end_img);
                txtEndCount = (TextView) viewEnd.findViewById(R.id.active_end_count);
                txtEndTbDes = (TextView) viewEnd.findViewById(R.id.active_end_countDes);
                viewEndCenter = viewEnd.findViewById(R.id.active_end_center);
                imgEndRightBtn = (ImageView) viewEnd.findViewById(R.id.active_end_right_btn);
                imgEndLeftBottom = (ImageView) viewEnd.findViewById(R.id.active_end_left_img);
                imgEndRightBottom = (ImageView) viewEnd.findViewById(R.id.active_end_right_img);
                viewEnd.findViewById(R.id.active_end_close).setOnClickListener(listener);
                viewEnd.findViewById(R.id.active_end_left_btn).setOnClickListener(listener);
                imgEndRightBtn.setOnClickListener(listener);
                imgEndLeftBottom.setOnClickListener(listener);
                imgEndRightBottom.setOnClickListener(listener);
            }
            rootView.addView(viewEnd, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            txtEndCount.setText(getCoin + "");
            if (null == queryModel) {
                imgEndRightBtn.setImageResource(R.drawable.active_btn_tomorrow);
                imgEndRightBtn.setEnabled(false);
            } else if (queryModel.active == 1 && queryModel.share == 1) {
                imgEndRightBtn.setImageResource(R.drawable.active_btn_again);
                imgEndRightBtn.setEnabled(true);
            } else {
                imgEndRightBtn.setImageResource(R.drawable.active_btn_tomorrow);
                imgEndRightBtn.setEnabled(false);
            }
            if (getCoin > 0) {
                imgEndTop.setImageResource(R.drawable.active_end_congratulation);
                txtEndTbDes.setText("可在泰然金融投资时使用");
            } else {
                imgEndTop.setImageResource(R.drawable.active_end_sorry);
                txtEndTbDes.setText("继续加油哦");
            }
            if (null == activeEndModel || null == activeEndModel.goodsList || activeEndModel.goodsList.size() < 2) {
                imgEndLeftBottom.setVisibility(View.GONE);
                imgEndRightBottom.setVisibility(View.GONE);
                viewEndCenter.setVisibility(View.GONE);
            } else {
                imgEndLeftBottom.setVisibility(View.VISIBLE);
                imgEndRightBottom.setVisibility(View.VISIBLE);
                viewEndCenter.setVisibility(View.VISIBLE);
//            ImageLoadUtil.loadNormalImage(imgEndLeftBottom, activeEndModel.goodsList.get(0).image, R.drawable.ad_default_trc, R.drawable.ad_default_trc);
//            ImageLoadUtil.loadNormalImage(imgEndRightBottom, activeEndModel.goodsList.get(1).image, R.drawable.ad_default_trc, R.drawable.ad_default_trc);
            }
        }

        private View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.active_end_close:
                        finish();
                        break;
                    case R.id.active_end_left_btn:
//                    startActivity(WebViewActivity.newIntent(ActiveGameActivity.this, "http://www.trc.com/wap/world.html"));
                        break;
                    case R.id.active_end_right_btn:
//                    if (canShare)
//                        UIDefaultDialogHelper.showDefaultAskAlert(ActiveGameActivity.this, "您当前已无游戏机会,可通过分享活动获得一次额外游戏机会",
//                                "去分享", new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Intent intent = CustomShareActivity.newIntent(
//                                                new CustomShareActivity.OnShareClickListener() {
//                                                    @Override
//                                                    public void onShareClick() {
//                                                        afterShared = true;
//                                                    }
//                                                }, ActiveGameActivity.this,
//                                                "http://www.trc.com/wap/world.html",
//                                                "泰然城丨横扫全球好货 为你狂欢",
//                                                "集齐全球爆款尖货，精选人气心水好物，满足您全球美食、家居生活、时尚穿戴的品质需要");
//                                        startActivity(intent);
//                                        overridePendingTransition(0, 0);
//                                        UIDefaultDialogHelper.dialog.dismiss();
//                                        UIDefaultDialogHelper.dialog = null;
//                                    }
//                                });
//                    break;
                    case R.id.active_end_left_img:
//                    startActivity(WebViewActivity.newIntent(ActiveGameActivity.this, activeEndModel.goodsList.get(0).link));
                        break;
                    case R.id.active_end_right_img:
//                    startActivity(WebViewActivity.newIntent(ActiveGameActivity.this, activeEndModel.goodsList.get(1).link));
                        break;
                }
            }
        };

//    @Override
//    public void onBackPressed() {
//        switch (gameStatus) {
//            case GAME_START:
//            case GAME_END:
//                super.onBackPressed();
//                break;
//            case GAME_RUNNING:
//                break;
//        }
//    }
    }
}
