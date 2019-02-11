package com.ukcl.driverapp.viewbinders;


import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.ukcl.driverapp.R;
import com.ukcl.driverapp.model.User;
import com.ukcl.driverapp.viewbinders.abstracts.ViewBinder;

public class ChildBinder extends ViewBinder<User> {

	public ChildBinder() {
		super(R.layout.activity_history);
	}

	@Override
	public BaseViewHolder createViewHolder(View view) {
		return new ViewHolder(view);
	}

	@Override
	public void bindView(User entity, int position, int grpPosition,
			View view, Activity activity) {
		ViewHolder holder = (ViewHolder) view.getTag();
		//holder.txtTitle.setText(entity.getCategory_title());
//		holder.txtTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_arrow_enter, 0, 0, 0);
	}

	protected static class ViewHolder extends BaseViewHolder {

		private TextView txtTitle;

		public ViewHolder(View view) {
			this.txtTitle = (TextView) view.findViewById(R.id.edit_query);
		}

	}

}
