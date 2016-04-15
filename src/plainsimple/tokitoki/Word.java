package plainsimple.tokitoki;

import java.time.LocalDateTime;

public class Word {
    public Word(String target_language_word, String base_language_word) {
        this.target_language_word = target_language_word;
        this.base_language_word = base_language_word;
        easiness_factor = UserSettings.getDefault_easiness_factor();
    }
    public String target_language_word, base_language_word;
    public LocalDateTime last_practiced;
    public double practice_interval, easiness_factor;

    public void updateEasinessFactor(int grade) {
        /* the following is taken from the SuperMemo2 algorithm */
        easiness_factor = easiness_factor-0.8+0.28*grade-0.02*grade*grade;
        easiness_factor = (easiness_factor < 1.3 ? 1.3 : easiness_factor);
    }
    public void updateInterval() {
        practice_interval *= easiness_factor;
    }
    public boolean timeToReview() {
        return LocalDateTime.now().isAfter(last_practiced.plusDays((long)practice_interval));
    }
    public void markReviewed() {
        last_practiced = LocalDateTime.now();
    }
}
