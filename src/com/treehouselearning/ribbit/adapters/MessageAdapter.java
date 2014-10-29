package com.treehouselearning.ribbit.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.treehouselearning.ribbit.R;
import com.treehouselearning.ribbit.utils.ParseConstants;
import com.treehouselearning.ribbit.utils.TimeUtil;

public class MessageAdapter extends ArrayAdapter<ParseObject> {
	
	protected Context mContext;
	protected List<ParseObject> mMessages;
	
	public MessageAdapter(Context context, List<ParseObject> messages){
		super(context, R.layout.message_item, messages);
		mContext = context;
		mMessages = messages;		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, null);
			holder = new ViewHolder();
			holder.iconImageView = (ImageView) convertView.findViewById(R.id.messageIcon);
			holder.nameLabel = (TextView) convertView.findViewById(R.id.senderLabel);
			holder.timeLabel = (TextView) convertView.findViewById(R.id.timeLabel);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		ParseObject message = mMessages.get(position);
		
		if(message.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_IMAGE)){
			holder.iconImageView.setImageResource(R.drawable.ic_picture);
		}
		else if(message.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_VIDEO)){
			holder.iconImageView.setImageResource(R.drawable.ic_video);
		}
		else {
			holder.iconImageView.setImageResource(R.drawable.ic_action_chat_holo_light);
		}
		
		holder.nameLabel.setText(message.getString(ParseConstants.KEY_SENDER_NAME));		
		holder.timeLabel.setText(TimeUtil.getTimeAgo(message.getCreatedAt().getTime(), mContext));
		
	/*	Date createdAt = message.getCreatedAt();
		long now = new Date().getTime();
		String convertedDate = DateUtils.getRelativeTimeSpanString(
				createdAt.getTime(), now, DateUtils.SECOND_IN_MILLIS).toString();
		
		holder.timeLabel.setText(convertedDate);*/
				
		//Log.i("Elapsed Time: ", TimeUtil.getTimeAgo(message.getCreatedAt().getTime(), mContext));
		return convertView;
	}
	
	private static class ViewHolder{
		ImageView iconImageView;
		TextView nameLabel;
		TextView timeLabel;
	}
	
	public void refill(List<ParseObject> messages) {
		mMessages.clear();
		mMessages.addAll(messages);
		notifyDataSetChanged();
	}

}
