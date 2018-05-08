package com.example.hvn15.finaleapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class AdminFragment1 extends Fragment implements AdapterView.OnItemSelectedListener{

    private static final String Tag = "AdminFragment1";
    private ScrollView scrollView;
    private EditText input_period;
    private EditText input_title;
    private EditText input_description;
    private EditText input_discount;
    private Button add_discount;
    private Spinner category;
    private String selecetedCategory;
    DatabaseReference databaseReference;
    private String adminName;
    private long numbOfChildren;

    private String TAG = "hej";
   DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adminfragment1_layout, container, false);
        adminName = ((LoggedInAdmin) getActivity()).adminName;
        Log.d(TAG, "onCreateView: " + adminName);
        //scrollView = (ScrollView) view.findViewById(R.id.discount_scroll);
        input_period = (EditText) view.findViewById(R.id.input_period);
        input_title = (EditText) view.findViewById(R.id.input_title);
        input_discount = (EditText)view.findViewById(R.id.input_discount);
        input_description = (EditText) view.findViewById(R.id.input_description);
        add_discount = (Button) view.findViewById(R.id.add_discount);
        category = (Spinner) view.findViewById(R.id.dropdown_category);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("data").child(adminName);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        category.setAdapter(adapter);
        category.setOnItemSelectedListener(this);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numbOfChildren = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        add_discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                String nowDate= dateFormat.format(date);
                HashMap<String, String> mapToFirebase = new HashMap<>();
                //Log.d(TAG, "onClick: " + selecetedCategory);
                mapToFirebase.put("title", input_title.getText().toString());
                mapToFirebase.put("category",selecetedCategory);
                mapToFirebase.put("discount", input_discount.getText().toString());
                mapToFirebase.put("period", input_period.getText().toString());
                mapToFirebase.put("description", input_description.getText().toString());
                mapToFirebase.put("date", dateFormat.format(date));
                mapToFirebase.put("price_before", "1000");
                mapToFirebase.put("price_after", "300");
                //We made a counter
                databaseReference.push().setValue(mapToFirebase);
                //Log.d(TAG, "onClick: " + nowDate);

            }
        });
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selecetedCategory = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getActivity(),  "VÆLG NOGET DIN NØRD", Toast.LENGTH_LONG).show();
    }
    /*
    public void addDiscount(View view){
        System.out.println(input_period.getText().toString());
        System.out.println(input_description.getText().toString());
        System.out.println(input_title.getText().toString());
    }
*/

}