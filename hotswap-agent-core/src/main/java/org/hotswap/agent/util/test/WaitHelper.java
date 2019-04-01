package org.hotswap.agent.util.test;

/**
 * Convenience class to waitForResult for an event to occur.
 */
public class WaitHelper {

    /**
     * Wait for result set to true by another thread in resultHolder. Default timeout 1000ms.
     *
     * @param resultHolder holder of a boolean result - wait for value "true"
     * @return true if value was set during timeout
     */
    public static boolean waitForResult(WaitHelper.ResultHolder resultHolder) {
        return waitForResult(resultHolder, 1000);
    }


    /**
     * Wait for result set to true by another thread in resultHolder.
     *
     * @param resultHolder holder of a boolean result - wait for value "true"
     * @param timeout      how long to wait
     * @return true if value was set during timeout
     */
    public static boolean waitForResult(WaitHelper.ResultHolder resultHolder, int timeout) {
        for (int i = 0; i < timeout / 10; i++) {
            if (resultHolder.result)
                return true;

            // waitForResult for command to execute
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
        return false;
    }

    /**
     * Holder to share result between threads.
     * Another thread will set result to true.
     */
    public static class ResultHolder {
        public boolean result = false;
    }


    /**
     * Wait for custom command to return true. Default timeout 1000ms.
     *
     * @param command wait for "true"
     * @return true if command returned true during command
     */
    public static boolean waitForCommand(Command command) {
        return waitForCommand(command, 1000);
    }

    /**
     * Wait for custom command to return true. Default timeout 1000ms.
     *
     * @param command wait for return "true" value
     * @return true if command returned true during command
     */
    public static boolean waitForCommand(Command command, int timeout) {
        for (int i = 0; i < timeout / 10; i++) {
            try {
                if (command.result())
                    return true;
            } catch (Exception e) {
                // ignore - return for true without exception
            }

            // waitForResult for command to execute
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
        return false;
    }

    /**
     * Holder to share result between threads.
     */
    public static abstract class Command {
        /**
         * Invoke method to resolve current result - wait for true.
         *
         * @return true if the event we wait for occurred.
         * @throws java.lang.Exception any exception is considered as false result
         */
        public abstract boolean result() throws Exception;
    }
}
