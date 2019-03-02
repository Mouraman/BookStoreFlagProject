package com.genioussolutions.joaomoura.bookstoreflagproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.genioussolutions.joaomoura.bookstoreflagproject.interfaces.AdapterCallBack;
import com.genioussolutions.joaomoura.bookstoreflagproject.R;
import com.genioussolutions.joaomoura.bookstoreflagproject.model.Item;
import com.genioussolutions.joaomoura.bookstoreflagproject.model.Result;
import com.genioussolutions.joaomoura.bookstoreflagproject.preferences.SharedPreference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookAdapterViewHolder> {
    private List<Item> items;
    private Context context;
    private AdapterCallBack adapterCallBack;
    private SharedPreference sharedPreference;

    public BookAdapter(List<Item> items, Context context, AdapterCallBack adapterCallBack) {
        this.items = items;
        this.context = context;
        this.adapterCallBack = adapterCallBack;
        this.sharedPreference = new SharedPreference();
    }

    @NonNull
    @Override
    public BookAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, viewGroup, false);
        BookAdapterViewHolder bookAdapterViewHolder = new BookAdapterViewHolder(view);
        return bookAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapterViewHolder bookAdapterViewHolder, final int i) {
        final Item item = items.get(i);

        bookAdapterViewHolder.tvNome.setText(item.getClass().getName());
        bookAdapterViewHolder.tvPublishedDate.setText(item.getVolumeInfo().getPublishedDate());
        if (item.getVolumeInfo().getImageLinks() == null) {
            Picasso.get()
                    .load("https://ksr-ugc.imgix.net/assets/019/391/281/d236a09ffe246ae261318c9eb98099a5_original.png?ixlib=rb-1.1.0&w=680&fit=max&v=1511745527&auto=format&gif-q=50&lossless=true&s=7a88acc7c1e049e402cb388ddae8eefd")
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(bookAdapterViewHolder.ivBookSmall);
        } else {
            Picasso.get().load(item.getVolumeInfo().getImageLinks().getSmallThumbnail()).into(bookAdapterViewHolder.ivBookSmall);
        }
        bookAdapterViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCallBack.selectBookOnClick(item.getId());
            }
        });


    }

    @Override
    public int getItemCount() {
        if (items == null){
            return 0;
        }else{
return items.size();
        }



    }


    public static class BookAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNome;
        private TextView tvPublishedDate;
        private ImageView ivBookSmall;
        private ImageButton imgbtnFavoritos;

        public BookAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = (TextView) itemView.findViewById(R.id.tv_nome);
            tvPublishedDate = (TextView) itemView.findViewById(R.id.tv_publishedDate);
            ivBookSmall = (ImageView) itemView.findViewById(R.id.iv_bookSmall);
            imgbtnFavoritos = (ImageButton) itemView.findViewById(R.id.imgbtn_favoritos);
        }
    }


}
