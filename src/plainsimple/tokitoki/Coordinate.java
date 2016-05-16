package plainsimple.tokitoki;

public class Coordinate {
    public Coordinate(int first, int second) {
        coordinates[0] = first;
        coordinates[1] = second;
    }
    private int[] coordinates = new int[2];
    public boolean quizNoGrade() {
        return WordLearning.all_known_words.get(coordinates[0]).words.get(coordinates[1]).quizNoGrade();
    }
    public boolean quizAndGrade() {
        return WordLearning.all_known_words.get(coordinates[0]).words.get(coordinates[1]).quizAndGrade();
    }
    public void introduce() {
        WordLearning.all_known_words.get(coordinates[0]).words.get(coordinates[1]).introduce();
    }
    public void incrementReps() {
        WordLearning.all_known_words.get(coordinates[0]).words.get(coordinates[1]).incrementReps();
    }
    public boolean isFullyLearned() {
        return WordLearning.all_known_words.get(coordinates[0]).words.get(coordinates[1]).isFullyLearned();
    }
    public boolean equals(Coordinate coordinate) {
        return (coordinate == null) ? false : coordinates.equals(coordinate.coordinates);
    }
    public boolean timeToReview() {
        return WordLearning.all_known_words.get(coordinates[0]).words.get(coordinates[1]).timeToReview();
    }
}
