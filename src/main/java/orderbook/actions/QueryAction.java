package orderbook.actions;

import lombok.RequiredArgsConstructor;
import orderbook.model.QueryType;
import orderbook.core.OrderBook;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class QueryAction extends Action {

    private final OrderBook orderBook;
    private static final String SEGMENT_DELIMITER = ",";
    private static final String LINE_DELIMITER = "\n";

    @Override
    protected String processCommand(List<String> lineSegments) {
        QueryType queryType = QueryType.getByText(lineSegments.get(1));
        if (queryType == QueryType.SIZE) {
            if (lineSegments.size() != 3) {
                return null;
            }
            int size = Integer.parseInt(lineSegments.get(2));
            return orderBook.query(size)
                            .stream()
                            .filter(Objects::nonNull)
                            .map(line -> line.getSize() + SEGMENT_DELIMITER + line.getUpdateType().toString())
                            .collect(Collectors.joining(LINE_DELIMITER));
        }
        return orderBook.query(queryType)
                        .stream()
                        .filter(Objects::nonNull)
                        .map(line -> line.getPrice() + SEGMENT_DELIMITER + line.getSize())
                        .collect(Collectors.joining(LINE_DELIMITER));
    }

    @Override
    protected String getActionLetter() {
        return "q";
    }

    @Override
    protected boolean isExpectedArgsSize(List<String> lineSegments) {
        return lineSegments.size() >= 2 && lineSegments.size() <= 3;
    }
}
