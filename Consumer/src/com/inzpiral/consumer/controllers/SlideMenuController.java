package com.inzpiral.consumer.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.inzpiral.consumer.R;
import com.inzpiral.consumer.customs.RingGraphic;
import com.inzpiral.consumer.fragments.SlideMenuFragment.SlideMenuListAdapter;
import com.inzpiral.consumer.models.Evaluation;
import com.inzpiral.consumer.models.Node;
import com.inzpiral.consumer.models.PresentationNode;
import com.inzpiral.consumer.utils.EvaluationHelper;
import com.inzpiral.consumer.views.SlideMenuView;
import com.inzpiral.consumer.views.SpinnersView;

/**
 * LoginController intercepts the on click login button event, verify the inputs 
 *
 */
public class SlideMenuController implements OnClickListener {
	
	private SlideMenuView mSpinnersView;
	private SlideMenuControllerListener mListener;
	private EvaluationHelper mHelper;
	private SlideMenuListAdapter mAdapter;

	public SlideMenuController(SlideMenuView slideMenuView, SlideMenuControllerListener listener) {
		this.mSpinnersView = slideMenuView;
		this.mListener = listener;
		this.mHelper = EvaluationHelper.getInstance();

		mSpinnersView.getRingGraphic().setPercent(75);
		mListener.onSetListAdapter(mHelper.getCategories());
		
//		mAdapter
		for (Node node : mHelper.getCategories()) {
			if(node instanceof PresentationNode) {
				PresentationNode presentationNode = (PresentationNode)node;
				System.out.println(node.getName() + ": " + presentationNode.countAnswers());
				System.out.println(node.getName() + ": " + presentationNode.totalAnswers());
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		Thread t = new Thread(null, new Runnable() {
			@Override
			public void run() {
//				Node v = (Node) EvaluationHelper.getInstance().getRoot();
				Evaluation v = EvaluationHelper.getInstance().getEvaluations();
				Gson gson = new Gson();

				String s = gson.toJson(v);
				System.out.println(s);
				System.out.println(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/json.txt");
				File myFile = new File(Environment.getExternalStorageDirectory() + "/json.txt");
				try {
					myFile.createNewFile();
					FileOutputStream fOut = new FileOutputStream(myFile);
					OutputStreamWriter myOutWriter =new OutputStreamWriter(fOut);
					myOutWriter.append(s);
					myOutWriter.close();
					fOut.close();
					System.out.println("guardo todo en un txt");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, "saving", 1024 * 1024);
		t.start();

		try {
			t.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
//        File myFile = new File("/sdcard/myjsonstuff.txt");
//        FileInputStream fIn = new FileInputStream(myFile);
//        BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
//        String aDataRow = "";
//        String aBuffer = ""; //Holds the text
//        while ((aDataRow = myReader.readLine()) != null) 
//        {
//            aBuffer += aDataRow ;
//        }
//        myReader.close();
//		
	}
	
	// Interfaces
	public interface SlideMenuControllerListener {
		public void onSetListAdapter(List<Node> list);
	}

}
