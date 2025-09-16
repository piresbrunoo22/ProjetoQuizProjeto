package com.example.projetoquiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView txvPergunta;
    private RadioGroup rbgPrincipal;
    private Button btnResponder;
    private TextView txvResultado;
    private Button btnReiniciar;

    private int pontuacao = 0;
    private int indicePergunta = 0;

    // Perguntas
    private String[] perguntas = {
            "Pergunta 1: Qual o primeiro campeão mundial?",
            "Pergunta 2: Qual o maior campeão brasileiro de todos os tempos?",
            "Pergunta 3: Quem será rebaixado em 2025?",
            "Pergunta 4: Qual time se orgulha de ser time de favelados e miseráveis?",
            "Pergunta 5: Qual time so perde eh conhecido com trikas?"
    };

    // IDs das respostas corretas
    private int[] respostasCorretas = {
            R.id.rba, // Pergunta 1 -> rb_a
            R.id.rba, // Pergunta 2 -> rb_a
            R.id.rbb, // Pergunta 3 -> rb_b
            R.id.rbd, // Pergunta 4 -> rb_d
            R.id.rbc  // Pergunta 5 -> rb_c
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        txvPergunta = findViewById(R.id.txvPerguntas);
       rbgPrincipal = findViewById(R.id.rdgPrincipal);
        btnResponder = findViewById(R.id.btnResponder);
        //txvResultado = findViewById(R.id.txvResultado);
        //btnReiniciar = findViewById(R.id.btnReiniciar);

        carregarPergunta();

        //btnResponder.setOnClickListener(v -> verificarResposta());
        btnResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarResposta();
            }
        });

        //btnReiniciar.setOnClickListener(v -> reiniciarQuiz());

        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarQuiz();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void carregarPergunta() {
        if (indicePergunta < perguntas.length) {
            txvPergunta.setText(perguntas[indicePergunta]);
            rbgPrincipal.clearCheck();

            txvResultado.setText("Pontuação: " + pontuacao + " / " + perguntas.length);
            txvResultado.setVisibility(View.VISIBLE);
            btnResponder.setEnabled(true);
            btnReiniciar.setVisibility(View.GONE);

        } else {
            txvPergunta.setText("Quiz finalizado!");
            txvResultado.setText("Você acertou " + pontuacao + " de " + perguntas.length + " perguntas.");
            txvResultado.setVisibility(View.VISIBLE);
            btnResponder.setEnabled(false);
            btnReiniciar.setVisibility(View.VISIBLE);
        }
    }

    private void verificarResposta() {
        if (indicePergunta >= perguntas.length) return;

        int respostaCorreta = respostasCorretas[indicePergunta];
        int respostaSelecionada = rbgPrincipal.getCheckedRadioButtonId();

        if (respostaSelecionada == -1) {
            Toast.makeText(this, "Selecione uma resposta!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (respostaSelecionada == respostaCorreta) {
            pontuacao++;
            Toast.makeText(this, "Correto!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Errado!", Toast.LENGTH_SHORT).show();
        }
        btnResponder.postDelayed(() ->{
            indicePergunta++;
            carregarPergunta();
        }, 800);
    }
    private void reiniciarQuiz(){
        pontuacao = 0;
        indicePergunta = 0;
        carregarPergunta();
    }
}
