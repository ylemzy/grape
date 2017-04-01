package util;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;

/**
 * Created by huangzebin on 2017/2/22.
 */
public class JsonHelperTest {

    @Test
    public void randomTest(){
        System.out.println("" + System.currentTimeMillis() + ":" + RandomUtils.nextInt(10000));
    }


}