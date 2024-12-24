package Lab3;

import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите фамилию, имя и отчество:");
                String name = scanner.nextLine();
                System.out.println("Введите дату рождения в формате ДД.ММ.ГГГГ:");
                String birthDate = scanner.nextLine();
                try {
                    Processor processor = new Processor();
                    processor.processing(name, birthDate);
                }
                catch (Exception exc) {
                    System.err.println("Ошибка: " + exc.getMessage());
                }
        }
    }
}
