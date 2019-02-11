package com.ukcl.driverapp.viewbinders;
/*package com.taxinow.driverapp.viewbinders;


import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.appostrophic.sdaring.R;
import com.taxinow.driverapp.viewbinders.abstracts.ViewBinder;
import com.upkeep.data.entities.Category;
import com.upkeep.ui.views.AnyTextView;

public class GroupBinder extends ViewBinder<Category> {

	public GroupBinder() {
		super(R.layout.list_item_sub_menu);
	}

	@Override
	public BaseViewHolder createViewHolder(View view) {
		return new ViewHolder(view);
	}

	@Override
	public void bindView(Category entity, int position, int grpPosition,
			View view, Activity activity) {
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.txtTitle.setText(entity.getCategory_title());
	}

	public void bindView(Category entity, int position, View view,
			boolean isExpanded, Activity activity) {
		ViewHolder holder = (ViewHolder) view.getTag();

		holder.imgView.setImageResource(entity.getCategory_icon());
		
		if (entity.getSubNewsCategory()== null || entity.getSubNewsCategory().size() == 0)
			holder.txtTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		else if (!isExpanded)
			holder.txtTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ic_launcher, 0);
		else
			holder.txtTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_launcher, 0);
		
			holder.txtTitle.setText(entity.getCategory_title());
			
			
			
	}

	 

	protected static class ViewHolder extends BaseViewHolder {

		private AnyTextView txtTitle;
		private ImageView imgView;

		public ViewHolder(View view) {
			this.txtTitle = (AnyTextView) view.findViewById(R.id.txtView);
			this.imgView  = (ImageView) view.findViewById( R.id.imgView );
		}

	}
}
*/