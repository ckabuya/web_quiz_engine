import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Map<String, Integer> words = new HashMap<>();
        for (String word : input.split(" ")) {
            String wordToCheck = word.toLowerCase();//lower case it
            if (words.containsKey(wordToCheck)) {
                words.put(wordToCheck, words.get(wordToCheck) + 1); //increment by one
            } else {
                words.putIfAbsent(wordToCheck, 1);
            }
        }
        //display the values
        for (var entry : words.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}