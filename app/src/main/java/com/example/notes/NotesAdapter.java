package com.example.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.Room.NotesAdapterInterface;
import com.example.notes.Room.NotesTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.holder> implements Filterable {

    private Context context;
    private List<NotesTable> notesTableList;
    public ArrayList<String> notesTableListAll;
    private NotesAdapterInterface notesAdapterInterface;

    public NotesAdapter(Context context, NotesAdapterInterface notesAdapterInterface) {
        this.context = context;
        this.notesAdapterInterface = notesAdapterInterface;
        notesTableList = new ArrayList<>();
        notesTableListAll = new ArrayList<String>();
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemrow,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
    NotesTable notesTable = notesTableList.get(position);
    holder.title.setText(notesTable.getTitle());
    holder.description.setText(notesTable.getDescription());
    }

    @Override
    public int getItemCount() {
        return notesTableList.size();
    }

    public void addNotes(NotesTable notesTable){
        notesTableList.add(notesTable);
        notifyDataSetChanged();
    }

    public void removeNotes(int pos){
        notesTableList.remove(pos);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

//        Run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<String> filteredlist = new ArrayList<>();

            if(charSequence.toString().isEmpty()){
                filteredlist.addAll(notesTableListAll);
            }else{
                for(String notes : notesTableListAll){
                    if(notes.toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredlist.add(notes);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredlist;


            return filterResults;
        }

//        Run on main thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            notesTableList.clear();
            notesTableList.addAll((Collection<? extends NotesTable>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class holder extends RecyclerView.ViewHolder {

        TextView title,description;
        public holder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    notesAdapterInterface.Longclick(getAdapterPosition(),notesTableList.get(getAdapterPosition()).getId());

                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notesAdapterInterface.simpleclick(getAdapterPosition(),notesTableList.get(getAdapterPosition()).getId());

                }
            });

        }


    }
}
