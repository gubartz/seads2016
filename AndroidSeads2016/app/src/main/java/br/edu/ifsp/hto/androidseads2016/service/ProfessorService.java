package br.edu.ifsp.hto.androidseads2016.service;

import java.util.List;

import br.edu.ifsp.hto.androidseads2016.domain.Professor;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ProfessorService {
    @GET("professor/list")
    Call<List<Professor>> listarProfessores();

    @Multipart
    @POST("professor/new")
    Call<Professor> criarProfessor(@Part("foto") RequestBody image,
                                         @Part("professor") Professor professor);
}
