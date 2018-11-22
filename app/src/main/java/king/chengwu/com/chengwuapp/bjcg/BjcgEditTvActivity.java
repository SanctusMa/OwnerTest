package king.chengwu.com.chengwuapp.bjcg;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import chengwu.com.chengwuapp.R;
import cn.passguard.PassGuardEdit;

/**
 * Created by shmyh on 2018/3/1.
 */

public class BjcgEditTvActivity extends Activity {
    private PassGuardEdit edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bjcg_edit);
        edit = findViewById(R.id.editBjcg);

    }
}
