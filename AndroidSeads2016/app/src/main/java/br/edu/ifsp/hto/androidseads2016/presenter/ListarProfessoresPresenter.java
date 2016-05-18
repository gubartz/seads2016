package br.edu.ifsp.hto.androidseads2016.presenter;

import android.content.Context;
import android.widget.ListView;

import java.util.List;

import br.edu.ifsp.hto.androidseads2016.adapter.ProfessorAdapter;
import br.edu.ifsp.hto.androidseads2016.domain.Professor;
import br.edu.ifsp.hto.androidseads2016.service.ProfessorService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gustavohome on 17/05/2016.
 */
public class ListarProfessoresPresenter {
    public static String baseURL = "http://192.168.1.6:9090/";

    public void listarProfessores(final Context context, final ListView listView){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProfessorService service = retrofit.create(ProfessorService.class);
        Call<List<Professor>> call = service.listarProfessores();

        call.enqueue(new Callback<List<Professor>>() {
            @Override
            public void onResponse(Call<List<Professor>> call, Response<List<Professor>> response) {
                List<Professor> list = response.body();

                listView.setAdapter(new ProfessorAdapter(context, list));
            }

            @Override
            public void onFailure(Call<List<Professor>> call, Throwable t) {

            }
        });
    }
}
