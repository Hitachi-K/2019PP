package com.example.p12019;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Database.DatabaseMaster;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameViewHolder> {
    private List<DatabaseMaster.Game> gameList;
    private Context context;

    public GameListAdapter(Context context, List<DatabaseMaster.Game> gameList) {
        this.context = context;
        this.gameList = gameList;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_item, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        holder.gameName.setText(gameList.get(position).getGameName());
        holder.gameYear.setText(String.valueOf(gameList.get(position).getYear()));

        holder.gameName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GameOverview.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name", gameList.get(position).getGameName());
                context.startActivity(intent);
            }
        });

        holder.gameYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GameOverview.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name", gameList.get(position).getGameName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public class GameViewHolder extends RecyclerView.ViewHolder {
        TextView gameName, gameYear;

        public GameViewHolder(View view) {
            super(view);
            gameName = view.findViewById(R.id.game_item_name);
            gameYear = view.findViewById(R.id.game_item_year);
        }
    }
}
