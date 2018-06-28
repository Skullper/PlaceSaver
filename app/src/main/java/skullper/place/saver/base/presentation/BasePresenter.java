package skullper.place.saver.base.presentation;

/*
  Created by pugman on 18.01.18.
  Contact the developer - sckalper@gmail.com
  company - A2Lab
 */

/**
 * Base class for each presenter in project
 * @param <V> view used by presenter to interact with activity
 */
public abstract class BasePresenter<V extends BaseView>{

	protected V view;

	public BasePresenter(V view){
		this.view = view;
	}

	/**
	 * Use this to clear view or another object which has been initialized in presenter
	 */
    public void destroy() {
        view = null;
    }

}
