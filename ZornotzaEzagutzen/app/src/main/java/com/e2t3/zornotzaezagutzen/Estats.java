package com.e2t3.zornotzaezagutzen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.e2t3.zornotzaezagutzen.appdatabase.AppDatabase;
import com.e2t3.zornotzaezagutzen.data.entities.Estadistikak;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

// Klase hau erabiltzaileak guneak zenbat alditan jokatu dituen eta zein egunean erakusteko klasea da
public class Estats extends AppCompatActivity {
    private TableLayout table;
    private TextView tv, tv2, tv3, txtFecha;
    private Button btnDelete;
    private FloatingActionButton btnAtera;
    private List<Estadistikak> estadistikak = new ArrayList<>();
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estats);
        table = findViewById(R.id.tableLayout);
        txtFecha = findViewById(R.id.txtFecha);
        btnAtera = findViewById(R.id.ateraButtonEstats);
        // Datu-baseko instantzia hartzen du
        appDatabase = AppDatabase.getDatabase(getApplicationContext());

        // Datu-baseko datuak hartzen ditu eta setRow-ean sartzen ditu.
        estadistikak = appDatabase.estadistikakDao().getAll();
        for(int i =0; i< estadistikak.size(); i++){
            setRow(estadistikak.get(i).fecha,estadistikak.get(i).guneZenb, estadistikak.get(i).vecesJugado);
        }

        // Datuak data zehatz batean ezabatzeko botoia
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtFecha.getText().toString() != null || txtFecha.getText().toString().length() != 0){
                    String[] data = txtFecha.getText().toString().split("/");
                    if (data.length == 3) {
                        appDatabase.estadistikakDao().deleteBydAY(txtFecha.getText().toString());
                        refresh();
                    } else {
                        txtFecha.setError("Data formatua txarto dago (uuuu/hh/ee)");
                    }
                }
            }
        });

        btnAtera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // TextView bat sortzen du TableRow batean sartzeko
    private TextView newRow(String texto){
        TextView tv = new TextView(this);
        tv.setText(texto);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tv.setWidth(350);
        return tv;
    }

    // TableRow bat sortzen da emandako datuekin. Sortzean lerro berri bat egingo du eta activity-ko taulan sartuko du.
    private void setRow(String fecha, int gunea, int cantidad){
        TableRow row = new TableRow(this);
        tv = newRow(fecha);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));

        tv2 = newRow(String.valueOf(gunea));
        tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        tv3 = newRow(String.valueOf(cantidad));
        tv3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));

        row.addView(tv);
        row.addView(tv2);
        row.addView(tv3);
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        table.addView(row);
    }

    // Datuak errefreskatzen ditu ezabatzeko botoia klikatu ostean
    private void refresh() {
        estadistikak = appDatabase.estadistikakDao().getAll();
        table.removeAllViews();
        for(int i =0; i< estadistikak.size(); i++){
            setRow(estadistikak.get(i).fecha,estadistikak.get(i).guneZenb, estadistikak.get(i).vecesJugado);
        }
    }
}
