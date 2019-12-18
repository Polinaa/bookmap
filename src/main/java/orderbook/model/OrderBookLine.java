package orderbook.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderBookLine {

    private int price;
    private int size;
    private UpdateType updateType;

    public void increaseSize(int size) {
        if (size > 0) {
            this.size += size;
        }
    }

    public void decreaseSize(int size) {
        if (size > 0) {
            this.size -= size;
        }
    }
}
