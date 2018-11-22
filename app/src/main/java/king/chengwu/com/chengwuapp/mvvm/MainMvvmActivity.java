package king.chengwu.com.chengwuapp.mvvm;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import chengwu.com.chengwuapp.R;
import chengwu.com.chengwuapp.databinding.MvvmTestLayoutBinding;

/**
 * Created by shmyhui on 2017/4/5.
 */

public class MainMvvmActivity extends Activity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        People people = new People();
        people.name = "ChengwuKing";
        people.age = 200;
        people.province = "Zhejiang";

        MvvmTestLayoutBinding binding = DataBindingUtil.setContentView(this, R.layout.mvvm_test_layout);
        binding.setPeople(people);
    }
}
