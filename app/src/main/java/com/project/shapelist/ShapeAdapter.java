package com.project.shapelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class ShapeAdapter extends ArrayAdapter<Shape> {

    public ShapeAdapter(Context context, int resource, List<Shape> shapeList) {
        super(context, resource, shapeList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Shape shape = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shape_cell,parent,false);
        }
        TextView txV = (TextView) convertView.findViewById(R.id.shapeName);
        ImageView imV = (ImageView) convertView.findViewById(R.id.shapeImage);

        txV.setText(shape.getName());
        imV.setImageResource(shape.getImage());

        return convertView;
    }
}
