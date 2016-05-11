package plainsimple.tokitoki;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Wordlist implements Serializable {
    public List<Word> words = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String title;
    public Wordlist(String file_name) {
        List<String> word_lines;
        try {
            word_lines = Files.readAllLines(Paths.get("languages/" + UserSettings.getCurrent_language() + "/wordlists/" + file_name));
            title = word_lines.get(0);
            for (String line : word_lines.subList(1, word_lines.size())) {
                String target_language_word = line.substring(0, line.indexOf('\t'));
                String base_language_word = line.substring(line.indexOf('\t') + 1);
                words.add(new Word(target_language_word, base_language_word));
            }
        } catch (Exception e) {
            System.out.println("error: couldn't find wordlist at : " + file_name);
            System.exit(1);
        }
    }
}
