package io.github.daniil547;

import io.github.daniil547.domain.Member;
import io.github.daniil547.domain.Role;
import io.github.daniil547.domain.User;
import io.github.daniil547.services.CardService;
import io.github.daniil547.services.UserService;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    static final Scanner in = new Scanner(System.in);
    static Member currentMember;
    static CardService cardService = new CardService();

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Tell me your full name, mea...");
        Thread.sleep(700);
        System.out.println("...human.");
        String fullName = in.nextLine();
        String nickname = Arrays.stream(fullName.split("\\s+"))
                                .map(x -> x.substring(0, 3).toLowerCase())
                                .collect(Collectors.joining());
        System.out.println("I will call you " + nickname + " for short");
        System.out.println();

        User currentUser = new UserService().create(nickname, fullName);
        currentMember = new Member(currentUser);
        currentMember.setRole(Role.ADMIN);

        Thread.sleep(700);

        System.out.println("I gave you admin rights, human. Rejoice.");
        System.out.println("Now, how do you wish to use your unlimited power?");
        Thread.sleep(700);
        System.out.println();
        while (true) {
            askForAction();
        }

    }

    public static void askForAction() {
        System.out.println("Available actions:");
        System.out.println("1) create new card");
        System.out.println("2) print all cards");
        System.out.println("x - should you get bored");
        String option = in.next();
        switch (option) {
            case "1" -> {
                System.out.println();
                addCard();
                System.out.println("Card has been created. \nMy creator didn't ensure proper error handling but I guess it was.");
                System.out.println();
            }
            case "2" -> {
                printCards();
                System.out.println();
            }
            case "x" -> {
                System.out.println();
                System.out.println("System has been corrupte");
                System.exit(7734);
            }
            default -> {
                System.out.println("I guess you meant something else");
                System.out.println();
                askForAction();
            }
        }

    }

    private static void addCard() {
        System.out.println("A card, huh? You could've chosen better, human");
        System.out.println("What do you want to name your new card?");
        String cardTitle = in.next();
        cardService.create(currentMember, cardTitle);
    }

    private static void printCards() {
        System.out.println("You want to see your cards, huh? You could've chosen better, human");
        System.out.println("Here they are: ");
        cardService.printAllCards();
    }
}
