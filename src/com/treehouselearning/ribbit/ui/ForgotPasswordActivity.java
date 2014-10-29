package com.treehouselearning.ribbit.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.treehouselearning.ribbit.R;

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
}
