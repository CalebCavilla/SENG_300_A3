/*This test class has three tests:

testPrintReceipt tests that the printReceipt method works correctly when paper is present.
testPrintReceiptNoPaper tests that an EmptyException is thrown when the printReceipt method is called and there is no paper present.
testPrintReceiptOverload tests that an OverloadException is thrown when the printReceipt method is called too many times. */



import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.autovend.devices.EmptyException;
import com.autovend.devices.OverloadException;
import com.autovend.devices.ReceiptPrinter;
import com.autovend.devices.observers.AbstractDeviceObserver;

public class ReceiptPrinterTest {
    private ReceiptPrinter printer;
    private AbstractDeviceObserver observer;

    @Before
    public void setUp() {
        observer = mock(AbstractDeviceObserver.class);
        printer = new ReceiptPrinter(observer);
    }

    @Test
    public void testPrintReceipt() throws EmptyException, OverloadException {
        // Stub the observer to return true when asked if paper is present
        when(observer.isPaperPresent()).thenReturn(true);

        // Call the method being tested
        printer.printReceipt(new Work("Test work"));

        // Verify that the observer's update method was called once
        verify(observer, times(1)).update(any(Device.class));
    }

    @Test(expected = EmptyException.class)
    public void testPrintReceiptNoPaper() throws EmptyException, OverloadException {
        // Stub the observer to return false when asked if paper is present
        when(observer.isPaperPresent()).thenReturn(false);

        // Call the method being tested, should throw an EmptyException
        printer.printReceipt(new Work("Test work"));
    }

    @Test(expected = OverloadException.class)
    public void testPrintReceiptOverload() throws EmptyException, OverloadException {
        // Stub the observer to return true when asked if paper is present
        when(observer.isPaperPresent()).thenReturn(true);

        // Call the method being tested multiple times, should throw an OverloadException
        for (int i = 0; i < 11; i++) {
            printer.printReceipt(new Work("Test work"));
        }
    }
}
