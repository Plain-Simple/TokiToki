package plainsimple.tokitoki;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordLearning {
    public static List<Wordlist> all_known_words = new ArrayList<>();

    /**
     * Imports serialized version of all_known_words
     */
    public static void importPreviousWords() {
        try {
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream("all_words"));
            all_known_words = (ArrayList<Wordlist>) reader.readObject();
            reader.close();
        } catch (Exception e) {
            System.out.println("Error: unable to import known words.");
        }
    }
    public static void saveProgress() {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("all_words"));
            writer.writeObject(all_known_words);
            writer.close();
        } catch (Exception e) {
            System.out.println("unable to save progress");
        }
    }

    /**
     * Reviews words that are up for review
     */
    public void reviewWords() {
        ArrayList<Integer[]> to_review = getReviewWords();
        ArrayList<Integer[]> incorrect = new ArrayList<Integer[]>();

        Collections.shuffle(to_review);
        for (Integer[] coordinates : to_review) {
            LocalDateTime question_asked = LocalDateTime.now();
            String user_answer = all_known_words.get(coordinates[0]).words.get(coordinates[1]).quizUser();
            int grade = all_known_words.get(coordinates[0]).words.get(coordinates[1]).gradeResponse(user_answer, question_asked);
            all_known_words.get(coordinates[0]).words.get(coordinates[1]).markReviewed(grade);
            if (grade < 4) {
                incorrect.add(coordinates);
            }
        }
        for (Integer[] coordinates : incorrect) {
            String user_answer = all_known_words.get(coordinates[0]).words.get(coordinates[1]).quizUser();
            String correct_answer = all_known_words.get(coordinates[0]).words.get(coordinates[1]).target_language_word;
            if (!user_answer.equals(user_answer)) {
                incorrect.add(coordinates);
            }
        }
    }


    /**
     *
     * @param filename
     */
    public void addWordsFromFile(String filename) {
        all_known_words.add(new Wordlist(filename));
    }
    public ArrayList<Integer[]> getReviewWords() {
        ArrayList<Integer[]> to_review = new ArrayList<>();
        for (int i = 0; i < all_known_words.size(); i++) {
            for (int j = 0; j < all_known_words.get(i).words.size(); j++) {
                if (all_known_words.get(i).words.get(j).timeToReview()) {
                    to_review.add(new Integer[]{i, j});
                }
            }
        }
        return to_review;
    }

}
