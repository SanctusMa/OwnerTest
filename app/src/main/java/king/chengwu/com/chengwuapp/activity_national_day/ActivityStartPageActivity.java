package king.chengwu.com.chengwuapp.activity_national_day;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chengwu.com.chengwuapp.R;
import king.chengwu.com.chengwuapp.DialogLoadingView;

/**
 * Created by 金城武 on 2016/9/24.
 */
public class ActivityStartPageActivity extends Activity {
    private static final String AFTER_SHARED = "afterShared";

    private FrameLayout viewRoot;
    private View viewStart;
    private View viewRules;
    private View viewRewards;
    private ImageView imgStart;
    private FrameLayout.LayoutParams params;
    private ActiveQueryModel queryModel;
    //    private ActiveStartModel startModel;
    private RecyclerView recyclerView;
    private List<ActiveQueryModel.ActiveRewardsList> listRewards;
    private DialogLoadingView dialogLoadingView;
    private boolean afterShared = false;
    private MediaPlayer mediaPlayer;

    public static void startActivityStartPageActivity(Activity activity, boolean afterShared) {
        Intent intent = new Intent(activity, ActivityStartPageActivity.class);
        intent.putExtra(AFTER_SHARED, afterShared);
        activity.startActivity(intent);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.startClose:
                case R.id.rulesClose:
                case R.id.active_rewards_list_close:
                case R.id.active_rewards_none_close:
                    finish();
                    break;
                case R.id.btnStart: //开始游戏
//                    toStartPage();
//                    if (TextUtils.isEmpty(UserConfig.getUserToken())) {
//                        startActivity(new Intent(ActivityStartPageActivity.this, LoginActivity.class));
//                        return;
//                    }
                    dialogLoadingView.show();
                    if (afterShared) {
//                        ActiveApi.getActiveShareData(new Callback<ActiveShareModel>() {
//                            @Override
//                            public void onSuccess(ActiveShareModel model) {
//                                afterShared = false;
//                                toStartPage();
//                            }
//
//                            @Override
//                            public void onFail(ServerResultCode serverResultCode, String errorMessage) {
//                                dialogLoadingView.dismiss();
//                                ToastUtil.showNormalToast(errorMessage);
//                            }
//                        });
                    } else {
                        toStartPage();
                    }
                    break;
                case R.id.startRules://展示活动规则
                    if (null == viewRules) {
                        viewRules = LayoutInflater.from(ActivityStartPageActivity.this).inflate(R.layout.active_rules, null);
                        (viewRules.findViewById(R.id.rulesBtn)).setOnClickListener(listener);
                        (viewRules.findViewById(R.id.rulesClose)).setOnClickListener(listener);
                    }
                    viewRoot.removeView(viewStart);
                    viewRoot.addView(viewRules, params);
                    break;
                case R.id.startRewards: //展示已获奖励页面
                    toRewardsPage();
                    break;
                case R.id.rulesBtn://规则页面下面按钮
                    viewRoot.removeView(viewRules);
                    viewRoot.addView(viewStart);
                    break;
                case R.id.active_rewards_list_btn://已获奖励-有奖励时的页面下面按钮
                case R.id.active_rewards_none_btn://已获奖励-没有奖励时的页面下面按钮
                    viewRoot.removeView(viewRewards);
                    viewRoot.addView(viewStart);
                    break;
            }
        }
    };

    private void toStartPage() {
//        ActiveApi.getActiveQueryData(new Callback<ActiveQueryModel>() {
//            @Override
//            public void onSuccess(ActiveQueryModel modelQuery) {
//                queryModel = modelQuery;
//                if (modelQuery.active == 0) {
//                    dialogLoadingView.dismiss();
//                    imgStart.setImageResource(R.drawable.active_btn_tomorrow);
//                    imgStart.setEnabled(false);
//                    return;
//                }
//                if (modelQuery.chance == 0 && modelQuery.share == 0) {
//                    dialogLoadingView.dismiss();
//                    imgStart.setImageResource(R.drawable.active_btn_tomorrow);
//                    imgStart.setEnabled(false);
//                    return;
//                }
//                if (modelQuery.chance == 1) {// 有游戏机会
//                    ActiveApi.getActiveStartData(new Callback<ActiveStartModel>() {
//                        @Override
//                        public void onSuccess(ActiveStartModel model) {
//                            dialogLoadingView.dismiss();
//                            ActiveGameActivity.startActiveGameActivity(ActivityStartPageActivity.this, model, queryModel.share == 1 ? true : false);
//                            finish();
//                        }
//
//                        @Override
//                        public void onFail(ServerResultCode serverResultCode, String errorMessage) {
//                            dialogLoadingView.dismiss();
//                            ToastUtil.showNormalToast(errorMessage);
//                        }
//                    });
//                } else if (modelQuery.share == 1) {//有分享机会
//                    dialogLoadingView.dismiss();
//                    UIDefaultDialogHelper.showDefaultAskAlert(ActivityStartPageActivity.this, "您当前已无游戏机会,可通过分享活动获得一次额外游戏机会",
//                            "去分享", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Intent intent = CustomShareActivity.newIntent(
//                                            new CustomShareActivity.OnShareClickListener() {
//                                                @Override
//                                                public void onShareClick() {
//                                                    afterShared = true;
//                                                }
//                                            }, ActivityStartPageActivity.this,
//                                            "http://www.trc.com/wap/world.html",
//                                            "泰然城丨横扫全球好货 为你狂欢",
//                                            "集齐全球爆款尖货，精选人气心水好物，满足您全球美食、家居生活、时尚穿戴的品质需要");
//                                    startActivity(intent);
//                                    overridePendingTransition(0, 0);
//                                    UIDefaultDialogHelper.dialog.dismiss();
//                                    UIDefaultDialogHelper.dialog = null;
//                                }
//                            });
//                }
//            }
//
//            @Override
//            public void onFail(ServerResultCode serverResultCode, String errorMessage) {
//                dialogLoadingView.dismiss();
//                ToastUtil.showNormalToast(errorMessage);
//            }
//        });
    }

    private void toRewardsPage() {
//        if (TextUtils.isEmpty(UserConfig.getUserToken())
//                || null == queryModel || null == queryModel.list || 0 == queryModel.list.size()) {
//            if (null == viewRewards) {
//                viewRewards = LayoutInflater.from(ActivityStartPageActivity.this).inflate(R.layout.active_rewards_none, null);
//                (viewRewards.findViewById(R.id.active_rewards_none_close)).setOnClickListener(listener);
//                (viewRewards.findViewById(R.id.active_rewards_none_btn)).setOnClickListener(listener);
//            }
//        } else {
//            if (null == viewRewards) {
//                listRewards = new ArrayList<>();
//                viewRewards = LayoutInflater.from(ActivityStartPageActivity.this).inflate(R.layout.active_rewards_list, null);
//                (viewRewards.findViewById(R.id.active_rewards_list_close)).setOnClickListener(listener);
//                (viewRewards.findViewById(R.id.active_rewards_list_btn)).setOnClickListener(listener);
//                recyclerView = (RecyclerView) viewRewards.findViewById(R.id.active_rewards_list_recycleView);
//                recyclerView.setLayoutManager(new LinearLayoutManager(ActivityStartPageActivity.this));
//                recyclerView.setAdapter(new RecyclerView.Adapter<Holder>() {
//                    @Override
//                    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
//                        return new Holder(LayoutInflater.from(ActivityStartPageActivity.this).inflate(R.layout.active_rewards_adapter, null));
//                    }
//
//                    @Override
//                    public void onBindViewHolder(Holder holder, int position) {
//                        holder.txtDes.setText("获得" + listRewards.get(position).usedTb + "个T币");
//                        holder.txtDate.setText(listRewards.get(position).createDate.substring(5, 10));
//                    }
//
//                    @Override
//                    public int getItemCount() {
//                        return listRewards.size();
//                    }
//                });
//            } else {
//                listRewards.clear();
//            }
//            int coinCount = 0;
//            for (ActiveQueryModel.ActiveRewardsList item : queryModel.list) {
//                coinCount = coinCount + item.usedTb;
//            }
//            ((TextView) viewRewards.findViewById(R.id.active_rewards_list_count)).setText(coinCount + "");
//            listRewards.addAll(queryModel.list);
//            recyclerView.getAdapter().notifyDataSetChanged();
//        }
//        viewRoot.removeView(viewStart);
//        viewRoot.addView(viewRewards, params);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewRoot = new FrameLayout(this);
        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(viewRoot, params);

        viewStart = LayoutInflater.from(this).inflate(R.layout.active_start, null);
        viewRoot.addView(viewStart, params);
        imgStart = (ImageView) viewStart.findViewById(R.id.btnStart);
        (viewStart.findViewById(R.id.startClose)).setOnClickListener(listener);
        (viewStart.findViewById(R.id.startRules)).setOnClickListener(listener);
        (viewStart.findViewById(R.id.startRewards)).setOnClickListener(listener);
        imgStart.setOnClickListener(listener);

        dialogLoadingView = new DialogLoadingView(this);
        afterShared = getIntent().getBooleanExtra(AFTER_SHARED, false);
        mediaPlayer = MediaPlayer.create(this, R.raw.active_ready);
        mediaPlayer.setLooping(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();

        if (!dialogLoadingView.isShowing()) {
            dialogLoadingView.show();
        }
//        ActiveApi.getActiveQueryData(new Callback<ActiveQueryModel>() {
//            @Override
//            public void onSuccess(final ActiveQueryModel queryModel) {
//                if (TextUtils.isEmpty(UserConfig.getUserToken())) {
//                    dialogLoadingView.dismiss();
//                    imgStart.setImageResource(R.drawable.active_btn_start);
//                    imgStart.setEnabled(true);
//                    return;
//                }
//                ActivityStartPageActivity.this.queryModel = queryModel;
//                if (0 == queryModel.active || (queryModel.chance == 0 && queryModel.share == 0)) {
//                    dialogLoadingView.dismiss();
//                    imgStart.setImageResource(R.drawable.active_btn_tomorrow);
//                    imgStart.setEnabled(false);
//                } else if (queryModel.chance == 1 || queryModel.share == 1) {
//                    imgStart.setImageResource(R.drawable.active_btn_start);
//                    imgStart.setEnabled(true);
//                    dialogLoadingView.dismiss();
//                }
//            }
//
//            @Override
//            public void onFail(ServerResultCode serverResultCode, String errorMessage) {
//                dialogLoadingView.dismiss();
//                ToastUtil.showNormalToast(errorMessage);
//            }
//        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView txtDate;
        private TextView txtDes;

        public Holder(View itemView) {
            super(itemView);
            txtDate = (TextView) itemView.findViewById(R.id.active_adaoter_date);
            txtDes = (TextView) itemView.findViewById(R.id.active_adaoter_des);
        }
    }

}
