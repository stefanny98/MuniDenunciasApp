package com.aquino.munidenuncias.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aquino.munidenuncias.services.ApiService;
import com.aquino.munidenuncias.services.ApiServiceGenerator;
import com.aquino.munidenuncias.models.Denuncia;
import com.aquino.munidenuncias.R;
import com.aquino.munidenuncias.services.ResponseMessage;
import com.aquino.munidenuncias.models.Usuario;
import com.aquino.munidenuncias.activities.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class DenunciasAdapter extends RecyclerView.Adapter<DenunciasAdapter.ViewHolder>  {

    private List<Denuncia> denuncias;

    private Activity activity;

    public DenunciasAdapter(Activity activity){
        this.denuncias = new ArrayList<>();
        this.activity = activity;
    }

    public void setDenuncias(List<Denuncia> denuncias) {
        this.denuncias = denuncias;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView fotoImage;
        public TextView nombreText;
        public TextView usuarioText;
        public ImageButton menuButton;

        public ViewHolder(View itemView) {
            super(itemView);
            fotoImage = (ImageView) itemView.findViewById(R.id.foto_image);
            nombreText = (TextView) itemView.findViewById(R.id.nombre_text);
            usuarioText = (TextView) itemView.findViewById(R.id.usuario_text);
            menuButton = (ImageButton) itemView.findViewById(R.id.menu_button);
        }
    }

    @Override
    public DenunciasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DenunciasAdapter.ViewHolder viewHolder, final int position) {


        final Denuncia denuncia = this.denuncias.get(position);
          int user_id =  denuncia.getUsuarios_id();
        ApiService service = ApiServiceGenerator.createService(ApiService.class);
        Call<Usuario> call = service.getUser(user_id);



        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                try {


                    if (response.isSuccessful()) {

                         Usuario usuario = response.body();
                        viewHolder.usuarioText.setText("Por: " + usuario.getUsername());


                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
            }

        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DetailActivity.class);

                intent.putExtra("ID", denuncia.getId());
                intent.putExtra("nombre_user",  viewHolder.usuarioText.getText());
                activity.startActivity(intent);
            }
        });

        viewHolder.nombreText.setText(denuncia.getTitulo());


        String url = ApiService.API_BASE_URL + "/images/" + denuncia.getImagen();
        Picasso.with(viewHolder.itemView.getContext()).load(url).into(viewHolder.fotoImage);

        viewHolder.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.remove_button:

                                ApiService service = ApiServiceGenerator.createService(ApiService.class);

                                Call<ResponseMessage> call = service.destroyDenuncia(denuncia.getId());

                                call.enqueue(new Callback<ResponseMessage>() {
                                    @Override
                                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                                        try {

                                            int statusCode = response.code();
                                            Log.d(TAG, "HTTP status code: " + statusCode);

                                            if (response.isSuccessful()) {

                                                ResponseMessage responseMessage = response.body();
                                                Log.d(TAG, "responseMessage: " + responseMessage);

                                                // Eliminar item del recyclerView y notificar cambios
                                                denuncias.remove(position);
                                                notifyItemRemoved(position);
                                                notifyItemRangeChanged(position, denuncias.size());

                                                Toast.makeText(v.getContext(), responseMessage.getMessage(), Toast.LENGTH_LONG).show();

                                            } else {
                                                Log.e(TAG, "onError: " + response.errorBody().string());
                                                throw new Exception("Error en el servicio");
                                            }

                                        } catch (Throwable t) {
                                            try {
                                                Log.e(TAG, "onThrowable: " + t.toString(), t);
                                                Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                            }catch (Throwable x){}
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseMessage> call, Throwable t) {
                                        Log.e(TAG, "onFailure: " + t.toString());
                                        Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                });

                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.denuncias.size();
    }

}
