package conversio;

import java.util.Hashtable;

/**
 * The Romanizer class implements an conversion of a decimal number into
 * roman number.
 *
 * @author: Daniil Ivanov
 */
class Romanizer {
    private Hashtable<Integer, Character> map = new Hashtable<Integer, Character>(7);

    /**
    * Public constructor for Romanizer class. Performs initial initialization.
    */
    public Romanizer() {
        map.put(1, 'I');
        map.put(5, 'V');
        map.put(10, 'X');
        map.put(50, 'L');
        map.put(100, 'C');
        map.put(500, 'D');
        map.put(1000, 'M');
    }

    /**
    * Converts string representation of decimal decimal numeral into string
    * representation of Roman numeral
    *
    * @throws IllegalArgumentException when @value isn't decimal decimal
    *
    * @param  value string representation of decimal numeral
    * @return       string representation of Roman numeral
    */
    public String convertDecimalToRoman(String value) throws IllegalArgumentException {
        try {
            int decimal = Integer.valueOf(value);
            /* Not decimal number */
            if (decimal <= 0)
                throw new IllegalArgumentException();

            /* Let's pretend we do not support numbers larger than 3000 */
            if (decimal > 3000)
                throw new IllegalArgumentException();

            String romanNumeral = new String("");
            int base1 = 1000, base2 = 5000;
            while (decimal > 0) {
                Character symbol2, symbol1;

                /* Try high base */
                symbol2 = map.get(base2);
                while (symbol2 != null && decimal >= base2) {
                    decimal -= base2;
                    romanNumeral += symbol2;
                }

                /* Try high base - low base difference */
                symbol1 = map.get(base1);
                if (symbol1 != null && symbol2 != null && decimal >= base2 - base1) {
                    decimal -= base2 - base1;
                    romanNumeral += symbol1;
                    romanNumeral += symbol2;
                }

                /* Try low base */
                while (symbol1 != null && decimal >= base1) {
                    decimal -= base1;
                    romanNumeral += symbol1;
                }

                /* Forbidden: low base - next high base */
                /* Try low base - next low base */
                symbol2 = map.get(base1 / 10);
                if (symbol1 != null && symbol2 != null && decimal >= base1 - base1 / 10) {
                    decimal -= base1 - base1 / 10;
                    romanNumeral += symbol2;
                    romanNumeral += symbol1;
                }
                base1 /= 10;
                base2 /= 10;
            }

            return romanNumeral;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }
}
