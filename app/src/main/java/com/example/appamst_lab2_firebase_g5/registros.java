package com.example.appamst_lab2_firebase_g5;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class registros extends AppCompatActivity {

    DatabaseReference db_reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);
        db_reference = FirebaseDatabase.getInstance().getReference().child("Grupos").child("Grupo4");
        leerRegistros();
    }
    public void leerRegistros(){
        db_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    mostrarRegistrosPorPantalla(snapshot);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println(error.toException());
            }
        });
    }

    public void mostrarRegistrosPorPantalla(DataSnapshot snapshot){
        LinearLayout contTemp = (LinearLayout) findViewById(R.id.ContenedorTemp);
        LinearLayout contHum = (LinearLayout) findViewById(R.id.ContenedorHum);


        System.out.println(snapshot.child("uplink_message").getValue());
        Object decoded_payload = snapshot.child("uplink_message").child("decoded_payload").getValue();
        if(decoded_payload != null){
            String tempVal = String.valueOf(snapshot.child("uplink_message").child("decoded_payload").child("temp").getValue());
            String humVal = String.valueOf(snapshot.child("uplink_message").child("decoded_payload").child("humedad").getValue());

            TextView temp = new TextView(getApplicationContext());
            temp.setText(tempVal+" °C");
            contTemp.addView(temp);

            TextView hum = new TextView(getApplicationContext());
            hum.setText(humVal+" %");
            contHum.addView(hum);
        }

    }
}