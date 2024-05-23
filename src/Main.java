
import java.text.NumberFormat;
import java.util.Scanner;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IllegalArgumentException {
        Scanner in = new Scanner(System.in);
        String input;
        while (true) {
            try {
                System.out.println("input: ");
                input = in.nextLine();

                String result;
                result = calc(input.toUpperCase());
                System.out.println(result);
            } catch (Exception e) {
                System.out.println("Некорректная операция!");
            }
        }
    }

    static String calc(String input) throws Exception {
        String[] array = input.split(" ");
        if(array.length > 3){
            throw new Exception();
        }
        String firstNum = array[0];
        String secondNum = array[2];
        String sign = array[1];

        if (isRoman(array)) {
            int num1 = converterRomanToInteger(firstNum);
            int num2 = converterRomanToInteger(secondNum);
            if (num1 < num2 && sign.equals("-")) {
                throw new ArithmeticException();
            }
            if (isInRange(num1) && isInRange(num2)) {
                return converterIntegerToRoman(calc(num1, num2, sign));
            }
        } else if (isArabian(array)) {
            int num1 = Integer.parseInt(firstNum);
            int num2 = Integer.parseInt(secondNum);
            if (num2 == 0 && sign.equals("/")) throw new ArithmeticException();

            if (isInRange(num1) && isInRange(num2)) {
                int result = calc(num1, num2, sign);
                return String.format("%d", result);
            }
        } else {
            throw new ArithmeticException();
        }
        return " ";
    }

    static int calc(int firstNum, int secondNum, String sign) {
        return switch (sign) {
            case "+" -> (firstNum + secondNum);
            case "-" -> (firstNum - secondNum);
            case "*" -> (firstNum * secondNum);
            case "/" -> (firstNum / secondNum);
            default -> 0;
        };
    }

    static boolean isInRange(int num) throws Exception {
        if (num >= 1 && num <= 10) {
            return true;
        } else {
            throw new Exception();
        }
    }

    enum RomanNum {
        I(1), II(2), III(3), IV(4), V(5), VI(6), VII(7), VIII(8), IX(9), X(10), XL(40), L(50), XC(90), C(100);
        final int key;

        RomanNum(int key) {
            this.key = key;
        }

        int getKey() {
            return key;
        }
    }

    static boolean isRoman(String[] input) {
        return converterRomanToInteger(input[0]) != -1 && converterRomanToInteger(input[2]) != -1;
    }

    static boolean isArabian(String[] input) {
        return converterRomanToInteger(input[0]) == -1 && converterRomanToInteger(input[2]) == -1;
    }

    static int converterRomanToInteger(String romanNumber) {
        for (RomanNum num : RomanNum.values()) {
            if (num.name().equals(romanNumber)) {
                return num.getKey();
            }
        }
        return -1;
    }

    static String converterIntegerToRoman(int romanNumber) {
        if (romanNumber == 100) return "C";
        if (romanNumber >= 50)
            return romanNumber >= 90 ? ("XC" + converterIntegerToRoman(romanNumber - 90)) : ("L" + converterIntegerToRoman(romanNumber - 50));
        if (romanNumber >= 10)
            return romanNumber >= 40 ? ("XL" + converterIntegerToRoman(romanNumber - 40)) : ("X" + converterIntegerToRoman(romanNumber - 10));

        if (romanNumber >= 5)
            return romanNumber == 9 ? "IX" : "V" + converterIntegerToRoman(romanNumber - 5);

        if (romanNumber > 0)
            return romanNumber == 4 ? "IV" : "I" + converterIntegerToRoman(romanNumber - 1);

        return "";
    }
}