package plainsimple.tokitoki;

import java.util.Scanner;

public class Main {
    static Conjugator conj = new Conjugator();
    static Pronoun_Getter pron = new Pronoun_Getter();
    public static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        new Word("tener", "to have").introduce();
    }
}