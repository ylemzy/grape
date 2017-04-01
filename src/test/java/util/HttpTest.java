package util;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by huangzebin on 2017/3/1.
 */
public class HttpTest {

    @Test
    public void testHttp() throws IOException {
        String post = Http.postBody("http://wap.gd.10086.cn/businessHsh/hshShop/loginAndBinding/getSms.jsps", null);
        System.out.println("RESULT:" + post);
    }

    @Test
    public void testSort(){

        List<Integer> list = new ArrayList<>();
        list.add(9);
        list.add(5);
        list.add(20);

        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2){
                    return -1;
                }else if (o1 < o2){
                    return 1;
                }
                return 0;
            }
        });

        System.out.println(JsonHelper.toJSON(list));
    }
}