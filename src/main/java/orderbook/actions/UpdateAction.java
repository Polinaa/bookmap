package orderbook.actions;

import lombok.RequiredArgsConstructor;
import orderbook.model.UpdateType;
import orderbook.core.OrderBook;

import java.util.List;

@RequiredArgsConstructor
public class UpdateAction extends Action {

    private final OrderBook orderBook;

    @Override
    protected String processCommand(List<String> lineSegments) {
        int price = Integer.parseInt(lineSegments.get(1));
        int size = Integer.parseInt(lineSegments.get(2));
        UpdateType updateType = UpdateType.getByText(lineSegments.get(3));
        orderBook.update(price, size, updateType);
        return null;
    }

    @Override
    protected String getActionLetter() {
        return "u";
    }

    @Override
    protected boolean isExpectedArgsSize(List<String> lineSegments) {
        return lineSegments.size() == 4;
    }
}
