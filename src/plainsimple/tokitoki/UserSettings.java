package plainsimple.tokitoki;

public class UserSettings {
    private static String base_language = "english";
    private static double initial_easiness_factor = 2.5;
    private static int initial_repetitions = 6;
    private static String target_language = "spanish";
    private static int wordgroup_size = 8;

    public static double getInitial_easiness_factor() {
        return initial_easiness_factor;
    }

    public static void setInitial_easiness_factor(double initial_easiness_factor) {
        UserSettings.initial_easiness_factor = initial_easiness_factor;
    }

    public static int getInitial_repetitions() {
        return initial_repetitions;
    }

    public static void setInitial_repetitions(int initial_repetitions) {
        UserSettings.initial_repetitions = initial_repetitions;
    }

    public static String getTarget_language() {
        return target_language;
    }

    public static void setTarget_language(String target_language) {
        UserSettings.target_language = target_language;
    }

    public static int getWordgroup_size() {
        return wordgroup_size;
    }

    public static void setWordgroup_size(int wordgroup_size) {
        UserSettings.wordgroup_size = wordgroup_size;
    }

    public static boolean isDebug() {
        return true;
    }
}
