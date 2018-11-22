package king.chengwu.com.chengwuapp.activity_national_day;

import java.util.List;

/**
 * Created by hzhm on 2016/9/26.
 */

public class ActiveQueryModel {

    public int active;
    public int chance;
    public int share;
    public int totalTb;
    public List<ActiveRewardsList> list;

   public class ActiveRewardsList{
       public String createDate;
       public String frozenTb;
       public String phone;
       public String shareType;
       public String status;
       public int usedTb;
   }
}
