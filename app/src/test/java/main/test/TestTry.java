package main.test;

import com.google.gson.Gson;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class TestTry {
    public String value;

    @Test
    public void test() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        long interval = new Date().getTime() - new Date("2018/11/03").getTime();
        long result = interval / 24 / 3600 / 1000;
        System.out.println(result + " " + interval + " " + (result * 24 * 36000 * 1000 > interval) + " " + simpleDateFormat.format(new Date()));

        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        if (list.size() > 4)
            list = list.subList(0, 4);
        System.out.println(list.subList(0, 3));


        System.out.println(simpleDateFormat.format(Long.parseLong("1545879202000")));
        long time0 = new Date("2018/12/27 00:00:00").getTime();
        System.out.println(time0 + " " + simpleDateFormat.format(time0));

        long time = new Date("2018/12/24 00:00:00").getTime();
        System.out.println(time + " " + simpleDateFormat.format(time));
        time = time + 7 * 24 * 3600 * 1000;
        System.out.println(time + " " + simpleDateFormat.format(time - 1) + " " + (7 * 24 * 3600 * 1000 - 1));

        System.out.println("currentTime:" + System.currentTimeMillis());
        System.out.println(timeD(180514457) + " " + time(4188422459l) + " " + time(13432170333l - 4188422459l));

        System.out.println("2018/10/08-2018/10/14".compareTo("2018/10/08-2018/10/14"));

//        System.out.println(" 封仙台: " + timeD(1688329237l) + " 蜀山: " + timeD(1329984784l) + " 琅琊榜: " + timeD(1239613851l)
//                + " \n八卦田: " + timeD(852260926l) + " 盘丝洞: " + timeD(555598542l) + " 论剑锋: " + timeD(773817405l)
//                + " \n昆仑山: " + timeD(1002506945l) + " 天枢: " + timeD(468881567l) + " 蓬莱阁: " + timeD(890775847l)
//                + " \n谍者: " + timeD(203472119l) + " 泰然生活馆会议室: " + timeD(174819655l) + " 棱镜: " + timeD(63686996l));

        Calendar c = Calendar.getInstance(Locale.CHINESE);
        c.setTime(new Date("2018/12/30"));
        System.out.println(c.getTimeInMillis() + " " + c.get(Calendar.DAY_OF_WEEK));

        Set<String> set = new HashSet<>();
        set.add("b");
        set.add("b");
        set.add("c");
        set.add("a");
        Gson gson = new Gson();
        String value = gson.toJson(set);
        Set<String> setResult = gson.fromJson(value, Set.class);
        for (String s : setResult) {
            System.out.print(s + "  ");
        }
        if (set.contains("a"))
            set.remove("a");
        System.out.println(gson.fromJson(gson.toJson(set), Set.class));
    }

    @Test
    public void tt() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        long time = new Date("2018/11/03").getTime();
//        time += 60L * 60 * 24 * 1000 * 100;
//        System.out.println(simpleDateFormat.format(time));
//        System.out.println(new Date("2019/01/25 11:00:00").getTime());
//        System.out.println(new Date("2019/01/25 12:00:00").getTime());
//
//        SimpleDateFormat s1 = new SimpleDateFormat("HH:mm:ss");
//        System.out.println(s1.format(System.currentTimeMillis()));
//
//
//        String p = "/data/user/0/com.tairanchina.taiheapp/files/RN-Bundles/TRFINANCEAPP/1.1.1.7";
//        System.out.println(p.substring(p.length() - 7).compareTo("1.1.1.0"));
//
////        try {
////            String s = null;
////            s.toString();
////        } catch (Exception e) {
////            Exception e1 = new Exception("1\n");
////            StackTraceElement[] s = e.getStackTrace();
////            e1.setStackTrace(e.getStackTrace());
////            System.out.println(e1);
////        }
//
//        System.out.println("0.0.0.1->" + compareAppVersion("0.0.1.0", "0.0.0.1"));
//        System.out.println("0.0.1.00->" + compareAppVersion("0.0.1.0", "0.0.1.0"));
//        System.out.println("0.0.0.116->" + compareAppVersion("0.0.1.0", "0.0.0.116"));
//        System.out.println("0.0.0.120->" + compareAppVersion("0.0.1.0", "0.0.0.120"));
//        System.out.println("0.0.1.0->" + compareAppVersion("0.0.1.0", "0.0.1.0"));
//        System.out.println("0.0.1.1->" + compareAppVersion("0.0.1.0", "0.0.1.1"));
//        System.out.println("0.0.1.10->" + compareAppVersion("0.0.1.0", "0.0.1.10"));
//        System.out.println("0.0.1.98->" + compareAppVersion("0.0.1.0", "0.0.1.98"));
//        System.out.println("0.0.1.120->" + compareAppVersion("0.0.1.0", "0.0.1.120"));
//
//        String ss = null;
//        String ss1 = null;
//        System.out.println(ss == ss1);

        String url = "https://vip.trc.com";
        System.out.println("https://vip.trc.co".startsWith(url));

//        TestTry testTry = new TestTry();
//        testTry.value = "Create";
//        TestReflect testReflect = new TestReflect(testTry);
//        try {
//            Field testFeild = testReflect.getClass().getDeclaredField("testTry");
//            testFeild.setAccessible(true);
//            System.out.println("before:" + testFeild.get("value"));
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
    }

    class TestReflect {
        private TestTry testTry;

        TestReflect(TestTry testTry) {
            this.testTry = testTry;
            System.out.println(testTry.value);
        }

        private void print() {
            System.out.println(testTry.value);
        }
    }

    public static int compareAppVersion(String version1, String version2) {
        if (null == version1 || version1.equals(version2)) {
            return 0;
        }
        // 注意此处为正则匹配，不能用.
        String[] versionArray1 = version1.split("\\.");
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        // 取数组最小长度值
        int minLength = Math.min(versionArray1.length, versionArray2.length);
        int diff = 0;
        // 先比较长度，再比较字符
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {
            ++idx;
        }
        // 如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }

    public static int compareVersion(String curVersion, String newVersion) {
        if (isEmpty(curVersion) || isEmpty(newVersion)) {
            return 0;
        }
        if (curVersion.equals(newVersion)) {
            return 0;
        }

        //移除空白字符
        curVersion = curVersion.replaceAll("\\s", "");
        newVersion = newVersion.replaceAll("\\s", "");

        Pattern pattern = Pattern.compile("[1-9]\\d*(\\.\\d+)*(-[0-9a-zA-Z.]*)?");
        if (!pattern.matcher(curVersion).matches() || !pattern.matcher(newVersion).matches()) {
            //版本号不合法，取消比较
            return 0;
        }

        String curVersionSuffix = null, newVersionSuffix = null;//版本号后缀
        String curVersionMain = curVersion, newVersionMain = newVersion;  //主版本号

        //区分数字版本号与修饰版本号
        if (curVersion.indexOf("-") > 0) {
            curVersionSuffix = curVersion.substring(curVersion.indexOf("-"));
            curVersionMain = curVersion.substring(0, curVersion.indexOf("-"));
        }
        if (newVersion.indexOf("-") > 0) {
            newVersionSuffix = newVersion.substring(newVersion.indexOf("-"));
            newVersionMain = newVersion.substring(0, newVersion.indexOf("-"));
        }

        //比较数字版本号
        int result = compare(curVersionMain, newVersionMain);
        if (-2 != result) {
            //通过数字版本号比较已经区分出结果
            return result;
        }
        //数字版本号比较完成

        //数字版本号相同，继续比较修饰版本号
        if (!isEmpty(curVersionSuffix) && !isEmpty(newVersionSuffix)) {
            //两个都带修饰，其优先层级必须透过由左到右的每个被句点分隔的标识符号来比较，直到找到一个差异值后决定
            return compare(curVersionSuffix, newVersionSuffix);
        }

        //有一个版本不带修饰，不带修饰的版本号高
        //例如 1.0.0-rc1 < 1.0.0
        if (isEmpty(curVersionSuffix)) {
            return 1;
        } else if (isEmpty(newVersionSuffix)) {
            return -1;
        }
        return 0;
    }

    /**
     * 根据"."符号将版本号拆分成数组,逐位比较
     *
     * @param curVersion
     * @param newVersion
     * @return
     */
    private static int compare(String curVersion, String newVersion) {
        String[] array1 = curVersion.split("\\.");
        String[] array2 = newVersion.split("\\.");

        int min = Math.min(array1.length, array2.length);
        Pattern pattern = Pattern.compile("[0-9]*");

        for (int index = 0; index < min; index++) {
            int release1 = -1, release2 = -1;

            String sCurVersion = array1[index];
            String sNewVersion = array2[index];

            if (sCurVersion.equals(sNewVersion)) {
                //该位的版本号相同，比较下一位
                continue;
            }

            //逐位比较版本号
            if (pattern.matcher(sCurVersion).matches()) {
                release1 = Integer.parseInt(sCurVersion);
            }
            if (pattern.matcher(sNewVersion).matches()) {
                release2 = Integer.parseInt(sNewVersion);
            }
            if (release1 == -1 || release2 == -1) {
                //有一位出现了字符，比较ascii
                return sCurVersion.compareTo(sNewVersion) > 0 ? 1 : -1;
            } else if (release1 != release2) {
                //比较数字版本号高低
                return release1 < release2 ? -1 : 1;
            }
        }

        //数字版本号比较，执行到这里说明根据较短的版本号比较尚未分出结果，继续根据长度进行判断
        //例如 1.0.0.1 > 1.0.0
        if (curVersion.length() != newVersion.length()) {
            return curVersion.length() > newVersion.length() ? 1 : -1;
        }
        return -2;
    }

    private static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }


    class MyException extends Exception {
        public MyException() {

        }
    }

    private String time(long l) {
        l = l / 1000;
        long d = l / 24 / 3600;
        l = l - d * 24 * 3600;
        long h = l / 3600;
        l = l - h * 3600;
        long m = l / 60;
        l = l - m * 60;
        return d + ":" + h + ":" + m + ":" + l;
    }

    private String timeD(long l) {
        l = l / 1000;
//        long d = l / 24 / 3600;
//        l = l - d * 24 * 3600;
        long h = l / 3600;
        l = l - h * 3600;
        long m = l / 60;
        l = l - m * 60;
        return h + ":" + m + ":" + l;
    }
}
