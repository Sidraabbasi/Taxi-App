package com.ukcl.driverapp.viewbinders;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ukcl.driverapp.R;
import com.ukcl.driverapp.model.AlarmInfo;
import com.ukcl.driverapp.model.Request;
import com.ukcl.driverapp.utills.PreferenceHelper;
import com.ukcl.driverapp.viewbinders.abstracts.ViewBinder;


public class BookingsBinder extends ViewBinder<Request> {
	OnClickListener onClikClickListener;
	PreferenceHelper preferenceHelper;
	AlarmInfo alarmInfo;
	ArrayList<AlarmInfo> arrayListBookings = new ArrayList<AlarmInfo>();
	public BookingsBinder(OnClickListener onClickListener, PreferenceHelper preferenceHelper) {
		super(R.layout.list_item_bookings);
		this.onClikClickListener = onClickListener;
		this.preferenceHelper = preferenceHelper;
	}
	
	@Override
	public BaseViewHolder createViewHolder(View view) {
		return new ViewHolder(view);
	}
	
	@Override
	public void bindView(Request entity, int position, int grpPosition,
			View view, Activity activity) {
		    ViewHolder holder = (ViewHolder) view.getTag();
		    holder.tvBookingName.setText(entity.getStarting_address());
		    alarmInfo = checkBooking(entity,preferenceHelper); 
		    if(alarmInfo == null){
		    	holder.btnsetalarm.setVisibility(View.VISIBLE);
		    	holder.btnsetalarm.setOnClickListener(onClikClickListener);
			    holder.btnsetalarm.setTag(entity);	
		    }else {
		    	holder.btnsetalarm.setVisibility(View.VISIBLE);
		    	holder.btnsetalarm.setText(alarmInfo.getTime());
		    }
		    
		    ImageLoader.getInstance().displayImage(entity.getOwner().getPicture(), holder.ivIcon);
	}
	private AlarmInfo checkBooking(Request entity, PreferenceHelper preferenceHelper2) {
		 
		 arrayListBookings  = preferenceHelper2.getHistory();
		 if(arrayListBookings != null && arrayListBookings.size() > 0) {
			 for(AlarmInfo bookingInfo: arrayListBookings) {
				 if(bookingInfo.getId().equalsIgnoreCase(entity.getId())){
					 bookingInfo.setAlarmSet(true);
					 return bookingInfo;
				 }
			 }
		 }
		 
		 return null;
	}
	protected static class ViewHolder extends BaseViewHolder {

		private ImageView ivIcon;
		private TextView tvBookingName;
		Button btnsetalarm;
		public ViewHolder(View view) {
			this.tvBookingName  = (TextView) view.findViewById( R.id.tvBookingName);
			this.ivIcon  = (ImageView) view.findViewById( R.id.ivIcon);
			this.btnsetalarm = (Button) view.findViewById( R.id.btnsetalarm);
		}

	}
	
	
	
}
