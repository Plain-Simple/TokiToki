package plainsimple.tokitoki;

import java.util.Scanner;

public class Main {
    static Conjugator conj = new Conjugator();
    static Pronoun_Getter pron = new Pronoun_Getter();
    public static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        Integer first = 1;
        Integer second = first;
        first = 2;
        System.out.print(second);
        System.out.print(new Verb("sonreir", "conditional", "second", "plural", "null").conjugate());
    }
}