package orderbook.core;

import orderbook.actions.Action;
import orderbook.actions.OrderAction;
import orderbook.actions.QueryAction;
import orderbook.actions.UpdateAction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderBookFileCommunicator {

    private static final String SPLITTER = ",";

    private final List<Action> actions;

    public OrderBookFileCommunicator(OrderBook orderBook) {
        this.actions = Arrays.asList(new UpdateAction(orderBook), new QueryAction(orderBook), new OrderAction(orderBook));
    }

    public void process(String filePath, String outputFile) throws IOException {
        String output = Files.lines(Paths.get(filePath))
                             .map(this::processLine)
                             .filter(Objects::nonNull)
                             .filter(lineOutput -> lineOutput.length() > 0)
                             .collect(Collectors.joining("\n"));
        if (output != null && output.length() > 0) {
            Files.write(Paths.get(outputFile), output.getBytes());
        }
    }

    private String processLine(String line) {
        List<String> lineSegments = Arrays.stream(line.split(SPLITTER))
                                          .map(String::trim)
                                          .collect(Collectors.toList());

        return actions.stream()
                      .filter(a -> a.isApplicable(lineSegments))
                      .findAny()
                      .map(value -> value.process(lineSegments))
                      .orElse(null);
    }
}
