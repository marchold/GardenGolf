package com.catglo.gardengolf18;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

public class ReviewMePreference extends EditTextPreference {

	Context	context;

	public ReviewMePreference(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	@Override
	protected void onClick() {
		final Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=com.catglo.gardengolf18")); 
		context.startActivity(marketIntent);
	}

}