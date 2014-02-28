package src.com.employeesurvey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends FragmentActivity implements OnClickListener {

	/**
	 * Edit field for username
	 */
	private EditText username_editfield;
	/**
	 * Edit filed for store Name
	 */
	private EditText storename_editfield;
	/**
	 * Cancel button for cancel the form
	 */
	private Button cancel_button;
	/**
	 * Login button for Saving the store and username and loging in
	 */
	private Button login_button;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		final Window win = getWindow();

		win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// No Titlebar
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_layout);

		initializaComponents();
	}

	/**
	 * initializing the main components
	 */
	private void initializaComponents() {
		username_editfield = (EditText) findViewById(R.id.username_editText);
		storename_editfield = (EditText) findViewById(R.id.storename_editText);
		login_button = (Button) findViewById(R.id.login_button);
		login_button.setOnClickListener(this);
		cancel_button = (Button) findViewById(R.id.cancel_button);
		cancel_button.setOnClickListener(this);
	}

	/**
	 * overriden method onClick of buttons
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_button:
			Intent intent = new Intent(LoginActivity.this,
					DashboardActivity.class);
			startActivity(intent);
			break;

		case R.id.cancel_button:
			finish();
			break;

		default:
			break;
		}
	}

}
