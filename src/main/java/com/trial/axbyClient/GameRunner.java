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

@Order(value = 1)
@Component
public class GameRunner implements CommandLineRunner {
    private RegisterApi registerApi;
    private MoveApi moveApi;
    private GameStatusChecker gameStatusChecker;

    private Player player;
    private Board board;
    private int moveCount;


    @Autowired
    public GameRunner(RegisterApi registerApi, MoveApi moveApi, GameStatusChecker gameStatusChecker) {
        this.registerApi = registerApi;
        this.moveApi = moveApi;
        this.gameStatusChecker = gameStatusChecker;
    }

    @Override
    public void run(String... args) throws Exception {

        boolean gameOver = false;

        moveCount = 0;
        while(!gameOver){
            if(moveCount == 0) {
                startGame();
                moveCount++;
            }

            gameOver = !makeMove(player);

            if(gameOver)
                gameOver = rePlay();
        }
    }

    private boolean checkStatus() {
        String message = moveApi.getBoard().getMoveMessage();
        return gameStatusChecker.toContinue(message);
    }

    private void startGame() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nPlease enter you name.");
        String name = reader.readLine();

        player = createPlayer(registerApi.registerPlayer(name), name);

        if(player.getId() == 1){
            System.out.println("\nHi " + player.getName() +". You are Player #1, your marker : 'X' ");
            int i = 0;
            while(!moveApi.bothPlayersPresent()){
                if(i == 0)
                    System.out.println("Waiting for second player to join");
                i++;
            }
            System.out.println("Player 2 joined. Let the game begin.");
        }
        else {
            System.out.println("\nHi " + player.getName() +". You are Player #2, your marker : 'O' ");
            System.out.println("Waiting for Player 1 to make the first move.");
        }
    }

    private Player createPlayer(int id, String name) throws Exception {
        if(id == 0){
            throw new Exception("\nGame participation full!");
        }
        return new Player(id, name);
    }

    private boolean makeMove(Player player) throws IOException {
        if(validFirstMove() || validNextMoves()){
                displayBoard();
                int nStack = readStackNumber(player);
                moveApi.makeMove(player.getId(), nStack);
                displayBoard();
        }else if(!moveApi.bothPlayersPresent()){
            System.out.println("\nOther Player left.");
            endGame();
        }
        return checkStatus();
    }

    private boolean validFirstMove() {
        return moveApi.myTurn(player.getId()) && moveCount == 1;
    }

    private boolean validNextMoves() {
        return moveApi.myTurn(player.getId()) && moveApi.bothPlayersPresent();
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

    private void endGame() {
        System.out.println("\nThanks for playing!");
        moveApi.endGame();
        System.exit(0);
    }

    private boolean rePlay() throws IOException {
        if (moveApi.bothPlayersPresent()) {
            System.out.println("\nUp for rematch? Y/n");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            if (reader.readLine().equalsIgnoreCase("Y")) {
                moveApi.rematch();
                return false;
            }
            else
                endGame();
        }
        else{
            System.out.println("\nOther Player left.");
            endGame();
        }
        return true;
    }

}
