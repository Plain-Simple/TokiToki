package plainsimple.tokitoki;

import java.util.Scanner;

public class Main {
    static Conjugator conj = new Conjugator();
    static Pronoun_Getter pron = new Pronoun_Getter();

    public static void main(String[] args) {
        WordLearning.addWordsFromFile("test1");
        WordLearning.learnNewWords();
    }
    public static final Scanner scanner = new Scanner(System.in);
}