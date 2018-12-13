package main.test;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TestTry {

    @Test
    public void test() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        long interval = new Date().getTime() - new Date("2018/11/03").getTime();
        long result = interval / 24 / 3600 / 1000;
        System.out.println(result + " " + interval + " " + (result * 24 * 36000 * 1000 > interval) + " " + simpleDateFormat.format(new Date()));
    }
}
