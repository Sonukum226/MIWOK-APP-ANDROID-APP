package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class NumbersClickListeners implements View.OnClickListener {

    @Override
    public void onClick(View view){
        Toast.makeText(view.getContext(),"open the List of numbers",Toast.LENGTH_SHORT).show();
    }
}