package com.aquino.munidenuncias.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aquino.munidenuncias.services.ApiService;
import com.aquino.munidenuncias.services.ApiServiceGenerator;
import com.aquino.munidenuncias.models.Denuncia;
import com.aquino.munidenuncias.adapters.DenunciasAdapter;
import com.aquino.munidenuncias.R;
import com.aquino.munidenuncias.models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = DashboardActivity.class.getSimpleName();
    private RecyclerView denunciasList;
    private static final int REGISTER_FORM_REQUEST = 100;
    Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        denunciasList = (RecyclerView) findViewById(R.id.recyclerview);
        denunciasList.setLayoutManager(new LinearLayoutManager(this));
        denunciasList.setAdapter(new DenunciasAdapter(this));

        initialize();
        Intent e = getIntent();
        user = (Usuario) e.getSerializableExtra("usuario");
    }

    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Denuncia>> call = service.getDenuncias();

        call.enqueue(new Callback<List<Denuncia>>() {
            @Override
            public void onResponse(Call<List<Denuncia>> call, Response<List<Denuncia>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Denuncia> productos = response.body();
                        Log.d(TAG, "productos: " + productos);

                        DenunciasAdapter adapter = (DenunciasAdapter) denunciasList.getAdapter();
                        adapter.setDenuncias(productos);
                        adapter.notifyDataSetChanged();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<List<Denuncia>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }

    public void action(View view) {

        Intent i1= new Intent(this, DenunciaActivity.class);
        i1.putExtra("usuario", user);
        startActivityForResult(i1, REGISTER_FORM_REQUEST);

       // startActivity(i1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REGISTER_FORM_REQUEST) {
            // refresh data
            initialize();
        }
    }

}
