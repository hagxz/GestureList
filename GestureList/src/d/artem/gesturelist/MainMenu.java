package d.artem.gesturelist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import d.artem.gesturelist.R;

public class MainMenu extends Activity implements OnClickListener {

	Button listBtn;
	Button helpBtn;
	Button exitBtn;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);

		listBtn = (Button) findViewById(R.id.listbtn);
		helpBtn = (Button) findViewById(R.id.helpbtn);
		exitBtn = (Button) findViewById(R.id.exitbtn);

		listBtn.setOnClickListener(this);
		helpBtn.setOnClickListener(this);
		exitBtn.setOnClickListener(this);
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.listbtn:
			Intent listInt = new Intent(getApplicationContext(),
					GestureList.class);
			startActivity(listInt);
			break;
		case R.id.helpbtn:
			Intent helpInt = new Intent(getApplicationContext(), Help.class);
			startActivity(helpInt);
			break;
		case R.id.exitbtn:
			finish();
			break;
		}
	}
}
