package com.skipit.remainder.skipitreminder.util;

import org.springframework.stereotype.Component;

@Component
public class ExceptionFormatUtils {

    private ExceptionFormatUtils() {
    }

    /**
     * Extract the the message from the exception
     * using : as a separator and getting the value in the first position of the
     * array
     * 
     * @param errorMessage error message from the exception
     * @return String error message formated
     */
    public String getException(String errorMessage) {
        return getException(errorMessage, ": ");
    }

    /**
     * Extract the the message from the exception
     * using : as a separator and getting the especific value
     * 
     * @param errorMessage error message from the exception
     * @param position     index to get an especific the message error
     * @return String error message formated
     */
    public String getException(String errorMessage, int position) {
        return getException(errorMessage, ": ", position);
    }

    /**
     * Extract the the message from the exception
     * in the first position of the array
     * 
     * @param errorMessage error message from the exception
     * @param separator    string to split the message
     * @return String error message formated
     */
    public String getException(String errorMessage, String separator) {
        return getException(errorMessage, separator, 0);
    }

    /**
     * Extract the the message from the exception
     * 
     * @param errorMessage error message from the exception
     * @param separator    string to split the message
     * @param position     index to get an especific the message error
     * @return String error message formated
     */
    public String getException(String errorMessage, String separator, int position) {
        if (!errorMessage.isEmpty()) {
            String[] errors = errorMessage.split(separator);
            errorMessage = errors[position >= errorMessage.length() ? 0 : position];
        }
        return errorMessage;
    }
}
