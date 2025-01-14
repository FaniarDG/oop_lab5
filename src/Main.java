import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите текст: ");
        String text = scanner.nextLine();

        wordsCount(text);

        scanner.close();
    }

    public static void wordsCount(String text) {

        String[] words = text.split(" ");

        Map<String, Integer> wordCounts = new HashMap<>();

        for (String word : words) {
            word = word.toLowerCase();
            word = word.replaceAll("[^a-zA-Zа-яА-Я0-9]", "");
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
}