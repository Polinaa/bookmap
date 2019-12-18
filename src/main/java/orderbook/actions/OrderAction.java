package orderbook.actions;

import lombok.RequiredArgsConstructor;
import orderbook.model.OrderType;
import orderbook.core.OrderBook;

import java.util.List;

@RequiredArgsConstructor
public class OrderAction extends Action {

    private final OrderBook orderBook;

    @Override
    protected String processCommand(List<String> lineSegments) {
        OrderType orderType = OrderType.getByText(lineSegments.get(1));
        int size = Integer.parseInt(lineSegments.get(2));
        orderBook.order(size, orderType);
        return null;
    }

    @Override
    protected String getActionLetter() {
        return "o";
    }

    @Override
    protected boolean isExpectedArgsSize(List<String> lineSegments) {
        return lineSegments.size() == 3;
    }
}
