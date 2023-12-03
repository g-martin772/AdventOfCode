import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        File file = new File("input");
        List<String> data = new java.util.ArrayList<>(Collections.emptyList());
        try {
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextLine())
                data.add(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<Integer> numbers = new ArrayList<Integer>();

        for (int i = 0; i < data.size(); i++) {
            String line = data.get(i);

            List<Character> numberChars = new java.util.ArrayList<>(Collections.emptyList());
            for (int j = 0; j < line.length(); j++) {
                if(Character.isDigit(line.charAt(j)))
                    numberChars.add(line.charAt(j));
                else if (!numberChars.isEmpty()) {
                    check(numberChars, data, i, j, numbers);
                }
            }

            if (!numberChars.isEmpty())
                check(numberChars, data, i, line.length() - 1, numbers);
            System.out.println(line);
        }

        Integer result = 0;
        for (Integer num : numbers) {
            result += num;
            System.out.println(num);
        }
        System.out.printf("Result: %d%n", result);
    }

    private static void check(List<Character> numberChars, List<String> data, int i, int j, List<Integer> numbers) {
        boolean hasSymbol = false;
        for (int k = 0; k < numberChars.size() + 2; k++) {
            for (int l = -1; l < 2; l++) {
                try {
                    char c = data.get(i - l).charAt(j - k);
                    if (!Character.isDigit(c) && c != '.')
                        hasSymbol = true;
                } catch (Exception e) {
                    continue;
                }
            }
        }
        if(hasSymbol)
            numbers.add(Integer.parseInt(numberChars.stream().map(String::valueOf).collect(Collectors.joining())));
        numberChars.clear();
    }
}