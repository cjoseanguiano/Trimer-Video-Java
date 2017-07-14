package com.devix.www.video_record_trimmer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    private Button btnUno;
    private Button btnDos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnUno = findViewById(R.id.btnUno);
        btnDos = findViewById(R.id.btnDos);


        btnUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "ABC", Toast.LENGTH_SHORT).show();
                startActivityForResult(new Intent(MainActivity.this, RecordAndSavex.class)
                                .putExtra(Utilsx.operation_type, Utilsx.PERFORM_CHOOSE_FROM_GALLERY_OR_RECORD_VIDEO)
                        , Utilsx.REQUEST_CODE_PEFORM_VIDEO_TRIM);
            }
        });

        btnDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "B", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
