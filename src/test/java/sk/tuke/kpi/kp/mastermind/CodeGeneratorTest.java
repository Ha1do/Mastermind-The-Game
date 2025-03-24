package test.java.sk.tuke.kpi.kp.mastermind;

import main.java.sk.tuke.kpi.kp.mastermind.core.CodeGenerator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CodeGeneratorTest {

    @Test
    public void testGenerateSecretCodeLength() {
        int codeLength = 4;
        CodeGenerator codeGenerator = new CodeGenerator(codeLength);
        int[] code = codeGenerator.generateSecretCode();
        assertEquals(codeLength, code.length, "The generated code length should match the specified code length.");
    }

    @Test
    public void testGenerateSecretCodeValues() {
        int codeLength = 4;
        CodeGenerator codeGenerator = new CodeGenerator(codeLength);
        int[] code = codeGenerator.generateSecretCode();
        for (int value : code) {
            assertTrue(value >= 0 && value <= 9, "Each digit in the generated code should be between 0 and 9.");
        }
    }

    @Test
    public void testGenerateSecretCodeDifferentLengths() {
        for (int codeLength = 1; codeLength <= 10; codeLength++) {
            CodeGenerator codeGenerator = new CodeGenerator(codeLength);
            int[] code = codeGenerator.generateSecretCode();
            assertEquals(codeLength, code.length, "The generated code length should match the specified code length.");
        }
    }

    @Test
    public void testGenerateSecretCodeRandomness() {
        int codeLength = 4;
        CodeGenerator codeGenerator = new CodeGenerator(codeLength);
        int[] code1 = codeGenerator.generateSecretCode();
        int[] code2 = codeGenerator.generateSecretCode();
        assertNotEquals(java.util.Arrays.toString(code1), java.util.Arrays.toString(code2), "Two generated codes should not be the same.");
    }

    @Test
    public void testGenerateSecretCodeMinLength() {
        int codeLength = 1;
        CodeGenerator codeGenerator = new CodeGenerator(codeLength);
        int[] code = codeGenerator.generateSecretCode();
        assertEquals(codeLength, code.length, "The generated code length should match the specified minimum code length.");
    }

    @Test
    public void testGenerateSecretCodeMaxLength() {
        int codeLength = 50;
        CodeGenerator codeGenerator = new CodeGenerator(codeLength);
        int[] code = codeGenerator.generateSecretCode();
        assertEquals(codeLength, code.length, "The generated code length should match the specified maximum code length.");
    }
}