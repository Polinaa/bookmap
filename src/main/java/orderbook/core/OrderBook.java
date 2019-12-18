package orderbook.core;

import orderbook.model.OrderBookLine;
import orderbook.model.OrderType;
import orderbook.model.QueryType;
import orderbook.model.UpdateType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderBook {

    private List<OrderBookLine> bookLines = new ArrayList<>();

    public void update(int price, int size, UpdateType updateType) {
        if (size < 0 || price <= 0 || updateType == null) {
            return;
        }
        OrderBookLine existingOrderBookLine = bookLines.stream()
                                                       .filter(line -> line.getPrice() == price && line.getUpdateType() == updateType)
                                                       .findAny()
                                                       .orElse(null);
        if (existingOrderBookLine == null) {
            bookLines.add(new OrderBookLine(price, size, updateType));
        } else {
            existingOrderBookLine.increaseSize(size);
        }
    }

    public void order(int size, OrderType orderType) {
        if (size <= 0 || orderType == null) {
            return;
        }
        if (orderType == OrderType.BUY) {
            OrderBookLine bestAsk = getBestAsk();
            size = removeAndReturnRemaining(size, bestAsk);
        }
        if (orderType == OrderType.SELL) {
            OrderBookLine bestBid = getBestBid();
            size = removeAndReturnRemaining(size, bestBid);
        }
        order(size, orderType);
    }

    public List<OrderBookLine> query(QueryType queryType) {
        if (queryType == null) {
            return Collections.emptyList();
        }
        if (queryType == QueryType.BEST_ASK) {
            return Collections.singletonList(getBestAsk());
        }
        if (queryType == QueryType.BEST_BID) {
            return Collections.singletonList(getBestBid());
        }
        return Collections.emptyList();
    }

    public List<OrderBookLine> query(Integer price) {
        if (price < 0) {
            return Collections.emptyList();
        }
        return bookLines.stream()
                        .filter(line -> line.getPrice() == price)
                        .collect(Collectors.toList());
    }

    private Integer removeAndReturnRemaining(int sizeToRemove, OrderBookLine orderBookLine) {
        if (orderBookLine == null) {
            return 0;
        }
        if (orderBookLine.getSize() > sizeToRemove) {
            orderBookLine.decreaseSize(sizeToRemove);
            return 0;
        }
        if (orderBookLine.getSize() == sizeToRemove) {
            bookLines.remove(orderBookLine);
            return 0;
        }
        bookLines.remove(orderBookLine);
        return sizeToRemove - orderBookLine.getSize();
    }

    private OrderBookLine getBestBid() {
        return bookLines.stream()
                        .filter(line -> line.getUpdateType() == UpdateType.BID && line.getPrice() > 0)
                        .max(Comparator.comparing(OrderBookLine::getPrice))
                        .orElse(null);
    }

    private OrderBookLine getBestAsk() {
        return bookLines.stream()
                        .filter(line -> line.getUpdateType() == UpdateType.ASK && line.getPrice() > 0)
                        .min(Comparator.comparing(OrderBookLine::getPrice))
                        .orElse(null);
    }
}
