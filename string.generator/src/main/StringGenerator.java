package main;

import java.util.Set;

public interface StringGenerator
{
        Set<String> generate(String pattern, int rangeFrom, int size);
}
