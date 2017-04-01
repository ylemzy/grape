package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huangzebin on 2017/2/10.
 */
public class PatternUtil {

    static String regex = "\\(.*?\\)";
    static Pattern pattern = Pattern.compile(regex);
    public static Matcher matchParenthesis(String str){
        return pattern.matcher(str);
      /*  while (matcher.find()) {
            System.out.println("matcher: " + matcher.group(0));
        }*/
    }
}
