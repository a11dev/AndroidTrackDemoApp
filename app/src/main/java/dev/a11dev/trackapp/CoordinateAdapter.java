package dev.a11dev.trackapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CoordinateAdapter extends RecyclerView.Adapter<CoordinateAdapter.CoordinateViewHolder> {

    private final List<String> coordinates;

    public CoordinateAdapter(List<String> coordinates) {
        this.coordinates = coordinates;
    }

    @NonNull
    @Override
    public CoordinateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new CoordinateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoordinateViewHolder holder, int position) {
        holder.coordinateTextView.setText(coordinates.get(position));
    }

    @Override
    public int getItemCount() {
        return coordinates.size();
    }

    static class CoordinateViewHolder extends RecyclerView.ViewHolder {
        TextView coordinateTextView;

        CoordinateViewHolder(@NonNull View itemView) {
            super(itemView);
            coordinateTextView = itemView.findViewById(android.R.id.text1);
        }
    }
}
