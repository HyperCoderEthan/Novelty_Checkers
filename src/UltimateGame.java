import java.util.*;

public class UltimateGame {

    public static void main(String[] args) {

        Scanner fetch = new Scanner(System.in);

        //constructing all the arrays that this program will need
        int[] player1Range = {1, 12};
        int[] player2Range = {13, 24};
        int[] chosenPiece = new int[2];

        //4 directions and potential moves because a checker can move at max in 4 directions
        int[] potentialMoveA = new int[3];
        int[] potentialMoveB = new int[3];
        int[] potentialMoveC = new int[3];
        int[] potentialMoveD = new int[3];

        //A is upper right, B upper left, C lower right, D lower left
        //I 100% should have made these constants, unfortunately I am retarded
        int[] directionA = {-1, -1};
        int[] directionB = {-1, 1};
        int[] directionC = {1, -1};
        int[] directionD = {1, 1};

        boolean[] areKings = new boolean[24];
        int[][] board = new int[8][8];
        fillBoard(board);

        description(fetch);

        //these just prime the while loop
        int player1Checkers = 1;
        int player2Checkers = 1;

        while (player1Checkers > 0 && player2Checkers > 0) {

            boolean askAgain = true;

            System.out.println();
            fancyDecoration();
            System.out.println("PLAYER 1'S TURN YOOOOOOOO!!!!!");
            fancyDecoration();
            System.out.println();

            drawFrame(board, areKings);

            //this loop loops if the player wants to choose a different checker to move instead of the one they chose
            while (askAgain) {

                whichCheckerDoesUserWant(fetch, board, chosenPiece, player1Range);
                potentialMoves(board, chosenPiece, potentialMoveC, player1Range, directionC);
                potentialMoves(board, chosenPiece, potentialMoveD, player1Range, directionD);

                if (areKings[(board[chosenPiece[0]][chosenPiece[1]] - 1)]) {

                    potentialMoves(board, chosenPiece, potentialMoveA, player1Range, directionA);
                    potentialMoves(board, chosenPiece, potentialMoveB, player1Range, directionB);

                } else {

                    Arrays.fill(potentialMoveA, -1);
                    Arrays.fill(potentialMoveB, -1);

                }

                //method whichMoveToPerform will force this string to ONLY be either a, b, c, d, e. and only if those moves exist
                String moveToPerform = whichMoveToPerform(fetch, potentialMoveA, potentialMoveB, potentialMoveC, potentialMoveD);

                //there was probably a more elegant way to do this, but this switch just takes the string received from
                //whichMoveToPerform and then performs the corresponding move, checks if any pieces are now kings and also will
                //perform killstreaks as long as they are available
                switch (moveToPerform) {

                    case "a":
                        performMove(board, chosenPiece, potentialMoveA, directionA);
                        updateKings(board, areKings, player1Range);

                        delineateKillStreak(fetch, board, chosenPiece, potentialMoveA, potentialMoveB,
                                potentialMoveC, potentialMoveD, directionA, directionB ,directionC,
                                directionD, moveToPerform, player1Range, areKings);

                        askAgain = false;
                        break;

                    case "b":
                        performMove(board, chosenPiece, potentialMoveB, directionB);
                        updateKings(board, areKings, player1Range);

                        delineateKillStreak(fetch, board, chosenPiece, potentialMoveA, potentialMoveB,
                                potentialMoveC, potentialMoveD, directionA, directionB ,directionC,
                                directionD, moveToPerform, player1Range, areKings);

                        askAgain = false;
                        break;

                    case "c":
                        performMove(board, chosenPiece, potentialMoveC, directionC);
                        updateKings(board, areKings, player1Range);

                        delineateKillStreak(fetch, board, chosenPiece, potentialMoveA, potentialMoveB,
                                potentialMoveC, potentialMoveD, directionA, directionB ,directionC,
                                directionD, moveToPerform, player1Range, areKings);

                        askAgain = false;
                        break;

                    case "d":
                        performMove(board, chosenPiece, potentialMoveD, directionD);
                        updateKings(board, areKings, player1Range);

                        delineateKillStreak(fetch, board, chosenPiece, potentialMoveA, potentialMoveB,
                                potentialMoveC, potentialMoveD, directionA, directionB ,directionC,
                                directionD, moveToPerform, player1Range, areKings);

                        askAgain = false;
                        break;

                    default:
                        askAgain = true;

                }

            }

            //this is just a copy of the above code with player2's range threaded in the parameters instead of 1's
            player1Checkers = howManyCheckersPerTeam(board, player1Range);
            player2Checkers = howManyCheckersPerTeam(board, player2Range);

            askAgain = true;

            if (player1Checkers > 0 && player2Checkers > 0) {

                System.out.println();
                fancyDecoration();
                System.out.println("PLAYER 2'S TURN LETS GOOOOOOOOO!!!!!");
                fancyDecoration();
                System.out.println();

                drawFrame(board, areKings);

                while (askAgain) {

                    //because player2's non-king checkers move up instead of down i passed potential move A and B for the
                    //actual parameters here instead of C and D like player 1
                    whichCheckerDoesUserWant(fetch, board, chosenPiece, player2Range);
                    potentialMoves(board, chosenPiece, potentialMoveA, player2Range, directionA);
                    potentialMoves(board, chosenPiece, potentialMoveB, player2Range, directionB);

                    if (areKings[(board[chosenPiece[0]][chosenPiece[1]] - 1)]) {

                        potentialMoves(board, chosenPiece, potentialMoveC, player2Range, directionC);
                        potentialMoves(board, chosenPiece, potentialMoveD, player2Range, directionD);

                    } else {

                        Arrays.fill(potentialMoveC, -1);
                        Arrays.fill(potentialMoveD, -1);

                    }

                    String moveToPerform = whichMoveToPerform(fetch, potentialMoveA, potentialMoveB, potentialMoveC, potentialMoveD);

                    switch (moveToPerform) {

                        case "a":
                            performMove(board, chosenPiece, potentialMoveA, directionA);
                            updateKings(board, areKings, player2Range);

                            delineateKillStreak(fetch, board, chosenPiece, potentialMoveA, potentialMoveB,
                                    potentialMoveC, potentialMoveD, directionA, directionB ,directionC,
                                    directionD, moveToPerform, player2Range, areKings);

                            askAgain = false;
                            break;

                        case "b":
                            performMove(board, chosenPiece, potentialMoveB, directionB);
                            updateKings(board, areKings, player2Range);

                            delineateKillStreak(fetch, board, chosenPiece, potentialMoveA, potentialMoveB,
                                    potentialMoveC, potentialMoveD, directionA, directionB ,directionC,
                                    directionD, moveToPerform, player2Range, areKings);

                            askAgain = false;
                            break;

                        case "c":
                            performMove(board, chosenPiece, potentialMoveC, directionC);
                            updateKings(board, areKings, player2Range);

                            delineateKillStreak(fetch, board, chosenPiece, potentialMoveA, potentialMoveB,
                                    potentialMoveC, potentialMoveD, directionA, directionB ,directionC,
                                    directionD, moveToPerform, player2Range, areKings);

                            askAgain = false;
                            break;

                        case "d":
                            performMove(board, chosenPiece, potentialMoveD, directionD);
                            updateKings(board, areKings, player2Range);

                            delineateKillStreak(fetch, board, chosenPiece, potentialMoveA, potentialMoveB,
                                    potentialMoveC, potentialMoveD, directionA, directionB ,directionC,
                                    directionD, moveToPerform, player2Range, areKings);

                            askAgain = false;
                            break;

                        default:
                            askAgain = true;

                    }

                }

            }

            player1Checkers = howManyCheckersPerTeam(board, player1Range);
            player2Checkers = howManyCheckersPerTeam(board, player2Range);

        }

        fancyDecoration();

        if (player1Checkers == 0) {

            System.out.print("PLAYER 2 WON, GET FUCKED PLAYER 1");

        } else {

            System.out.print("eat shit and die player 2 it is the year of the player 1");

        }

        fancyDecoration();

    }

    //this disgusting amount of parameters could have been avoided if declared some of the arrays in the class scope
    //method exists to 1. see if the move taken was a capture, 2. if so, is there a kill streak available 3. perform the killstreak
    //4. keep the killStreak going as long as it is available
    public static void delineateKillStreak(Scanner fetch, int[][] board, int[] chosenPiece, int[] potentialMoveA,
                                        int[] potentialMoveB, int[] potentialMoveC, int[] potentialMoveD,
                                        int[] directionA, int[] directionB, int[] directionC, int[] directionD,
                                        String moveToPerform, int[] range, boolean[] areKings) {

        String streak;

        do {

            streak = "";

            int pMain;

            if (moveToPerform.equals("a")) {

                pMain = potentialMoveA[2];

            } else if (moveToPerform.equals("b")) {

                pMain = potentialMoveB[2];

            } else if (moveToPerform.equals("c")) {

                pMain = potentialMoveC[2];

            } else {

                pMain = potentialMoveD[2];

            }

            //basically this tests if the move the user just performed was a capturing move or not
            if (pMain == 1) {

                //updates the chosenPiece array to the new checker location, resets the potential move arrays
                resetFinners(chosenPiece, potentialMoveA, potentialMoveB, potentialMoveC, potentialMoveD, moveToPerform);

                //exists to assess potential moves for player 1, else player 2
                if (range[0] == 1) {

                    potentialMoves(board, chosenPiece, potentialMoveC, range, directionC);
                    potentialMoves(board, chosenPiece, potentialMoveD, range, directionD);

                    if (areKings[(board[chosenPiece[0]][chosenPiece[1]] - 1)]) {

                        potentialMoves(board, chosenPiece, potentialMoveA, range, directionA);
                        potentialMoves(board, chosenPiece, potentialMoveB, range, directionB);

                    }

                } else {

                    potentialMoves(board, chosenPiece, potentialMoveA, range, directionA);
                    potentialMoves(board, chosenPiece, potentialMoveB, range, directionB);

                    if (areKings[(board[chosenPiece[0]][chosenPiece[1]] - 1)]) {

                        potentialMoves(board, chosenPiece, potentialMoveC, range, directionC);
                        potentialMoves(board, chosenPiece, potentialMoveD, range, directionD);

                    }

                }

                //because killstreaks only work with consecutive captures, and we just filled the potential move arrays
                //with all possible next moves, we must get rid of all the non capturing potential moves
                if (potentialMoveA[2] != 1) {
                    Arrays.fill(potentialMoveA, -1);
                }

                if (potentialMoveB[2] != 1) {
                    Arrays.fill(potentialMoveB, -1);
                }

                if (potentialMoveC[2] != 1) {
                    Arrays.fill(potentialMoveC, -1);
                }

                if (potentialMoveD[2] != 1) {
                    Arrays.fill(potentialMoveD, -1);
                }

                //if there is a potential move still available then a killstreak is available
                //the code forces you to perform a killstreak even if you dont want to
                if (potentialMoveA[0] != -1 || potentialMoveB[0] != -1 || potentialMoveC[0] != -1
                        || potentialMoveD[0] != -1) {

                    //because streak gets initialized here, this entire method will loop itself, so basically it performs
                    //the killstreak until there are no more kills to streak
                    streak = whichKillStreak(fetch, potentialMoveA, potentialMoveB,
                            potentialMoveC, potentialMoveD);

                    switch (streak) {

                        case "a":
                            performMove(board, chosenPiece, potentialMoveA, directionA);
                            updateKings(board, areKings, range);
                            break;

                        case "b":
                            performMove(board, chosenPiece, potentialMoveB, directionB);
                            updateKings(board, areKings, range);
                            break;

                        case "c":
                            performMove(board, chosenPiece, potentialMoveC, directionC);
                            updateKings(board, areKings, range);
                            break;

                        default:
                            performMove(board, chosenPiece, potentialMoveD, directionD);
                            updateKings(board, areKings, range);
                            break;

                    }

                }

            }

        } while (!streak.equals(""));

    }

    //this exists because in order to do killstreaks, i reuse the potential move and chosen piece arrays.
    public static void resetFinners(int[] chosenPiece, int[] pA, int[] pB, int[] pC, int[] pD, String move) {

        if (move.equals("a")) {

            chosenPiece[0] = pA[0];
            chosenPiece[1] = pA[1];

            Arrays.fill(pA, -1);
            Arrays.fill(pB, -1);
            Arrays.fill(pC, -1);
            Arrays.fill(pD, -1);

        } else if (move.equals("b")) {

            chosenPiece[0] = pB[0];
            chosenPiece[1] = pB[1];

            Arrays.fill(pA, -1);
            Arrays.fill(pB, -1);
            Arrays.fill(pC, -1);
            Arrays.fill(pD, -1);

        } else if (move.equals("c")) {

            chosenPiece[0] = pC[0];
            chosenPiece[1] = pC[1];

            Arrays.fill(pA, -1);
            Arrays.fill(pB, -1);
            Arrays.fill(pC, -1);
            Arrays.fill(pD, -1);

        } else if (move.equals("d")) {

            chosenPiece[0] = pD[0];
            chosenPiece[1] = pD[1];

            Arrays.fill(pA, -1);
            Arrays.fill(pB, -1);
            Arrays.fill(pC, -1);
            Arrays.fill(pD, -1);

        }

    }

    public static void updateKings(int[][] board, boolean[] areKings, int[] range) {

        if (range[0] == 1) {

            for (int i = 0; i < board[7].length; i++) {

                if (board[7][i] >= range[0] && board[7][i] <= range[1]) {

                    areKings[board[7][i] -1] = true;

                }

            }

        } else {

            for (int i = 0; i < board[0].length; i++) {

                if (board[0][i] >= range[0] && board[0][i] <= range[1]) {

                    areKings[board[0][i] -1] = true;

                }

            }

        }

    }

    public static void performMove(int[][] board, int[] chosenPiece, int[] potentialMove, int[] direction) {

        board[potentialMove[0]][potentialMove[1]] += board[chosenPiece[0]][chosenPiece[1]];
        board[chosenPiece[0]][chosenPiece[1]] -= board[chosenPiece[0]][chosenPiece[1]];

        if (potentialMove[2] == 1) {

            int finnaKillThis = board[potentialMove[0] - direction[0]][potentialMove[1] - direction[1]];
            board[potentialMove[0] - direction[0]][potentialMove[1] - direction[1]] -= finnaKillThis;

        }

    }

    //basically same method as whichMoveToPerform except there is no e option
    public static String whichKillStreak(Scanner fetch, int[] potentialMoveA, int[] potentialMoveB,
                                         int[] potentialMoveC, int[] potentialMoveD) {

        System.out.println("KILLSTREAK OPPORTUNITY ALERT");

        String A = "A: " + killPrompt(potentialMoveA);
        String B = "B: " + killPrompt(potentialMoveB);
        String C = "C: " + killPrompt(potentialMoveC);
        String D = "D: " + killPrompt(potentialMoveD);

        if (potentialMoveA[0] != -1)
            System.out.println(A);

        if (potentialMoveB[0] != -1)
            System.out.println(B);

        if (potentialMoveC[0] != -1)
            System.out.println(C);

        if (potentialMoveD[0] != -1)
            System.out.println(D);

        String trueAnswer = "";

        while (trueAnswer.equalsIgnoreCase("")) {

            String answer = fetch.nextLine();

            if (answer.equalsIgnoreCase("a") && potentialMoveA[0] != -1) {
                trueAnswer = answer;

            } else if (answer.equalsIgnoreCase("b") && potentialMoveB[0] != -1) {
                trueAnswer = answer;

            } else if (answer.equalsIgnoreCase("c") && potentialMoveC[0] != -1) {
                trueAnswer = answer;

            } else if (answer.equalsIgnoreCase("d") && potentialMoveD[0] != -1) {
                trueAnswer = answer;

            } else {

                System.out.println("You are a god damned fool, type something valid");

            }

        }

        return trueAnswer.toLowerCase();

    }


    public static String whichMoveToPerform(Scanner fetch, int[] potentialMoveA, int[] potentialMoveB,
                                            int[] potentialMoveC, int[] potentialMoveD) {

        System.out.println("these are your choices for moving, fool");

        String A = "A: " + prompt(potentialMoveA);
        String B = "B: " + prompt(potentialMoveB);
        String C = "C: " + prompt(potentialMoveC);
        String D = "D: " + prompt(potentialMoveD);

        if (potentialMoveA[0] != -1)
            System.out.println(A);

        if (potentialMoveB[0] != -1)
            System.out.println(B);

        if (potentialMoveC[0] != -1)
            System.out.println(C);

        if (potentialMoveD[0] != -1)
            System.out.println(D);

        System.out.println("E: you want none of these jawns");

        String trueAnswer = "";

        while (trueAnswer.equalsIgnoreCase("")) {

            String answer = fetch.nextLine();

            if (answer.equalsIgnoreCase("a") && potentialMoveA[0] != -1) {
                trueAnswer = answer;

            } else if (answer.equalsIgnoreCase("b") && potentialMoveB[0] != -1) {
                trueAnswer = answer;

            } else if (answer.equalsIgnoreCase("c") && potentialMoveC[0] != -1) {
                trueAnswer = answer;

            } else if (answer.equalsIgnoreCase("d") && potentialMoveD[0] != -1) {
                trueAnswer = answer;

            } else if (answer.equalsIgnoreCase("e")){
                trueAnswer = "e";

            } else {

                System.out.println("You are a god damned fool, type something valid");

            }

        }

        return trueAnswer.toLowerCase();

    }

    public static String prompt(int[] potentialMove) {

        String prompt = "";

        if (potentialMove[0] > -1 && potentialMove[2] == -1) {

            prompt = "you can move this piece to ( " + (char) (potentialMove[0] + 65) + " , " + (potentialMove[1] + 1) + " )";

        } else if (potentialMove[0] > -1 && potentialMove[2] == 1) {

            prompt = "CAPTURE ALERT AT ( " + (char) (potentialMove[0] + 65) + " , " + (potentialMove[1] + 1) + " )";

        }

        return prompt;

    }

    public static String killPrompt(int[] potentialMove) {

        String prompt = "";

        if (potentialMove[0] > -1) {

            prompt = "Dunk on  ( " + (char) (potentialMove[0] + 65) + " , " + (potentialMove[1] + 1) + " ) ?";

        }

        return prompt;

    }

    //will initialize the potential move array in a given direction on the board to a potential move, only if that move exists
    public static void potentialMoves(int[][] board, int[] chosenPiece,
                                      int[] potentialMove, int[] range, int[] direction) {

        boolean potentialMoveIsInArray = (chosenPiece[0] + direction[0] <= 7 && chosenPiece[0] + direction[0] >= 0
                && chosenPiece[1] + direction[1] <= 7 && chosenPiece[1] + direction[1] >= 0);

        boolean captureIsInArray = (chosenPiece[0] + (direction[0] * 2) >= 0 && chosenPiece[0] + (direction[0] * 2) <= 7
                && chosenPiece[1] + (direction[1] * 2) >= 0 && chosenPiece[1] + (direction[1] * 2) <= 7);

        Arrays.fill(potentialMove, -1);

        if (potentialMoveIsInArray && board[chosenPiece[0] + direction[0]][chosenPiece[1] + direction[1]] == 0) {

            potentialMove[0] = chosenPiece[0] + direction[0];
            potentialMove[1] = chosenPiece[1] + direction[1];

        } else if (captureIsInArray &&

                //this monstrosity of boolean bullshit checks if the piece we might capture is a teammate or not
                (board[chosenPiece[0] + direction[0]][chosenPiece[1] + direction[1]] < range[0] ||
                        board[chosenPiece[0] + direction[0]][chosenPiece[1] + direction[1]] > range[1])

                && (board[chosenPiece[0] + (direction[0] * 2)][chosenPiece[1] + (direction[1] * 2)] == 0)) {

            potentialMove[0] = chosenPiece[0] + (direction[0] * 2);
            potentialMove[1] = chosenPiece[1] + (direction[1] * 2);
            potentialMove[2] = potentialMove[2] + 2;

        }

    }


    public static void whichCheckerDoesUserWant(Scanner fetch, int[][] board, int[] chosenPiece, int[] range) {

        Arrays.fill(chosenPiece, -1);
        int checkerNum = -1;

        while ((checkerNum < range[0] || checkerNum > range[1]) || !(checkerExists(board, checkerNum))) {

            System.out.print("Which checker do you want to move bitch?: ");

            while (!fetch.hasNextInt()) {

                fetch.nextLine();
                System.out.print("you really are fucking STUPID arent you? choose a real number: ");

            }

            checkerNum = fetch.nextInt();
            fetch.nextLine();

        }

        for (int i = 0; i < board.length; i++) {

            for (int j = 0; j < board[i].length; j++) {

                if (checkerNum == board[i][j]) {

                    chosenPiece[0] = i;
                    chosenPiece[1] = j;

                }

            }

        }

    }

    public static void drawFrame(int[][] board, boolean[] areKings) {

        String nothing = " ";
        String wallThing = "|";
        String underScore = "_";

        System.out.printf("%4s %4s", nothing, nothing);

        for (int i = 1; i <= 8; i++) {

            String topRows = String.valueOf(i);

            System.out.printf("%4s", topRows);
            System.out.printf("%4s", nothing);

        }

        for (int i = 0; i < board.length; i++) {

            String leftColumn = String.valueOf((char) (65 + i));

            System.out.println();

            System.out.printf("%4s", nothing);

            for (int g = 1; g <= 17; g++) {

                System.out.printf("%4s", underScore);

            }

            System.out.printf("\n%4s", leftColumn);
            System.out.printf("%4s", wallThing);

            for (int j = 0; j < board[i].length; j++) {

                String foolChecker1 = "x" + board[i][j] + "x";
                String foolChecker2 = "o" + board[i][j] + "o";
                String gangsterChecker1 = "X" + board[i][j] + "X";
                String gangsterChecker2 = "O" + board[i][j] + "O";

                if (board[i][j] > 0) {

                    if (board[i][j] >= 1 && board[i][j] <= 12) {

                        if (areKings[(board[i][j] - 1)]) {

                            System.out.printf("%4s", gangsterChecker1);
                            System.out.printf("%4s", wallThing);

                        } else {

                            System.out.printf("%4s", foolChecker1);
                            System.out.printf("%4s", wallThing);

                        }

                    } else {

                        if (areKings[(board[i][j] - 1)]) {

                            System.out.printf("%4s", gangsterChecker2);
                            System.out.printf("%4s", wallThing);

                        } else {

                            System.out.printf("%4s", foolChecker2);
                            System.out.printf("%4s", wallThing);

                        }

                    }

                } else {

                    System.out.printf("%4s", nothing);
                    System.out.printf("%4s", wallThing);

                }

            }

        }

        System.out.println();

        System.out.printf("%4s", nothing);

        for (int g = 1; g <= 17; g++) {

            System.out.printf("%4s", underScore);

        }

        System.out.println("\n");

    }

    public static boolean checkerExists(int[][] board, int checkerNum) {

        for (int i = 0; i < board.length; i++) {

            for (int j = 0; j < board[i].length; j++) {

                if (board[i][j] == checkerNum) {

                    return true;

                }

            }

        }

        return false;

    }

    public static int howManyCheckersPerTeam(int[][] board, int[] range) {

        int count = 0;

        for (int i = 0; i < board.length; i++) {

            for (int j = 0; j < board[i].length; j++) {

                if (board[i][j] >= range[0] && board[i][j] <= range[1]) {

                    count++;

                }

            }

        }

        return count;

    }

    public static void description(Scanner fetch) {

        System.out.println();
        System.out.println("Ever since the dawn of time, man has thirsted for the latest and greatest in entertainment to fill the void");
        System.out.println("thanks to the genius programmer, Ethan Coles, Pure, Raw, Unadulterated Pleasure is at your fingertips");
        System.out.println("What you are about to experience is some serious intense ass shit");
        System.out.print("are you ready?: ");
        fetch.nextLine();

        System.out.println();
        System.out.println("risks using this program include");
        System.out.println("• Profuse Sweating");
        System.out.println("• Uncontrollable quivering");
        System.out.println("• Anal Polyps");
        System.out.println("• Mesothelioma");
        System.out.println("• 3rd degree stroke");
        System.out.println("• addiction");
        System.out.println("• unspeakable horror beyond your greatest imagination");
        System.out.print("that being said are you still down?: ");
        fetch.nextLine();

        System.out.println();
        System.out.println("Player 1 is X's and player 2 is O's, Kings pieces are capitalised");
        System.out.print("k, bitch?");
        fetch.nextLine();
    }

    public static void fillBoard(int[][] board) {

        board[0][1] = 1;
        board[0][3] = 2;
        board[0][5] = 3;
        board[0][7] = 4;

        board[1][0] = 5;
        board[1][2] = 6;
        board[1][4] = 7;
        board[1][6] = 8;

        board[2][1] = 9;
        board[2][3] = 10;
        board[2][5] = 11;
        board[2][7] = 12;

        board[5][0] = 13;
        board[5][2] = 14;
        board[5][4] = 15;
        board[5][6] = 16;

        board[6][1] = 17;
        board[6][3] = 18;
        board[6][5] = 19;
        board[6][7] = 20;

        board[7][0] = 21;
        board[7][2] = 22;
        board[7][4] = 23;
        board[7][6] = 24;

    }

    public static void fancyDecoration() {

        for (int i = 1; i < 40; i++) {

            if (i % 2 == 0) {

                System.out.print("*");

            } else {

                System.out.print("%");

            }

        }

        System.out.println();

    }

}
