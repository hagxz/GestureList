package d.artem.gesturelist;

import java.util.ArrayList;
import java.util.Comparator;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import d.artem.gesturelist.R;

public class GestureList extends Activity implements OnGesturePerformedListener {

	public final static String LATIN_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public final static String ACTION_SORT = "sort";
	public final static String ACTION_EXIT = "exit";

	public final static String NOTICE_SORT = "List is sorted";
	public final static String NOTICE_UNCOMND = "Unknown command";

	public final static int HELP_ID = 101;
	public final static int BACK_ID = 102;

	GestureLibrary mGestureLib;

	ArrayAdapter<CharSequence> adapter;
	Button clrBtn;
	ListView lvNames;
	AutoCompleteTextView autoCpmlTV;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mGestureLib = GestureLibraries.fromRawResource(this, R.raw.mgestures);
		if (!mGestureLib.load()) {
			finish();
		}

		GestureOverlayView gesturesView = (GestureOverlayView) findViewById(R.id.gesturesV);
		gesturesView.addOnGesturePerformedListener(this);

		lvNames = (ListView) findViewById(R.id.listNames);
		View header = getLayoutInflater().inflate(R.layout.header, null, false);
		lvNames.addHeaderView(header);

		adapter = ArrayAdapter.createFromResource(this, R.array.names,
				R.layout.list_item);
		lvNames.setAdapter(adapter);

		autoCpmlTV = (AutoCompleteTextView) findViewById(R.id.autoComlt);
		autoCpmlTV.setAdapter(adapter);

		clrBtn = (Button) findViewById(R.id.button1);
		clrBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				autoCpmlTV.setText("");
			}
		});

	}

	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		ArrayList<Prediction> predictions = mGestureLib.recognize(gesture);

		Log.d("SFS", predictions.get(0).name + " " + predictions.get(0).score);
		if (predictions.size() > 0) {
			if (predictions.get(0).score > 3.2) {
				String action = predictions.get(0).name;
				if (LATIN_CHARACTERS.contains(action)) {
					autoCpmlTV.append(action);
				} else {
					if (action.equals(ACTION_SORT)) {
						sortList();
						Toast.makeText(getApplicationContext(), NOTICE_SORT,
								Toast.LENGTH_SHORT).show();
					}
					if (action.equals(ACTION_EXIT)) {
						finish();
					}
				}
			} else {
				Toast.makeText(getApplicationContext(), NOTICE_UNCOMND,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, HELP_ID, 0, getResources().getString(R.string.m_help));
		menu.add(0, BACK_ID, 0, getResources().getString(R.string.m_back));

		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case HELP_ID:
			Intent intent = new Intent(getApplicationContext(), Help.class);
			startActivity(intent);
			break;
		case BACK_ID:
			finish();
		}

		return super.onOptionsItemSelected(item);
	}

	private void sortList() {
		if (adapter != null) {

			Comparator<CharSequence> ItemCmptr = new Comparator<CharSequence>() {
				public int compare(CharSequence lhs, CharSequence rhs) {
					String str1 = rhs.toString();
					String str2 = lhs.toString();
					return str2.compareToIgnoreCase(str1);
				}
			};

			adapter.sort(ItemCmptr);
		}
	}
}