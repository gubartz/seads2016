package br.edu.ifsp.hto.androidseads2016.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.edu.ifsp.hto.androidseads2016.R;
import br.edu.ifsp.hto.androidseads2016.presenter.ListarProfessoresPresenter;

public class ListarProfessoresFragment extends Fragment {
    private ListView mList;

    public ListarProfessoresFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_professores, container, false);

        mList = (ListView) view.findViewById(R.id.listView);
        ListarProfessoresPresenter listarProfessoresPresenter = new ListarProfessoresPresenter();

        listarProfessoresPresenter.listarProfessores(getContext(), mList);

        return view;
    }

}
