public class Computor {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Computor \"equation\"");
        } else {
            // DONE 0. VALIDATION
            // DONE 1. REDUCED FORM (print)
                // 1.1 DISPLAY INTERMEDIATE STEPS
            // DONE 2. POLYNOMIAL DEGREE (print)
                // DONE 2.1 IF DEGREE = 2, DISPLAY DISCRIMINANT
                    // 2.1.1 DISPLAY INTERMEDIATE STEPS
            // DONE 3. SOLUTION (print)
            String niceUserInput = args[0].replaceAll(" ", "").toUpperCase();
            if (isValidEquationSyntax(niceUserInput)) {
                String formattedInput = niceUserInput.replaceAll("-", "+-");
                BCMath.solve(formattedInput);
            } else {
                System.out.println("Please enter a properly formatted equation.");
            }
        }
    }

    private static boolean isValidEquationSyntax(String input) {
        // Only valid characters allowed
        if (!input.matches("[0-9X\\.\\+\\-\\^\\*\\=]+")) {
            return false;
        }

        // single = sign splitting sides
        String formattedInput = input.replaceAll("-", "+-");
        String[] equalSplit = formattedInput.split("=");
        if (equalSplit.length == 2) {
            String[] leftTerms = equalSplit[0].split("\\+");
            String[] rightTerms = equalSplit[1].split("\\+");
            if (equalSplit[0].length() == 0 || equalSplit[1].length() == 0 ||
                !isProperlyFormatted(leftTerms) || !isProperlyFormatted(rightTerms)) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private static boolean isProperlyFormatted(String[] terms) {
        if (terms.length == 0) {
            return false;
        }
        for (int i = 0; i < terms.length; i++) {
            if (terms[i].equals("")) {
                if (i == 0) {
                    continue;
                } else {
                    return false;
                }
            }
            // [-]?[0-9]+(\\.[0-9]+)? for int or double
            // X(\\^[-]?[0-9]+(\\.[0-9]+)?)? for X with or without power
            if (!terms[i].matches("[-]?[0-9]+(\\.[0-9]+)?(\\*X(\\^[-]?[0-9]+(\\.[0-9]+)?)?)?|X(\\^[-]?[0-9]+(\\.[0-9]+)?)?")) {
                return false;
            }
        }
        return true;
    }
}