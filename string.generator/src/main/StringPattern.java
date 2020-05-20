package main;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class StringPattern
{
    private String pattern;

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
            else
            {
                addLiteral();
            }
        }
    }

    private void addLiteral()
    {
        StringBuilder literal = new StringBuilder();
        while(!pattern.substring(0,2).equals("?d"))
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
            number.append("*");
            pattern = pattern.substring(2);
        }
        representation.add(number.toString());
    }

    public List<String> getRepresentation()
    {
        return representation;
    }
}
