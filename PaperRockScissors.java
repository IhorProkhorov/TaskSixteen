package game;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class PaperRockScissors {
    private User user;
    private Comp computer;
    private int playerScore;
    private int computerScore;
    private int countForTies;
    private static int roundsToPlay;
    private static int numberOfGames;

    public PaperRockScissors() {
        user = new User();
        computer = new Comp();
        playerScore = 0;
        computerScore = 0;
        numberOfGames = 0;
    }

    private enum Move{
        ROCK, PAPER, SCISSORS;

        public int compareMoves(Move otherMove) {
            if (this == otherMove)
                return 0;
            switch (this) {
                case ROCK:
                    return (otherMove == SCISSORS ? 1 : -1);
                case PAPER:
                    return (otherMove == ROCK ? 1 : -1);
                case SCISSORS:
                    return (otherMove == PAPER ? 1 : -1);
            }
            return 0;
        }
    }

    public void startGame() {

        Move userMove = user.getMove();
        Move compMove = computer.getMove();
        System.out.println("\nВаш ход  " + userMove + ".");
        System.out.println("Ход компьютера  " + compMove + ".\n");

        int compareMoves = userMove.compareMoves(compMove);
        switch (compareMoves) {
            case 0:
                countForTies++;
                break;
            case 1:
                playerScore++;
                break;
            case -1:
                computerScore++;
                break;
        }
        numberOfGames++;

        if ( user.playAgain() ) {
            System.out.println();
            startGame();
        } else {
            System.out.println("You stopped the game or rounds == games played");
            System.out.println("Watch stats of the game in file C:\\state.txt");
            writeStatsOfGameToFile();
        }
    }

    public void writeStatsOfGameToFile(){
        try {
            FileWriter fileWriter = new FileWriter("C:score.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print("The stats of the game\n");
            printWriter.printf("Player score is %s wins, computer score is %s wins and number of ties is %s\n", playerScore, computerScore,countForTies);
            printWriter.close();
        }catch (IOException ex){
            System.out.println("File not found");
        }
    }

    public static void main(String[] args)  {
        System.out.println("Enter a number of rounds:");
        Scanner scanner = new Scanner(System.in);
        roundsToPlay = Integer.parseInt(scanner.next());
        System.out.println("We will play " + roundsToPlay + " times if You will not stop the game");
        PaperRockScissors game = new PaperRockScissors();
        game.startGame();
    }

    private class User{
        private Scanner inputScanner;

        public User() {
            inputScanner = new Scanner(System.in);
        }

        public Move getMove(){
            System.out.println("Камень, ножницы или бумага? ");
            String userInput = inputScanner.nextLine();
            userInput = userInput.toUpperCase();
            char charFirst = userInput.charAt(0);

            if (charFirst == 'К' || charFirst == 'Н' || charFirst == 'Б') {
                switch (charFirst) {
                    case 'К':
                        return Move.ROCK;
                    case 'Н':
                        return Move.SCISSORS;
                    case 'Б':
                        return Move.PAPER;
                }
            }
            return getMove();
        }

        public boolean playAgain(){
            boolean allowToPlay = false;
            if (numberOfGames < roundsToPlay ){
                System.out.print("Хотите сыграть еще раз? ");
                String userInput = inputScanner.nextLine();
                userInput = userInput.toUpperCase();
                if (userInput.charAt(0) == 'Д'){
                    allowToPlay = true;
                }else
                    allowToPlay = false;
            }
            return allowToPlay ;
        }
    }

    private class Comp{
        public Move getMove(){
            Move[] moves = Move.values();
            Random random = new Random();
            int index = random.nextInt(moves.length);
            return moves[index];
        }
    }

}
