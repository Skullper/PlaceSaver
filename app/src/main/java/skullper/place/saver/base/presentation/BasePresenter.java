package skullper.place.saver.base.presentation;

/*
  Created by pugman on 18.01.18.
  Contact the developer - sckalper@gmail.com
  company - A2Lab
 */

public abstract class BasePresenter<T extends BaseView>{

	protected T view;

	public BasePresenter(T view){
		this.view = view;
	}

	/**
	 * Use this to clear view or another object which has been initialized in presenter
	 */
    public void destroy() {
        view = null;
    }

}
