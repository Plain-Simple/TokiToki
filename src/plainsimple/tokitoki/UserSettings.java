package plainsimple.tokitoki;

public class UserSettings {
    static double default_easiness_factor = 2.5;
    static int default_initial_repetitions = 6;
    static String current_language = "spanish";

    public static int getWords_at_once() {
        return words_at_once;
    }

    public static void setWords_at_once(int words_at_once) {
        UserSettings.words_at_once = words_at_once;
    }

    static int words_at_once;

    public static double getDefault_easiness_factor() {
        return default_easiness_factor;
    }

    public static void setDefault_easiness_factor(double default_easiness_factor) {
        UserSettings.default_easiness_factor = default_easiness_factor;
    }

    public static int getDefault_initial_repetitions() {
        return default_initial_repetitions;
    }

    public static void setDefault_initial_repetitions(int default_initial_repetitions) {
        UserSettings.default_initial_repetitions = default_initial_repetitions;
    }
    public static void setCurrent_language(String current_language) {
        UserSettings.current_language = current_language;
    }
    public static String getCurrent_language() {
        return current_language;
    }
}
