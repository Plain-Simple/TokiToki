package plainsimple.tokitoki;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Word implements Serializable {
    /**
     * Constructs new Word from word and definition
     * @param target_language_word the word in the target language (the one being learned)
     * @param base_language_word   the word in the base language (the one already known)
     */
    public Word(String target_language_word, String base_language_word) {
        this.target_language_word = target_language_word;
        this.base_language_word = base_language_word;
    }
    private boolean fully_learned;
    private LocalDateTime last_practiced;
    private double practice_interval, easiness_factor = UserSettings.getInitial_easiness_factor();
    private int reps_left = UserSettings.getInitial_repetitions();
    private String target_language_word, base_language_word;

    public void incrementReps() {
        reps_left -= 1;
        if (reps_left <= 0) {
            fully_learned = true;
            last_practiced = LocalDateTime.now();
            easiness_factor = UserSettings.getInitial_easiness_factor();
            practice_interval = UserSettings.getInitial_interval();
        }
    }

    public void introduce() {
        WordLearning.clearScreen();
        System.out.print("The Spanish word for \"" + base_language_word + "\" is \"" + target_language_word + "\". Type \"" + target_language_word + "\": ");
        while (!Main.scanner.nextLine().equals(target_language_word)) {
            System.out.print("Sorry, try again. Type \"" + target_language_word + "\": ");
        }
    }

    public boolean isFullyLearned() {
        return fully_learned;
    }

    public void markReviewed(int grade) {
        updateEasinessFactor(grade);
        updateInterval();
        updateLastPracticed();
        WordLearning.saveProgress();
    }

    public boolean quizAndGrade() {
        LocalDateTime question_asked = LocalDateTime.now();
        String user_answer = quizUser();
        int grade = gradeResponse(user_answer, question_asked);
        markReviewed(grade);
        return grade >= 4;
    }

    public boolean quizNoGrade() {
        return quizUser().equals(target_language_word);
    }

    public boolean timeToReview() {
        return LocalDateTime.now().isAfter(last_practiced.plusDays((long) practice_interval));
    }

    private int gradeResponse(String user_answer, LocalDateTime question_asked) {
        LocalDateTime question_answered = LocalDateTime.now();
        if (target_language_word.equals(user_answer)) {
            return question_answered.minusSeconds(target_language_word.length()).compareTo(question_asked) <= 0 ? 5 : 4;
        }
        double lev_distance = levenshteinDistance(user_answer, target_language_word);
        if ("".equals(user_answer)) {
            return 0;
        } else if (.2 <= lev_distance / user_answer.length()) {
            return 3;
        } else if (.3 <= lev_distance / user_answer.length()) {
            return 2;
        } else if (.4 <= lev_distance / user_answer.length()) {
            return 0;
        } else {
            return 0;
        }
    }

    /* method taken from https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java */
    private static int levenshteinDistance(CharSequence lhs, CharSequence rhs) {
        int len0 = lhs.length() + 1;
        int len1 = rhs.length() + 1;

        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++) cost[i] = i;

        // dynamically computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1
            newcost[0] = j;

            // transformation cost for each letter in s0
            for (int i = 1; i < len0; i++) {
                // matching current letters in both strings
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert = cost[i] + 1;
                int cost_delete = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }

            // swap cost/newcost arrays
            int[] swap = cost;
            cost = newcost;
            newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }

    private String quizUser() {
        WordLearning.clearScreen();
        System.out.print("Type the Spanish word for \"" + base_language_word + "\": ");
        return Main.scanner.nextLine();
    }

    private void updateEasinessFactor(int grade) {
        /* the following is taken from the SuperMemo2 algorithm */
        easiness_factor = easiness_factor - 0.8 + 0.28 * grade - 0.02 * grade * grade;
        easiness_factor = (easiness_factor < 1.3 ? 1.3 : easiness_factor);
    }

    private void updateInterval() {
        practice_interval *= easiness_factor;
    }

    private void updateLastPracticed() {
        last_practiced = LocalDateTime.now();
    }
}
