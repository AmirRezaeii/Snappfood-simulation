package Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    REGEX1("^register\\s+(?<username>\\S+)\\s+(?<password>\\S+)$"),
    REGEX2("^[A-Za-z0-9_]*[A-Za-z][A-Za-z0-9_]*$"),
    REGEX3("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,}$"),
    REGEX4("^login\\s+(?<username>\\S+)\\s+(?<password>\\S+)$"),
    REGEX5("^change\\s+password\\s+(?<username>\\S+)\\s+(?<oldPassword>\\S+)\\s+(?<newPassword>\\S+)$"),
    REGEX6("^remove\\s+account\\s+(?<username>\\S+)\\s+(?<password>\\S+)$"),
    REGEX7("^enter\\s+(?<menuName>.+)$"),
    REGEX8("^add\\s+restaurant\\s+(?<username>\\S+)\\s+(?<password>\\S+)\\s+(?<typeRestaurant>\\S+)$"),
    REGEX9("^[a-z\\-]+$"),
    REGEX10("^show\\s+restaurant\\s+\\-t\\s+(?<type>\\S+)$"),
    REGEX11("^remove\\s+restaurant\\s+(?<name>\\S+)$"),
    REGEX12("^set\\s+discount\\s+(?<username>\\S+)\\s+(?<amount>\\S+)\\s+(?<code>\\S+$)"),
    REGEX13("^[a-zA-Z0-9]+$"),
    REGEX14("^show\\s+discounts$"),
    REGEX15("^charge\\s+account\\s+(?<amount>\\S+)$"),
    REGEX16("^show\\s+balance$"),
    REGEX17("^add\\s+food\\s+(?<name>\\S+)\\s+(?<category>\\S+)\\s+(?<price>\\S+)\\s+(?<cost>\\S+)$"),
    REGEX18("^remove\\s+food\\s+(?<name>\\S+)$"),
    REGEX19("^show\\s+restaurant\\s+\\-t\\s+(?<type>\\S+)$"),
    REGEX20("^show\\s+menu\\s+(?<restaurantName>\\S+)\\s+\\-c\\s+(?<category>\\S+)$"),
    REGEX21("^show\\s+menu\\s+(?<restaurantName>\\S+)$"),
    REGEX22("^add\\s+to\\s+cart\\s+(?<restaurantName>\\S+)\\s+(?<foodName>\\S+)\\s+\\-n\\s+(?<number>\\S+)$"),
    REGEX23("^remove\\s+from\\s+cart\\s+(?<restaurantName>\\S+)\\s+(?<foodName>\\S+)\\s+\\-n\\s+(?<number>\\S+)$"),
    REGEX24("^show\\s+cart$"),
    REGEX25("^show\\s+discounts$"),
    REGEX26("^purchase\\s+cart\\s+\\-d\\s+(?<discountCode>\\S+)$"),
    REGEX27("^show\\s+current\\s+menu$"),
    REGEX28("^add\\s+to\\s+cart\\s+(?<restaurantName>\\S+)\\s+(?<foodName>\\S+)$"),
    REGEX29("^remove\\s+from\\s+cart\\s+(?<restaurantName>\\S+)\\s+(?<foodName>\\S+)$"),
    REGEX30("^purchase\\s+cart$"),
    REGEX31("^show\\s+restaurant$"),
    REGEX32("^[A-Za-z0-9_]*$");
    private String regex;

    private Commands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, Commands command) {
        Pattern pattern = Pattern.compile(command.regex);
        return pattern.matcher(input);
    }
}
