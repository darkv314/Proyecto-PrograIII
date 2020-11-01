package com.example.appp3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appp3.R;
import com.example.appp3.model.FilesModel;
import com.example.appp3.model.FoldersModel;

import java.util.List;

public class FilesAdapter extends BaseAdapter {

    private Context context;
    private List<FilesModel> items;

    public FilesAdapter(Context context, List<FilesModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public FilesModel getItem(int position) {
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
            view = inflater.inflate(R.layout.files_layout, null);

            //Vincular los objetos con los IDs
            viewHolder.nameFileView = view.findViewById(R.id.fileName);
            viewHolder.sizeFileView = view.findViewById(R.id.fileSize);
            viewHolder.creationDateView = view.findViewById(R.id.creationDate);
            viewHolder.iconFileImageView = view.findViewById(R.id.iconFile);
            viewHolder.numberOfClicks = view.findViewById(R.id.numberOfClicks);

            view.setTag(viewHolder); //Guardar para reciclar
        } else {
            //Se puede reciclar
            viewHolder = (ViewHolder) view.getTag(); //Obtenemos el dato reciclado
        }

        //Sí o sí tenemos una vista, ya sea reciclada o nueva
        FilesModel task = this.items.get(position);
        viewHolder.nameFileView.setText(task.getName());
        viewHolder.sizeFileView.setText(task.getSize());
        viewHolder.creationDateView.setText(task.getCreation());
        viewHolder.iconFileImageView.setImageResource(task.getImage());
        viewHolder.numberOfClicks.setText(String.valueOf(task.getNumberOfClicks()));
        return view;
    }

    static class ViewHolder {
        TextView nameFileView;
        TextView sizeFileView;
        TextView creationDateView;
        TextView numberOfClicks;
        ImageView iconFileImageView;
    }

    public void setItems(List<FilesModel> items)
    {
        this.items = items;
    }
}