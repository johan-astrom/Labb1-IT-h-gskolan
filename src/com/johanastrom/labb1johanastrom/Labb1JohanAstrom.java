package com.johanastrom.labb1johanastrom;

import java.util.Arrays;
import java.util.Scanner;

public class Labb1JohanAstrom {

    //Tre arrayer för antal spelare, för omgångens resultat och för poängställning.
    static String[] players;
    static int[] roundScore;
    static int[] score;

    static Scanner scan1 = new Scanner(System.in);

    static int noOfThrows = 0;
    static int playerIndex = 0;
    static String playComputer = "";
    static boolean isRunning = true;

    public static void main(String[] args) {

        intro();

        while (isRunning) {
            System.out.println("Tryck \"S\" för att spela, tryck \"R\" för att rensa poängställningen, " +
                    "tryck \"X\" för att avsluta och visa resultat.");
            String menuOption = scan1.nextLine().toUpperCase();

            switch (menuOption) {
                case "S":
                    playDice();
                    break;

                case "R":
                    Arrays.fill(score, 0);
                    break;

                case "X":
                    isRunning = false;
                    endGame();
                    break;

                default:
                    System.out.println("Ogiltigt menyval.");
            }

        }

    }

    //Metod som körs när programmet startas. Användaren gör sina val.
    static void intro() {
        System.out.println("Välkommen till tärningsspelet! Välj antal spelare: ");
        int noOfPlayers = inputInt();

        //Alternativ för att spela mot datorn
        while (!playComputer.equals("Y") && !playComputer.equals("N")) {
            System.out.println("Spela mot datorn? \"Y\" / \"N\" ");
            playComputer = scan1.nextLine().toUpperCase();
            if (playComputer.equals("Y"))
                playerIndex = noOfPlayers + 2;
            else if (playComputer.equals("N"))
                playerIndex = noOfPlayers + 1;
            else
                System.out.println("Felaktig inmatning.");
        }

        players = new String[playerIndex];
        roundScore = new int[playerIndex];
        score = new int[playerIndex];

        System.out.println("Välj namn på spelarna.");
        //Anger namn på spelarna om datorn är en motspelare
        if (playerIndex == (noOfPlayers + 2)) {
            for (int i = 1; i < players.length - 1; i++) {
                System.out.print("Spelare " + i + ": ");
                players[i] = scan1.nextLine();
            }
            players[players.length - 1] = "Datorn";
        }
        //Anger namn på spelarna om datorn ej är en motspelare.
        else {
            for (int i = 1; i < players.length; i++) {
                System.out.print("Spelare " + i + ": ");
                players[i] = scan1.nextLine();


            }
        }

        System.out.print("Välj hur många tärningskast varje spelare får: ");
        noOfThrows = inputInt();
        System.out.println();
    }


    //Metod som innehåller själva spelmomentet.
    static void playDice() {

        for (int i = 1; i < players.length; i++) {
            if (!players[i].equals("Datorn")) {
                if (players[i].charAt(players[i].length() - 1) == ('s')) {
                    System.out.println(players[i] + " tur! Tryck enter för att kasta.");
                    scan1.nextLine();
                } else {
                    System.out.println(players[i] + "s tur! Tryck enter för att kasta.");
                    scan1.nextLine();
                }
            }
            for (int j = 1; j <= noOfThrows; j++) {
                int diceRoll = (int) (1 + (Math.random() * 6));
                System.out.println("Kast " + j + ": " + players[i] + " fick: " + diceRoll);
                roundScore[i] += diceRoll;
                score[i] += diceRoll;
            }
            System.out.println("\nTotal poäng för " + players[i] + ": " + roundScore[i] + "\n");
        }
        if (checkDuplicatesOfLargestElement(roundScore))
            System.out.println("Omgången blev oavgjord!");
        else
            System.out.println("***   ***   ***   ***\n\nVinnaren denna omgång är: " + players[indexOfLargestElement(roundScore)] + "!");
        System.out.println("\nPoängställning:\n");
        for (int i = 1; i < players.length; i++) {
            System.out.println(players[i] + ": " + score[i] + " poäng.");
            roundScore[i] = 0;
        }
        System.out.println("\n***   ***   ***   ***\n");
    }

    //Metod som körs när spelet avslutas.
    static void endGame() {
        sortTwoArrays(score, players);
        for (int i = 1; i < players.length; i++)
            System.out.println("Plats " + i + ": " + players[i] + " med " + score[i] + " poäng.\n");

        if (checkDuplicatesOfLargestElement(score)) {
            System.out.println("***   ***   ***   ***\n");
            System.out.println("Spelet blev oavgjort!");
            System.out.println("\n***   ***   ***   ***");
        } else {
            System.out.println("***   ***   ***   ***\n");
            System.out.println("Vinnaren är: " + players[indexOfLargestElement(score)] + "!");
            System.out.println("\n***   ***   ***   ***");
        }
        System.out.println("\nTack för att du spelade!");
    }

    //Metod för inmatning av heltal (rensar cachen med en nextLine-metod).
    static int inputInt() {
        int number = scan1.nextInt();
        scan1.nextLine();
        return number;

    }


    //Metod för att hitta indextalet för det största elementet i en array.
    static int indexOfLargestElement(int[] array) {
        int largest = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] > array[largest])
                largest = i;
        }
        return largest;
    }

    /*Metod för att undersöka ifall det största elementet i en array förekommer flera gånger
    (och resultatet således blir "oavgjort").
     */
    static boolean checkDuplicatesOfLargestElement(int[] array) {
        int largest = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] > array[largest])
                largest = i;
        }
        for (int i = 0; i < array.length; i++) {
            if (i != largest) {
                if (array[i] == array[largest])
                    return true;
            }
        }
        return false;
    }

    //Specifik metod för att kunna sortera "score" och "players" likadant.
    static void sortTwoArrays(int[] array1, String[] array2) {
        int temp;
        String temp1;

        for (int i = array1.length - 1; i > 0; i--) {
            for (int j = 1; j < i; j++) {
                if (array1[j] < array1[j + 1]) {
                    temp = array1[j];
                    array1[j] = array1[j + 1];
                    array1[j + 1] = temp;
                    temp1 = array2[j];
                    array2[j] = array2[j + 1];
                    array2[j + 1] = temp1;
                }
            }
        }

    }
}



