package com.fish.fishapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fish.fishapp.feines.FeinesLlistat_Activity;
import com.fish.fishapp.feines.InfoContracteActivity;
import com.fish.fishapp.utils.Server;


import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBSettings;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.users.model.QBUser;

import org.json.JSONException;
import org.json.JSONObject;

public class Main_Activity extends Activity {
    // Creddencials Quickblox
    static final String APP_ID = "40090";
    static final String AUTH_KEY = "RTRE77YTj83X5js";
    static final String AUTH_SECRET = "DazYncmnOA2T3ra";
    static final String ACCOUNT_KEY = "cFLbCoJkVJsHmw6YGduo";



    @Override
    public void onDestroy(){

        App.getInstance().log("*");
        App.getInstance().log("************** Tanquem la classe '" + this.getClass().getSimpleName() + "' **************");
        App.getInstance().log("*");

        super.onDestroy();
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {

        QBSettings.getInstance().init(getApplicationContext(), APP_ID, AUTH_KEY, AUTH_SECRET);
        App.getInstance().log("Iniciem conexió amb QB");
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        //Creació de la sessió QB

        App.getInstance().log("*");
        App.getInstance().log("************** Iniciem la classe '" + this.getClass().getSimpleName() + "' **************");
        App.getInstance().log("*");

		super.onCreate(savedInstanceState);

		// El mètode "trackAppOpened(getIntent())" està obsolet. En el seu lloc es fa servir el mètode "trackAppOpenedInBackground(getIntent())"

       // ParseAnalytics.trackAppOpenedInBackground(getIntent());

		App.getInstance().appContext = this.getApplicationContext();

		App.getInstance().ActualizandoGeoposicionUsuario = false;

       // ParseUser pUser = ParseUser.getCurrentUser();
        QBUser qbUser= new QBUser();
        QBCustomObject object = new QBCustomObject();

        App.getInstance().log("Sessió no iniciada. Presentem la pantalla prèvia al inici de sessió a través de Facebook");

        Intent intent2 = new Intent(App.getInstance().appContext, Login_Activity.class);

        startActivity(intent2);



        // Comprovem si ha iniciat sessió

        if (qbUser.getLogin() == null){

            // Sessió no iniciada. Presentem la pantalla prèvia al inici de sessió a través de Facebook

            App.getInstance().log("Sessió no iniciada. Presentem la pantalla prèvia al inici de sessió a través de Facebook");

        	Intent intent = new Intent(App.getInstance().appContext, Login_Activity.class);

            startActivity(intent);

        } else {

            // Sessió iniciada

            App.getInstance().log("Sessió iniciada");

        	Server.setUserData(qbUser);

            // Comprovem si ha acceptt l'EULA

        	if (!App.getInstance().usuari.eulaAccepted){

                // No ha acceptat l'EULA. Presentem la pantalla de l'EULA, perquè l'usuari les accepti

                App.getInstance().log("No ha acceptat l'EULA. Presentem la pantalla de l'EULA, perquè l'usuari les accepti");

        		Intent intent = new Intent(App.getInstance().appContext, EulaAcceptar_Activity.class);

                startActivity(intent);

        	} else {

                // Sí ha acceptat l'EULA. Comprovem si l'usuari està geolocalizat

        		if (App.getInstance().usuari.profileLocation == null){

                    // L'usuari no està geolocalitzat. Presentem la pantalla de geolocalització

                    App.getInstance().log("L'usuari no està geolocalitzat. Presentem la pantalla de geolocalització");

        			Intent intent = new Intent(App.getInstance().appContext, Geolocalitzacio_Activity.class);

	                startActivity(intent);

        		} else {
                    App.getInstance().log("GEOLOCALITZAT i Comprovar si és push");

	        		// L'usuari sí està geolocalitzat. Accedim a la pantalla principal. Ja ha iniciat sessió, acceptat l'EULA i està geoposicionat


                    if (getIntent() != null && getIntent().getStringExtra("idJobsHiring") != null) {
                        App.getInstance().log("PUSH: " + getIntent().getStringExtra("idJobsHiring"));
                        Intent pushIntent = new Intent(Main_Activity.this, InfoContracteActivity.class);
                        pushIntent.putExtra("idJobsHiring", getIntent().getStringExtra("idJobsHiring"));
                        pushIntent.putExtra("priceHour", getIntent().getStringExtra("priceHour"));
                        pushIntent.putExtra("dateFinish", getIntent().getStringExtra("dateFinish"));
                        pushIntent.putExtra("dateStart", getIntent().getStringExtra("dateStart"));
                        pushIntent.putExtra("jobOffered", getIntent().getStringExtra("jobOffered"));
                        pushIntent.putExtra("fromUser", getIntent().getStringExtra("fromUser"));


                        startActivity(pushIntent);
                        this.finish();


                    } else {
                        App.getInstance().log("L'usuari sí està geolocalitzat. Accedim a la pantalla principal. Ja ha iniciat sessió, acceptat l'EULA i està geoposicionat");
                        Intent intent = new Intent(App.getInstance().appContext, FeinesLlistat_Activity.class);
                        startActivity(intent);
                        this.finish();

                    }



        		}
        	}
        }

        finish();
	}
}
