package utils;

import java.util.Scanner;

public class Validator {
    public static int getIntInput(Scanner scanner, String prompt) {
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, insira um número válido.");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
