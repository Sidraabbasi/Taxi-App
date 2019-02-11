package com.ukcl.driverapp.viewbinders;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.sromku.simple.fb.entities.User;
import com.ukcl.driverapp.R;
import com.ukcl.driverapp.viewbinders.abstracts.ViewBinder;


public class SearchBinder extends ViewBinder<User>{
	
	private boolean isRecentlyUsed;
	public SearchBinder(int layoutResId) {
		super(R.layout.list_item_type);
	}

	public SearchBinder(boolean isRecentlyUsed, OnClickListener onClickListener) {
		super(R.layout.list_item_type);
		this.isRecentlyUsed = isRecentlyUsed;
	}
	
	@Override
	public BaseViewHolder createViewHolder(View view) {
		return new ViewHolder(view);
	}
	

	@Override
	public void bindView(User entity, int position, int grpPosition,
			View view, Activity activity) {
		    ViewHolder holder = (ViewHolder) view.getTag();
	
		    if(isRecentlyUsed){
		    	//holder.imgView.setImageResource(entity.getImage());
		    }
		    else {
		    	//holder.imgView.setImageResource(entity.getImage());
		    }
		    	
	}
	
	protected static class ViewHolder extends BaseViewHolder {

		private ImageView imgView;
		//private AnyTextView txtView;

		public ViewHolder(View view) {
			//this.txtView  = (AnyTextView) view.findViewById( R.id.txtView);
		}

	}
	
	
	
}
