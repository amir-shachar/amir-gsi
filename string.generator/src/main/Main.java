package main;

import java.util.Set;

public class Main
{
    public static void main(String[] args)
    {
        StringGenerator generator = new StringGeneratorImpl();
        Set<String> strings = generator.generate("amir?d", 0, 5);
        for(String str : strings)
        {
            System.out.println(str);
        }
    }

}
