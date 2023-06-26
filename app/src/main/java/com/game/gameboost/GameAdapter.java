package com.game.gameboost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class GameAdapter extends BaseAdapter {

    private final Context context;
    private final List<GameFragment.Game> games;

    public GameAdapter(Context context, List<GameFragment.Game> games) {
        this.context = context;
        this.games = games;
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int position) {
        return games.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            itemView = inflater.inflate(R.layout.item_game, parent, false);
        }

        GameFragment.Game currentGame = (GameFragment.Game) getItem(position);

        ImageView gameImageView = itemView.findViewById(R.id.image_game);
        TextView gameTextView = itemView.findViewById(R.id.text_game);

        gameImageView.setImageDrawable(currentGame.getIcon());
        gameTextView.setText(currentGame.getName());

        int margin = (int) context.getResources().getDimension(R.dimen.custom_item_margin);
        int itemSize = (int) context.getResources().getDimension(R.dimen.custom_item_size);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(itemSize, itemSize);
        layoutParams.setMargins(margin, margin, margin, margin);
        gameImageView.setLayoutParams(layoutParams);
        gameTextView.setLayoutParams(layoutParams);

        return itemView;
    }
}

