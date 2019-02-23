package com.genioussolutions.joaomoura.bookstoreflagproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.genioussolutions.joaomoura.bookstoreflagproject.interfaces.AdapterCallBack;
import com.genioussolutions.joaomoura.bookstoreflagproject.R;
import com.genioussolutions.joaomoura.bookstoreflagproject.model.Item;
import com.genioussolutions.joaomoura.bookstoreflagproject.model.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookAdapterViewHolder>  {
    private List<Item> items;

    private List<Result> results;
    private Context context;
    private AdapterCallBack adapterCallBack;

    public BookAdapter(List<Result> results, Context context, AdapterCallBack adapterCallBack) {
        this.results = results;
        this.context = context;
        this.adapterCallBack = adapterCallBack;
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
        Picasso.get().load(item.getVolumeInfo().getImageLinks().getSmallThumbnail()).into(bookAdapterViewHolder.ivBookSmall);
        bookAdapterViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCallBack.selectBookOnClick(item.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size() ;
    }



    public static class BookAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNome;
        private TextView tvPublishedDate;
        private ImageView ivBookSmall;

        public BookAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = (TextView) itemView.findViewById(R.id.tv_nome);
            tvPublishedDate = (TextView) itemView.findViewById(R.id.tv_publishedDate);
            ivBookSmall = (ImageView) itemView.findViewById(R.id.iv_bookSmall);
        }
    }
}
