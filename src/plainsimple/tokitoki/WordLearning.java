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
     * Imports serialized (saved on disk) version of all_known_words
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

    /**
     * Serializes and saves current version of all_known_words to disk
     */
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
        ArrayList<Integer[]> incorrect = new ArrayList<>();

        Collections.shuffle(to_review);
        for (Integer[] coordinates : to_review) {
            // Review each word and add incorrect words to "incorrect" array
            if (!all_known_words.get(coordinates[0]).words.get(coordinates[1]).quizAndGrade()) {
                incorrect.add(coordinates);
            }
        }
        Collections.shuffle(incorrect);
        for (Integer[] coordinates : incorrect) {
            if (!all_known_words.get(coordinates[0]).words.get(coordinates[1]).quizNoGrade()) {
                incorrect.add(coordinates);
            }
        }
    }
    /**
     * Adds words from a wordlist to be learned
     * @param filename file where the wordlist is stored
     */
    public void addWordsFromFile(String filename) {
        all_known_words.add(new Wordlist(filename));
    }
    public ArrayList<Integer[]> getReviewWords() {
        ArrayList<Integer[]> to_review = new ArrayList<>();
        for (int i = 0; i < all_known_words.size(); i++) {
            for (int j = 0; j < all_known_words.get(i).words.size(); j++) {
                if (all_known_words.get(i).words.get(j).timeToReview() && all_known_words.get(i).words.get(j).isFullyLearned()) {
                    to_review.add(new Integer[]{i, j});
                }
            }
        }
        return to_review;
    }

}
