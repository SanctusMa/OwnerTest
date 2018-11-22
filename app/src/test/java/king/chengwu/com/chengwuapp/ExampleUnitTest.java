package king.chengwu.com.chengwuapp;

import org.junit.Test;

import Decoder.BASE64Decoder;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);

        BASE64Decoder base64Decoder = new BASE64Decoder();
        System.out.println(new String(base64Decoder.decodeBuffer("eWlodWJhaXlpOi8vZ28/Y29kZT1hZmE4Yjg3MTc5Njc0YzQ0ODA3OTk0YTEzYzU5YTIyYw==\n")));
    }
}