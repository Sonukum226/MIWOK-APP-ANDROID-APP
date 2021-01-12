package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //the view that shows the Numbers category

        TextView numbers=(TextView) findViewById(R.id.numbers);

        //set a clicklisteners on that view
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent numbersIntent = new Intent(MainActivity.this, NumberActivity.class);
                startActivity(numbersIntent);
            }
        });
         // @ Family Categaory
        TextView family=(TextView) findViewById(R.id.family);
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent family=new Intent(MainActivity.this,FamilyActivity.class);
                startActivity(family);
            }
        });

        // @ Color category
        TextView color=(TextView) findViewById(R.id.colors);
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent color=new Intent(MainActivity.this,ColorsActivity.class);
                startActivity(color);
            }
        });

        //@ Pharases Category
            TextView pharases=(TextView)findViewById(R.id.Phrases);
            pharases.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pharases=new Intent(MainActivity.this,PhrasesActivity.class);
                    startActivity(pharases);
                }
            });


    }


}