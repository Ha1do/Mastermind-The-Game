package test.java.sk.tuke.kpi.kp.mastermind;

import main.java.sk.tuke.kpi.kp.mastermind.core.CodeGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class CodeGeneratorTest {

    @org.junit.Test
    public void testGenerateSecretCode() {
        CodeGenerator codeGenerator = new CodeGenerator();
        int[] code = codeGenerator.generateSecretCode();
        assertNotNull(code);
        assertEquals(4, code.length);
        for (int digit : code) {
            assertTrue(digit >= 0 && digit <= 9);
        }
    }
}