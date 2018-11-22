package king.chengwu.com.chengwuapp.finger;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import chengwu.com.chengwuapp.R;

/**
 * Created by shmyh on 2017/10/20.
 */

public class DialogForFingnerprint extends Dialog {
    private TextView txtContent;

    public DialogForFingnerprint(@NonNull Context context) {
        super(context, R.style.CommonLoadingDialogStyle);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.base_dialog_fingerprint);
        txtContent = (TextView)findViewById(R.id.fingerprintDes);
        findViewById(R.id.fingerprintCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 取消指纹传感器的感应状态
                dismiss();
            }
        });
    }
}
