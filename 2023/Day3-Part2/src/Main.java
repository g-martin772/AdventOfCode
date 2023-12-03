import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static class Gear {
        public int x, y, val1, val2;
        public Gear(int x, int y, int val1, int val2) {

            this.x = x;
            this.y = y;
            this.val1 = val1;
            this.val2 = val2;
        }
    }
    public static List<Gear> gears = new java.util.ArrayList<>(Collections.emptyList());

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

        for (int i = 0; i < data.size(); i++) {
            String line = data.get(i);

            List<Character> numberChars = new java.util.ArrayList<>(Collections.emptyList());
            for (int j = 0; j < line.length(); j++) {
                if(Character.isDigit(line.charAt(j)))
                    numberChars.add(line.charAt(j));
                else if (!numberChars.isEmpty()) {
                    check(numberChars, data, i, j);
                }
            }

            if (!numberChars.isEmpty())
                check(numberChars, data, i, line.length() - 1);
            System.out.println(line);
        }

        int result = 0;
        for (int i = 0; i < gears.size(); i++) {
            Gear g = gears.get(i);
            if(g.val2 != -1)
                result += g.val1 * g.val2;
            System.out.printf("Gear %d: %d, %d \n", i, g.val1, g.val2);
        }
        System.out.printf("Result: %d \n", result);
    }

    private static void check(List<Character> numberChars, List<String> data, int i, int j) {
        for (int k = 0; k < numberChars.size() + 2; k++) {
            for (int l = -1; l < 2; l++) {
                try {
                    char c = data.get(i - l).charAt(j - k);
                    if (c == '*') {
                        Gear gear = null;
                        for (Gear g : gears) {
                            if (g.x == (i - l) && g.y == (j - k)) {
                                gear = g;
                                break;
                            }
                        }
                        if (gear == null) {
                            gear = new Gear(i - l, j - k, Integer.parseInt(numberChars.stream().map(String::valueOf).collect(Collectors.joining())), -1);
                            gears.add(gear);
                        } else {
                            gear.val2 = Integer.parseInt(numberChars.stream().map(String::valueOf).collect(Collectors.joining()));
                        }
                        numberChars.clear();
                        return;
                    }
                } catch (Exception ignored) {}
            }
        }

        numberChars.clear();
    }
}