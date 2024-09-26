package dev.a11dev.trackapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CoordinatesAdapter extends RecyclerView.Adapter<CoordinatesAdapter.CoordinateViewHolder> {

    private List<String> coordinatesList;

    public CoordinatesAdapter(List<String> coordinatesList) {
        this.coordinatesList = coordinatesList;
    }

    @NonNull
    @Override
    public CoordinateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coordinate, parent, false);
        return new CoordinateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoordinateViewHolder holder, int position) {
        holder.coordinateTextView.setText(coordinatesList.get(position));
    }

    @Override
    public int getItemCount() {
        return coordinatesList.size();
    }

    public static class CoordinateViewHolder extends RecyclerView.ViewHolder {
        TextView coordinateTextView;

        public CoordinateViewHolder(@NonNull View itemView) {
            super(itemView);
            coordinateTextView = itemView.findViewById(R.id.coordinateTextView);
        }
    }
}
