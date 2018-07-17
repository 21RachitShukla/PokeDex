package rs21.pokedex;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class MainActivity extends AppCompatActivity {
    public static int i = (int) 1;
    ImageView img;
    EditText id;
    TextView name;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String url = "http://pokeapi.co/api/v2/pokemon/";
        final Button previous = findViewById(R.id.previous);
        final Button next = findViewById(R.id.next);
        img = findViewById(R.id.img);
        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        Button go = findViewById(R.id.go);
        previous.setVisibility(View.GONE);

        makeNetworkCall(url + i);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous.setVisibility(View.VISIBLE);
                makeNetworkCall(url + (--i));
                if (i == (int) 1)
                    previous.setVisibility(View.GONE);
                else
                    previous.setVisibility(View.VISIBLE);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setVisibility(View.VISIBLE);
                makeNetworkCall(url + (++i));
                if (i == (int) 802)
                    previous.setVisibility(View.GONE);
                else
                    previous.setVisibility(View.VISIBLE);

            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = id.getText().toString();
                if (Integer.parseInt(s) >= (int) 1 && Integer.parseInt(s) <= (int) 801) {
                    i = Integer.parseInt(s);
                    makeNetworkCall(url + s);
                } else
                    Toast.makeText(getBaseContext(), "Entered rank is invalid!", Toast.LENGTH_LONG).show();
            }
        });

        id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: ");
                Intent x = new Intent(getBaseContext(), SecondActivity.class);
                startActivity(x);
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void makeNetworkCall(String s) {
        handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(MainActivity.this.getApplicationContext(), "Please Wait...", Toast.LENGTH_LONG).show();
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

                (MainActivity.this).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String url = apiResponse.sprites.front_default;
                        Picasso.get().load(url).placeholder(R.drawable.load).into(img);
                        name.setText(apiResponse.name.toUpperCase());
                        id.setText(apiResponse.id);
                    }
                });
            }
        });
    }
}
