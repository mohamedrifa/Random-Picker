package com.gnarlydinouser.randompicker;

import android.annotation.SuppressLint;
import android.content.ContentValues;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class modify extends Fragment {
    private EditText modifyText;
    private Button buttonModify;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private int selectedItemPosition = -1;
    private List<String> names;
    private PersonDbHelper dbHelper;

    // Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public modify() {
        // Required empty public constructor
    }

    public static modify newInstance(String param1, String param2) {
        modify fragment = new modify();
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
        View view = inflater.inflate(R.layout.fragment_modify, container, false);
        modifyText = view.findViewById(R.id.ModifyText);
        buttonModify = view.findViewById(R.id.ButtonModify);
        spinner = view.findViewById(R.id.PersonNames);
        loadSpinnerData(view);

        // Set up the button click listener
        Button homeButton = view.findViewById(R.id.home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
            }
        });

        buttonModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyPerson();
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
        names = new ArrayList<>();
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
    }

    private void modifyPerson() {
        if (selectedItemPosition != -1) {
            String oldName = names.get(selectedItemPosition);
            String newName = modifyText.getText().toString();

            if (newName.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a new name", Toast.LENGTH_SHORT).show();
                return;
            }

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PersonContract.PersonEntry.COLUMN_NAME, newName);

            int rowsUpdated = db.update(PersonContract.PersonEntry.TABLE_NAME, values, PersonContract.PersonEntry.COLUMN_NAME + "=?", new String[]{oldName});
            if (rowsUpdated > 0) {
                names.set(selectedItemPosition, newName);
                adapter.notifyDataSetChanged();
                spinner.setSelection(0); // Reset spinner selection
                modifyText.setText(""); // Clear the edit text
                Toast.makeText(getContext(), "Name replaced", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to replace name", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "No item selected", Toast.LENGTH_SHORT).show();
        }
    }
}
