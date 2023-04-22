package org.irc;


/**
 * @author Tomas Kozakas
 */
public class MessageHandler {
    public boolean isError(String message) {
        String[] messageParts = message.split(" ");
        if (messageParts.length >= 2) {
            try {
                int code = Integer.parseInt(messageParts[1]);
                return code >= 400 && code <= 599;
            } catch (NumberFormatException e) {
                // Not a numeric error code
            }
        }
        return false;
    }
}
