package com.homework.firesotreretrieve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    Context context;
    List<Tutorial> list;
    List<Tutorial> listClone;
    Listener listener;

    public RecyclerAdapter(Context context, List<Tutorial> list) {
        this.context = context;
        this.list = list;
        listClone = list;
    }

    public interface Listener{
        void onClick(int position);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.tutorial_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tutorial tutorial = list.get(position);

        holder.title.setText(tutorial.getTitle());
        holder.description.setText(tutorial.getDescription());
        holder.priority.setText(tutorial.getPriority());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title,description,priority;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.note_title);
            description = itemView.findViewById(R.id.note_description);
            priority = itemView.findViewById(R.id.note_priority);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null){
                int i = getAdapterPosition();
                listener.onClick(i);
            }
        }
    }
}
