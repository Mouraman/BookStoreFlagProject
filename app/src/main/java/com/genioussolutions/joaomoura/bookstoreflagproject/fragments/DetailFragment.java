package com.genioussolutions.joaomoura.bookstoreflagproject.fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.genioussolutions.joaomoura.bookstoreflagproject.R;
import com.genioussolutions.joaomoura.bookstoreflagproject.interfaces.GetBookService;
import com.genioussolutions.joaomoura.bookstoreflagproject.model.Favorito;
import com.genioussolutions.joaomoura.bookstoreflagproject.model.Item;
import com.genioussolutions.joaomoura.bookstoreflagproject.preferences.SharedPreference;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private ImageView ivDetail;
    private TextView tvNomeDetail;
    private TextView tvStoreLink;
    private TextView tvPublishedDateDetail;
    private TextView tvDescription;
    private Switch swtFavoritos;
    private ScrollView scrollViewDescription;
    private SharedPreference sharedPreference;
    private Context context;
    private boolean switchState;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        scrollViewDescription = (ScrollView) view.findViewById(R.id.sv_description);
        swtFavoritos = (Switch) view.findViewById(R.id.swt_favoritos);
        ivDetail = (ImageView) view.findViewById(R.id.iv_detail);
        tvNomeDetail = (TextView) view.findViewById(R.id.tv_nome_detail);
        tvStoreLink = (TextView) view.findViewById(R.id.tv_storeLink);
        tvPublishedDateDetail = (TextView) view.findViewById(R.id.tv_storeLink);
        tvDescription = (TextView) view.findViewById(R.id.tv_publishedDateDetail);

        Bundle args = this.getArguments();
       // Realm.init(context);
        // switchState= swtFavoritos.isChecked();

        getBookDetail(args.getString("id"));
tvStoreLink.setMovementMethod(LinkMovementMethod.getInstance());
        swtFavoritos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


            }
        });


        return view;
    }

    private void addToFavorites(Favorito favorito) {
        favorito.setFavorite(true);
        }

    private void prepareView(Item item) {
        Picasso.get().load(item.getVolumeInfo().getImageLinks().getThumbnail()).into(ivDetail);
        tvNomeDetail.setText(item.getVolumeInfo().getTitle());
        tvStoreLink.setText(item.getVolumeInfo().getPreviewLink());
        tvPublishedDateDetail.setText(item.getVolumeInfo().getPublishedDate());
        tvDescription.setText(item.getVolumeInfo().getDescription());
    }

    private void getBookDetail(String id) {

        Retrofit retrofit = new Retrofit.
                Builder().
                baseUrl("https://www.googleapis.com/books/v1/").
                addConverterFactory(GsonConverterFactory.create()).build();

        GetBookService service = retrofit.create(GetBookService.class);


        Call<Item> bookDetail = service.getBook(id);
        bookDetail.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Log.v("RETROFIT", "OK");

                prepareView(response.body());
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Log.v("RETROFIT", "NOK");
                Log.v("RETROFIT", t.getMessage());
            }
        });
    }


}
