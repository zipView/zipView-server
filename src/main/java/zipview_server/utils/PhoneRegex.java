package zipview_server.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneRegex {
    public static boolean isRegexPhone(String target) {
        String regex = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
}
