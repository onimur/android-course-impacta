package com.onimus.courseimpacta.lab06;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.onimus.courseimpacta.R;
import com.onimus.courseimpacta.lab01.app.controller.MainUtilitiesActivity;

import org.jetbrains.annotations.NotNull;

public class JogoVelhaActivity extends MainUtilitiesActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int contagem;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab06_jogodavelha);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetarJogo();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        contagem++;

        if (checarVitoria()) {
            if (player1Turn) {
                jogador1Vence();
            } else {
                jogador2Vence();
            }
        } else if (contagem == 9) {
            empata();
        } else {
            player1Turn = !player1Turn;
        }

    }

    private boolean checarVitoria() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        else return field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("");

    }

    private void jogador1Vence() {
        player1Points++;
        Toast.makeText(this, getString(R.string.player_1_vence), Toast.LENGTH_SHORT).show();
        enviaPontosAtualizados();
        resetarTabuleiro();
    }

    private void jogador2Vence() {
        player2Points++;
        Toast.makeText(this, getString(R.string.player_2_vence), Toast.LENGTH_SHORT).show();
        enviaPontosAtualizados();
        resetarTabuleiro();
    }

    private void empata() {
        Toast.makeText(this, getString(R.string.draw), Toast.LENGTH_SHORT).show();
        resetarTabuleiro();
    }

    private void enviaPontosAtualizados() {
        textViewPlayer1.setText((getString(R.string.player_1) + player1Points));
        textViewPlayer2.setText((getString(R.string.player_2) + player2Points));
    }

    private void resetarTabuleiro() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        contagem = 0;
        player1Turn = true;
    }

    private void resetarJogo() {
        player1Points = 0;
        player2Points = 0;
        enviaPontosAtualizados();
        resetarTabuleiro();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("contagem", contagem);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        contagem = savedInstanceState.getInt("contagem");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}
