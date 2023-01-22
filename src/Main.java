import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Введите выражение:");
        String input = scan.nextLine();
        System.out.println(calc(input));
    }

    private static int romanToArabic(String romanNumber) {
        int value = 0;
        int lastDigitValue = 0;
        for (int i = romanNumber.length() - 1; i >= 0; i--) {
            RomanNumerals romanDigit = RomanNumerals.valueOf(Character.toString(romanNumber.charAt(i)));
            int digitValue = romanDigit.getValue();
            if (digitValue >= lastDigitValue) {
                value += digitValue;
                lastDigitValue = digitValue;
            } else
                value -= digitValue;
        }
        return value;
    }

    private static String arabicToRoman(int arabicNumber) {
        StringBuilder finalRomanNumber = new StringBuilder();
        RomanNumerals[] romanNumbers = RomanNumerals.values();
        int i = romanNumbers.length - 1;
        while (arabicNumber > 0) {
            RomanNumerals currentRomanNumber = RomanNumerals.valueOf(String.valueOf(romanNumbers[i]));
            int currentNumber = currentRomanNumber.getValue();
            if (arabicNumber >= currentNumber) {
                finalRomanNumber.append(currentRomanNumber);
                arabicNumber -= currentNumber;
            } else {
                i--;
            }
        }
        return finalRomanNumber.toString();
    }

    private static boolean isRoman(String string) {
        return Pattern.matches("^(M{0,3})(D?C{0,3}|C[DM])(L?X{0,3}|X[LC])(V?I{0,3}|I[VX])$", string);
    }

    //    public static String calc(String input) throws Exception {
//        int result = 0;
//        boolean isRoman = false;
//        String[] splitInput = input.split(" ");
//        if (splitInput.length == 3) {
//            int number1, number2;
//            if (isRoman(splitInput[0]) == true && isRoman(splitInput[2]) == true) {
//                isRoman = true;
//                number1 = romanToArabic(splitInput[0]);
//                number2 = romanToArabic(splitInput[2]);
//            } else {
//                number1 = Integer.parseInt(splitInput[0]);
//                number2 = Integer.parseInt(splitInput[2]);
//            }
//            String operation = splitInput[1];
//            if ((number1 >= 1) && (number1 <= 10) && (number2 >= 1) && (number2 <= 10)) {
//                switch (operation) {
//                    case "+":
//                        result = number1 + number2;
//                        break;
//                    case "-":
//                        result = number1 - number2;
//                        break;
//                    case "*":
//                        result = number1 * number2;
//                        break;
//                    case "/":
//                        result = number1 / number2;
//                        break;
//                    default:
//                        System.out.println("Выражение записано не правильно");
//                }
//            } else {
//                return "Числа должны быть от 1 до 10";
//            }
//            if (isRoman == true) {
//                if (result > 0) {
//                    return arabicToRoman(result);
//                } else {
//                    return "Ответ не может быть отрицательным или равным нулю";
//                }
//            } else {
//                return Integer.toString(result);
//            }
//        } else {
//            try {
//                throw new Exception();
//            } catch (Exception e) {
//                return "Неверный ввод";
//            }
//        }
//    }
    private static boolean isDigit(String s) {
        return Pattern.matches("[-+]?\\d+", s);
    }

    public static String calc(String input) {
        int result = 0;
        boolean isRoman = false;
        String[] splitInput = input.split(" ");
        if (splitInput.length != 3) {
            try {
                throw new IOException();
            } catch (IOException e) {
                return "Выражение записано не верно";
            }
        }
        int number1, number2;
        try {
            if ((isRoman(splitInput[0]) == true && isRoman(splitInput[2]) == false && isDigit(splitInput[2])) |
                    (isRoman(splitInput[0]) == false && isRoman(splitInput[2]) == true && isDigit(splitInput[0]))) {
                throw new IOException();
            }
        } catch (IOException e) {
            return "Нельзя использовать одновременно разные системы счисления";
        }

        if (isRoman(splitInput[0]) == true && isRoman(splitInput[2]) == true) {
            isRoman = true;
            number1 = romanToArabic(splitInput[0]);
            number2 = romanToArabic(splitInput[2]);
        } else {
            try {
                number1 = Integer.parseInt(splitInput[0]);
                number2 = Integer.parseInt(splitInput[2]);
            } catch (NumberFormatException e) {
                return "Числа введены не корректно";
            }
        }
        String operation = splitInput[1];
        try {
            if ((number1 < 1) || (number1 > 10) || (number2 < 1) || (number2 > 10)) {
                throw new IOException();
            }
        } catch (IOException e) {
            return "Числа должны быть от 1 до 10";
        }
        try {
            switch (operation) {
                case "+":
                    result = number1 + number2;
                    break;
                case "-":
                    result = number1 - number2;
                    break;
                case "*":
                    result = number1 * number2;
                    break;
                case "/":
                    result = number1 / number2;
                    break;
                default:
                    throw new IOException();
            }
        } catch (IOException e) {
            return "Не верно введеная арифметическая операция";
        }

        if (isRoman == true) {
            if (result < 1) {
                try {
                    throw new IllegalArgumentException();
                } catch (IllegalArgumentException e) {
                    return "Ответ в римской системе не может быть отрицательным или равным нулю";
                }
            }
            return arabicToRoman(result);
        } else {
            return Integer.toString(result);
        }
    }
}