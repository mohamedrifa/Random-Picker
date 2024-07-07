package com.gnarlydinouser.randompicker;// CustomSpinnerAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] items;

    public CustomSpinnerAdapter(Context context, int textViewResourceId, String[] items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_item, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.spinner_item_text);
        textView.setText(items[position]);
        // Change the text color or other properties if needed
        textView.setTextColor(context.getResources().getColor(R.color.black)); // Example

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_item, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.spinner_item_text);
        textView.setText(items[position]);
        // Change the text color or other properties if needed
        textView.setTextColor(context.getResources().getColor(R.color.white)); // Example

        return convertView;
    }
}
