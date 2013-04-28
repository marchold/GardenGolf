package com.catglo.gardengolf18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;


interface DoForEachLine {
	void parseAndStore(String line);
}

public class HttpDataFetcher {
	private final HttpClient	client;
	private final DoForEachLine	doForEachLine;
	private final String		requestString;

	public HttpDataFetcher(final HttpClient client, final String requestString, final DoForEachLine doForEachLine) {
		this.client = client;
		this.doForEachLine = doForEachLine;
		this.requestString = requestString;
	}

	void fetchAndParse() {
		final HttpGet request = new HttpGet(requestString);
		HttpResponse response;
		try {
			response = client.execute(request);
			InputStream in;

			in = response.getEntity().getContent();

			final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = null;

			while ((line = reader.readLine()) != null) {
				doForEachLine.parseAndStore(line);
			}
			in.close();
		}
		catch (IOException e) {
			Log.i("HttpDataFetcher", "failed " + requestString);
		}
	}
}