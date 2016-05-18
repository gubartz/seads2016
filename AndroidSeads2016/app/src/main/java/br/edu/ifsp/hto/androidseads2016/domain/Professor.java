package br.edu.ifsp.hto.androidseads2016.domain;

import android.graphics.Bitmap;

/**
 * Created by gustavohome on 17/05/2016.
 */
public class Professor {
    private long id;
    private String prontuario;
    private String nome;
    private Bitmap foto;
    private Titulacao titulacao;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProntuario() {
        return prontuario;
    }

    public void setProntuario(String prontuario) {
        this.prontuario = prontuario;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public Titulacao getTitulacao() {
        return titulacao;
    }

    public void setTitulacao(Titulacao titulacao) {
        this.titulacao = titulacao;
    }
}
