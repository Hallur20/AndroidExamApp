package com.example.hvn15.finaleapp.adminFragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hvn15.finaleapp.activities.LoggedInAdmin;
import com.example.hvn15.finaleapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class CreateDiscountFragment extends Fragment implements AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private EditText input_title;
    private EditText input_description;
    private EditText input_discount;
    private Button add_discount;
    private Spinner category;
    private String selecetedCategory;
    DatabaseReference dbReferenceToMyAdmin;
    private String adminName;
    private Button DateTimePickBtn;
    private int day, month, year, hour, minute;
    private int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private String convertCalendarToDateformat;
    private Calendar calendar = Calendar.getInstance();
    private Date date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adminfragment1_layout, container, false);
        adminName = ((LoggedInAdmin) getActivity()).adminName;
        //scrollView = (ScrollView) view.findViewById(R.id.discount_scroll);
        input_title = view.findViewById(R.id.input_title);
        input_discount = view.findViewById(R.id.input_discount);
        input_description =view.findViewById(R.id.input_description);
        add_discount =  view.findViewById(R.id.add_discount);
        category =  view.findViewById(R.id.dropdown_category);
        dbReferenceToMyAdmin = FirebaseDatabase.getInstance().getReference().child("data").child(adminName);
        DateTimePickBtn = view.findViewById(R.id.dateTimeBtn);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        category.setAdapter(adapter);
        category.setOnItemSelectedListener(this);
        DateTimePickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), CreateDiscountFragment.this::onDateSet,
                        year, month, day);
                datePickerDialog.show();
            }
        });

        add_discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                HashMap<String, String> mapToFirebase = new HashMap<>();
                mapToFirebase.put("title", input_title.getText().toString());
                mapToFirebase.put("category", selecetedCategory);
                mapToFirebase.put("discount", input_discount.getText().toString());
                mapToFirebase.put("period", convertCalendarToDateformat);
                mapToFirebase.put("description", input_description.getText().toString());
                mapToFirebase.put("date", dateFormat.format(date));
                mapToFirebase.put("price_before", "1000");
                mapToFirebase.put("price_after", "300");
                mapToFirebase.put("store", "kvickly");
                dbReferenceToMyAdmin.push().setValue(mapToFirebase);
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
        Toast.makeText(getActivity(), "Choose something", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal = i1;
        dayFinal = i2;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this::onTimeSet,
                hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal = i;
        minuteFinal = i1;
        calendar.set(Calendar.YEAR, yearFinal);
        calendar.set(Calendar.MONTH, monthFinal);
        calendar.set(Calendar.DAY_OF_MONTH, dayFinal);
        calendar.set(Calendar.HOUR, hourFinal);
        calendar.set(Calendar.MINUTE, minuteFinal);
        date = calendar.getTime();
        convertCalendarToDateformat = dateFormat.format(date);
    }


}