package com.example.aplicatiecinema.Activities;

import static com.android.volley.VolleyLog.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.aplicatiecinema.Adapters.CategoryEachFilmListAdapter;
import com.example.aplicatiecinema.Adapters.CategoryListAdapter;
import com.example.aplicatiecinema.Domain.Detail;
import com.example.aplicatiecinema.Domain.GenreDetail;
import com.example.aplicatiecinema.R;
import com.google.gson.Gson;

import java.io.IOException;

import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private ProgressBar progressBar;
    private TextView titleTxt,movieRateTxt,movieTimeTxt,movieSummaryInfo,movieActorsInfo;
    private int idFilm;
    private ImageView pic2,backImg;
    private RecyclerView.Adapter adapterActorList,adapterCategory;
    private RecyclerView recyclerViewActors,recyclerViewCategory;
    private NestedScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        idFilm=getIntent().getIntExtra("id",0);
        initView();
        sendRequest();
    }

    private void sendRequest() {
        OkHttpClient client4 = new OkHttpClient();

        Request request4 = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/" + idFilm + "?append_to_response=823464&language=en-US")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2Mzk0MzY0MTFjMTIwZDQ0YmUwNmNhMjEwZTVhZjcxMyIsInN1YiI6IjY2MzhiOTNhMmEwOWJjMDEyOTVhOTQ1OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.PLRbI4UREODqw8wx6JL0LSgOdzq3UrvwYbBFnUUzUz0")
                .build();

        client4.newCall(request4).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle failure
                Log.e(TAG, "Failed to fetch data: " + e.getMessage());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Log.d(TAG, "Response data: " + responseData);

                    // Parse JSON response using Gson
                    Gson gson = new Gson();
                    Detail detail = gson.fromJson(responseData, Detail.class);

                    // Update UI on the main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            //DetailAdapter detailAdapter= new DetailAdapter(detail);


                            titleTxt.setText(detail.getTitle());
                            movieRateTxt.setText(String.valueOf(detail.getVoteAverage()));
                            movieTimeTxt.setText(String.valueOf(detail.getRuntime()) + " min");
                            movieSummaryInfo.setText(detail.getOverview());
                            movieActorsInfo.setText(detail.getBudget());


                            Glide.with(getApplicationContext())
                                    .load("https://image.tmdb.org/t/p/w500" + detail.getPosterPath())
                                    .into(pic2);

                            // Set other details such as genres and actors (if available)
                            // For example, you can set genres using a loop if they are in a list
                            StringBuilder genresBuilder = new StringBuilder();
                            List<GenreDetail> genres = detail.getGenresDetail();
                            for (GenreDetail genre : genres) {
                                genresBuilder.append(genre.getName()).append(", ");
                            }
                            String genresText = genresBuilder.toString().trim();

                            if (!genresText.isEmpty()) {
                                genresText = genresText.substring(0, genresText.length() - 2); // Remove the last comma and space
                                adapterCategory = new CategoryEachFilmListAdapter(genres);
                                recyclerViewCategory.setAdapter(adapterCategory);
                            }
                        }
                    });
                } else {
                    // Handle unsuccessful response
                    Log.e(TAG, "Failed to fetch data: " + response.code() + " - " + response.message());
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initView() {
        titleTxt=findViewById(R.id.movieNameTxt);    //TO MODIFY
        progressBar=findViewById(R.id.progressBarDetail); //TO MODIFY
        scrollView=findViewById(R.id.scrollView2);
        pic2=findViewById(R.id.picDetail);
        movieRateTxt=findViewById(R.id.movieStar);
        movieTimeTxt=findViewById(R.id.movieTime);
        movieSummaryInfo=findViewById(R.id.movieSummary);
        movieActorsInfo=findViewById(R.id.movieActorInfo);
        backImg=findViewById(R.id.backImg);
        recyclerViewCategory=findViewById(R.id.genreView);
//        recyclerViewActors=findViewById(R.id.imagesRecycler);
//        recyclerViewActors.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        backImg.setOnClickListener(view -> finish());
    }
}
