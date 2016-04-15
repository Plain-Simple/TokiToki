package plainsimple.tokitoki;

import java.util.Scanner;

public class Main {
    static Conjugator conj = new Conjugator();
    static Pronoun_Getter pron = new Pronoun_Getter();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String one = scanner.nextLine();
            String two = scanner.nextLine();
            System.out.println(LevenshteinDistance.computeLevenshteinDistance(one, two));
        }
    }
}