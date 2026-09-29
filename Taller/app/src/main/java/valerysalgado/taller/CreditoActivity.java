package valerysalgado.taller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreditoActivity extends AppCompatActivity {

    private EditText etMontoCredito, etCuotas, etInteres;
    private Button btnCalcularCredito;
    private TextView tvValorCuota, tvTotalCredito, tvGanancia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taller2); // Carga el diseño taller2.xml

        etMontoCredito = findViewById(R.id.etMontoCredito);
        etCuotas = findViewById(R.id.etCuotas);
        etInteres = findViewById(R.id.etInteres);
        btnCalcularCredito = findViewById(R.id.btnCalcularCredito);
        tvValorCuota = findViewById(R.id.tvValorCuota);
        tvTotalCredito = findViewById(R.id.tvTotalCredito);
        tvGanancia = findViewById(R.id.tvGanancia);

        btnCalcularCredito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularCredito();
            }
        });
    }

    private void calcularCredito() {
        String strMonto = etMontoCredito.getText().toString();
        String strCuotas = etCuotas.getText().toString();
        String strInteres = etInteres.getText().toString();

        if (strMonto.isEmpty() || strCuotas.isEmpty() || strInteres.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double monto = Double.parseDouble(strMonto);
        int cuotas = Integer.parseInt(strCuotas);
        double interesMensual = Double.parseDouble(strInteres) / 100.0;

        double valorInteresCuota = monto * interesMensual;
        double capitalPorCuota = monto / cuotas;
        double valorCuota = capitalPorCuota + valorInteresCuota;

        double totalCredito = valorCuota * cuotas;
        double gananciaTotal = totalCredito - monto;

        tvValorCuota.setText(String.format("Valor por cuota: $%.2f", valorCuota));
        tvTotalCredito.setText(String.format("Valor total del crédito: $%.2f", totalCredito));
        tvGanancia.setText(String.format("Ganancia total del crédito: $%.2f", gananciaTotal));
    }
}