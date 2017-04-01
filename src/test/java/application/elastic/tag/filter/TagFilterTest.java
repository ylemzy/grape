package application.elastic.tag.filter;

import application.elastic.tag.Tag;
import application.elastic.tag.TagType;
import org.junit.Test;
import util.JsonHelper;

import static org.junit.Assert.*;

/**
 * Created by huangzebin on 2017/2/28.
 */
public class TagFilterTest {


    public void test(String age){
        Tag tag = TagFilter.parseAgeLimit(age);
        System.out.println(JsonHelper.toJSON(tag));
    }

    @Test
    public void test(){
        String age = "14-45";
        test(age);

        String age2 = "14天-45周岁";
        test(age2);
    }

    @Test
    public void test2(){
        Tag tag = TagFilter.build(TagType.TAG).put("tag", TagFilter.noEmptyValue(null));
        System.out.println(JsonHelper.toJSON(tag));
    }
}