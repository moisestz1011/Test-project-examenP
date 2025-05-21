package com.example.test_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    private TextView tvInput;
    private StringBuilder expression = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        tvInput = findViewById(R.id.tvInput);
        int[] numberButtonIDs = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        };
        int[] operatorButtonIDs = {
                R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide
        };
        View.OnClickListener numberClickListener = v -> {
            Button b = (Button) v;
            expression.append(b.getText());
            tvInput.setText(expression.toString());
        };
        for (int id : numberButtonIDs) {
            findViewById(id).setOnClickListener(numberClickListener);
        }
        View.OnClickListener operatorClickListener = v -> {
            Button b = (Button) v;
            if (expression.length() > 0) {
                char lastChar = expression.charAt(expression.length() - 1);
                if ("+-*/".indexOf(lastChar) == -1) {
                    expression.append(convertOperator(b.getText().toString()));
                    tvInput.setText(expression.toString());
                }
            }
        };
        for (int id : operatorButtonIDs) {
            findViewById(id).setOnClickListener(operatorClickListener);
        }
        findViewById(R.id.btnEqual).setOnClickListener(v -> {
            try {
                String exp = expression.toString().replace("×", "*").replace("÷", "/");
                Expression e = new ExpressionBuilder(exp).build();
                double result = e.evaluate();
                tvInput.setText(String.valueOf(result));
                expression = new StringBuilder(String.valueOf(result));
            } catch (Exception e) {
                tvInput.setText("Error");
                expression = new StringBuilder();
            }
        });

        // Botón Clear (C)
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            expression.setLength(0);
            tvInput.setText("0");
        });
    }
    private String convertOperator(String op) {
        switch (op) {
            case "×": return "*";
            case "÷": return "/";
            default: return op;
        }
    }

    public void sumar(){

    }

    public void Restar(){

    }
}