package com.trial.axbyClient.Client.validator;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class GameStatusChecker {

    private final String STACK_FULL = "Stack is full. Please select another stack";
    private final String BOARD_FULL = "Board full. Thanks for playing";
    private final String GAME_WON = "won the game.";
    private final String REMATCH = "Rematch";
    private String message;

    public boolean toContinue(String message){
        if(message == null)
            return true;
        this.message = message;
        AtomicBoolean status = new AtomicBoolean(true);

        SwitchCustom.of(message)
                .when(GAME_WON, () -> {
                    status.set(gameWon());
                })
                .when(STACK_FULL, () -> {
                    status.set(stackFull());

                })
                .when(BOARD_FULL, () -> {
                    status.set(boardFull());

                })
                .when(REMATCH, () -> {
                    status.set(rematch());

                })
                .orElse(() -> {});

        return status.get();
    }


    public static class SwitchCustom {
        private static final SwitchCustom DONE = new SwitchCustom(null) {
            @Override
            public SwitchCustom when(String subString, Runnable r) {
                return this;
            }

            @Override
            public void orElse(Runnable r) {
            }
        };

        private final String str;

        private SwitchCustom(String str) {
            this.str = str;
        }

        public SwitchCustom when(String subString, Runnable r) {
            if(str.contains(subString)) {
                r.run();
                return DONE;
            }
            return this;
        }

        public void orElse(Runnable r) {
            r.run();
        }

        public static SwitchCustom of(String str) {
            return new SwitchCustom(str);
        }
    }


    public boolean gameWon(){
        System.out.println("\n" + message);
        return false; //set flag to stop the main game loop
    }

    public boolean stackFull(){
        System.out.println(message);
        return true;
    }

    public boolean boardFull(){
        System.out.println(message);
        return false;
    }

    public boolean rematch(){
        System.out.println(message);
        return false;
    }

}
