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
        int curValue = 0;
        while(maxNumericValueAchieved(list) && generatedStrings.size() < size)
        {
            generatedStrings.add(insertValueIntoString(list));
        }

        return generatedStrings;
    }
}
