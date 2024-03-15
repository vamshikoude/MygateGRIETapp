package com.mygate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {


        CardView admin,student,faculty;
        Intent i1,i2,i3,i4;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            admin = (CardView) findViewById(R.id.admin);
            i1 = new Intent(this,HOD.class);
            admin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(i1);
                }
            });


            student= (CardView) findViewById(R.id.student);
            i3 = new Intent(this,Student.class);
            student.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(i3);
                }
            });


            faculty= (CardView) findViewById(R.id.faculty);
            i4 = new Intent(this,Faculty.class);
            faculty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(i4);
                }
            });

        }




}
