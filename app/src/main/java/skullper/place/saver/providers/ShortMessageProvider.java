package skullper.place.saver.providers;

/**
 * Created by pugman on 23.01.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public interface ShortMessageProvider {

	void toast(int resourceId);

	void toast(int resourceId, Object... args);

	/**
	 * Shows a toast message
	 * @param text to be shown
	 */
	void toast(String text);

}
