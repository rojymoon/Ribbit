package com.treehouselearning.ribbit;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends Activity {

	protected EditText mEmailRecovery;
	protected Button mSubmitButton;
	private static final String TAG = ForgotPasswordActivity.class
			.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);
		
		getActionBar().hide();

		mEmailRecovery = (EditText) findViewById(R.id.emailRecoveryField);
		mSubmitButton = (Button) findViewById(R.id.submitButton);
		mSubmitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String emailRecovery = mEmailRecovery.getText().toString();
				ParseUser.requestPasswordResetInBackground(emailRecovery,
						new RequestPasswordResetCallback() {

							@Override
							public void done(ParseException e) {
								if (e == null) {
									// An email was successfully sent with reset
									// instructions.
									Toast.makeText(
											ForgotPasswordActivity.this,
											"An email was successfully sent with reset instructions.",
											Toast.LENGTH_LONG).show();
									Intent intent = new Intent(
											ForgotPasswordActivity.this,
											LoginActivity.class);
									intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
									startActivity(intent);
								} else {
									// Something went wrong. Look at the
									// ParseException to see what's up.
									Log.e(TAG, e.getMessage());
									Toast.makeText(
											ForgotPasswordActivity.this,
											e.getMessage(),
											Toast.LENGTH_LONG).show();
								}

							}
						});

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forgot_password, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
