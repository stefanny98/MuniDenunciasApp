package com.aquino.munidenuncias.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aquino.munidenuncias.services.ApiService;
import com.aquino.munidenuncias.services.ApiServiceGenerator;
import com.aquino.munidenuncias.models.Denuncia;
import com.aquino.munidenuncias.R;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private Integer id;

    private ImageView fotoImage;
    private TextView nombreText;
    private TextView comentarioText;
    private TextView userText;
    private String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        fotoImage = (ImageView)findViewById(R.id.foto_image);
        nombreText = (TextView)findViewById(R.id.nombre_text);
        comentarioText = (TextView)findViewById(R.id.comentario_text);
        userText = (TextView)findViewById(R.id.user_text);

        id = getIntent().getExtras().getInt("ID");
        user_name = getIntent().getExtras().getString("nombre_user");
        Log.e(TAG, "id:" + id);

        initialize();
    }

    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<Denuncia> call = service.showDenuncia(id);

        call.enqueue(new Callback<Denuncia>() {
            @Override
            public void onResponse(Call<Denuncia> call, Response<Denuncia> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        Denuncia denuncia = response.body();
                        Log.d(TAG, "denuncia: " + denuncia);

                        String url = ApiService.API_BASE_URL + "/images/" + denuncia.getImagen();
                        Picasso.with(DetailActivity.this).load(url).into(fotoImage);

                        nombreText.setText(denuncia.getTitulo());
                        comentarioText.setText(denuncia.getComentario());
                        userText.setText(user_name);

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<Denuncia> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

}

