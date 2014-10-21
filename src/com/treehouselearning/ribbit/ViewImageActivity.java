package com.treehouselearning.ribbit;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewImageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_image);
		
		ImageView imageView = (ImageView) findViewById(R.id.imageView);
		TextView textMessage = (TextView) findViewById(R.id.textMessageView);
		
		if(getIntent().getData() != null){
			
			Uri imageUri = getIntent().getData();					
			Picasso.with(this).load(imageUri).placeholder(R.drawable.ic_action_picture).into(imageView);			
		}
		else {
			Log.i("textMessage", "textttt");
			textMessage.setText(getIntent().getExtras().getString(ParseConstants.KEY_TEXT_MESSAGE));
		}
	}
}
