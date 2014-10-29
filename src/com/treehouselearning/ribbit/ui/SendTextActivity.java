package com.treehouselearning.ribbit.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.treehouselearning.ribbit.R;
import com.treehouselearning.ribbit.utils.ParseConstants;

public class SendTextActivity extends Activity {
	
	protected EditText mText;
	protected Button mChooseRecipientButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_text);
		
		mText = (EditText) findViewById(R.id.textField);
		mChooseRecipientButton = (Button) findViewById(R.id.chooseRecipientButton);

		mChooseRecipientButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(mText.getText().toString().isEmpty()){
					AlertDialog.Builder builder = new AlertDialog.Builder(SendTextActivity.this);
					builder.setTitle(R.string.error_title)
						.setMessage(R.string.send_text_error_message)
						.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				}
				else {
					Intent intent = new Intent(SendTextActivity.this, RecipientsActivity.class);
					intent.putExtra("textMessage", mText.getText().toString());
					intent.putExtra(ParseConstants.KEY_FILE_TYPE, ParseConstants.TYPE_TEXT);
					startActivity(intent);
					finish();
				}
			}
		});
	}

}
