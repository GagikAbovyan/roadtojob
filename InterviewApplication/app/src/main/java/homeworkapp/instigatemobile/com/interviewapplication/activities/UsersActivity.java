package homeworkapp.instigatemobile.com.interviewapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import homeworkapp.instigatemobile.com.interviewapplication.R;
import homeworkapp.instigatemobile.com.interviewapplication.adapters.UsersAdapter;
import homeworkapp.instigatemobile.com.interviewapplication.models.User;
import homeworkapp.instigatemobile.com.interviewapplication.providers.DataProvider;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UsersActivity extends AppCompatActivity {

    private UsersAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        startUsersList();
        getUser();
    }

    private void startUsersList() {
        final RecyclerView usersRecLists = findViewById(R.id.rec_view);
        usersAdapter = new UsersAdapter(this, DataProvider.getList());
        usersRecLists.setHasFixedSize(true);
        usersRecLists.setLayoutManager(new LinearLayoutManager(this));
        usersRecLists.setAdapter(usersAdapter);
    }

    private void getUser(){
        final OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url("https://randomuser.me/api/?results=30")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                runThread(response);
            }
        });
    }

    private void runThread(final Response response) throws IOException {
        final String myResponse = response.body().string();
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                UsersActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject object = new JSONObject(myResponse);
                            JSONArray array = object.getJSONArray("results");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject json = array.getJSONObject(i);
                                DataProvider.getList().add(new User(json.getString("picture"), json.getString("name"),
                                        json.getString("phone"), json.getString("email")));
                            }
                            usersAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        thread.start();
    }

}
