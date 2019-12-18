package orderbook.model;

import java.util.Arrays;

public enum QueryType {
    BEST_ASK,
    BEST_BID,
    SIZE;

    public static QueryType getByText(String text) {
        return Arrays.stream(QueryType.values())
                     .filter(type -> type.toString().equalsIgnoreCase(text))
                     .findAny()
                     .orElse(null);
    }
}
