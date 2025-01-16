import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * The IBANChecker class provides a method to validate an IBAN (International Bank Account Number).
 */
public class IBANChecker {
    private static final Map<String, Integer> chars = new HashMap<>();

    static {
        chars.put("AT", 20);
        chars.put("BE", 16);
        chars.put("CZ", 24);
        chars.put("DE", 22);
        chars.put("DK", 18);
        chars.put("FR", 27);
    }

    public static void main(String[] args) {
        String iban = "DE227902007600279131";
        System.out.println("Welcome to the IBAN Checker!");
        System.out.println("IBAN " + iban + " is " + validate(iban));
    }

    /**
     * Validates the provided IBAN (International Bank Account Number) string.
     * <p>
     * This method performs the following steps to validate the IBAN:
     * 1. Checks the length of the IBAN based on the country code.
     * 2. Rearranges the IBAN to move the country code and check digit to the end.
     * 3. Converts the IBAN to an integer representation.
     * 4. Splits the integer representation into segments.
     * 5. Calculates the remainder of the segments divided by 97.
     * 6. Returns true if the remainder is 1, indicating a valid IBAN.
     *
     * @param iban the IBAN string to be validated
     * @return true if the IBAN is valid, false otherwise
     */
    public static boolean validate(String iban) {
        if (!checkLength(iban)) {
            return false;
        }

        String rearrangedIban = rearrangeIban(iban);
        String convertedIban = convertToInteger(rearrangedIban);
        List<String> segments = createSegments(convertedIban);

        return calculate(segments) == 1;
    }

    /**
     * Rearranges the IBAN by moving the country code and check digit to the end.
     * @param segments
     * @return
     */
    private static int calculate(List<String> segments) {
        long n = 0;

        for (String segment : segments) {
            if (segment.length() == 9) {
                n = Long.parseLong(segment) % 97;
            } else {
                segment = n + segment;
                n = Long.parseLong(segment) % 97;
            }
        }

        return (int) n;
    }

    /**
     * Checks if the length of the IBAN matches the expected length based on the country code.
     * @param iban
     * @return
     */
    private static boolean checkLength(String iban) {
        String countryCode = iban.substring(0, 2);
        return chars.containsKey(countryCode) && chars.get(countryCode) == iban.length();
    }

    /**
     * Converts the given IBAN (International Bank Account Number) string to an integer representation.
     * This method is used as part of the IBAN validation process.
     *
     * @param iban the IBAN string to be converted
     * @return the integer representation of the IBAN
     */
    private static String convertToInteger(String iban) {
        StringBuilder convertedIban = new StringBuilder();
        String upperIban = iban.toUpperCase();

        for (char c : upperIban.toCharArray()) {
            if (Character.isDigit(c)) {
                convertedIban.append(c);
            }
            if (Character.isLetter(c)) {
                convertedIban.append(c - 55);
            }
        }

        return convertedIban.toString();
    }

    /**
     * Splits the IBAN into segments of length 9.
     * @param iban
     * @return
     */
    private static List<String> createSegments(String iban) {
        List<String> segments = new ArrayList<>();
        String remainingIban = iban;

        segments.add(remainingIban.substring(0, 9));
        remainingIban = remainingIban.substring(9);

        while (remainingIban.length() >= 9) {
            segments.add(remainingIban.substring(0, 7));
            remainingIban = remainingIban.substring(7);
        }

        segments.add(remainingIban);
        return segments;
    }

    /**
     * Moves the country code and check digit to the end of the IBAN.
     * @param iban
     * @return
     */
    private static String rearrangeIban(String iban) {
        return iban.substring(4) + iban.substring(0, 4);
    }
}
