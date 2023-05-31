package zipview_server.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailRegex {
    public static boolean isRegexEmail(String target) {
        String regex = "^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
}
