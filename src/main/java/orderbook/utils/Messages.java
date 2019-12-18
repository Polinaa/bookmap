package orderbook.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Messages {

    public static final String ENTER_FILE_MESSAGE = "Please enter required args to run an app. First argument - input file path, second - output file path";
    public static final String OOPS_MESSAGE = "Ooops something went wrong. Please fix me";
    public static final String UNEXPECTED_LINE_FORMAT_MESSAGE = "Unexpected line format";
}
