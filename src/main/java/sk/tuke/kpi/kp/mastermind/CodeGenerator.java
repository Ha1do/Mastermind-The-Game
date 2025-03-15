package main.java.sk.tuke.kpi.kp.mastermind;

import java.util.Random;

public class CodeGenerator
{
    private static final int CODE_LENGTH = 4;

    public int[] generateSecretCode()
    {
        Random rand = new Random();
        int[] code = new int[CODE_LENGTH];
        for (int i = 0; i < CODE_LENGTH; i++)
            code[i] = rand.nextInt(10); // Random from 0 to 9

        System.out.println("The secret code is " + java.util.Arrays.toString(code)); // for debugging
        return code;
    }
}
