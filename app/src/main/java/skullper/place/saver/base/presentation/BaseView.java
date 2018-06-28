package skullper.place.saver.base.presentation;

/*
  Created by pugman on 18.01.18.
  Contact the developer - sckalper@gmail.com
  company - A2Lab
 */

import android.content.Context;

public interface BaseView {

	/**
	 * @return current view's context
	 */
	Context getContext();

	/**
	 * restarts app and clear all user data
	 */
	void logOut();

}
