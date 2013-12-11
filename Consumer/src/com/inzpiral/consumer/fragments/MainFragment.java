package com.inzpiral.consumer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.inzpiral.consumer.R;
import com.inzpiral.consumer.activities.HomeActivity;
import com.inzpiral.consumer.controllers.MainController;
import com.inzpiral.consumer.controllers.MainController.MainControllerListener;
import com.inzpiral.consumer.models.Evaluation;
import com.inzpiral.consumer.views.MainView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainFragment extends SherlockFragment implements MainControllerListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	return inflater.inflate(R.layout.fragment_main, container, false);
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	super.onViewCreated(view, savedInstanceState);

		// Obtener evaluacion
		Evaluation ev = ((HomeActivity) getActivity()).getEvaluation();
		System.out.println("Desde Main: " + ev.getName());
		
		// Activity links the view and the controller
    	MainController mainController = new MainController((MainView) view.findViewById(R.id.main_view), this);
		
		// Intercept the events of MainView
//		((MainView) view.findViewById(R.id.main_view)).setListeners(spinnerController);

	}

}
