package com.example.aplicatiecinema.Activities;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

//import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplicatiecinema.Adapters.CategoryListAdapter;
import com.example.aplicatiecinema.Adapters.FilmListAdapter;
import com.example.aplicatiecinema.Adapters.SliderAdapters;
import com.example.aplicatiecinema.Domain.GenresItem;
import com.example.aplicatiecinema.Domain.ListFilm;
import com.example.aplicatiecinema.Domain.ListFilm1;
import com.example.aplicatiecinema.Domain.Result;
import com.example.aplicatiecinema.Domain.SliderItems;
import com.example.aplicatiecinema.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterBestMovies,adapterUpcomming,adapterCategory;
    private RecyclerView recyclerViewBestMovies, recyclerviewUpcomming,recyclerviewCategory;
    private RequestQueue mRequesstQueue;
    private StringRequest mStringRequest,mStringRequest2,mStringRequest3;
    private ProgressBar loading1,loading2,loading3;
    private ViewPager2 viewPager2;
    private Handler slideHandler= new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        banner();
        sendRequestBestMovies();
//        sendRequestUpComming();
//        sendRequestCategory();

    }

    private void sendRequestBestMovies() {
//        mRequesstQueue= Volley.newRequestQueue(this);
//        loading1.setVisibility(View.VISIBLE);
//        mStringRequest=new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1", response -> {
//            Gson gson=new Gson();
//            loading1.setVisibility(View.GONE);
//            ListFilm items=gson.fromJson(response,ListFilm.class);
//            adapterBestMovies=new FilmListAdapter(items);
//            recyclerViewBestMovies.setAdapter(adapterBestMovies);
//        }, error -> {
//            loading1.setVisibility(View.GONE);
//            Log.i(TAG, "onErrorResponse: "+error.toString());
//        });
//        mRequesstQueue.add(mStringRequest);
        OkHttpClient client = new OkHttpClient();
        loading1.setVisibility(View.VISIBLE);
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/popular?language=en-US&page=1")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhOTc2Yzg2N2IxYjY2MjE1OWJhNmM4ODAzYzVlYTFlMyIsInN1YiI6IjY2MzdmYTZmODEzY2I2MDEyMTg5MGQzMCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.eOSdLss3jQYCM2vo2ajh90o18qDOWCngItgPcWToUcE")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Failed to fetch data: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    loading1.setVisibility(View.GONE);
                    Log.d(TAG, "Response data: " + responseData); // Log the response for debugging
                    Gson gson = new Gson();
                    ListFilm1 listFilm1 = gson.fromJson(responseData, ListFilm1.class);
                    List<Result> results = listFilm1.getResults();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            FilmListAdapter filmListAdapter = new FilmListAdapter(results);
                            recyclerViewBestMovies.setAdapter(filmListAdapter);
                        }
                    });
                } else {
                    Log.e(TAG, "Failed to fetch data: " + response.code() + " - " + response.message());
                    loading1.setVisibility(View.VISIBLE);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

//    private void sendRequestUpComming() {
//        mRequesstQueue= Volley.newRequestQueue(this);
//        loading3.setVisibility(View.VISIBLE);
//        mStringRequest3=new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=2", response -> {
//            Gson gson=new Gson();
//            loading3.setVisibility(View.GONE);
//            ListFilm items=gson.fromJson(response,ListFilm.class);
//            adapterUpcomming=new FilmListAdapter(items);
//            recyclerviewUpcomming.setAdapter(adapterUpcomming);
//        }, error -> {
//            loading3.setVisibility(View.GONE);
//            Log.i(TAG, "onErrorResponse: "+error.toString());
//        });
//        mRequesstQueue.add(mStringRequest3);
//    }
//
//    private void sendRequestCategory() {
//        mRequesstQueue= Volley.newRequestQueue(this);
//        loading2.setVisibility(View.VISIBLE);
//        mStringRequest2=new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/genres", response -> {
//            Gson gson=new Gson();
//            loading2.setVisibility(View.GONE);
//            ArrayList<GenresItem> catList=gson.fromJson(response,new TypeToken<ArrayList<GenresItem>>(){
//
//            }.getType());
//            adapterCategory=new CategoryListAdapter(catList);
//            recyclerviewCategory.setAdapter(adapterCategory);
//        }, error -> {
//            loading2.setVisibility(View.GONE);
//            Log.i(TAG, "onErrorResponse: "+error.toString());
//        });
//        mRequesstQueue.add(mStringRequest2);
//    }

    private void banner() {
        List<SliderItems> sliderItems= new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.wide));
        sliderItems.add(new SliderItems(R.drawable.wide1));
        sliderItems.add(new SliderItems(R.drawable.wide3));

        viewPager2.setAdapter(new SliderAdapters(sliderItems,viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r=1-Math.abs(position);
                page.setScaleY(0.85f+r*0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setCurrentItem(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(sliderRunnable);
            }
        });
    }
    private Runnable sliderRunnable= new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(sliderRunnable, 2000);
    }

    private void initView() {
        viewPager2=findViewById(R.id.viewpagerSlider);
        recyclerViewBestMovies=findViewById(R.id.view1);
        recyclerViewBestMovies.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerviewUpcomming=findViewById(R.id.view3);
        recyclerviewUpcomming.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerviewCategory=findViewById(R.id.view2);
        recyclerviewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        loading1=findViewById(R.id.progressBar1);
        loading2=findViewById(R.id.progressBar2);
        loading3=findViewById(R.id.progressBar3);
    }
}