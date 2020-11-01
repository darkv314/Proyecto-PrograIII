package com.example.appp3.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appp3.R;

import java.util.ArrayList;
import java.util.List;

public class HiddenFoldersAdapter extends BaseAdapter
{
    private List<String> data = new ArrayList<>();
    private boolean[] selection;

    public void setData(List<String> data) {
        if (data != null) {
            this.data.clear();
            if (data.size() > 0) {
                this.data.addAll(data);
            }
            notifyDataSetChanged();
        }
    }

    public void setSelection(boolean[] selection) {
        if (selection != null) {
            this.selection = new boolean[selection.length];
            System.arraycopy(selection, 0, this.selection, 0, selection.length);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_layout, parent, false);
            convertView.setTag(new ViewHolder((TextView) convertView.findViewById(R.id.tittleTextView)));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        final String item = getItem(position);
        holder.info.setText(item.substring(item.lastIndexOf('/') + 1));
        if (selection != null) {
            if (selection[position]) {
                holder.info.setBackgroundColor(Color.argb(100, 9, 9, 9));
            } else {
                holder.info.setBackgroundColor(Color.WHITE);
            }
        }
        return convertView;
    }

    class ViewHolder {
        TextView info;

        ViewHolder(TextView info) {
            this.info = info;
        }
    }
}
