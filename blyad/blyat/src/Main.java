import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ваши числа и действие:");
        String input = scanner.nextLine();
        // Должен быть вывод результата
        System.out.println(calc(input));
    }

    public static String calc(String input) {
        String[] parts = input.split(" "); // Значения разделенные пробелом 101 + 102
        // исключение
        if (parts.length !=3){
            throw new IllegalArgumentException("Ошибка:Пример должен быть вида 'Число операция число'.");
        }
        // создание двух переменных типом String
        String strA = parts[0];
        String strB = parts[2];
        // Правда/Ложь
        boolean isRoman = isRomanNumerals(strA) && isRomanNumerals(strB);
        boolean isArabic = isArabicNumerals(strA) && isArabicNumerals(strB);
        // Исключение, если формат Рим/Араб разный
        if (!isRoman && !isArabic) {
            throw new IllegalArgumentException("Ошибка: Цифры должны быть арабскими или римскими.");
        }
        int a, b, result;
        if (isRoman){
            a = romanToArabic(strA);
            b = romanToArabic(strB);
        } else {
            a = Integer.parseInt(parts[0]);
            b = Integer.parseInt(parts[2]);
        }
        String action = parts[1];
        if (a<1 || a>10 || b<1 || b>10){
            throw new IllegalStateException("Ошибка - числа вышли за пределы");
        }
        switch (action) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                if (a==0 || b==0){
                    throw new IllegalStateException("Делить на 0 нельзя");
                }
                result = a / b;
                break;
            default: // Если выбрано действие, которого нет.
                throw new IllegalStateException("Unexpected value: " + action);
        }
        if(isArabic){
            return String.valueOf(result); // Возвращает строку переданного результата int в main
        } else return arabicToRoman(result);
    }
    private static boolean isArabicNumerals(String str) {
        return str.matches("-?\\d+");
    }
    private static boolean isRomanNumerals(String str) {
        return str.matches("[IVXLC]+");
    }
    private static int romanToArabic(String s) {  //принимает строку - возвращает int
        Map<Character , Integer> map = new HashMap<>();
        {
            map.put('I', 1);  // map - структура данных put - назначае(принимает) get - передает
            map.put('V', 5);
            map.put('X', 10);
            map.put('L', 50);
            map.put('C', 100);
        }

        int resultRoman = 0;
        int prev = 0;

        for (int i = s.length() - 1; i >= 0; i--) {
            int curr = map.get(s.charAt(i));
            if (curr < prev) {
                resultRoman -= curr;
            } else {
                resultRoman += curr;
            }
            if (curr < prev) {
                resultRoman -= curr;
            } else {
                resultRoman += curr;
            }
            if (curr < prev && (prev == 5 || prev == 10)){
                resultRoman += curr;
            }
            prev = curr;
        }
        return resultRoman;
    }
    private static String arabicToRoman(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Римские числа могут быть только положительными.");
        }

        int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanValues = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < arabicValues.length; i++) {
            while (number >= arabicValues[i]) {
                result.append(romanValues[i]);
                number -= arabicValues[i];
            }
        }
        return result.toString();
    }
}

