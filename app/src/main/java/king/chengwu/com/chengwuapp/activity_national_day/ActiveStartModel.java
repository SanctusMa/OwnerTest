package king.chengwu.com.chengwuapp.activity_national_day;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hzhm on 2016/9/26.
 */

public class ActiveStartModel implements Parcelable {

    public int active;
    public int tb;
    public int time;
    public double da;
    public double zhong;
    public double xiao;

    public ActiveStartModel(){

    }

    protected ActiveStartModel(Parcel in) {
        active = in.readInt();
        tb = in.readInt();
        time = in.readInt();
        da = in.readDouble();
        zhong = in.readDouble();
        xiao = in.readDouble();
    }

    public static final Creator<ActiveStartModel> CREATOR = new Creator<ActiveStartModel>() {
        @Override
        public ActiveStartModel createFromParcel(Parcel in) {
            return new ActiveStartModel(in);
        }

        @Override
        public ActiveStartModel[] newArray(int size) {
            return new ActiveStartModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(active);
        dest.writeInt(tb);
        dest.writeInt(time);
        dest.writeDouble(da);
        dest.writeDouble(zhong);
        dest.writeDouble(xiao);
    }
}
