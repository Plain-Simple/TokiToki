package plainsimple.tokitoki;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllWords {
    public static List<Wordlist> all_known_words = new ArrayList();
    public void addWordsFromFile(String filename) {
        all_known_words.add(new Wordlist(filename));
    }
    public void learnNewWord(){}

}
