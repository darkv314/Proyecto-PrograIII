package com.example.appp3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.w3c.dom.Text;

import com.example.appp3.R;
import com.example.appp3.model.FilesV;

import java.util.List;

public class FilesAdapter extends BaseAdapter {

    private Context context;
    private List<FilesV> items;

    public FilesAdapter(Context context, List<FilesV> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public FilesV getItem(int position) {
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

            view.setTag(viewHolder); //Guardar para reciclar
        } else {
            //Se puede reciclar
            viewHolder = (ViewHolder) view.getTag(); //Obtenemos el dato reciclado
        }

        //Sí o sí tenemos una vista, ya sea reciclada o nueva
        FilesV task = this.items.get(position);
        viewHolder.nameFileView.setText(task.getName());
        viewHolder.sizeFileView.setText(task.getSize());
        viewHolder.creationDateView.setText(task.getCreation());
        viewHolder.iconFileImageView.setImageResource(task.getImage());
        return view;
    }

    static class ViewHolder {
        TextView nameFileView;
        TextView sizeFileView;
        TextView creationDateView;
        ImageView iconFileImageView;
    }
}