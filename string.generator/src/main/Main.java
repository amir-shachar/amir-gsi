package main;

import java.util.Set;

public class Main
{
    public static void main(String[] args)
    {
        StringGenerator generator = new StringGeneratorImpl();
        Set<String> strings = generator.generate("AM?d?dIR", 0, 25);
        for(String str : strings)
        {
            System.out.println(str);
        }
        System.out.println("size is :" + strings.size());
    }

}
