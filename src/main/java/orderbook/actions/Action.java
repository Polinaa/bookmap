package orderbook.actions;

import orderbook.utils.Messages;

import java.util.List;

public abstract class Action {

    public boolean isApplicable(List<String> lineSegments) {
        if (lineSegments == null) {
            return false;
        }
        boolean isApplicable = lineSegments.get(0).equalsIgnoreCase(getActionLetter());
        if (isApplicable) {
            if (!isExpectedArgsSize(lineSegments)) {
                System.out.println(Messages.UNEXPECTED_LINE_FORMAT_MESSAGE);
                isApplicable = false;
            }
        }
        return isApplicable;
    }

    public String process(List<String> lineSegments) {
        try {
            return processCommand(lineSegments);
        } catch (NumberFormatException e) {
            System.out.println(Messages.UNEXPECTED_LINE_FORMAT_MESSAGE);
            e.printStackTrace();
        }
        return null;
    }

    protected abstract String processCommand(List<String> lineSegments);

    protected abstract String getActionLetter();

    protected abstract boolean isExpectedArgsSize(List<String> lineSegments);

}
