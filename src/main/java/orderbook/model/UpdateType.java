package orderbook.model;

import java.util.Arrays;

public enum UpdateType {
    ASK,
    BID,
    SPREAD;

    public static UpdateType getByText(String text) {
        return Arrays.stream(UpdateType.values())
                     .filter(type -> type.toString().equalsIgnoreCase(text))
                     .findAny()
                     .orElse(null);
    }
}
