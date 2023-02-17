package com.autovend.hardware.test;

import static org.junit.Assert.assertEquals;


import java.util.Currency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Test.None;


import com.autovend.devices.EmptyException;
import com.autovend.devices.OverloadException;
import com.autovend.devices.ReceiptPrinter;
import com.autovend.devices.ReceiptPrinterObserverStub;
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

    		printer.register(listener1);
    		printer.disable();
    		printer.enable();
    		
    		// Initialize the fields inside the listeners. Having these fields public would
    		// be a bad idea in real code, but this is just a demo.
    		listener1.device = null;
    		listener1.inkAdded = false;

    	}
    	
  
    	/**
    	 * Testing adding a normal amount of ink below the limit
    	 * Expected that the ink is added and the observers detect that the ink was added
    	 * @throws OverloadException 
    	 */
        @Test
        public void inkAdd() throws OverloadException{
            printer.addInk(1);
            assertEquals(true, listener1.inkAdded);
        }
        /**
    	 * Testing adding a negative amount of ink
    	 * Expected that the ink causes SimulationException
    	 * @throws OverloadException 
    	 */
        @Test (expected = SimulationException.class)
        public void inkAddNegative() throws OverloadException{
            printer.addInk(-1);
        } 
        /**
    	 * Testing adding ink beyond the maximum
    	 * Expected that the ink causes OverloadException
    	 * @throws OverloadException 
    	 */
        @Test (expected = OverloadException.class)
        public void inkAddAboveMax() throws OverloadException{
            printer.addInk(1048577);
        }
        
        /**
    	 * Testing adding a normal amount of paper below the limit
    	 * Expected that the paper is added and the observers detect that the paper was added
    	 * @throws OverloadException 
    	 */
        @Test
        public void paperAdd() throws OverloadException{
        	printer.addPaper(20);
            assertEquals(true, listener1.paperAdded);
        }
        
        /**
    	 * Testing adding a negative amount of paper
    	 * Expected that the paper causes SimulationException
    	 * @throws OverloadException 
    	 */
        @Test (expected = SimulationException.class)
        public void paperAddNegative() throws OverloadException{
            printer.addPaper(-1);
        }
        
        /**
    	 * Testing adding paper beyond the maximum
    	 * Expected that the paper causes OverloadException
    	 * @throws OverloadException 
    	 */
        @Test (expected = OverloadException.class)
        public void paperAddAboveMax() throws OverloadException{
        	printer.addPaper(1048577);
        }
        
        /**
    	 * Testing print with sufficient ink and paper with a standard non blank character
    	 * Expected that no errors are thrown and the ink level is decreased.
    	 *  Since the observer does not detect successful prints, there is no way to test other than expecting no errors.
    	 * @throws OverloadException 
         * @throws EmptyException 
    	 */
        @Test
        public void print_fullInk_fullPaper_normalChar() throws OverloadException, EmptyException{
            printer.addInk(100);
            printer.addPaper(100);
            printer.print('a');
        }
        
        /**
    	 * Testing print with sufficient paper but not enough ink with a standard non blank character
    	 * Expected that an EmptyException should be thrown.
    	 * (TESTERS NOTE: THIS TEST FAILS suggests bug in the code of print)
    	 * @throws OverloadException 
         * @throws EmptyException 
    	 */
        @Test (expected = EmptyException.class)
        public void print_emptyInk_FullPaper() throws OverloadException, EmptyException{
            printer.addPaper(100);
            printer.print('a');
            assertEquals(true, listener1.outOfInk);
        }
        
        /**
    	 * Testing print with sufficient ink but not enough paper with a standard non blank character
    	 * Expected that an EmptyException should be thrown.
    	 * @throws OverloadException 
         * @throws EmptyException 
    	 */
        @Test (expected = EmptyException.class)
        public void print_emptyPaper_FullInk() throws OverloadException, EmptyException{
            printer.addInk(100);
            printer.print('a');
            assertEquals(true, listener1.outOfPaper);
        }
        
        /**
    	 * Testing print with not enough ink or paper with a standard non blank character
    	 * Expected that an EmptyException should be thrown.
    	 * @throws OverloadException 
         * @throws EmptyException 
    	 */
        @Test (expected = EmptyException.class)
        public void print_emptyPaper_emptyInk() throws OverloadException, EmptyException{
            printer.print('a');
            assertEquals(true, listener1.outOfPaper);
            assertEquals(true, listener1.outOfInk);
        }
        
        /** 
    	 * Testing print with sufficient ink and paper with a blank character
    	 * Expected that the white space is ignored. Nothing should happen (no errors are thrown)
    	 * Again, very frustrating but no observer events are called and all fields are private making testing impossible
    	 * @throws OverloadException 
         * @throws EmptyException 
    	 */
        @Test 
        public void print_fullInk_fullPaper_BlankChar() throws OverloadException, EmptyException{
            printer.addInk(100);
            printer.addPaper(100);
            printer.print(' ');
        }
        
        /**
    	 * Testing print with sufficient ink and paper with way too many characters, prints 61 char when the limit is 60
    	 * Expected that an OverloadException is thrown
    	 * @throws OverloadException 
         * @throws EmptyException 
    	 */
        @Test (expected = OverloadException.class)
        public void print_fullInk_fullPaper_TooManyChar() throws OverloadException, EmptyException{
            printer.addInk(100);
            printer.addPaper(100);
         
            for(int i = 0; i < 61; i++) {
            	printer.print('a');
            }
        }
        
        /**
    	 * Testing removing a receipt after successfully cutting it
    	 * Expected that the string returned is the same one that was printed using print
    	 * @throws OverloadException 
         * @throws EmptyException 
    	 */
        @Test
        public void remove_receipt() throws OverloadException, EmptyException{
            printer.addInk(100);
            printer.addPaper(100);
            printer.print('a');
            printer.cutPaper();
            String receipt = printer.removeReceipt();
            assertEquals("a", receipt);
        }
        
        /**
    	 * Testing removing a receipt without cutting it
    	 * Expected that the string returned is null as the receipt was not cut
    	 * @throws OverloadException 
         * @throws EmptyException 
    	 */
        @Test
        public void remove_receipt_without_cutting() throws OverloadException, EmptyException{
            printer.addInk(100);
            printer.addPaper(100);
            printer.print('a');
            String receipt = printer.removeReceipt();
            assertEquals(null, receipt);
        }
        

}
