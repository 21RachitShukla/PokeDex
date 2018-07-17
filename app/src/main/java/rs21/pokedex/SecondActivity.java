package rs21.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SecondActivity extends AppCompatActivity {
    Handler handler;
    TextView details;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent x = getIntent();
        details = findViewById(R.id.details);
        Button exit = findViewById(R.id.exit);
        details.setMovementMethod(new ScrollingMovementMethod());
        makeNetworkCall("http://pokeapi.co/api/v2/pokemon/" + MainActivity.i);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void makeNetworkCall(String s) {
        handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(SecondActivity.this.getApplicationContext(), "Please Wait...", Toast.LENGTH_LONG).show();
            }
        });
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(s).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String result = response.body().string();
                final Gson gson = new Gson();
                final PokemonDetails apiResponse = gson.fromJson(result, PokemonDetails.class);

                (SecondActivity.this).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        details.setText("NAME : " + apiResponse.name + "\n\nID : " + apiResponse.id +
                                "\n\nORDER : " + apiResponse.order + "\n\nBASE EXPERIENCE : " + apiResponse.base_experience +
                                "\n\nWEIGHT : " + apiResponse.weight + "\n\nHEIGHT : " + apiResponse.height +
                                "\n\nTYPES (name - slot) :\n" + apiResponse.getAllTypes() +
                                "\nABILITIES (name - slot) :\n" + apiResponse.getAllAbilities() +
                                "\nHELD ITEMS :\n" + apiResponse.getAllHeldItems());
                    }
                });
            }
        });
    }
}
