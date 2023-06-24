package com.loto188.gameboost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_game, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.image_game);
        TextView textView = convertView.findViewById(R.id.text_game);

        GameFragment.Game game = games.get(position);

        imageView.setImageDrawable(game.getIcon());
        textView.setText(game.getName());

        return convertView;
    }
}

