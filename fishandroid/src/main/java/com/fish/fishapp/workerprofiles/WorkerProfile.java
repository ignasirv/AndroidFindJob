package com.fish.fishapp.workerprofiles;

import android.graphics.Bitmap;
import android.location.Location;

import com.fish.fishapp.App;
import com.fish.fishapp.R;
import com.fish.fishapp.Usuari;
import com.fish.fishapp.utils.Server;
import com.fish.fishapp.utils.ServerException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class WorkerProfile {
	
	//public String workerProfileObjectId;

	public String ObjectId;
	public int userID;
	public String pictureURL;
	public Bitmap picture;
	public Integer workType;
	public Location location;
	public String ciudad;//locationName;
	public String locationCountry;
	public String distancia;//distance;
	public ArrayList<String> tags;
	//public String tags;
	public String currency;
	public Integer precioHora;//priceHour;
	public Integer priceDay;
	public Integer priceWeek;
	public List<Date> availabilityCalendar;
	public String disponibilidad;
	public Date deletedAt;
	public String trabajo;
	public int fotoInt;

	
	
	public void getNewWorkerProfile(){
		
		Usuari user = App.getInstance().usuari;
		
		//Alimenta los datos por defecto, procedentes del perfil de usuario
		ObjectId=user.ObjectID;
		userID = user.userID;
		//pictureURL= user.profilePictureURL;
		//picture = user.profilePicture;
		workType = 1;
		//locationName = usuari.profileLocationName;
		ciudad = user.profileLocationName;
		location= user.profileLocation;
		App.getInstance().log("locationcountry is:" + user.profileLocationCountry);
		locationCountry= user.profileLocationCountry;
		//distance="1";
		distancia="1";
		tags=null;
		currency= user.profileCurrency;
		//priceHour=0;
		precioHora=0;
		priceDay=0;
		priceWeek=0;
		//availabilityCalendar = new ArrayList<Date>();
		
	}
	
	public void save(Boolean pictureChanged) {
		//Save to QB
		try {
			Server.saveWorkerProfile(this, pictureChanged,ObjectId);
		} catch (ServerException e) {
			e.printStackTrace();
			App.getInstance().missatgeEnPantalla(App.getInstance().getStringResource(R.string.server_error));
		}
	}	
}
