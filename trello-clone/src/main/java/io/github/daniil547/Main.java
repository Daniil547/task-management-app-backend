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
        cardService.loadAllCards();


        System.out.println("Tell me your first name, mea...");
        Thread.sleep(700);
        System.out.println("...human.");
        String firstName = in.next();
        System.out.println("And your last name");
        String lastName = in.next();

        String nickname = Arrays.stream(new String[]{firstName, lastName})
                                .map(x -> x.substring(0, 3).toLowerCase())
                                .collect(Collectors.joining());
        System.out.println("I will call you " + nickname + " for short");
        System.out.println();

        User currentUser = new UserService().create(nickname, firstName, lastName);
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
        System.out.println("3) edit a card");
        System.out.println("4) delete a card"); //a soft delete, in fact
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
                System.out.println();
                printCards();
                System.out.println();
            }
            case "3" -> {
                System.out.println();
                performEdit(getCardPos("edit"));
                System.out.println("If you didn't see an error, than edit was successful");
            }
            case "4" -> {
                System.out.println();
                performArchive(getCardPos("delete"));
                System.out.println("If you didn't see an error, than archiving was successful");
            }
            case "x" -> {
                System.out.println();
                System.out.println("System has been corrupte");
                System.exit(7734);
            }
            default -> {
                System.out.println();
                System.out.println("I guess you meant something else");
                System.out.println();
                return;
            }
        }

    }

    private static void performEdit(int cardPos) {
        System.out.println("What would you like to edit? (T)itle/(D)escription");
        switch (in.next()) {
            case "t", "T" -> {
                System.out.println("Enter new title for the card");
                in.nextLine();
                String newTitle = in.nextLine();
                cardService.editTitle(cardPos, newTitle);
            }
            case "d", "D" -> {
                System.out.println("Enter new description for the card");
                in.nextLine();
                String newDescr = in.nextLine();
                cardService.editDescription(cardPos, newDescr);
            }
            default -> {
                System.out.println("I guess you meant something else");
                performEdit(cardPos);
            }
        }
    }

    private static void performArchive(int cardPos) {
        cardService.archiveCard(cardPos);
        System.out.println();
    }

    private static int getCardPos(String forAction) {
        System.out.println("Which card you would like to " + forAction + "? (enter its number)");
        int cardPos = in.nextInt();
        if (cardPos >= 1 && cardPos <= cardService.cardsCount()) {
            return cardPos - 1;
        } else {
            System.out.println("Incorrect number");
            getCardPos(forAction);
        }
        return -1; //unreachable
    }

    private static void addCard() {
        System.out.println("A card, huh? You could've chosen better, human");
        System.out.println("What do you want to name your card?");
        in.nextLine(); //stub
        String cardTitle = in.nextLine();
        System.out.println("Enter a description for your shiny new card");
        String cardDescr = in.nextLine();
        cardService.create(currentMember, cardTitle, cardDescr);
    }

    private static void printCards() {
        System.out.println("You want to see your cards, huh? You could've chosen better, human");
        System.out.println("Here they are: ");
        cardService.printAllCards();
    }
}
