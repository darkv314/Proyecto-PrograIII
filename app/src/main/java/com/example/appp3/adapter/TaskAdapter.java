package com.example.appp3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appp3.R;
import com.example.appp3.AllSongsTask;

import java.util.List;

public class TaskAdapter extends BaseAdapter {

    private Context context;
    private List<AllSongsTask> items;

    public TaskAdapter(Context context, List<AllSongsTask> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public AllSongsTask getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.items.get(position).getId();
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            //No se puede reciclar --> Nuevo
            viewHolder = new ViewHolder();

            //Mostrar el layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_allsongs, null);

            //Vincular los objetos con los IDs
            viewHolder.nameTextView = view.findViewById(R.id.songNameView);
            viewHolder.detailsTextView = view.findViewById(R.id.songArtistView);


            view.setTag(viewHolder); //Guardar para reciclar
        } else {
            //Se puede reciclar
            viewHolder = (ViewHolder) view.getTag(); //Obtenemos el dato reciclado
        }

        //Sí o sí tenemos una vista, ya sea reciclada o nueva
        AllSongsTask task = this.items.get(position);
        viewHolder.nameTextView.setText(task.getSong_name());
        viewHolder.detailsTextView.setText(task.getSong_artist());
        return view;
    }

    static class ViewHolder {
        TextView nameTextView;
        TextView detailsTextView;
    }
}
