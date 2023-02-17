public class OverSoloadedException extends Exception {
    public OverSoloadedException() {
        super("OverSoloadedException: The queue is full.");
    }
}
