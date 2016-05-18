package br.edu.ifsp.hto.androidseads2016.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import br.edu.ifsp.hto.androidseads2016.R;
import br.edu.ifsp.hto.androidseads2016.domain.Professor;
import br.edu.ifsp.hto.androidseads2016.presenter.ListarProfessoresPresenter;

/**
 * Created by gustavohome on 17/05/2016.
 */
public class ProfessorAdapter extends BaseAdapter {
    private final Context context;
    private final List<Professor> professores;

    public ProfessorAdapter(Context context, List professores) {
        this.context = context;
        this.professores = professores;
    }

    @Override
    public int getCount() {
        return professores != null ? professores.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return professores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return professores.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_professores, parent, false);

        TextView tNome = (TextView) view.findViewById(R.id.tNome);
        TextView tProntuario = (TextView) view.findViewById(R.id.tProntuario);
        ImageView ivFoto = (ImageView) view.findViewById(R.id.ivFoto);

        Professor professor = professores.get(position);
        tNome.setText(professor.getNome());
        tProntuario.setText(professor.getProntuario());

        baixarImagem(professor, ivFoto);

        return view;
    }

    private void baixarImagem(final Professor professor, final ImageView imageView) {
        //Picasso.with(context).load(ListarProfessoresPresenter.baseURL + "images/" + professor.getId() + ".png").into(imageView);

        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                professor.setFoto(bitmap);
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        imageView.setTag(target);

        Picasso.with(context)
                .load(ListarProfessoresPresenter.baseURL + "images/" + professor.getId() + ".png")
                .into(target);
    }
}
