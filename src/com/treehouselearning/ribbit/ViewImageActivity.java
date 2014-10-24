package com.treehouselearning.ribbit;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewImageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_view_image);
		
		ImageView imageView = (ImageView) findViewById(R.id.imageView);
		TextView textMessage = (TextView) findViewById(R.id.textMessageView);
		
		if(getIntent().getData() != null){
			
			//setProgressBarIndeterminateVisibility(true);
			Uri imageUri = getIntent().getData();					
			Picasso.with(this).load(imageUri).error(R.drawable.ic_action_picture).placeholder(R.drawable.ic_action_picture).into(imageView);
			
			//setProgressBarIndeterminateVisibility(false);
			/*
			Timer timer = new Timer();
		    timer.schedule(new TimerTask()
		    {
		        @Override
		        public void run()
		        {
		            finish();
		        }
		    }, 10*1000);*/
		}
		else {
			Log.i("textMessage", "textttt");
			textMessage.setText(getIntent().getExtras().getString(ParseConstants.KEY_TEXT_MESSAGE));
		}
		
		
	}

}
