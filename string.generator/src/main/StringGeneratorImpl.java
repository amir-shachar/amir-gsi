package main;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringGeneratorImpl implements StringGenerator
{
    @Override
    public Set<String> generate(String pattern, int rangeFrom, int size)
    {
        assertValidPattern(pattern);
        StringPattern stringPattern = new StringPattern(pattern);
        List<String> list = stringPattern.getRepresentation();
        Set<String> generatedStrings = new HashSet<>();
        int curValue = rangeFrom;
        while((maxNumericValueNotAchieved(list, curValue) || maxAlphaBeticalValueNotAchieved(list,curValue))
                && generatedStrings.size() < size)
        {
            generatedStrings.add(insertValueIntoString(list, curValue));
            curValue++;
        }

        return generatedStrings;
    }

    private void assertValidPattern(String pattern) throws InvalidCharacterException
    {
        new PatternValidator(pattern).assertValid();
    }

    private boolean maxNumericValueNotAchieved(List<String> list, int value)
    {
        return Math.pow(10, getLengthOfSegmentsWith(list, "*")) > value;
    }

    private boolean maxAlphaBeticalValueNotAchieved(List<String> list, int value)
    {
        return Math.pow(10, getLengthOfSegmentsWith(list, "#")) > value;
    }

    private int getLengthOfSegmentsWith(List<String> list, String pattern)
    {
        return list.stream().filter(x -> x.contains(pattern)).map(String::length).reduce(Integer::sum).orElse(0);
    }

    private String insertValueIntoString(List<String> list, int curValue)
    {
        int index =0;
        String numericValue = buildNumericString(getLengthOfSegmentsWith(list, "*"),curValue);
        String alphaValue = buildOrderedString(getLengthOfSegmentsWith(list, "&"),curValue);
        int numericValueIndex = 0;
        int alphaValueIndex = 0;
        StringBuilder builder = new StringBuilder();
        while(index < list.size())
        {
            if(list.get(index).contains("*"))
            {
                numericValueIndex = addNumeric(list.get(index).length(), numericValue, numericValueIndex, builder);
            }
            else if (list.get(index).contains("&"))
            {
                alphaValueIndex = addAlpha(list.get(index).length(),alphaValue, alphaValueIndex, builder);
            }
            else
            {
                builder.append(list.get(index));
            }
            index++;
        }
        return builder.toString();
    }

    private String buildOrderedString(int len, int curValue)
    {
        StringBuilder builder = new StringBuilder();
        while(builder.length()< len)
        {
            builder.append((char)('a' + curValue % 26));
            curValue /= 26;
        }
        return builder.reverse().toString();
    }

    private String buildNumericString(int len, int curValue)
    {
        StringBuilder builder = new StringBuilder();
        while(builder.length()< len)
        {
            builder.append((char)('0' + curValue % 10));
            curValue /= 10;
        }
        return builder.reverse().toString();
    }

    private int addNumeric(int len , String numericValue, int stringIndex, StringBuilder builder)
    {
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
        return stringIndex;
    }

    private int addAlpha(int len, String alphaValue, int alphaIndex, StringBuilder builder)
    {
        while( len > 0)
        {
            builder.append(alphaValue.charAt(alphaIndex));
            alphaIndex++;
            len--;
        }
        return alphaIndex;
    }

    private class PatternValidator
    {
        private String pattern;
        private Set<String> patternLetters;
        private String staticLetters = "!@#$";

        public PatternValidator(String pattern)
        {
            this.pattern = pattern;
            initializeFields();
        }

        private void initializeFields()
        {
            patternLetters = new HashSet<>();
            patternLetters.add("?d");
            patternLetters.add("?l");
            patternLetters.add("?a");
        }

        public void assertValid()
        {
            int i =0 ;

            while (i < pattern.length())
            {
                if(isAValidSingleLetter(i))
                {
                    i++;
                }
                else if(isAValidPatternLetter(i))
                {
                    i+=2;
                }
                else
                {
                    throw new InvalidCharacterException();
                }
            }
        }

        private boolean isAValidPatternLetter(int i)
        {
            return i+2 <= pattern.length() && patternLetters.contains(pattern.substring(i,i+2));
        }

        private boolean isAValidSingleLetter(int i)
        {
            return Character.isUpperCase(pattern.charAt(i)) ||
                    Character.isLowerCase(pattern.charAt(i)) ||
                    staticLetters.contains(pattern.substring(i,i+1));
        }
    }
}
