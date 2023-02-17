import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.autovend.devices.ReceiptPrinter;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


    public class ReceiptPrinterTest {
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
