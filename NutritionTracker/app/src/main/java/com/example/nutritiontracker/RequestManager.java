package com.example.nutritiontracker;

import android.content.Context;

import com.example.nutritiontracker.Models.SearchItemAPIResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;


public class RequestManager {
    private Context context;
    Retrofit retrofit;

    public RequestManager(Context context) {
        this.context = context;
        retrofit = new Retrofit.Builder()
                .baseUrl("https://trackapi.nutritionix.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private interface CallSearchItem {
        @GET("/v2/search/item")
        Call<SearchItemAPIResponse> callSearchItemAPI(
                @Header("x-app-id") String appId,
                @Header("x-app-key") String appKey,
                @Query("upc") String upc
        );
    }

    public void fetchItemData(String upc) {
        // Retrieve the credentials from string resources
        String appId = context.getString(R.string.x_app_id);
        String appKey = context.getString(R.string.x_app_key);

        // Create the API interface
        CallSearchItem api = retrofit.create(CallSearchItem.class);

        // Make the API call using the credentials from resources
        Call<SearchItemAPIResponse> call = api.callSearchItemAPI(appId, appKey, upc);
        call.enqueue(new retrofit2.Callback<SearchItemAPIResponse>() {
            @Override
            public void onResponse(Call<SearchItemAPIResponse> call, retrofit2.Response<SearchItemAPIResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    System.out.println("Response: " + response.body().toString());
                } else {
                    System.out.println("Error: Response code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<SearchItemAPIResponse> call, Throwable t) {
                System.out.println("API call failed.");
                t.printStackTrace();
            }
        });
    }
}

