/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ukcl.driverapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.gson.Gson;
import com.google.maps.android.clustering.ClusterManager;
import com.ukcl.driverapp.locationupdate.LocationHelper;
import com.ukcl.driverapp.locationupdate.LocationHelper.OnLocationReceived;
import com.ukcl.driverapp.model.MyItem;
import com.ukcl.driverapp.model.SurgeAreaModel;
import com.ukcl.driverapp.model.SurgeAreaModel.Surge;
import com.ukcl.driverapp.parse.AsyncTaskCompleteListener;
import com.ukcl.driverapp.parse.HttpRequester;
import com.ukcl.driverapp.utills.AndyConstants;
import com.ukcl.driverapp.utills.AndyUtils;

public class SurgeActivity extends BaseSurgeActivity implements AsyncTaskCompleteListener {
    private ClusterManager<MyItem> mClusterManager;
    LocationHelper locationHelper;
    LatLng currentLatLong;
    LatLng defaultLatlng;
    boolean isLocationSet  = false;
    
    ArrayList<LatLng> latLong = new ArrayList<LatLng>();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	defaultLatlng =  new LatLng(51.503186, -0.126446);
    	getAllSurgeAreas();
    }
    
    @Override
    protected void startSurgePoints() {
    	
    	locationHelper = new LocationHelper(this);
    	currentLatLong = locationHelper.getCurrentLocation(); 
   /* 	if(currentLatLong != null){
    		//drawMarker(currentLatLong);
            getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 10));
    	}else {
    		drawMarker(currentLatLong, R.drawable.pin_driver);
    		getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));
    		
    	}
        mClusterManager = new ClusterManager<MyItem>(this, getMap());
        getMap().setOnCameraChangeListener(mClusterManager);
        try {
           // readItems();
        } catch (JSONException e) {
            Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }*/
        
   	 	locationHelper.setLocationReceivedLister(new OnLocationReceived() {

			@Override
			public void onLocationReceived(LatLng latlong) {
				if(latlong != null){
					if(!isLocationSet){
						isLocationSet =true;
						populateDummySurgePoints(latlong);
					}
					//locationHelper.o
					
				}else {
					drawMarker(latlong, R.drawable.pin_driver,"Driver Location");
		    		getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatlng, 10));
		    	}
			}

		});
        locationHelper.onStart();
       
    }
    
    protected void populateDummySurgePoints(LatLng latlong) {
    	LatLng latLngTmp = ((latlong == null) ? defaultLatlng : latlong );
    	LatLng ltlngSp = null;
    	drawMarker(latLngTmp, R.drawable.pin_driver, "Driver Location");
		getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(latLngTmp, 10));
		
		
	/*	for(int i=0;i<3;i++){
			if(i == 0) {
				ltlngSp = new LatLng(latLngTmp.latitude + 0.3,latLngTmp.longitude);
				drawMarker(ltlngSp, R.drawable.pin_surge, "");
				latLong.add(ltlngSp);
				
				Draw_Circle(ltlngSp);
				
				
			}
			if(i == 1) {
				ltlngSp = new LatLng(latLngTmp.latitude ,latLngTmp.longitude +0.1);
				drawMarker(ltlngSp, R.drawable.pin_client, "Surge Area 4");
				latLong.add(ltlngSp);
			}
			if(i == 2) {
				ltlngSp = new LatLng(latLngTmp.latitude +0.01 ,latLngTmp.longitude +0.2);
				drawMarker(ltlngSp, R.drawable.pin_surge, "");
				latLong.add(ltlngSp);
				Draw_Circle(ltlngSp);
				
			}
			if(i == 1) {
				ltlngSp = new LatLng(latLngTmp.latitude + 0.1 ,latLngTmp.longitude  + 0.3);
				drawMarker(ltlngSp, R.drawable.pin_surge, "");
				latLong.add(ltlngSp);
				Draw_Circle(ltlngSp);
				
			}
			
		}*/
		
		
		
		//Draw_Map();
	
		/*latLong.clear();
		
		for(int i=0;i<3;i++){
			if(i == 0) {
				ltlngSp = new LatLng(latLngTmp.latitude + 0.1,latLngTmp.longitude);
				drawMarker(ltlngSp, R.drawable.pin_surge, "");
				latLong.add(ltlngSp);
			}
			if(i == 1) {
				ltlngSp = new LatLng(latLngTmp.latitude ,latLngTmp.longitude +0.1);
				drawMarker(ltlngSp, R.drawable.pin_client, "Surge Area 4");
				latLong.add(ltlngSp);
			}
			if(i == 2) {
				ltlngSp = new LatLng(latLngTmp.latitude +0.01 ,latLngTmp.longitude +0.05);
				drawMarker(ltlngSp, R.drawable.pin_surge, "");
				latLong.add(ltlngSp);
			}
			if(i == 1) {
				ltlngSp = new LatLng(latLngTmp.latitude + 0.1 ,latLngTmp.longitude  + 0.1);
				drawMarker(ltlngSp, R.drawable.pin_surge, "");
				latLong.add(ltlngSp);
			}
			
			
			Draw_Map();*/
				
			
		//}
		
		
		
		
		
		
		
    }

	public void drawMarker (LatLng latlng, int pin, String title) {
    	
    	getMap().addMarker(new MarkerOptions().position(
    			latlng).title(title).icon(
				BitmapDescriptorFactory
						.fromResource(pin)));
		
    	
    }
    
	
	public void draw_Circle(LatLng latlng, float radius) {  
		
		  CircleOptions rectOptions = new CircleOptions(); 
		  rectOptions.center(latlng);  
		  rectOptions.strokeColor(Color.RED);  
		  rectOptions.fillColor(getResources().getColor(R.color.black_trans_dark));  
		  rectOptions.strokeWidth(1);  
		  rectOptions.radius((radius * 1609.34)); // convert meters to miles
		  
		  getMap().addCircle(rectOptions);  
		  
		/*  if (latLong.size() > 1) {  
			    PolylineOptions polyLine = new PolylineOptions().color(  
			      Color.BLUE).width((float) 7.0);  
			    //polyLine.add(latLong);  
			    LatLng previousPoint = latLong.get(latLong.size() - 2);  
			    polyLine.add(previousPoint);  
			    getMap().addPolyline(polyLine);  
			   }*/  
			
		  
	}  
	
	public void Draw_Map(ArrayList<LatLng> lalong) {  
		
		  PolygonOptions rectOptions = new PolygonOptions(); 
		  rectOptions.zIndex((float) 0.0);
		  rectOptions.addAll(latLong);  
		  rectOptions.strokeColor(Color.BLUE);  
		  rectOptions.fillColor(getResources().getColor(R.color.black_trans_dark));  
		  rectOptions.strokeWidth(7);  
		  getMap().addPolygon(rectOptions);  
		  
		  
		/*  if (latLong.size() > 1) {  
			    PolylineOptions polyLine = new PolylineOptions().color(  
			      Color.BLUE).width((float) 7.0);  
			    //polyLine.add(latLong);  
			    LatLng previousPoint = latLong.get(latLong.size() - 2);  
			    polyLine.add(previousPoint);  
			    getMap().addPolyline(polyLine);  
			   }*/  
			
		  
	}  
	
    /*private void readItems() throws JSONException {
    	
    	
        InputStream inputStream = getResources().openRawResource(R.raw.radar_search);
        List<MyItem> items = new MyItemReader().read(inputStream);
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            for (MyItem item : items) {
                LatLng position = item.getPosition();
                double lat = position.latitude + offset;
                double lng = position.longitude + offset;
                MyItem offsetItem = new MyItem(lat, lng);
                mClusterManager.addItem(offsetItem);
            }
        }
    }*/
	
	
	private void getAllSurgeAreas() {
		if (!AndyUtils.isNetworkAvailable(this)) {
			AndyUtils.showToast(
					getResources().getString(R.string.toast_no_internet),
					this);
			return;
		}
		AndyUtils.showCustomProgressDialog(this, "", getResources()
				.getString(R.string.progress_loading), false);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(AndyConstants.URL, AndyConstants.ServiceType.GET_ALL_SURGE);
		new HttpRequester(this, map,
				AndyConstants.ServiceCode.SURGE_AREA, this);

	}
	
	
	@Override
	public void onTaskCompleted(String response, int serviceCode) {
		AndyUtils.removeCustomProgressDialog();
		switch (serviceCode) {
		case AndyConstants.ServiceCode.SURGE_AREA:
			Gson gson = new Gson();
			SurgeAreaModel surgeArea = gson.fromJson(response, SurgeAreaModel.class);
			if(surgeArea != null && surgeArea.isSuccess()) {
				loadSurgeAreas(surgeArea);
				
			}else {
				AndyUtils.showToast(
						getResources().getString(R.string.toast_no_surge_area),
						this);
			}
			break;
		default:
			break;
		}
	}

	private void loadSurgeAreas(SurgeAreaModel surgeArea) {
		LatLng latlong = null;
		float radius  = 0;
		if(surgeArea.getResponse().size() > 0) {
			for(Surge surge : surgeArea.getResponse() ){
				latlong = new LatLng(Double.parseDouble(surge.getLatitude()), Double.parseDouble(surge.getLongitude()));
				radius = Float.parseFloat(surge.getRadius());
				draw_Circle(latlong , radius);
			}
		}
		
	}

	
	
}