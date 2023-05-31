package zipview_server.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PwdRegex {
    public static boolean isRegexPwd(String target) {
        String regex = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[`~!@$!%*#^?&\\(\\)\\-_=+])(?!.*[^a-zA-z0-9`~!@$!%*#^?&\\(\\)\\-_=+]).{8,16}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
}
