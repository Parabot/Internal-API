package org.parabot.api.io;

/**
 * Keeps track of a progress
 *
 * @author Everel
 */
public interface ProgressListener {

    /**
     * Called when progress increased
     *
     * @param value
     */
    void onProgressUpdate(double value);

    /**
     * Updates upload speed
     *
     * @param mbPerSecond
     */
    void updateDownloadSpeed(double mbPerSecond);

    /**
     * Sets the message displayed
     * @param message The Text to show
     */
    void updateMessage(String message);

    /**
     * Combination of the two
     * @param message
     * @param progress
     */
    void updateMessageAndProgress(String message, double progress);

    /**
     * Usage would be
     * <pre>double before = getCurrentProgress() <br> setCurrent(0) <br> for 0 .. 100: setCurrent(x) <br> done! <br> setCurrent(before)</pre>
     * @return
     */
    double getCurrentProgress();

}
