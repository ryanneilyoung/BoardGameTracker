package ca.ryanneilyoung.boardgametracker;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

	String game = null;
	String API_URL = "https://boardgamegeek.com/search/boardgame?q=";
	ProgressBar progressBar;
	Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.activity_main);
		Button button = (Button) findViewById(R.id.queryButton);
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				search(view);
			}
		});
		EditText boardGameText = (EditText) findViewById(R.id.boardGameText);
		boardGameText.setOnKeyListener(new View.OnKeyListener()
		{
			@Override
			public boolean onKey(View view, int i, KeyEvent keyEvent)
			{
				if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
						(i == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press
					search(view);
					return true;
				}
				return false;
			}
		});
		//Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		//setSupportActionBar(toolbar);

		/*
		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if(id == R.id.action_settings)
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	public void search(View view)
	{
		EditText boardGameText = (EditText) findViewById(R.id.boardGameText);
		game = boardGameText.getText().toString();
		if(game.matches(""))
		{
			Snackbar.make(view, "Board Game field is empty, please enter game title", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show();
		}
		else
		{
			RetrieveFeedTask task = new RetrieveFeedTask();
			task.execute();
		}
	}

	private class RetrieveFeedTask extends AsyncTask<Void, Void, String>
	{

		protected void onPreExecute() {
			progressBar = (ProgressBar) findViewById(R.id.progressBar);
			progressBar.setVisibility(View.VISIBLE);
		}

		protected String doInBackground(Void... urls) {

			// Do some validation here

			try {

				String encoded = TextUtils.htmlEncode(game).replace(" ", "+");
				URL url = new URL(API_URL + encoded + "&showcount=20");
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setRequestProperty("Content-Type", "application/json");
				urlConnection.setRequestProperty("Accept", "application/json");
				try {
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
					StringBuilder stringBuilder = new StringBuilder();
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						stringBuilder.append(line).append("\n");
					}
					bufferedReader.close();
					return stringBuilder.toString();
				}
				finally{
					urlConnection.disconnect();
				}
			}
			catch(Exception e) {
				Log.e("ERROR", e.getMessage(), e);
				return null;
			}
		}

		protected void onPostExecute(String response) {
			if(response == null) {
				response = "THERE WAS AN ERROR";
			}
			progressBar.setVisibility(View.GONE);
			Log.i("INFO", response);
			try {
				JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
				JSONArray items = object.getJSONArray("items");
				Log.i("INFO", items.toString());
				ArrayList<BoardGame> games = new ArrayList<>();
				for(int i = 0; i < items.length(); i++)
				{
					JSONObject game = items.getJSONObject(i);
					int gameId = game.getInt("objectid");
					String type = game.getString("subtype");
					String name = game.getString("name");
					int imageId = game.getInt("rep_imageid");
					String href = game.getString("href");
					BoardGame boardGame = new BoardGame(gameId, type, name, imageId, href);
					games.add(boardGame);
				}
				ArrayAdapter adapter = new ArrayAdapter(activity, R.layout.search_result_list, games);
				ListView view = (ListView) findViewById(R.id.listView);
				view.setAdapter(adapter);
				/*
				for(BoardGame boardGame: games)
				{
					TextView textView = new TextView(activity.getApplicationContext());
					textView.setText(boardGame.getName());
					view.addView(textView);
				}
				*/
				//TextView textView = new TextView(activity.getApplicationContext());
				//textView.setText(games.toString());
				//view.addView(textView);
				//responseView.setText(stringBuilder.toString());
			} catch (JSONException e) {
				Log.i("ERROR", e.getMessage());
			}

		}
	}

}
