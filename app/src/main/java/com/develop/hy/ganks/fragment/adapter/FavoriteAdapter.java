package com.develop.hy.ganks.fragment.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.develop.hy.ganks.R;
import com.develop.hy.ganks.model.Favorite;
import com.develop.hy.ganks.presenter.CommenInterface.OnItemClickListener;

import java.util.List;

/**
 * Created by Helloworld on 2017/9/17.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyHolder> {
    private List<Favorite> favorites;

    public FavoriteAdapter(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        holder.favoriteTitle.setText(favorites.get(position).getTitle());
        holder.favoriteAuthor.setText(favorites.get(position).getAuthor());
        holder.favoriteTime.setText(favorites.get(position).getCreatedAt());
        holder.favorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onItemClick(position,false);
                }
            }
        });
        holder.favorCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener!=null){
                    listener.onDeleteItem(position);
                }
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return favorites.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        private TextView favoriteTitle;
        private TextView favoriteAuthor;
        private TextView favoriteTime;
        private final CardView favorCard;
        private final CheckBox checkBox;

        public MyHolder(View itemView) {
            super(itemView);
            //大标题
            favoriteTitle = (TextView) itemView.findViewById(R.id.favorite_content);
            //文章出处
            favoriteAuthor = (TextView) itemView.findViewById(R.id.favorite_author);
            //收藏时间
            favoriteTime = (TextView) itemView.findViewById(R.id.favorite_time);

            favorCard = (CardView) itemView.findViewById(R.id.favor_cardview);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);

        }
    }
    public void setOnItemtClickListener(OnItemClickListener listener){
        this.listener= listener;
    }
    private OnItemClickListener listener;
}
