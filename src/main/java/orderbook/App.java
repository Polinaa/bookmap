package orderbook;

import orderbook.core.OrderBook;
import orderbook.core.OrderBookFileCommunicator;
import orderbook.utils.Messages;

public class App {

    public static void main(final String[] args) {
        try {
            if (args.length != 2) {
                System.out.println(Messages.ENTER_FILE_MESSAGE);
                return;
            }
            String inputFile = args[0];
            String outputFile = args[1];
            OrderBook orderBook = new OrderBook();
            OrderBookFileCommunicator orderBookFileCommunicator = new OrderBookFileCommunicator(orderBook);
            orderBookFileCommunicator.process(inputFile, outputFile);
        } catch (Exception e) {
            System.out.println(Messages.OOPS_MESSAGE);
            e.printStackTrace();
        }
    }
}
