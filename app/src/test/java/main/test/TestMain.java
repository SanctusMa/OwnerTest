package main.test;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import sun.misc.BASE64Decoder;

/**
 * Created by shmyhui on 2017/4/5.
 */

public class TestMain {
    public boolean show = false;
    public TestMain t;

    @Test
    public void test() {
        String sss1 = "1231";
        String sss2 = sss1;
        sss2 = null;
        System.out.println(sss1 + "sss2" + sss2);

        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.add(0);
        hashSet.add(1);
        hashSet.add(2);

        hashSet.remove(2);
        Iterator<Integer> integerIterator = hashSet.iterator();
        while (integerIterator.hasNext())
            System.out.print(integerIterator.next() + "  ");
        System.out.println();

//        BigDecimal bigDecimalResult = new BigDecimal(1);
//        for (int i = 2; i <= 10000; i++) {
//            bigDecimalResult = new BigDecimal(i).multiply(bigDecimalResult);
//        }
//        String result = bigDecimalResult + "";
//        for (int i = 0; i < result.length(); i += 100) {
//            int iAdd = result.length() - i;
//            if (iAdd > 100)
//                iAdd = 100;
//            System.out.println(result.substring(i, i + iAdd));
//        }
//        System.out.println(result.length());
//        System.out.println("1 & 0: " + (1 & 0));
//        System.out.println("1 & 1: " + (1 & 1));
//        System.out.println("1 & 2: " + (1 & 2));
//        System.out.println("1 & -1: " + (1 & -1));
//        System.out.println("3 & 4: " + (3 & 9));

        int i = 0, y = 0;
        System.out.println("i++ :" + (i++) + "              ++y :" + (++y));

        String s = "asdasdsd";

        String str = "";
        String str1 = null;
        System.out.println(str.equals(str1));

        try {
            new BASE64Decoder().decodeBuffer("anNicmlkZ2U6Ly9leGl0TW9kdWxl");
        } catch (IOException e) {
            e.printStackTrace();
        }

        HashSet<Integer> set1 = new HashSet<>();
        set1.add(0);
        set1.add(2);
        set1.add(1);
        System.out.println();

        System.out.println("tan:" + Math.tan(Math.PI / 3));
        Random random = new Random();
        for (int index = 0; index < 50; index++) {
            double speedX = random.nextInt(3);
            if (random.nextBoolean())
                speedX = 10f + speedX;
            else
                speedX = 10f - speedX;
            speedX = speedX / 10;
            System.out.print("speedX:" + speedX + " random:" + random.nextBoolean() + "   ");
            if (index % 6 == 0)
                System.out.println();
        }

        String ssss = "";
        String sssss = null;
        System.out.println(ssss.equals(sssss));

        Map<String, String> map = new HashMap<>();
        map.put("map", "hashMap");
        Set<String> set = map.keySet();
        for (String sSet : set) {
            System.out.println(map.get(sSet));
        }

        t = new TestMain();
        try {
            t.getClass().getField("t");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Test
    public void hah() {
        String str = "6226660605677380";
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            result += str.charAt(i);
            if ((i + 1) % 4 == 0) {
                result += " ";
            }
        }
        if (result.endsWith(" ")) {
            result = result.substring(0, result.length() - 1);
        }
        System.out.println(result);

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        System.out.println(calendar.get(Calendar.MONTH));

        SimpleDateFormat format = new SimpleDateFormat("MMM.", Locale.ENGLISH);
        System.out.println(format.format(new Date()));

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format1.format(new Date(1519616118000l)));

//        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.CHINESE);
//        numberFormat.setMaximumFractionDigits(2);
//        System.out.println(numberFormat.format(3.156));

        double d = 2.146;
        String money = d + "";
        System.out.println(keepTwoDecimal(3.146));

        TestMain t = new TestMain();
        if (t instanceof TestMain) System.out.println("FUCK U");

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Runnable-show:" + show);
//            }
//        }, 1000);

        show = true;
        System.out.println(show);

        BigDecimal bFeeRate = new BigDecimal(0.0036);
        BigDecimal bShare = new BigDecimal(9000);
        BigDecimal bDays = new BigDecimal(73);
        BigDecimal result1 = bFeeRate.multiply(bShare).multiply(bDays).divide(new BigDecimal(360));
        DecimalFormat format2 = new DecimalFormat("0.0000000");
        System.out.println(Double.parseDouble(format2.format(6.5699999999)));

        Map<Integer, Integer> map = new HashMap<>();
        Random random = new Random();
        int pos = random.nextInt(51);
        map.put(pos, 1);
        for (int i = 0; i < 4; i++) {
            while (true) {
                pos = random.nextInt(51);
                if (!map.containsKey(pos)) {
                    System.out.println("pos:" + pos);
                    break;
                } else {
                    System.out.println("else pos:" + pos);
                }
            }
        }
        for (int y = 0; y < 200; y++) {
            System.out.print(random.nextInt(51) + "  ");
        }
        System.out.println("end");

        long mill = 55620;
        System.out.println(mill / 3600 + " " + mill % 3600 / 60 + "  " + mill % 3600 % 60 % 60);
    }

    public static String keepTwoDecimal(@Nullable Double d) {
        String str = d + "";
        if (!str.contains(".")) {
            return d + ".00";
        }
        String arr[] = str.split("\\.");
        if (arr[1] == null || arr[1].length() == 0)
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

    @Test
    public void rename() throws Exception {
        File dir = new File("G:\\Desktop\\360jiagubao_windows_64\\after");
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".apk");
            }
        });
        for (File f : files) {
            String name = f.getName().replace("_4433_jiagu_sign", "");
            f.renameTo(new File(dir, name));
        }
    }

    @Test
    public void renameDel() throws Exception {
        File dir = new File("G:\\Desktop\\360jiagubao_windows_64\\after");
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".apk");
            }
        });
        for (File f : files) {
            String name = f.getName().replace("-4.4.3.3", "");
            f.renameTo(new File(dir, name));
        }
    }

    @Test
    public void doTest(){

    }
}
