public class BCMath {
    private static double coefA = 0.0;
    private static double coefB = 0.0;
    private static double coefC = 0.0;
    private static int highestDegree = 0;
    private static boolean invalidDegree = false;

    public static void solve(String input) {
        String reduced = reduce(input);
        if (reduced != null) {
            System.out.println("Reduced equation: "+reduced);
            System.out.println("Polynomial degree: "+String.valueOf(highestDegree));
            switch (highestDegree) {
                case 2:
                    solveDegreeTwo();
                    break;
                case 1:
                    solveDegreeOne();
                    break;
                default:
                    break;
            }
        }

    }

    public static String reduce(String input) {
        StringBuilder reducedEquation = new StringBuilder();
        String firstSign = " + ";
        String secondSign = " + ";

        String[] equalSplit = input.split("=");
        if (equalSplit.length == 2) {
            String[] leftTerms = equalSplit[0].split("\\+");
            String[] rightTerms = equalSplit[1].split("\\+");

            for (String string : leftTerms) {
                if (string.length() == 0) {
                    continue;
                }
                String[] splitTerm = string.split("\\*");
                if (splitTerm.length == 2) {
                    switch (splitTerm[1]) {
                        case "X^2":
                            coefA += getCoefficient(splitTerm[0]);
                            highestDegree = 2;
                            break;
                        case "X^1":
                        case "X":
                            coefB += getCoefficient(splitTerm[0]);
                            if (highestDegree == 0) {
                                highestDegree = 1;
                            }
                            break;
                        case "X^0":
                            coefC += getCoefficient(splitTerm[0]);
                            break;
                        default:
                            invalidDegree = true;
                    }
                } else if (splitTerm.length == 1) {
                    switch (splitTerm[0]) {
                        case "X^2":
                            coefA += 1;
                            highestDegree = 2;
                            break;
                        case "X^1":
                        case "X":
                            coefB += 1;
                            if (highestDegree == 0) {
                                highestDegree = 1;
                            }
                            break;
                        case "X^0":
                            coefC += 1;
                            break;
                        default:
                            coefC += getCoefficient(splitTerm[0]);
                    }
                } else {
                    return null;
                }
            }

            for (String string : rightTerms) {
                if (string.length() == 0) {
                    continue;
                }
                String[] splitTerm = string.split("\\*");
                if (splitTerm.length == 2) {
                    switch (splitTerm[1]) {
                        case "X^2":
                            coefA -= getCoefficient(splitTerm[0]);
                            highestDegree = 2;
                            break;
                        case "X^1":
                        case "X":
                            coefB -= getCoefficient(splitTerm[0]);
                            if (highestDegree == 0) {
                                highestDegree = 1;
                            }
                            break;
                        case "X^0":
                            coefC -= getCoefficient(splitTerm[0]);
                            break;
                        default:
                            invalidDegree = true;
                    }
                } else {
                    switch (splitTerm[0]) {
                        case "X^2":
                            coefA -= 1;
                            highestDegree = 2;
                            break;
                        case "X^1":
                        case "X":
                            coefB -= 1;
                            if (highestDegree == 0) {
                                highestDegree = 1;
                            }
                            break;
                        case "X^0":
                            coefC -= 1;
                            break;
                        default:
                            coefC -= getCoefficient(splitTerm[0]);
                    }
                }
            }

        }

        if (invalidDegree) {
            System.out.println("Sorry! This program only solves for degrees 0, 1 and 2.");
            return null;
        }

        if (coefB < 0) {
            firstSign = " ";
        }

        if (coefC < 0) {
            secondSign = " ";
        }

        if (coefA == 0 && coefB == 0) {
            if (coefC == 0) {
                System.out.println("All real numbers are a solution.");
            } else {
                System.out.println("There is no solution.");
            }
            return null;
        }

        if (coefA != 0) {
            reducedEquation.append(coefA);
            reducedEquation.append(" * X^2");
        }
        
        if (coefB != 0) {
            if (coefA != 0) {
                reducedEquation.append(firstSign);
            }
            reducedEquation.append(coefB);
            reducedEquation.append(" * X^1");
        }

        if (coefC != 0) {
            if (coefB != 0 || coefA != 0) {
                reducedEquation.append(secondSign);
            }
            reducedEquation.append(coefC);
            reducedEquation.append(" * X^0");
        }
        
        reducedEquation.append(" = 0");

        return reducedEquation.toString().replaceAll("-", "- ");
    }

    private static double getCoefficient(String input) {
        double coeff = 0;
        try {
            coeff = Double.parseDouble(input);
        } catch (Exception e) {
            invalidDegree = true;
        }
        return coeff;
    }

    public static void solveDegreeOne() {
        double solution = (-1 * coefC) / coefB;
        System.out.println("The soultion is:");
        System.out.println("X = "+Double.toString(solution));
    }

    public static void solveDegreeTwo() {
        double discriminant = calculateDiscriminant();
        System.out.println("Discriminant = "+Double.toString(discriminant));

        if (discriminant > 0) {
            double solutionOne = (-1 * coefB + sqrt(discriminant)) / (2 * coefA);
            double solutionTwo = (-1 * coefB - sqrt(discriminant)) / (2 * coefA);
            System.out.println("The discriminant is positive, therefore there are two real solutions:");
            System.out.println("X1 = "+Double.toString(solutionOne));
            System.out.println("X2 = "+Double.toString(solutionTwo));

        } else if (discriminant == 0) {
            double solution = (-1 * coefB) / (2 * coefA);
            System.out.println("The discriminant is zero, therefore there is only one solution:");
            System.out.println("X = "+Double.toString(solution)); 

        } else {
            double solutionReal = (-1 * coefB) / (2 * coefA);
            double solutionImaginary = sqrt(-1 * discriminant) / (2 * coefA);
            System.out.println("The discriminant is negative, therefore there are two complex solutions:");
            System.out.println("X1 = "+Double.toString(solutionReal)+" + "+Double.toString(solutionImaginary)+" * i");
            System.out.println("X2 = "+Double.toString(solutionReal)+" - "+Double.toString(solutionImaginary)+" * i");
        }
    }

    public static double sqrt(double a) {
        // newtons method
        double guess = getGuess(a);
        int iterations = 10;

        while (iterations > 0) {
            guess = guess - (guess * guess - a) / (2 * guess);
            iterations--;
        }
        return guess;
    }

    private static double getGuess(double a) {
        double i = 1.0;

        if (a <= 1.0) {
            return 1.0;
        }

        while (i * i < a) {
            i++;
            if (i * i == a) {
                return i;
            }
        }

        return (i - 1);
    }

    private static double calculateDiscriminant() {
        return coefB * coefB - 4 * coefA * coefC;
    }
}