package ca.ryanneilyoung.boardgametracker;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
		/*Button button = findViewById(R.id.queryButton);
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				InputMethodManager inputManager = (InputMethodManager)
						getSystemService(Context.INPUT_METHOD_SERVICE);

				inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
				search(view);
			}
		});
		EditText boardGameText = findViewById(R.id.boardGameText);
		boardGameText.setOnKeyListener(new View.OnKeyListener()
		{
			@Override
			public boolean onKey(View view, int i, KeyEvent keyEvent)
			{
				if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
						(i == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press
					search(view);
					InputMethodManager inputManager = (InputMethodManager)
							getSystemService(Context.INPUT_METHOD_SERVICE);

					inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
					return true;
				}
				return false;
			}
		});*/
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_main, menu);
		MenuItem searchViewItem = menu.findItem(R.id.action_search);
		// Get the SearchView and set the searchable configuration
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		final SearchView searchView = (SearchView) searchViewItem.getActionView();
		searchView.setQueryHint(getResources().getText(R.string.searchHint));
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
		{
			@Override
			public boolean onQueryTextSubmit(String query)
			{
				game = query;
				search(searchView);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText)
			{
				//game = newText;
				//search(searchView);
				return false;
			}
		});
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
		if(id == R.id.action_search)
		{
			Log.e("ERROR", id + "test");
			return true;
		}

		return super.onOptionsItemSelected(item);
	}


	public void search(View view)
	{
		//EditText boardGameText = findViewById(R.id.boardGameText);
		//game = boardGameText.getText().toString();
		ListView listView = findViewById(R.id.listView);
		listView.setAdapter(new ArrayAdapter<String>(activity, R.layout.search_result_list, new ArrayList<String>()));
		if(game.matches(""))
		{
			Snackbar.make(view, "Board Game field is empty, please enter game title", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show();
		}
		else if(isNetworkAvailable())
		{
			RetrieveFeedTask task = new RetrieveFeedTask();
			task.execute();
		}
		else
		{
			Snackbar.make(view, "No Network Connection", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show();
		}
	}

	private class RetrieveFeedTask extends AsyncTask<Void, Void, ArrayList<Map<String, Object>>>
	{

		protected void onPreExecute()
		{
			progressBar = findViewById(R.id.progressBar);
			progressBar.setVisibility(View.VISIBLE);
		}

		protected ArrayList<Map<String, Object>> doInBackground(Void... urls)
		{
			HttpURLConnection urlConnection = null;
			try
			{
				String encoded = TextUtils.htmlEncode(game).replace(" ", "+");
				URL url = new URL(API_URL + encoded + "&showcount=10");
				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setRequestProperty("Content-Type", "application/json");
				urlConnection.setRequestProperty("Accept", "application/json");
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				StringBuilder stringBuilder = new StringBuilder();
				String line;
				while((line = bufferedReader.readLine()) != null)
				{
					stringBuilder.append(line).append("\n");
				}
				bufferedReader.close();
				JSONObject object = (JSONObject) new JSONTokener(stringBuilder.toString()).nextValue();
				JSONArray items = object.getJSONArray("items");
				Log.i("INFO", items.toString());
				ArrayList<Map<String, Object>> games = new ArrayList<>();
				for(int i = 0; i < items.length(); i++)
				{
					JSONObject game = items.getJSONObject(i);
					int gameId = game.getInt("objectid");
					String type = game.getString("subtype");
					String name = game.getString("name");
					int imageId = game.getInt("rep_imageid");
					String href = game.getString("href");
					url = findGameUrl(gameId);
					//url = new URL("https://boardgamegeek.com/image/2419375/catan");
					Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
					BoardGame boardGame = new BoardGame(gameId, type, name, imageId, href);
					Map<String, Object> entry = new HashMap<>(2);
					entry.put("image", bmp);
					entry.put("game", boardGame);
					games.add(entry);
				}
				return games;
			}
			catch(IOException | JSONException | XmlPullParserException | IllegalArgumentException e)
			{
				Log.e("Error thrown", e.getMessage());
				e.printStackTrace();
				return null;
			}
			finally
			{
				urlConnection.disconnect();
			}
		}

		protected void onPostExecute(ArrayList games)
		{
			progressBar.setVisibility(View.GONE);
			if(games == null || games.size() == 0)
			{
				ListView view = findViewById(R.id.listView);
				view.setAdapter(null);
			}
			else
			{
				//ArrayAdapter adapter = new ArrayAdapter(activity, R.layout.search_result_list, games);
				ListView view = findViewById(R.id.listView);
				view.setAdapter(new CustomArrayAdapter(games, getApplicationContext()));
				//view.setAdapter(new SimpleAdapter(activity, games, R.layout.search_result_list, new String[]{"image", "game"}, new int[]{R.id.imageTest, R.id.resultListEntry}));
			}
		}

	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager
				= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	private URL findGameUrl(int gameId) throws IOException, XmlPullParserException
	{
		URL url = new URL("https://boardgamegeek.com/xmlapi2/thing?id="+gameId);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(10000 /* milliseconds */);
		conn.setConnectTimeout(15000 /* milliseconds */);
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		conn.connect();
		InputStream inputStream = conn.getInputStream();
		XmlPullParser parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(inputStream, null);
		parser.nextTag();
		String newURL = null;
		while (parser.next() != XmlPullParser.END_DOCUMENT)
		{
			if(parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
			String name = parser.getName();
			if(name == (null))
			{
				continue;
			}
			else if(name.equals("image"))
			{
				parser.next();
				newURL = parser.getText();
				//newURL = parser.getAttributeValue(null, "image");
				break;
			}
		}

		if(newURL == (null))
		{
			throw new XmlPullParserException("No URL found");
		}
		return new URL(newURL);
	}

	private class CustomArrayAdapter extends ArrayAdapter<Map<String, Object>> implements View.OnClickListener
	{

		private ArrayList<Map<String, Object>> list;
		Context context;

		// View lookup cache
		private class ViewHolder {
			ImageView image;
			TextView text;
		}

		public CustomArrayAdapter(ArrayList<Map<String, Object>> data, Context context) {
			super(context, R.layout.search_result_list, data);
			this.list = data;
			this.context=context;

		}

		@Override
		public void onClick(View v) {

			int position=(Integer) v.getTag();
			Object object= getItem(position);
			Map<String, Object> game = (Map<String, Object>)object;

			switch (v.getId())
			{
				case R.id.resultListEntry:
					Snackbar.make(v, "Release date " +game.size(), Snackbar.LENGTH_LONG)
							.setAction("No action", null).show();
					break;
			}
		}

		private int lastPosition = -1;

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Get the data item for this position
			Map<String, Object> game = getItem(position);
			// Check if an existing view is being reused, otherwise inflate the view
			ViewHolder viewHolder; // view lookup cache stored in tag

			final View result;

			if (convertView == null) {

				viewHolder = new ViewHolder();
				LayoutInflater inflater = LayoutInflater.from(getContext());
				convertView = inflater.inflate(R.layout.search_result_list, parent, false);
				viewHolder.image = (ImageView) convertView.findViewById(R.id.imageView);
				viewHolder.text = (TextView) convertView.findViewById(R.id.resultListEntry);

				result = convertView;

				convertView.setTag(viewHolder);

			}
			else
			{
				viewHolder = (ViewHolder) convertView.getTag();
				result=convertView;
			}

			Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
			result.startAnimation(animation);
			lastPosition = position;

			viewHolder.text.setText(game.get("game").toString());
			viewHolder.image.setImageBitmap((Bitmap) game.get("image"));
			viewHolder.image.setOnClickListener(this);
			viewHolder.image.setTag(position);
			// Return the completed view to render on screen
			return convertView;
		}

	}

}
