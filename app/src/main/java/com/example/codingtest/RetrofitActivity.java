package com.example.codingtest;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private ArrayList<String> InputData = new ArrayList<>();

    private ListView listView;
    private final String BASE_URL = "https://api.github.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        init();

        Github1 gitHub1 = retrofit.create(Github1.class);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, InputData
        );

        Call<List<Issue>> call = gitHub1.contributors("nodejs", "node", "comments");
        call.enqueue(new Callback<List<Issue>>() {
            @Override
            public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {
                List<Issue> contributors = response.body();
                for (Issue contributor : contributors) {
                    if (InputData.size() == 10) {
                        break;
                    } else {
                        InputData.add(contributor.getTitle());
                    }
                }
                listView.setAdapter(adapter);
            }


            @Override
            public void onFailure(Call<List<Issue>> call, Throwable t) {
                Toast.makeText(RetrofitActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void init() {
        listView = (ListView) findViewById(R.id.listView);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}

