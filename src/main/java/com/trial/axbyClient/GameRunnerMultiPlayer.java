package com.trial.axbyClient;

import com.trial.axbyClient.Client.Player;
import com.trial.axbyClient.Client.api.MoveApi;
import com.trial.axbyClient.Client.api.RegisterApi;
import com.trial.axbyClient.Client.dto.Board;
import com.trial.axbyClient.Client.validator.GameStatusChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Order(value = 2)
@Component
public class GameRunnerMultiPlayer implements CommandLineRunner {
    private RegisterApi registerApi;
    private MoveApi moveApi;
    private GameStatusChecker gameStatusChecker;

    private Player player1;
    private Player player2;
    private Board board;
    private int moveCount;


    @Autowired
    public GameRunnerMultiPlayer(RegisterApi registerApi, MoveApi moveApi, GameStatusChecker gameStatusChecker) {
        this.registerApi = registerApi;
        this.moveApi = moveApi;
        this.gameStatusChecker = gameStatusChecker;
    }

    @Override
    public void run(String... args) throws Exception {

        boolean gameOver = false;

        moveCount = 0;
        while (!gameOver) {

            if (moveCount == 0) {
                startGame();
                moveCount++;
            }

            if (!makeMove(player1))
                break;

            gameOver = !makeMove(player2);
        }
    }


    private boolean checkStatus(String message) {
        return gameStatusChecker.toContinue(message);
    }


    private void testRun() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Reading data using readLine
        String name = reader.readLine();
        player1 = createPlayer(registerApi.registerPlayer(name), name);
        System.out.println(player1.getId() + player1.getName());
    }


    private void startGame() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nPlease enter you name. <Player 1>");
        String name = reader.readLine();
        player1 = createPlayer(registerApi.registerPlayer(name), name);

        System.out.println("\nPlease enter you name. <Player 2>");
        name = reader.readLine();
        player2 = createPlayer(registerApi.registerPlayer(name), name);


        if (player1.getId() == 1) {
            System.out.println("\nHi " + player1.getName() + ". Your marker : 'X' ");
            System.out.println("Please make the first move. Choose stack from 1 to 9.");
        } else if (player2.getId() == 2) {
            System.out.println("\nHi " + player2.getName() + "Your marker : 'O' ");
            System.out.println("Waiting for Player 1 to make the first move.");
        }
    }

    private Player createPlayer(int id, String name) throws Exception {
        if (id == 0) {
            throw new Exception("\nGame participation full!");
        }
        return new Player(id, name);
    }

    private boolean makeMove(Player player) throws IOException {
        switch(moveApi.myTurn(player.getId())){
            case "continue":
                displayBoard();
                int nStack = readStackNumber(player);
                moveApi.makeMove(player.getId(), nStack);
                displayBoard();
                return true;
            case "not":
                return false;
            default:
                return true;
        }
    }

    private void displayBoard() {
        board = moveApi.getBoard();
        board.display();
    }

    private int readStackNumber(Player player) throws IOException {
        int nStack = 0;
        boolean invalidInput;
        do{
            invalidInput = false;
            System.out.println("\nMake your move, "+ player.getName() + ". Choose stack from 1 to 9.");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try{
                nStack = Integer.parseInt(reader.readLine());
                if(nStack < 0 || nStack > 9)
                    throw new Exception();
            }catch (Exception e){
                invalidInput = true;
                System.out.println("Please enter a number from 1 to 9.");
            }
        }
        while(invalidInput);

        return nStack;
    }
}
