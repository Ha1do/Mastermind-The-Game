package sk.tuke.kpi.kp.gamestudio.core;

import java.util.Random;

public class CodeGenerator
{
    private final int code_length;

    public CodeGenerator (int code_length)
    {
        this.code_length = code_length;
    }

    public int[] generateSecretCode()
    {
        Random rand = new Random();
        int[] code = new int[code_length];
        for (int i = 0; i < code_length; i++)
            code[i] = rand.nextInt(10); // Random from 0 to 9

//        System.out.println("The secret code is " + java.util.Arrays.toString(code)); // for debugging
        return code;
    }
}
