package main;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static main.StringPattern.ALPHABET_CHAR;
import static main.StringPattern.ALPHANUMERIC_CHAR;
import static main.StringPattern.DECIMAL_CHAR;

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
        while(maxValuesHadNotBeenReached(list, curValue) && generatedStrings.size() < size)
        {
            generatedStrings.add(insertValueIntoString(list, curValue));
            curValue++;
        }

        return generatedStrings;
    }

    private boolean maxValuesHadNotBeenReached(List<String> list, int curValue)
    {
        return maxNumericValueNotAchieved(list, curValue) ||
                maxAlphaBeticalValueNotAchieved(list,curValue) ||
                maxAlphaNumericValueNotAchieved(list, curValue);
    }

    private boolean maxAlphaNumericValueNotAchieved(List<String> list, int value)
    {
        return Math.pow(36, getLengthOfSegmentsWith(list, ALPHANUMERIC_CHAR)) > value;
    }

    private void assertValidPattern(String pattern) throws InvalidCharacterException
    {
        new PatternValidator(pattern).assertValid();
    }

    private boolean maxNumericValueNotAchieved(List<String> list, int value)
    {
        return Math.pow(10, getLengthOfSegmentsWith(list, DECIMAL_CHAR)) > value;
    }

    private boolean maxAlphaBeticalValueNotAchieved(List<String> list, int value)
    {
        return Math.pow(26, getLengthOfSegmentsWith(list, ALPHABET_CHAR)) > value;
    }

    private int getLengthOfSegmentsWith(List<String> list, String pattern)
    {
        return list.stream().filter(x -> x.contains(pattern)).map(String::length).reduce(Integer::sum).orElse(0);
    }

    private String insertValueIntoString(List<String> list, int curValue)
    {
        int index =0;
        String numericValue = buildNumericString(getLengthOfSegmentsWith(list, DECIMAL_CHAR),curValue);
        String alphaValue = buildOrderedString(getLengthOfSegmentsWith(list, ALPHABET_CHAR),curValue);
        String alphaNumericValue = buildAlphaNumericString(getLengthOfSegmentsWith(list, ALPHANUMERIC_CHAR),curValue);
        int numericValueIndex = 0;
        int alphaValueIndex = 0;
        int alphaNumericValueIndex = 0;
        StringBuilder builder = new StringBuilder();
        while(index < list.size())
        {
            if(list.get(index).contains(DECIMAL_CHAR))
            {
                numericValueIndex = addFromString(list.get(index).length(), numericValue, numericValueIndex, builder);
            }
            else if (list.get(index).contains(ALPHABET_CHAR))
            {
                alphaValueIndex = addFromString(list.get(index).length(),alphaValue, alphaValueIndex, builder);
            }
            else if(list.get(index).contains(ALPHANUMERIC_CHAR))
            {
                alphaNumericValueIndex = addFromString(list.get(index).length(),alphaNumericValue, alphaNumericValueIndex, builder);
            }
            else
            {
                builder.append(list.get(index));
            }
            index++;
        }
        return builder.toString();
    }

    private String buildAlphaNumericString(int len, int curValue)
    {
        StringBuilder builder = new StringBuilder();
        while(builder.length()< len)
        {
            int remainder = curValue%36;
            if(remainder < 10)
            {
                builder.append((char)('0' + remainder));
            }
            else
            {
                remainder-=10;
                builder.append((char)('a' + remainder));
            }

            curValue /= 36;
        }
        return builder.reverse().toString();
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

    private int addFromString(int len, String alphaValue, int alphaIndex, StringBuilder builder)
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
