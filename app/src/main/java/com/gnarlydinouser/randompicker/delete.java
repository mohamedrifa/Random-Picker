package com.gnarlydinouser.randompicker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class delete extends Fragment {
    private Spinner spinner;
    private Button buttonDelete;
    private ArrayAdapter<String> adapter;
    private int selectedItemPosition = -1;

    private PersonDbHelper dbHelper;

    // Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public delete() {
        // Required empty public constructor
    }

    public static delete newInstance(String param1, String param2) {
        delete fragment = new delete();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dbHelper = new PersonDbHelper(getContext());
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete, container, false);

        spinner = view.findViewById(R.id.DeletePerson);
        buttonDelete = view.findViewById(R.id.button_delete);
        loadSpinnerData(view);

        // Set up the button click listener
        Button homeButton = view.findViewById(R.id.home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
            }
        });

        return view;
    }

    private void home() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @SuppressLint("Range")
    private void loadSpinnerData(View view) {
        List<String> names = new ArrayList<>();
        Cursor cursor = dbHelper.getAllNames();

        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(cursor.getColumnIndex("name")));
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedItemPosition = -1;
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItemPosition != -1) {
                    String selectedItem = names.get(selectedItemPosition);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.delete(PersonContract.PersonEntry.TABLE_NAME, PersonContract.PersonEntry.COLUMN_NAME + "=?", new String[]{selectedItem});
                    names.remove(selectedItemPosition);
                    adapter.notifyDataSetChanged();
                    spinner.setSelection(0); // Reset spinner selection
                    Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "No item selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
