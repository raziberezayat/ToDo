    import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class EmailValidator {
public static  boolean emailValidator(String email){





            String regex = ".*@.*\\.";


             Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
             Matcher matcher = pattern.matcher(email);

            if (matcher.find()) {
                return true;
            }
            else {
                return false;
            }


}
}
