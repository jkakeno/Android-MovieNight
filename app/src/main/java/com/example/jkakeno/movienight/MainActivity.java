package com.example.jkakeno.movienight;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String SHOW = "SHOW";

    String apiKey = "3fbfeaf8eef9df5036f08387a9b24695";
    String media;
    String genreId;
    String sortMethod;
    String minRating;
    String minReleaseDate;
    String maxReleaseDate;
    Show mShow;

    @BindView(R.id.mediaSpinner) Spinner mMedia;
    @BindView(R.id.genreSpinner) Spinner mGenre;
    @BindView(R.id.sortSpinner) Spinner mSort;
    @BindView(R.id.ratingBar) RatingBar mRatingBar;
    @BindView(R.id.minReleaseDateText) EditText mMinReleaseDate;
    @BindView(R.id.maxReleaseDateText) EditText mMaxReleaseDate;
    @BindView(R.id.submitButton) Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//SET SPINNERS

// Create an ArrayAdapter for media spinner layout
        ArrayAdapter<CharSequence> msAdapter = ArrayAdapter.createFromResource(this, R.array.media_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        msAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mMedia.setAdapter(msAdapter);
//Applying OnItemSelectedListener on the spinner instance
        mMedia.setOnItemSelectedListener(this);

//NOTE: The adapter for genre is included in the onItemSelected case media spinner loop
//// Create an ArrayAdapter for genre spinner layout
//        ArrayAdapter<CharSequence> gsAdapter = ArrayAdapter.createFromResource(this, R.array.genre_array, android.R.layout.simple_spinner_item);
//// Specify the layout to use when the list of choices appears
//        gsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//        mGenre.setAdapter(gsAdapter);
////Applying OnItemSelectedListener on the spinner instance
//        mGenre.setOnItemSelectedListener(this);


 // Create an ArrayAdapter for genre spinner layout
        ArrayAdapter<CharSequence> ssAdapter = ArrayAdapter.createFromResource(this, R.array.sort_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        ssAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mSort.setAdapter(ssAdapter);
//Applying OnItemSelectedListener on the spinner instance
        mSort.setOnItemSelectedListener(this);


//GET RATING
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                minRating = String.valueOf(rating);
//                Toast.makeText(getApplicationContext(),"Your Selected Ratings  : " + minRating,Toast.LENGTH_LONG).show();
            }
        });


//INITIATE SEARCH
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//GET RELEASE DATE RANGE
                minReleaseDate = mMinReleaseDate.getText().toString().trim();
                maxReleaseDate = mMaxReleaseDate.getText().toString().trim();

//ASSEMBLE URL
                final String Url = "https://api.themoviedb.org/3/discover/" + media + "?api_key=" + apiKey +
                        "&language=en-US&sort_by=" + sortMethod +
                        "&include_adult=false&include_video=true&primary_release_date.gte=" + minReleaseDate +
                        "&primary_release_date.lte=" + maxReleaseDate +
                        "&vote_average.gte=" + minRating +
                        "&with_genres=" + genreId;
                if (isNetworkAvailable()) {

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(Url).build();

//                Log.d(TAG, "media is:" + media);
//                Log.d(TAG, "genreId is:" + genreId);
//                Log.d(TAG, "sort is:" + sortMethod);
//                Log.d(TAG, "min Rating is:" + minRating);
//                Log.d(TAG, "min Release Date is:" + minReleaseDate);
//                Log.d(TAG, "max Release Date is:" + maxReleaseDate);
//                Log.d(TAG, "URL is:" + Url);

//MAKE CALL WITH ASSEMBLED URL
                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, final IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                            alertUserAboutError();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                final String jsonData = response.body().string();
                                if (response.isSuccessful()) {
                                    mShow = parseShowDetails(jsonData);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
//CREATE INTENT TO DISPLAY THE JSON DATA ON TO THE movie_list_activity
                                            if(media.equals("movie")) {
//                                                Toast.makeText(MainActivity.this, media, Toast.LENGTH_LONG).show();
//                                                Log.d(TAG, "media is:" + media);
                                                Intent intent = new Intent(MainActivity.this, MovieListActivity.class);
                                                intent.putExtra(SHOW, mShow.getMovies());
                                                startActivity(intent);
//                                                Log.d(TAG, jsonData);
//CREATE INTENT TO DISPLAY THE JSON DATA ON TO THE tvshow_list_activity
                                            }else if (media.equals("tv")){
//                                                Toast.makeText(MainActivity.this, media, Toast.LENGTH_LONG).show();
//                                                Log.d(TAG, "media is:" + media);
                                                Intent intent = new Intent(MainActivity.this, TVShowListActivity.class);
                                                intent.putExtra(SHOW, mShow.getTVShows());
                                                startActivity(intent);
//                                                Log.d(TAG, jsonData);
                                            }
                                        }
                                    });
                                }else {
                                    alertUserAboutError();
                                }
                            } catch (IOException e) {
                                Log.e(TAG, "Exception caught: ", e);
                            } catch (JSONException e) {
                                Log.e(TAG, "Exception caught: ", e);
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.network_unavailable_message), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

//GET USER SELECTION FROM SPINNERS
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.mediaSpinner:
                media = getResources().getStringArray(R.array.media_array)[position];
//                Toast.makeText(this, getResources().getStringArray(R.array.media_array)[position], Toast.LENGTH_LONG).show();
                if (media.equals("movie")) {
                    // Create an ArrayAdapter for genre spinner layout
                    ArrayAdapter<CharSequence> gsAdapter = ArrayAdapter.createFromResource(this, R.array.movie_genre_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                    gsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                    mGenre.setAdapter(gsAdapter);
//Applying OnItemSelectedListener on the spinner instance
                    mGenre.setOnItemSelectedListener(this);
                } else if (media.equals("tv")) {
                    // Create an ArrayAdapter for genre spinner layout
                    ArrayAdapter<CharSequence> gsAdapter = ArrayAdapter.createFromResource(this, R.array.tv_genre_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                    gsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                    mGenre.setAdapter(gsAdapter);
//Applying OnItemSelectedListener on the spinner instance
                    mGenre.setOnItemSelectedListener(this);
                }
                break;
            case R.id.genreSpinner:
                String movieGenre = getResources().getStringArray(R.array.movie_genre_array)[position];
                String tvGenre = getResources().getStringArray(R.array.tv_genre_array)[position];
                if (media.equals("movie")) {
                    if (movieGenre.equals("Action")) {
                        genreId = "28";
                    }
                    if (movieGenre.equals("Adventure")) {
                        genreId = "12";
                    }
                    if (movieGenre.equals("Animation") ) {
                        genreId = "16";
                    }
                    if (movieGenre.equals("Comedy")) {
                        genreId = "35";
                    }
                    if (movieGenre.equals("Crime") ) {
                        genreId = "80";
                    }
                    if (movieGenre.equals("Documentary") ) {
                        genreId = "99";
                    }
                    if (movieGenre.equals("Drama")) {
                        genreId = "18";
                    }
                    if (movieGenre.equals("Family")) {
                        genreId = "10751";
                    }
                    if (movieGenre.equals("Fantasy")) {
                        genreId = "14";
                    }
                    if (movieGenre.equals("History")) {
                        genreId = "36";
                    }
                    if (movieGenre.equals("Horror")) {
                        genreId = "27";
                    }
                    if (movieGenre.equals("Music")) {
                        genreId = "10402";
                    }
                    if (movieGenre.equals("Mystery")) {
                        genreId = "9648";
                    }
                    if (movieGenre.equals("Romance")) {
                        genreId = "10749";
                    }
                    if (movieGenre.equals("Science Fiction")) {
                        genreId = "878";
                    }
                    if (movieGenre.equals("TV Movie")) {
                        genreId = "10770";
                    }
                    if (movieGenre.equals("Thriller")) {
                        genreId = "53";
                    }
                    if (movieGenre.equals("War")) {
                        genreId = "10752";
                    }
                    if (movieGenre.equals("Western")) {
                        genreId = "37";
                    }
                }
                if(media.equals("tv")) {
                    if (tvGenre.equals("Action & Adventure")) {
                        genreId = "10759";
                    }
                    if (tvGenre.equals("Animation")) {
                        genreId = "16";
                    }
                    if (tvGenre.equals("Comedy")) {
                        genreId = "35";
                    }
                    if (tvGenre.equals("Crime")) {
                        genreId = "80";
                    }
                    if (tvGenre.equals("Documentary")) {
                        genreId = "99";
                    }
                    if (tvGenre.equals("Drama")) {
                        genreId = "18";
                    }
                    if (tvGenre.equals("Family")) {
                        genreId = "10751";
                    }
                    if (tvGenre.equals("Kids")) {
                        genreId = "10762";
                    }
                    if (tvGenre.equals("Mystery")) {
                        genreId = "9648";
                    }
                    if (tvGenre.equals("News")) {
                        genreId = "10763";
                    }
                    if (tvGenre.equals("Reality")) {
                        genreId = "10764";
                    }
                    if (tvGenre.equals("Sci-Fi & Fantasy")) {
                        genreId = "10765";
                    }
                    if (tvGenre.equals("Soap")) {
                        genreId = "10766";
                    }
                    if (tvGenre.equals("Talk")) {
                        genreId = "10767";
                    }
                    if (tvGenre.equals("War & Politics")) {
                        genreId = "10768";
                    }
                }

//                Log.d(TAG, "movie genre is:" + movieGenre);
//                Log.d(TAG, "tv genre is:" + tvGenre);
//                Log.d(TAG, "genreId is:" + genreId);
//                Toast.makeText(this, getResources().getStringArray(R.array.genre_array)[position], Toast.LENGTH_LONG).show();
                break;
            case R.id.sortSpinner:
                sortMethod = getResources().getStringArray(R.array.sort_array)[position];
//                Toast.makeText(this, getResources().getStringArray(R.array.sort_array)[position], Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private Movie[] getMovieDetails(String jsonData) throws JSONException {
        JSONObject show = new JSONObject(jsonData);
        JSONArray results = show.getJSONArray("results");
        Movie[] movies = new Movie[results.length()];
        for (int i = 0; i < results.length(); i++) {
            JSONObject jsonMovie = results.getJSONObject(i);
            Movie movie = new Movie();
            movie.setTitle(jsonMovie.getString("title"));
            movie.setReleaseDate(jsonMovie.getString("release_date"));
            movie.setOverView(jsonMovie.getString("overview"));
            movies[i] = movie;
        }
        return movies;
    }

    private TVShow[] getTVshowDetails(String jsonData) throws JSONException {
        JSONObject show = new JSONObject(jsonData);
        JSONArray results = show.getJSONArray("results");
        TVShow[] tvShows = new TVShow[results.length()];
        for (int i = 0; i < results.length(); i++) {
            JSONObject jsonTVshow = results.getJSONObject(i);
            TVShow tvShow = new TVShow();
            tvShow.setName(jsonTVshow.getString("name"));
            tvShow.setFirstReleaseDate(jsonTVshow.getString("first_air_date"));
            tvShow.setOverView(jsonTVshow.getString("overview"));
            tvShows[i] = tvShow;
        }
        return tvShows;
    }

    private Show parseShowDetails (String jsonData) throws JSONException{
        Show show = new Show();
        if(media.equals("movie")) {
            show.setMovies(getMovieDetails(jsonData));
        }else if(media.equals("tv")) {
            show.setTVShows(getTVshowDetails(jsonData));
        }
        return show;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo !=null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(),"error_dialog");
    }
}