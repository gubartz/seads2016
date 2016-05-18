package br.edu.ifsp.hto.androidseads2016.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.edu.ifsp.hto.androidseads2016.R;
import br.edu.ifsp.hto.androidseads2016.activity.MainActivity;
import br.edu.ifsp.hto.androidseads2016.domain.Professor;
import br.edu.ifsp.hto.androidseads2016.domain.Titulacao;
import br.edu.ifsp.hto.androidseads2016.presenter.ListarProfessoresPresenter;
import br.edu.ifsp.hto.androidseads2016.service.ProfessorService;
import br.edu.ifsp.hto.androidseads2016.service.TitulacaoService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NovoProfessorFragment extends Fragment {
    private ImageView mImageView;
    private ListView mLvTitulacao;
    private Context context;
    private Titulacao mTitulacao;

    public NovoProfessorFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_novo_professor, container, false);

        Button btGravarProfessor = (Button) view.findViewById(R.id.btGravarProfessor);
        btGravarProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gravarProfessor();
            }
        });

        Button btTirarFoto = (Button) view.findViewById(R.id.btTirarFoto);
        btTirarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        mImageView = (ImageView) view.findViewById(R.id.ivFoto);
        mLvTitulacao = (ListView) view.findViewById(R.id.lvTitulacao);
        context = container.getContext();

        preencherTitulacao();

        return view;
    }

    private void preencherTitulacao(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ListarProfessoresPresenter.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TitulacaoService service = retrofit.create(TitulacaoService.class);
        Call<List<Titulacao>> call = service.listarTitulacoes();

        call.enqueue(new Callback<List<Titulacao>>() {
            @Override
            public void onResponse(Call<List<Titulacao>> call, Response<List<Titulacao>> response) {
                final List<Titulacao> list = response.body();

                ArrayAdapter<Titulacao> adapter = new ArrayAdapter<Titulacao>(getActivity(),
                        android.R.layout.simple_list_item_1, list);
                mLvTitulacao.setAdapter(adapter);
                mLvTitulacao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for(int i = 0; i < mLvTitulacao.getChildCount(); i++){
                            mLvTitulacao.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                        }
                        view.setBackgroundColor(Color.GRAY);

                        mTitulacao = list.get(position);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Titulacao>> call, Throwable t) {

            }
        });
    }

    public void gravarProfessor(){
        String baseURL = "http://192.168.1.6:9090/";

        EditText eNome       = (EditText) getView().findViewById(R.id.eNome);
        EditText eProntuario = (EditText) getView().findViewById(R.id.eProntuario);

        Professor professor = new Professor();
        professor.setNome(eNome.getText().toString());
        professor.setProntuario(eProntuario.getText().toString());
        professor.setTitulacao(mTitulacao);

        File file = new File(mCurrentPhotoPath);
        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);

        eNome.setText("");
        eProntuario.setText("");

//Debug
      HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
      logging.setLevel(HttpLoggingInterceptor.Level.BODY);
      OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
      httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ProfessorService service = retrofit.create(ProfessorService.class);

        Call<Professor> call = service.criarProfessor(fbody, professor);

        call.enqueue(new Callback<Professor>() {
            @Override
            public void onResponse(Call<Professor> call, Response<Professor> response) {

            }

            @Override
            public void onFailure(Call<Professor> call, Throwable t) {

            }
        });
    }

    //Foto
    String mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    int MY_PERMISSIONS_REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == MainActivity.RESULT_OK) {

            File imgFile = new  File(mCurrentPhotoPath);

            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                mImageView.setImageBitmap(myBitmap);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        if((ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))

        {
            ActivityCompat.requestPermissions
                    (getActivity(), new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },MY_PERMISSIONS_REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE);
        }


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
}
