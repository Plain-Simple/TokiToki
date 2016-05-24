package plainsimple.tokitoki;

import java.util.Scanner;

public class Main {
    static Conjugator conj_targ = new Conjugator(UserSettings.getTarget_language());
    static Pronoun_Getter pron_targ = new Pronoun_Getter(UserSettings.getTarget_language());
    static Conjugator conj_base = new Conjugator(UserSettings.getTarget_language());
    static Pronoun_Getter pron_base = new Pronoun_Getter(UserSettings.getTarget_language());

    public static void main(String[] args) {

    }
    public static final Scanner scanner = new Scanner(System.in);
}