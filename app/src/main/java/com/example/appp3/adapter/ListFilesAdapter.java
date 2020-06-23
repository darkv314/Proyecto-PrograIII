package com.example.appp3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appp3.model.FileModel;
import com.example.appp3.R;

import java.util.List;

public class ListFilesAdapter  extends BaseAdapter {

    private Context context;
    private List<FileModel> items;

    public ListFilesAdapter(Context context, List<FileModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public FileModel getItem(int position) {
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
            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.file_layout, null);

            viewHolder.nameTextView = view.findViewById(R.id.tittleTextView);
            viewHolder.iconImageView = view.findViewById(R.id.iconFileView);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        FileModel task = this.items.get(position);
        viewHolder.nameTextView.setText(task.getName());
        viewHolder.iconImageView.setImageResource(task.getImage());
        return view;
    }

    static class ViewHolder {
        TextView nameTextView;
        ImageView iconImageView;
    }
}
