package plainsimple.tokitoki;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class WordLearning {
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
    public static void reviewWords() {
        ArrayList<Coordinate> to_review = getReviewWords();
        ArrayList<Coordinate> incorrect = new ArrayList<>();

        Collections.shuffle(to_review);
        for (Coordinate coordinate : to_review) {
            // Review each word and add incorrect words to "incorrect" array
            if (!coordinate.quizAndGrade()) {
                incorrect.add(coordinate);
            }
        }
        Collections.shuffle(incorrect);
        for (Coordinate coordinate : incorrect) {
            if (!coordinate.quizNoGrade()) {
                incorrect.add(coordinate);
            }
        }
    }
    public static void learnNewWords() {
        ArrayList<Coordinate> unlearned = getUnlearnedWords();
        for (int i = 0; i < unlearned.size(); i += UserSettings.getWordgroup_size()) {
            List<Coordinate> sublist;
            if (i + (2 * UserSettings.getWordgroup_size()) <= unlearned.size()) {
                sublist = unlearned.subList(i, i + UserSettings.getWordgroup_size());
            } else {
                sublist = unlearned.subList(i, unlearned.size());
            }
            Coordinate[] coordinates = new Coordinate[sublist.size()];
            coordinates = sublist.toArray(coordinates);
            Wordgroup current = new Wordgroup(coordinates);
            if (UserSettings.isDebug()) {
                System.out.print("Wordgroup of size " + coordinates.length + " created ");
                System.out.println("using words " + i + " to " + (i + coordinates.length) + " out of " + unlearned.size());
            }
            current.learn();
        }
    }
    /**
     * Adds words from a wordlist to be learned
     * @param filename file where the wordlist is stored
     */
    public static void addWordsFromFile(String filename) {
        all_known_words.add(new Wordlist(filename));
    }

    private static ArrayList<Coordinate> getUnlearnedWords() {
        ArrayList<Coordinate> unlearned = new ArrayList<>();
        for (int i = 0; i < all_known_words.size(); i++) {
            for (int j = 0; j < all_known_words.get(i).words.size(); j++) {
                Coordinate coordinate = new Coordinate(i, j);
                if (!coordinate.isFullyLearned()) {
                    unlearned.add(coordinate);
                }
            }
        }
        return unlearned;
    }
    private static ArrayList<Coordinate> getReviewWords() {
        ArrayList<Coordinate> to_review = new ArrayList<>();
        for (int i = 0; i < all_known_words.size(); i++) {
            for (int j = 0; j < all_known_words.get(i).words.size(); j++) {
                Coordinate coordinate = new Coordinate(i, j);
                if (coordinate.isFullyLearned() && coordinate.timeToReview()) {
                    to_review.add(coordinate);
                }
            }
        }
        return to_review;
    }
    public static void clearScreen() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}