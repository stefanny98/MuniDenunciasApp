package com.aquino.munidenuncias.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.aquino.munidenuncias.services.ApiService;
import com.aquino.munidenuncias.services.ApiServiceGenerator;
import com.aquino.munidenuncias.R;
import com.aquino.munidenuncias.models.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.textUser);
        password = (EditText) findViewById(R.id.textPassword);

    }

    public void login(View view) {

        String uname = username.getText().toString();
        String pwd = password.getText().toString();

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<Usuario> call = service.login(uname, pwd);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                try {

                    if (response.isSuccessful()) {

                        Usuario usuario = response.body();

                        Toast.makeText(MainActivity.this, "Iniciando sesión...", Toast.LENGTH_SHORT).show();
                        Intent i1=new Intent(MainActivity.this, DashboardActivity.class);
                        i1.putExtra("usuario", usuario);
                        startActivity(i1);

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

        username.setText("");
        password.setText("");

}

    public void register(View view) {

        Intent intent= new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
