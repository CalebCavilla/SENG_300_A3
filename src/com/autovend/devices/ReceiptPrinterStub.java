public class ReceiptPrinter {
    private final AbstractDeviceObserver observer;
    
    public ReceiptPrinter(AbstractDeviceObserver observer) {
        this.observer = observer;
    }
    
    public void print(Work work) throws EmptyException, OverSoloadedException {
        // Stub code that simply logs the work to be printed
        System.out.println("Printing work: " + work);
        
        // Notify the observer that a work has been printed
        observer.notify(work);
    }
}
