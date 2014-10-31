package com.treehouselearning.ribbit;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;
import com.treehouselearning.ribbit.ui.MainActivity;
import com.treehouselearning.ribbit.utils.ParseConstants;

public class RibbitApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, getString(R.string.parse_app_id),
				getString(R.string.parse_client_key));		
		
		// Also in this method, specify a default Activity to handle push notifications
		  //PushService.setDefaultPushCallback(this, MainActivity.class);
		  PushService.setDefaultPushCallback(this, MainActivity.class, R.drawable.ic_stat_ic_launcher);
		  ParseInstallation.getCurrentInstallation().saveInBackground();
	}
	
	public static void updateParseInstallation(ParseUser user) {
		ParseInstallation installation = ParseInstallation.getCurrentInstallation();
		installation.put(ParseConstants.KEY_USER_ID, user.getObjectId());
		installation.saveInBackground();
	}

}
