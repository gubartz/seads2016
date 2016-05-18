package br.edu.ifsp.hto.androidseads2016.service;

import java.util.List;

import br.edu.ifsp.hto.androidseads2016.domain.Professor;
import br.edu.ifsp.hto.androidseads2016.domain.Titulacao;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by gustavohome on 17/05/2016.
 */
public interface TitulacaoService {
    @GET("titulacao/list")
    Call<List<Titulacao>> listarTitulacoes();
}
