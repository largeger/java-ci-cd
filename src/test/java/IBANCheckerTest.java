import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IBANCheckerTest {


    @BeforeEach
    void setUp() {

    }

    @Test
    void validateCorrect() {
        assertTrue(IBANChecker.validate("DE22790200760027913168"));
    }

    @Test
    void validateFalseChecksum() {
        assertFalse(IBANChecker.validate("DE21790200760027913173"));
    }

    @Test
    void validateFalseLength() {
        assertFalse(IBANChecker.validate("DE2179020076002791317"));
    }

    @Test
    void validateWrongCountryCode() {
        assertFalse(IBANChecker.validate("XX21790200760027913173"));
    }
}
