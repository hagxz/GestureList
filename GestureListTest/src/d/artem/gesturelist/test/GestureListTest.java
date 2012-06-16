package d.artem.gesturelist.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.KeyEvent;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import d.artem.gesturelist.GestureList;

public class GestureListTest extends
		ActivityInstrumentationTestCase2<GestureList> {

	private GestureList mActivity;
	private AutoCompleteTextView aComltTV;
	private ListView mList;

	public GestureListTest() {
		super("d.artem.gesturelist", GestureList.class);
	}

	protected void setUp() throws Exception {
		super.setUp();

		mActivity = getActivity();
		mList = (ListView) mActivity
				.findViewById(d.artem.gesturelist.R.id.listNames);
		aComltTV = (AutoCompleteTextView) mActivity
				.findViewById(d.artem.gesturelist.R.id.autoComlt);
	}

	public void testControlsCreated() {
		assertNotNull(mActivity);
		assertNotNull(mList);
		assertNotNull(aComltTV);
	}

	public void testControlsVisible() {
		ViewAsserts.assertOnScreen(mList.getRootView(), aComltTV);
		ViewAsserts.assertOnScreen(aComltTV.getRootView(), mList);
	}

	public void testRollingList() {

		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				mList.requestFocus();
			}
		});

		this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);

		for (int i = 1; i < mList.getAdapter().getCount(); i++) {
			this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
		}
		this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
	}

}
