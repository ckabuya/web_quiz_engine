import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String date = scanner.next();
        int offset = scanner.nextInt();


        LocalDate captureDate = LocalDate.parse(date);
        int year = captureDate.getYear();
        do {
            System.out.println(captureDate);
            captureDate = captureDate.plusDays(offset);
        }
        while (!captureDate.isAfter(LocalDate.of(year, 12, 31)));
    }
}