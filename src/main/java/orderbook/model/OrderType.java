package orderbook.model;

import java.util.Arrays;

public enum OrderType {
    BUY,
    SELL;

    public static OrderType getByText(String text) {
        return Arrays.stream(OrderType.values())
                     .filter(type -> type.toString().equalsIgnoreCase(text))
                     .findAny()
                     .orElse(null);
    }
}
