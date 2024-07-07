package com.gnarlydinouser.randompicker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link add#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add extends Fragment {
    private EditText editTextPersonName;
    private Button buttonAddPerson;
    private PersonDbHelper dbHelper;
    // Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public add() {
        // Required empty public constructor
    }
    public static add newInstance(String param1, String param2) {
        add fragment = new add();
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
        dbHelper = new PersonDbHelper(getContext()); // Pass the context to dbHelper
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        // Initialize the UI elements
        editTextPersonName = view.findViewById(R.id.editTextPersonName);
        buttonAddPerson = view.findViewById(R.id.buttonAddPerson);

        // Set up the button click listener for adding a person
        buttonAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPerson();
            }
        });

        // Set up the button click listener for the home button
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

    private void addPerson() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String name = editTextPersonName.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(getContext(), "Please Enter a Person Name", Toast.LENGTH_SHORT).show();
            return;
        }
        values.put(PersonContract.PersonEntry.COLUMN_NAME, name);
        db.insert(PersonContract.PersonEntry.TABLE_NAME, null, values);
        // Optionally, clear editTextPersonName after adding
        editTextPersonName.setText("");
        Toast.makeText(getContext(), "Person Added", Toast.LENGTH_SHORT).show();
    }
}
