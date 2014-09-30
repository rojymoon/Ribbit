package com.treehouselearning.ribbit;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class RibbitApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "DRLaMM4N2PrAOXjjbdXbOv4Fp9Gt2zv2OWZ5TpRM",
				"beiIlODhajVYeKFD48pyMAmnkcggXigbCODGgKO7");		
	}

}
