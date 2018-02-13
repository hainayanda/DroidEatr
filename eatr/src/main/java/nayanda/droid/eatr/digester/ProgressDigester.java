package nayanda.droid.eatr.digester;

/**
 * Created by nayanda on 13/02/18.
 */

public interface ProgressDigester<T extends Response> extends Digester<T> {

    /**
     * @param progress range from 0 to 1 indicate the progress
     */
    void onProgress(float progress);
}
