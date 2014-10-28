package com.treehouselearning.ribbit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends Activity {

	protected EditText mUsername;
	protected EditText mPassword;
	protected Button mLoginButton;
	protected TextView mSignUpTextView;
	protected TextView mForgotPassTextView;
	protected ProgressBar mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_login);

		getActionBar().hide();
		
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		
		mForgotPassTextView = (TextView) findViewById(R.id.forgotPasswordText);
		mForgotPassTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,
						ForgotPasswordActivity.class);
				startActivity(intent);

			}
		});
		
		mSignUpTextView = (TextView) findViewById(R.id.signUpText);
		mSignUpTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,
						SignUpActivity.class);
				startActivity(intent);

			}
		});

		mUsername = (EditText) findViewById(R.id.usernameField);
		mPassword = (EditText) findViewById(R.id.passwordField);

		mLoginButton = (Button) findViewById(R.id.loginButton);
		mLoginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = mUsername.getText().toString();
				String password = mPassword.getText().toString();

				username = username.trim();
				password = password.trim();

				if (username.isEmpty() || password.isEmpty()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							LoginActivity.this);
					builder.setMessage(R.string.login_error_message)
							.setTitle(R.string.signup_error_title)
							.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				} else {
					// Login
					//setProgressBarIndeterminateVisibility(true);
					mProgressBar.setVisibility(View.VISIBLE);
					ParseUser.logInInBackground(username, password,
							new LogInCallback() {
								@Override
								public void done(ParseUser user,
										ParseException e) {

									//setProgressBarIndeterminateVisibility(false);
									mProgressBar.setVisibility(View.INVISIBLE);
									// if(user != null){
									if (e == null) {
										// Success! The user is logged in.
										Intent intent = new Intent(
												LoginActivity.this,
												MainActivity.class);
										intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
										startActivity(intent);
									} else {
										AlertDialog.Builder builder = new AlertDialog.Builder(
												LoginActivity.this);
										builder.setMessage(e.getMessage())
												.setTitle(
														R.string.signup_error_title)
												.setPositiveButton(
														android.R.string.ok,
														null);
										AlertDialog dialog = builder.create();
										dialog.show();
									}

								}
							});
				}
			}
		});
	}
}
