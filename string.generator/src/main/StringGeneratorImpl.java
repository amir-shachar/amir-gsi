package main;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringGeneratorImpl implements StringGenerator
{
    @Override
    public Set<String> generate(String pattern, int rangeFrom, int size)
    {
        StringPattern stringPattern = new StringPattern(pattern);
        List<String> list = stringPattern.getRepresentation();
        Set<String> generatedStrings = new HashSet<>();
        int curValue = rangeFrom;
        while(maxNumericValueAchieved(list, curValue) && generatedStrings.size() < size)
        {
            generatedStrings.add(insertValueIntoString(list, curValue));
            curValue++;
        }

        return generatedStrings;
    }

    private boolean maxNumericValueAchieved(List<String> list, int value)
    {
        return Math.pow(10, getNumberOfDigits(list)) > value;
    }

    private int getNumberOfDigits(List<String> list)
    {
        return list.stream().filter(x -> x.contains("*")).map(String::length).reduce(Integer::sum).orElse(0);
    }

    private String insertValueIntoString(List<String> list, int curValue)
    {
        int index =0;
        String numericValue = String.valueOf(curValue);
        int stringIndex = 0;
        StringBuilder builder = new StringBuilder();
        while(index < list.size())
        {
            if(list.get(index).contains("*"))
            {
                int len = list.get(index).length();

                while( len > 0)
                {
                    if(stringIndex < numericValue.length())
                    {
                        builder.append(numericValue.charAt(stringIndex));
                        stringIndex++;
                    }
                    else
                    {
                        builder.append("0");
                    }
                    len--;
                }
            }
            else
            {
                builder.append(list.get(index));
            }
            index++;
        }
        return builder.toString();
    }
}
