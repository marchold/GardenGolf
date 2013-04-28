package com.catglo.gardengolf18;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HighScoreDialog extends Dialog implements OnClickListener {

	Button okButton;
	TextView gameName;
	TextView score;
	EditText userName;
	private SharedPreferences sharedPreferences;
	private Editor prefEditor;

	public HighScoreDialog(Context context) {
		super(context);
		/** 'Window.FEATURE_NO_TITLE' - Used to hide the title */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/** Design the dialog in main.xml file */
		setContentView(R.layout.high_score_dialog);
		okButton = (Button) findViewById(R.id.OkButton);
		okButton.setOnClickListener(this);
		
		gameName = (TextView)findViewById(R.id.gameName);
		
		score = (TextView)findViewById(R.id.score);
		
		userName = (EditText)findViewById(R.id.userHandle);
		
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
		prefEditor = sharedPreferences.edit();
	}

	public void onClick(View arg0) {
		 String name = userName.getText().toString();
		 if (name!=null && name.length()>0){
			 prefEditor.putString("userName", name);
		 }
		 dismiss();
	}

	
}
