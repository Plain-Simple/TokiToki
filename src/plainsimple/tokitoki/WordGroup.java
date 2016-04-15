package plainsimple.tokitoki;

import java.util.ArrayList;
import java.util.List;

public class WordGroup {
    Word[] words;
    int[] repetitions;
    public WordGroup (Word[] words){
        this.words = words;
        repetitions = new int[words.length];
    }
    public void showAll() {
        System.out.println("");
    }


}
