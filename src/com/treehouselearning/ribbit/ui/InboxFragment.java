package com.treehouselearning.ribbit.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.treehouselearning.ribbit.R;
import com.treehouselearning.ribbit.adapters.MessageAdapter;
import com.treehouselearning.ribbit.utils.ParseConstants;

public class InboxFragment extends ListFragment {

	protected List<ParseObject> mMessages;
	protected SwipeRefreshLayout mSwipeRefreshLayout;
	//private static final String LIST_STATE = "listState";
	//private Parcelable mListState = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_inbox, container,
				false);
		
		mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
		mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
		mSwipeRefreshLayout.setColorScheme(
				R.color.swipeRefresh1, 
				R.color.swipeRefresh2, 
				R.color.swipeRefresh3, 
				R.color.swipeRefresh4);
		
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		getActivity().setProgressBarIndeterminateVisibility(true);

		retrieveMessages();
	}

	private void retrieveMessages() {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
				ParseConstants.CLASS_MESSAGES);
		query.whereEqualTo(ParseConstants.KEY_RECIPIENT_IDS, ParseUser
				.getCurrentUser().getObjectId());
		query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> messages, ParseException e) {
				getActivity().setProgressBarIndeterminateVisibility(false);
				
				if(mSwipeRefreshLayout.isRefreshing()) {
					mSwipeRefreshLayout.setRefreshing(false);
				}

				if (e == null) {
					// We found messages!
					mMessages = messages;

					/*
					 * String[] usernames = new String[mMessages.size()]; int
					 * i=0; for(ParseObject message : mMessages){ usernames[i] =
					 * message.getString(ParseConstants.KEY_SENDER_NAME); i++; }
					 */

					if (getListView().getAdapter() == null) {
						MessageAdapter adapter = new MessageAdapter(
								getListView().getContext(), mMessages);
						setListAdapter(adapter);
					} else {
						// Refill the adapter
						((MessageAdapter) getListView().getAdapter())
								.refill(mMessages);
					}
				}

			}
		});
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		ParseObject message = mMessages.get(position);
		String messageType = message.getString(ParseConstants.KEY_FILE_TYPE);

		if (messageType.equals(ParseConstants.TYPE_TEXT)) {
			String textMessage = message
					.getString(ParseConstants.KEY_TEXT_MESSAGE);
			Intent intent = new Intent(getActivity(), ViewImageActivity.class);
			intent.putExtra(ParseConstants.KEY_TEXT_MESSAGE, textMessage);
			startActivity(intent);
		} else {
			ParseFile file = message.getParseFile(ParseConstants.KEY_FILE);
			Uri fileUri = Uri.parse(file.getUrl());

			if (messageType.equals(ParseConstants.TYPE_IMAGE)) {
				// View the image
				Intent intent = new Intent(getActivity(),
						ViewImageActivity.class);
				intent.setData(fileUri);
				startActivity(intent);
			} else {
				// View the video
				Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
				intent.setDataAndType(fileUri, "video/*");
				startActivity(intent);
			}

			// Delete it!
			List<String> ids = message
					.getList(ParseConstants.KEY_RECIPIENT_IDS);
			if (ids.size() == 1) {
				// last recipient - delete the whole thing!
				message.deleteInBackground();
			} else {
				// remove the recipient and save
				ids.remove(ParseUser.getCurrentUser().getObjectId());

				ArrayList<String> idsToRemove = new ArrayList<String>();
				idsToRemove.add(ParseUser.getCurrentUser().getObjectId());

				message.removeAll(ParseConstants.KEY_RECIPIENT_IDS, idsToRemove);
				message.saveInBackground();

				//new CallRestApi().execute();
			}
		}
	}	
	
	protected OnRefreshListener mOnRefreshListener = new OnRefreshListener() {		
		@Override
		public void onRefresh() {			
			retrieveMessages();
		}
	};
	
/*	private class CallRestApi extends AsyncTask<String, String, String> {
		*//**
		 * The system calls this to perform work in a worker thread and delivers
		 * it the parameters given to AsyncTask.execute()
		 *//*
		protected String doInBackground(String... urls) {

			//String urlString = urls[0]; // URL to call
			String resultToDisplay = "";

			String Content_Type = "application/json";

			String ENDPOINT = "https://api.parse.com/1/files/tfss-725ad5fe-3959-4399-8e82-5f83c89d2cf6-uploaded_file.png";
			try {
				// Create connection
				URL url = new URL(ENDPOINT);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();

				connection.setRequestMethod("DELETE");
				connection.setRequestProperty("X-Parse-Application-Id", getString(R.string.parse_app_id));
				connection.setRequestProperty("X-Parse-Master-Key", getString(R.string.parse_master_key));
				//connection.setRequestProperty("Content-Type", Content_Type);

			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			
			 * // HTTP Get try { HttpParams httpParams = new BasicHttpParams();
			 * HttpClient client = new DefaultHttpClient(httpParams); HttpPost
			 * httpost = new
			 * HttpPost("http://employeestracking.appspot.com/clockin.add_clockin"
			 * ); httpost.setHeader("X-Parse-Application-Id",
			 * getString(R.string.parse_app_id));
			 * httpost.setHeader("X-Parse-Master-Key",
			 * getString(R.string.parse_master_key)); System.out.println("2");
			 * JSONObject data = new JSONObject(); JSONObject userrequest = new
			 * JSONObject(); // /HttpResponse response = null; } catch
			 * (Exception e ) { System.out.println(e.getMessage()); return
			 * e.getMessage(); }
			 
			return resultToDisplay;
		}

		*//**
		 * The system calls this to perform work in the UI thread and delivers
		 * the result from doInBackground()
		 *//*
		protected void onPostExecute(String result) {
			// show toast after deletion
		}
	}*/

}
