import java.util.Scanner;
import java.util.TreeMap;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int low = scanner.nextInt();
        int upper = scanner.nextInt();
        int size = scanner.nextInt();
        TreeMap<Integer, String> words = new TreeMap<>();
        while (size > 0) {
            Integer key = scanner.nextInt();
            String value = scanner.next();
            words.put(key, value);
            --size;
        }

        for (var entry : words.subMap(low, true, upper, true).entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}