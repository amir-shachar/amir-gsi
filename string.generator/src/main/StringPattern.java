package main;

import java.util.ArrayList;
import java.util.List;

public class StringPattern
{
    private String pattern;
    public static String DECIMAL_CHAR = "*";
    public static String ALPHABET_CHAR = "&";
    public static String ALPHANUMERIC_CHAR = "^";

    private List<String> representation = new ArrayList<>();

    public StringPattern(String pattern)
    {
        this.pattern = pattern;
        parseToParts();
    }

    private void parseToParts()
    {
        while(!pattern.isEmpty())
        {
            if(pattern.substring(0,2).equals("?d"))
            {
                addNumeric();
            }
            else if(pattern.substring(0,2).equals("?l"))
            {
                addAlpha();
            }
            else if(pattern.substring(0,2).equals("?a"))
            {
                addAlphaNumeric();
            }
            else
            {
                addLiteral();
            }
        }
    }

    private void addAlphaNumeric()
    {
        StringBuilder alpha = new StringBuilder();
        while(!pattern.isEmpty() && pattern.substring(0,2).equals("?a"))
        {
            alpha.append(ALPHANUMERIC_CHAR);
            pattern = pattern.substring(2);
        }
        representation.add(alpha.toString());
    }

    private void addAlpha()
    {
        StringBuilder alpha = new StringBuilder();
        while(!pattern.isEmpty() && pattern.substring(0,2).equals("?l"))
        {
            alpha.append(ALPHABET_CHAR);
            pattern = pattern.substring(2);
        }
        representation.add(alpha.toString());
    }

    private void addLiteral()
    {
        StringBuilder literal = new StringBuilder();
        while(!pattern.isEmpty() && !pattern.substring(0,2).equals("?d")
                && !pattern.substring(0,2).equals("?l")
                && !pattern.substring(0,2).equals("?a"))
        {
            literal.append(pattern.charAt(0));
            pattern = pattern.substring(1);
        }
        representation.add(literal.toString());
    }

    private void addNumeric()
    {
        StringBuilder number = new StringBuilder();
        while(!pattern.isEmpty() && pattern.substring(0,2).equals("?d"))
        {
            number.append(DECIMAL_CHAR);
            pattern = pattern.substring(2);
        }
        representation.add(number.toString());
    }

    public List<String> getRepresentation()
    {
        return representation;
    }
}
