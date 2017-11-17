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

public class RegisterActivity extends AppCompatActivity {

    EditText uname, pwd, email;
    private static final String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        uname = (EditText)findViewById(R.id.textUsername);
        pwd = (EditText)findViewById(R.id.textPassword);
        email = (EditText)findViewById(R.id.textEmail);

    }

    public void registrar(View view) {

        String username = uname.getText().toString();
        String password = pwd.getText().toString();
        String e_mail = email.getText().toString();

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<Usuario> call = service.register(username, password,e_mail);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        Usuario usuario = response.body();
                        Log.d(TAG, "productos: " + usuario);

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

        Intent i1= new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(i1);
    }
}
