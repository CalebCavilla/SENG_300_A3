import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Currency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Test.None;

import com.autovend.devices.BillValidator;
import com.autovend.devices.BillValidatorObserverStub;
import com.autovend.devices.DisabledException;
import com.autovend.devices.FlowThroughEmitterStub;
import com.autovend.devices.ReceiptPrinter;
import com.autovend.devices.ReceiptPrinterObserverStub;
import com.autovend.Bill;
import com.autovend.devices.AcceptorStub;
import com.autovend.devices.BidirectionalChannel;
import com.autovend.devices.BillStorageObserverStub;
import com.autovend.devices.UnidirectionalChannelStub;
import com.autovend.devices.SimulationException;


    public class ReceiptPrinterTest {
    	
    	ReceiptPrinter printer;
    	public ReceiptPrinterObserverStub listener1;
    	
    	/**
    	 * Sets up the test suite. This is run before every test method.
    	 */
    	@Before
    	public void setup() {
    		
    		printer = new ReceiptPrinter();
    		
    		
    		// Create 3 listeners ... so you can see which ones receive events and which
    		// don't.
    		listener1 = new ReceiptPrinterObserverStub("listener1");

    		// Initialize the fields inside the listeners. Having these fields public would
    		// be a bad idea in real code, but this is just a demo.
    		listener1.device = null;

    	}
    	
    	
        @Test
        // Test that the printer initializes with the expected properties (0 lines and ink remaining)
        void printerInitialization(){
            int actual = ReceiptPrinter.charactersOfInkRemaining + ReceiptPrinter.linesOfPaperRemaining;
            assertEquals(0, actual);
        }
        @Test
        //Test that the printer can add ink within the maximum allowed ink limit.
        void inkAdd(){
            ReceiptPrinter.addInk(1);
            int actual = ReceiptPrinter.charactersOfInkRemaining;
            assertEquals(1, actual);
        }
        @Test
        //Test that adding negative ink throws a SimulationException.
        void inkAddNegative(){
            assertTrue("Are you trying to remove ink?", ReceiptPrinter.addInk(-1));
        }
        @Test
        //Test that adding ink beyond the maximum allowed ink limit throws an OverloadException.
        void inkAddAboveMax(){
            assertTrue("You spilled a bunch of ink!", ReceiptPrinter.addInk(100));
        }
        @Test
        //Test that the printer can add paper within the maximum allowed paper limit.
        void paperAdd(){
            ReceiptPrinter.addPaper(1);
            int actual = ReceiptPrinter.linesOfPaperRemaining;
            assertEquals(1, actual);
        }
        @Test
        //Test that adding negative paper throws a SimulationException.
        void paperAddNegative(){
            assertTrue("Are you trying to remove paper?", ReceiptPrinter.addPaper(-1));
        }
        @Test
        //Test that adding paper beyond the maximum allowed paper limit throws an OverloadException.
        void paperAddAboveMax(){
            assertTrue("You may have broken the printer, jamming so much in there!", ReceiptPrinter.addPaper(100));
        }
        @Test
        //Test that printing a character successfully decreases the ink level.
        void checkIfInkLevelDecreased(){
            ReceiptPrinter.addInk(1);
            ReceiptPrinter.addPaper(1);
            ReceiptPrinter.print("c");
            int actual = ReceiptPrinter.charactersOfInkRemaining;
            assertEquals(0, actual);
        }
}
