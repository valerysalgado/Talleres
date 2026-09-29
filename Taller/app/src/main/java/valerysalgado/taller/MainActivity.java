package valerysalgado.taller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etMonto;
    private Spinner spOrigen, spDestino;
    private Button btnConvertir, btnIrCredito;
    private TextView tvResultado;

    private String[] monedas = {"USD (Dólar)", "COP (Peso Col)", "EUR (Euro)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMonto = findViewById(R.id.etMonto);
        spOrigen = findViewById(R.id.spOrigen);
        spDestino = findViewById(R.id.spDestino);
        btnConvertir = findViewById(R.id.btnConvertir);
        tvResultado = findViewById(R.id.tvResultado);
        btnIrCredito = findViewById(R.id.btnIrCredito);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, monedas);
        spOrigen.setAdapter(adapter);
        spDestino.setAdapter(adapter);

        btnConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertirMoneda();
            }
        });

        // pantalla de la Calculadora de Créditos
        btnIrCredito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreditoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void convertirMoneda() {
        String inputMonto = etMonto.getText().toString();

        if (inputMonto.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese un monto", Toast.LENGTH_SHORT).show();
            return;
        }

        double monto = Double.parseDouble(inputMonto);
        String origen = spOrigen.getSelectedItem().toString();
        String destino = spDestino.getSelectedItem().toString();

        double montoEnUSD = 0;
        if (origen.contains("USD")) {
            montoEnUSD = monto;
        } else if (origen.contains("COP")) {
            montoEnUSD = monto / 4000.0;
        } else if (origen.contains("EUR")) {
            montoEnUSD = monto * 1.08;
        }

        double resultado = 0;
        if (destino.contains("USD")) {
            resultado = montoEnUSD;
        } else if (destino.contains("COP")) {
            resultado = montoEnUSD * 4000.0;
        } else if (destino.contains("EUR")) {
            resultado = montoEnUSD / 1.08;
        }

        tvResultado.setText(String.format("Resultado: %.2f", resultado));
    }
}