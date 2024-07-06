package midAct;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static midAct.DriverManagers.getConnection;

public class Controller {
    private static String currentUserEmail;
    private static Scanner kbd = new Scanner(System.in);
    /**
     * Sets the email of the current user.
     *
     * @param email The email of the current user.
     */
    public static void setCurrentUserEmail(String email) {
        currentUserEmail = email;
    }
    /**
     * Displays the introduction message for the Watch Gig application.
     */
    public static void showIntroduction(){
        System.out.println("Welcome to Watch Gig!");
    }
    /**
     * Provides options for user login or registration.
     *
     * @throws SQLException If a SQL exception occurs.
     */
    public static void choiceLogInAndReg() throws SQLException {
        final int MAX_CHOICE = 3;
        int choice = 0;
        boolean isValidChoice = false;
        try {
            while (!isValidChoice) {
                System.out.print("Choice: ");
                String input = kbd.nextLine().trim();
                if (!input.isEmpty() && input.matches("\\d+")) {
                    choice = Integer.parseInt(input);
                    if (choice >= 1 && choice <= MAX_CHOICE) {
                        isValidChoice = true;
                    } else {
                        System.out.println("Invalid choice. Please enter a number between 1 and " + MAX_CHOICE + ".");
                    }
                } else {
                    System.out.println("Invalid choice. Please enter a number.");
                }
            }
            switch (choice) {
                case 1 -> logIn();
                case 2 -> register();
                case 3 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Inputs should only be number format! please try again");
        }
    }
    /**
     * Provides options for admin actions.
     *
     * @throws SQLException If a SQL exception occurs.
     */
    public static void choiceAdmin() throws SQLException {
        try {
            System.out.print("Choice: ");
            int choice = Integer.parseInt(kbd.nextLine());
            switch (choice) {
                case 1 -> {
                    menuManageAudience();
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 2 -> {
                    menuManagePerformers();
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 3 -> {
                    menuManageSchedules();
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 4 -> {
                   showPurchases();
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 5 -> {
                    System.out.println("Logging out...");
                    LogInOrReg();
                }
                default -> {
                    System.out.println("The input should only range 1 - 5! Please try again");
                }
            }
        }catch (NumberFormatException e){
            System.out.println("Inputs should only be number format! please try again");
        }
    }
    /**
     * Provides options for customer actions.
     *
     * @throws SQLException If a SQL exception occurs.
     */
    public static void choiceCustomer() throws SQLException{
        System.out.print("Choice: ");
        int choice = Integer.parseInt(kbd.nextLine());
        try {
            switch (choice) {
                case 1 -> {
                    viewGigs();
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 2 -> {
                    purchaseTicket();
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 3 -> {
                    watchGig();
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 4 -> {
                    System.out.println("Logging out...");
                    LogInOrReg();
                }
                default -> {
                    System.out.println("The input should only range 1 - 4! Please try again");
                }
            }
        }catch (NumberFormatException e){
            System.out.println("Inputs should only be number format! please try again");
        }
    }
    /**
     * Provides options for managing schedules.
     *
     * @throws SQLException If a SQL exception occurs.
     */
    public static void choiceManageSchedule() throws SQLException{
        try {
            System.out.print("Choice: ");
            int choice = Integer.parseInt(kbd.nextLine());
            switch (choice) {
                case 1 -> {
                    showListsOfAvailableShows();
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 2 -> {
                    showListsOfPastShows();
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 3 -> {
                    addShow();
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 4 -> {
                    editPrice();
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 5 ->{
                    editDateAndTime();
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 6 -> {
                    removeShow();
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 7 -> {
                    menuAdmin();
                }

                default -> {
                    System.out.println("The input should only range 1 - 7! Please try again");
                }
            }
        }catch (NumberFormatException e){
            System.out.println("Inputs should only be number format! please try again");
        }
    }
    /**
     * Provides options for managing performers.
     *
     * @throws SQLException If a SQL exception occurs.
     */
    public static void choiceManagePerformers() throws SQLException {
        try {
            System.out.print("Choice: ");
            int choice = Integer.parseInt(kbd.nextLine());
            switch (choice) {
                case 1 -> {
                    showListsOfPerformers();
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 2 -> {
                    System.out.print("Which performer do you want to search?");
                    String performer = kbd.nextLine();
                    searchPerformer(performer);
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 3 -> {
                    addPerformer();
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 4 -> {
                    removePerformer();
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 5 -> {
                    menuAdmin();
                }
                default -> {
                    System.out.println("The input should only range 1 - 5! Please try again");
                }
            }
        }catch (NumberFormatException e){
            System.out.println("Inputs should only be number format! please try again");
        }
    }
    /**
     * Provides options for managing audience.
     *
     * @throws SQLException If a SQL exception occurs.
     */
    public static void choiceManageAudience() throws SQLException {
        try {
            System.out.print("Choice: ");
            int choice = Integer.parseInt(kbd.nextLine());
            switch (choice) {
                case 1 -> {
                    showListsOfUsers();
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 2 -> {
                    System.out.print("Which audience do you want to search?");
                    String audience = kbd.nextLine();
                    showListsOfAudience(audience);
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 3 -> {
                    addAudience();
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 4 -> {
                    removeAudience();
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 5 -> {
                    assignAdmin();
                    System.out.println("Press enter to continue.");
                    kbd.nextLine();
                }
                case 6 -> {
                    menuAdmin();
                }
                default -> {
                    System.out.println("The input should only range 1 - 6! Please try again");
                }
            }
        }catch (NumberFormatException e){
            System.out.println("Inputs should only be number format! please try again");
        }
    }
    /**
     * Removes an audience member from the database.
     */
    private static void removeAudience() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=======================Set Audience to Inactive=======================");
        System.out.print("Enter audience ID: ");
        int audId = sc.nextInt();
        sc.nextLine();

        if (!DriverManagers.checkAudienceExistence(audId)) {
            System.out.println("No data found for audience ID: " + audId);
            return;
        }

        if (DriverManagers.isAudienceInactive(audId)) {
            DriverManagers.deleteAudience(audId);
            System.out.println("Audience id: " + audId + " is now set to inactive!");
        }
    }
    /**
     * Adds a new audience member to the database.
     */
    private static void addAudience() {
        List<audience> audienceList = DriverManagers.showAudienceTable();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter first name: ");
        String fName = sc.nextLine();
        System.out.print("Enter last name: ");
        String lName = sc.nextLine();
        boolean emailAvailable = true;
        String email;
        do {
            System.out.print("Enter email: ");
            email = sc.nextLine();

            for (audience audience : audienceList) {
                if (audience.getEmail().equals(email)) {
                    System.out.println("Account is already registered.");
                    emailAvailable = false;
                }
            }
        } while (!emailAvailable || email.isEmpty());
        String password, cPassword;
        do {
            System.out.print("Enter password: ");
            password = sc.nextLine();
            System.out.print("confirm password: ");
            cPassword = sc.nextLine();

            if (password.equals(cPassword)) {
                System.out.println("Please verify the following information. ");
                System.out.println("First Name: " + fName);
                System.out.println("Last Name: " + lName);
                System.out.println("Email: " + email);
                System.out.println("Password: " +password);
                System.out.println("Enter \"confirm\" to proceed. ");
                String confirm = sc.nextLine();

                if (confirm.equals("confirm")) {
                    DriverManagers.addAudience(email, password, fName, lName, "Client");
                } else {
                    System.out.print("Enter \"restart\" to restart process. ");
                    String restart = sc.nextLine();

                    if (restart.equals("restart")) {
                        addAudience();
                    }
                }
            } else {
                System.out.println("Passwords do not match! Try again.");
            }
        } while (!password.equals(cPassword));
    }
    /**
     * Assigns an admin account to an audience member.
     */
    private static void assignAdmin() {
        Scanner sc = new Scanner(System.in);
        System.out.println("==================================Assign an admin account==================================");
        System.out.print("Enter first name: ");
        String fName = sc.nextLine();
        System.out.print("Enter last name: ");
        String lName = sc.nextLine();

        if (DriverManagers.isAdmin(fName, lName)) {
            System.out.println("The account is already an admin.");
            return;
        }

        System.out.println("Enter \"confirm\" to proceed. ");
        String confirm = sc.nextLine();

        if (confirm.equals("confirm")) {
            DriverManagers.assignAdminAccount(fName, lName);
        } else {
            System.out.print("Enter \"restart\" to restart process. ");
            String restart = sc.nextLine();

            if (restart.equals("restart")) {
                assignAdmin();
            }
        }
    }
    /**
     * Removes a performer from the database.
     *
     * @throws SQLException If a SQL exception occurs.
     */
    private static void removePerformer() throws SQLException {
        List<performers> performersList = DriverManagers.showPerformers();
        Scanner sc = new Scanner(System.in);
        boolean performerFound = false;
        int aID;
        String performerName;
        do {
            System.out.print("Enter performer name: ");
            performerName = sc.nextLine();

            for (performers performer : performersList) {
                if (performer.getName().equalsIgnoreCase(performerName)) {
                    System.out.println("Performer found.");
                    performerFound = true;
                    aID = performer.getPerformID();
                    System.out.print("Enter \"delete\" to delete performer. ");
                    String delete = sc.nextLine();
                    if(delete.equals("delete")){
                        Boolean ifDeleted = DriverManagers.deletePerformer(aID);
                        if(ifDeleted) {
                            System.out.println("Successfully deleted the performer!");
                            System.out.println("Press enter to continue.");
                            kbd.nextLine();
                            menuManagePerformers();
                        }  else {
                            System.out.println("Cannot delete performer which already have a show that have a customers who already bought a ticket");
                            System.out.println("Press enter to continue.");
                            kbd.nextLine();
                            menuManagePerformers();
                        }
                    } else {
                        System.out.print("Enter \"cancel\" to cancel delete process. ");
                        String cancel = sc.nextLine();

                        if (cancel.equals("cancel")){
                            System.out.println("Returning to menu...");
                            menuManagePerformers();
                        }
                    }
                }
            }
            System.out.println("Performer not found! ");
            while (true) {
                System.out.print("Do you want to try again?[y/n]");
                String choice = kbd.nextLine();
                char c = choice.charAt(0);
                while (true) {
                    if (c == 'y' || c == 'Y') {
                        break;
                    } else if (c == 'n' || c == 'N') {
                        return;
                    } else {
                        System.out.println("Inputs should only be y or n!");
                    }
                }
            }
        } while (true);
    }
    /**
     * Adds a new performer to the database.
     */
    private static void addPerformer() {
        List<performers> performersList = DriverManagers.showPerformers();
        Scanner sc = new Scanner(System.in);
        boolean nameAvailable = true;
        String name, performerType;
        do {
            System.out.print("Enter performer name: ");
            name = sc.nextLine();

            for (performers performer : performersList) {
                if (performer.getName().equalsIgnoreCase(name)) {
                    System.out.println("Performer is already registered.");
                    nameAvailable = false;
                }
            }
        } while (!nameAvailable || name.isEmpty());

        do{
            System.out.print("Enter performer type: ");
            performerType = sc.nextLine();
            if (!performerType.equalsIgnoreCase("solo") && !performerType.equalsIgnoreCase("band")){
                System.out.println("Invalid entry! Please enter if solo or band.");
            }
        } while (!performerType.equalsIgnoreCase("solo") && !performerType.equalsIgnoreCase("band"));
        DriverManagers.addPerformer(name, performerType);
    }
    /**
     * Displays the login or registration menu.
     *
     * @throws SQLException If a SQL exception occurs.
     */
    public static void LogInOrReg() throws SQLException {
        while (true) {
            System.out.println("");
            System.out.println("[1]. Log in");
            System.out.println("[2]. Register");
            System.out.println("[3]. Exit");
            choiceLogInAndReg();
        }
    }
    /**
     * Displays the admin menu.
     *
     * @throws SQLException If a SQL exception occurs.
     */
    public static void menuAdmin() throws SQLException {
        while (true) {
            System.out.println("[1]. Manage Audience");
            System.out.println("[2]. Manage Performers");
            System.out.println("[3]. Manage Schedule");
            System.out.println("[4]. View Purchases ");
            System.out.println("[5]. Log out");
            choiceAdmin();
        }
    }
    /**
     * Displays the customer menu.
     *
     * @throws SQLException If a SQL exception occurs.
     */
    public static void menuClient() throws SQLException {
        while (true) {
            System.out.println("[1]. View Upcoming Gigs");
            System.out.println("[2]. Purchase Tickets");
            System.out.println("[3]. Watch Gig");
            System.out.println("[4]. Log out");
            choiceCustomer();
        }
    }
    /**
     * Displays the performer management menu.
     *
     * @throws SQLException If a SQL exception occurs.
     */
    public static void menuManagePerformers() throws SQLException {
        while(true){
            // showListsOfPerformers();
            System.out.println("[1]  Check list of performers");
            System.out.println("[2]. Search a performer");
            System.out.println("[3]. Add A Performer");
            System.out.println("[4]. Remove A Performer");
            System.out.println("[5]. Return To Main Menu");
            choiceManagePerformers();
        }
    }
    /**
     * Displays the audience management menu.
     *
     * @throws SQLException If a SQL exception occurs.
     */
    public static void menuManageAudience() throws SQLException {
        while(true){
            System.out.println("[1]  Check list of audience");
            System.out.println("[2]. Search specific audience");
            System.out.println("[3]. Add A User");
            System.out.println("[4]. Set User into an inactive account");
            System.out.println("[5]. Assign an Admin account");
            System.out.println("[6]. Return To Main Menu");
            choiceManageAudience();
        }
    }
    /**
     * Displays the schedule management menu.
     *
     * @throws SQLException If a SQL exception occurs.
     */
    public static void menuManageSchedules() throws SQLException {
        while (true) {
            System.out.println("[1]. View Upcoming Schedules");
            System.out.println("[2]. View Past Schedules");
            System.out.println("[3]. Add a show");
            System.out.println("[4]. Update price of a show");
            System.out.println("[5]. Update scheduled date of a show");
            System.out.println("[6]. Remove a show");
            System.out.println("[7]. Return to Main Menu");
            choiceManageSchedule();
        }
    }
    /**
     * Authenticates a user login.
     *
     * @return True if login is successful, otherwise false.
     * @throws SQLException If a SQL exception occurs.
     */
    public static boolean logIn() throws SQLException {
        String email = "";
        String password = "";
        String accountType = "";
        String fullName = "";
        Boolean result = false;

        while (true) {
            System.out.print("What is your email?: ");
            email = kbd.nextLine();
            setCurrentUserEmail(email); // Debug only

            if (!DriverManagers.checkEmailExistence(email)) {
                System.out.println("Invalid email! Please try again.");
                continue; // Prompt again for email
            }
            break; // Break the loop if a valid email is entered
        }
        while (true) {
            System.out.print("What is your password?: ");
            password = kbd.nextLine();

            if (!DriverManagers.checkPasswordExistence(email, password)) {
                System.out.println("Invalid password! Please enter another");
                continue;
            }
            break;
        }

        String logInResult = DriverManagers.logInToGIG(email,password);

        if (logInResult != null) {
            String[] parts = logInResult.split("\\|");
            accountType = parts[0];
            fullName = parts[1];

            System.out.println("Successfully logged in as " + fullName + "! (" + accountType + ")");
            if (accountType.equals("Client")) {
                menuClient();
            } else if(accountType.equals("Admin")){
                menuAdmin();
            }else {
                System.out.println("The account is inactive!");
            }
            result = true;
        } else {
            System.out.println("Invalid password! Please check your password.");
        }
        return result;
    }
    /**
     * Registers a new user with the provided email, password, first name, and last name.
     * Validates the email format and password length.
     */
    public static void register(){
        String email = "";
        String password = "";
        String cPassword = "";
        String fName = "";
        String lName = "";
        String accountType = "Client";
        Boolean result = false;
        do {
            System.out.print("Enter your email?: ");
            email = kbd.nextLine();
            if (!email.matches(".+@.+\\..+")){
                System.out.println("Please insert a valid email! Please try again.");
            }
        }while (!email.matches(".+@.+\\..+"));
        do {
            while (password.length() < 6) {
                System.out.print("Enter your password?: ");
                password = kbd.nextLine();
                if (password.length() < 6){
                    System.out.println("The password should be at least 6 characters long");
                }
            }
            System.out.print("Re-enter your password?: ");
            cPassword = kbd.nextLine();
            if(!cPassword.equals(password)){
                System.out.println("Password doesn't match! please try again.");
            }
        }while (!cPassword.equals(password));
        System.out.print("Enter your first name: ");
        fName = kbd.nextLine();
        System.out.print("Enter your last name: ");
        lName = kbd.nextLine();
        audience a = new audience(email,password, fName, lName, accountType);
        boolean validate =  DriverManagers.RegisterToGig(a);
        if (!validate){
            return;
        }
        int audienceID = 0;
        ArrayList<audience> audienceList = DriverManagers.showAudienceTable();
        for (audience audience : audienceList) {
            if (audience.getEmail().equals(email)) {
                audienceID = audience.getAudienceID();
                break;
            }
        }
        DriverManagers.insertLoyaltyCard(audienceID);
    }
    /**
     * Displays options for viewing gigs, such as viewing all scheduled gigs or searching
     * for gigs by a specific performer.
     *
     * @throws SQLException if a database access error occurs
     */
    public static void viewGigs() throws SQLException {
        while (true) {
            System.out.println("[1]. View All Schedule");
            System.out.println("[2]. Specific performer");
            System.out.println("[3]. Back");
            System.out.print("Choice: ");
            int choice = Integer.parseInt(kbd.nextLine());
            try {
                switch (choice) {
                    case 1 -> {
                        showListsOfAvailableShows();
                        System.out.println("Press enter to continue.");
                        kbd.nextLine();
                    }
                    case 2 -> {
                        System.out.print("Which performer do you want to search?");
                        String performer = kbd.nextLine();
                        showListsOfPerformerShow(performer);
                        System.out.println("Press enter to continue.");
                        kbd.nextLine();
                    }
                    case 3 -> {
                        menuClient();
                    }
                    default -> {
                        System.out.println("The input should only range 1 - 3! Please try again.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Inputs should only be number format! please try again.");
            }
        }
    }
    /**
     * Displays options for purchasing tickets, viewing purchase history, and viewing loyalty points.
     *
     * @throws SQLException if a database access error occurs
     */
    public static void purchaseTicket() throws SQLException {
        while (true) {
            System.out.println("[1]. Buy Tickets");
            System.out.println("[2]. View Purchased History");
            System.out.println("[3]. View Loyalty Card");
            System.out.println("[4]. Back to Main Menu");
            System.out.print("Choice: ");
            int choice = Integer.parseInt(kbd.nextLine());
            try {
                switch (choice) {
                    case 1 -> {
                        makePurchase();
                        System.out.println("Press enter to continue.");
                        kbd.nextLine();
                    }
                    case 2 -> {
                        showUserPurchaseHistory();
                        System.out.println("Press enter to continue.");
                        kbd.nextLine();
                    }
                    case 3 -> {
                        showLoyaltyPoints();
                        System.out.println("Press enter to continue.");
                        kbd.nextLine();
                    }
                    case 4 -> {
                        menuClient();
                    }

                    default -> {
                        System.out.println("The input should only range 1 - 4! Please try again");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Inputs should only be number format! please try aain");
            }
        }
    }
    /**
     * Shows a list of past shows to the user.
     */
    public static void showListsOfPastShows(){
        ArrayList<schedule>listShows = DriverManagers.listsOfPastShows();
        System.out.printf("%-10s %-25s  %-15s %-15s \n", "schedID" ,"performer name", "price","Date", "start Time","end Time");
        System.out.println("================================================================================================");
        int i = 0;
        for (schedule s : listShows) {
            System.out.printf("%-10s %-25s  %-15s %-15s  %-15s  \n", s.getSchedID(), s.getName(), s.getPrice(),s.getDate(), s.getStartTime(), s.getEndTime());
            i++;
            if (i % 10== 0) {
                System.out.println("Do you want to show more shows?[y/n]");
                String choice = kbd.nextLine();
                char c = choice.charAt(0);
                if (c != 'y' && c != 'Y') {
                    break;
                }
            }
        }
    }
    /**
     * Shows a list of available shows to the user.
     */
    public static void showListsOfAvailableShows() {
        ArrayList<schedule> listShows = DriverManagers.listsOfShows();
        System.out.printf("%-10s %-25s %-15s %-15s %-15s \n", "Sched ID", "Performer Name", "Price", "Date", "Start Time", "End Time");
        System.out.println("===============================================================================");
        int i = 0;
        for (schedule s : listShows) {
            System.out.printf("%-10s %-25s %-15s %-15s %-15s  \n", s.getSchedID(), s.getName(), s.getPrice(), s.getDate(), s.getStartTime(), s.getEndTime());
            i++;
            if (i % 10 == 0) {
                while (true) {
                    System.out.println("Do you want to show more shows?[y/n]");
                    String choice = kbd.nextLine().toLowerCase();
                    char c = choice.charAt(0);
                    if (c == 'n') {
                        return; // Exit the method if input is 'n'
                    } else if (c == 'y') {
                        break; // Break the loop if input is 'y'
                    } else {
                        System.out.println("Invalid input! Please enter 'y' or 'n'.");
                    }
                }
            }
        }
    }
    /**
     * Shows a list of shows by a specific performer to the user.
     *
     * @param performer the name of the performer
     */
    public static void showListsOfPerformerShow(String performer){
        ArrayList<schedule>listShows = DriverManagers.listsOfShowsBasedOnPerformers(performer);
        System.out.printf("%-10s %-25s %-15s %-15s %-15s %-15s \n", "Sched ID" ,"Performer Name", "Price", "Date", "Start Time", "End Time");
        System.out.println("=============================================================================================");
        int i = 0;
        for (schedule s : listShows) {
            System.out.printf("%-10s %-25s %-15s %-15s %-15s %-15s \n", s.getSchedID(), s.getName(), s.getPrice(),s.getDate(), s.getStartTime(), s.getEndTime());
            i++;
            if (i % 10== 0) {
                while (true) {
                    System.out.println("Do you want to show more shows?[y/n]");
                    String choice = kbd.nextLine().toLowerCase();
                    char c = choice.charAt(0);
                    if (c == 'n') {
                        return; // Exit the method if input is 'n'
                    } else if (c == 'y') {
                        break; // Break the loop if input is 'y'
                    } else {
                        System.out.println("Invalid input! Please enter 'y' or 'n'.");
                    }
                }
            }
        }
    }
    /**
     * Searches for performers based on the provided performer name.
     *
     * @param performer the name of the performer to search for
     */
    public static void searchPerformer(String performer){
        ArrayList<performers>listPerformer = DriverManagers.listsOfPerformers(performer);
        System.out.printf("%-15s %-25s %-15s   \n", "performer Id" ,"Performer Name", "Performer type");
        System.out.println("================================================================================================");
        int i = 0;
        for (performers p : listPerformer) {
            System.out.printf("%-15s %-25s %-15s  \n",p.getPerformID(),p.getName(),p.getPerformerType());
            i++;
            if (i % 10== 0) {
                System.out.print("Do you want to show more shows?[y/n]");
                String choice = kbd.nextLine();
                char c = choice.charAt(0);
                if (c == 'n') {
                    return; // Exit the method if input is 'n'
                } else if (c == 'y') {
                    break; // Break the loop if input is 'y'
                } else {
                    System.out.println("Invalid input! Please enter 'y' or 'n'.");
                }
            }
        }
    }
    /**
     * Shows a list of users registered in the system.
     */
    public static void showListsOfUsers(){
        ArrayList<audience> listAudience = DriverManagers.showAudienceTable();
        System.out.printf("%-10s %-15s %-15s %-25s %-25s \n", "ID" ,"First Name" , "Last Name", "Email", "Account Type");
        System.out.println("====================================================================================");
        for(audience a: listAudience){
            if(listAudience.isEmpty()){
                System.out.println("No users found");
            }
            System.out.printf("%-10s %-15s %-15s %-25s %-25s \n", a.getAudienceID(),a.getFirstName(),a.getLastName(),a.getEmail(), a.getAccountType());
        }
    }
    /**
     * Processes the purchase of tickets by the user.
     *
     * @throws SQLException if a database access error occurs
     */
    public static void makePurchase() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        ArrayList<audience> audienceList = DriverManagers.showAudienceTable();
        System.out.print("Choose Performer: ");
        String performer = scanner.next();
        int audienceID = 0;
        for (audience audience : audienceList) {
            if (audience.getEmail().equals(currentUserEmail)) {
                audienceID = audience.getAudienceID();
                break;
            }
        }

        if (performer == null) {
            System.out.println("Performer not found. Please try again.");
            return;
        }

        int selectedScheduleID;
        ArrayList<schedule> performerShows = DriverManagers.listsOfShowsBasedOnPerformers(performer);
        if (performerShows.isEmpty()) {
            System.out.println("No shows available for the selected performer.");
            return;
        }
        while (true) {
            showListsOfPerformerShow(performer);
            while (true) {
                System.out.print("Do you want to buy a ticket?[y/n]");
                String choice1 = kbd.nextLine();
                char cc = choice1.charAt(0);
                if (cc == 'y' || cc == 'Y') {
                    break; // Break the loop if input is 'y'
                } else if (cc == 'n' || cc == 'N') {
                    return; // Exit the method if input is 'n'
                } else {
                    System.out.println("Inputs should only be y or n!");
                }
            }
            System.out.print("Please choose the schedule ID of the concert you will buy: ");
            selectedScheduleID = Integer.parseInt(kbd.nextLine());
            boolean checkShow = DriverManagers.validateShow(selectedScheduleID);

            if (!checkShow) {
                System.out.println("The show you choose doesn't exist! Please try again...");
            } else {
                break;
            }
        }
        double toPay = DriverManagers.getPrice(selectedScheduleID);
        double payment = toPay;
        ArrayList<loyalty_card> loyaltyCardList = DriverManagers.showLoyaltyCardTable();
        double discountAmount = 0;
        Integer loyaltyCardID = null;
        for (loyalty_card loyaltyCard : loyaltyCardList) {
            if (loyaltyCard.getAudienceID() == audienceID) {
                loyaltyCardID = loyaltyCard.getLoyalty_CardID();
                break;
            }
        }
        int currentPoints = DriverManagers.getPoints(loyaltyCardID);
        if (currentPoints >= 30) {
            System.out.println("You have now reach the redeemable points in your loyalty_card!");
            System.out.print("Do you want to use it in this transaction? [y/n]");
            String choice = kbd.nextLine();
            char c = choice.charAt(0);
            while (true) {
                if (c == 'y' || c == 'Y') {
                    discountAmount = payment * 0.2;
                    toPay = toPay - discountAmount;
                    System.out.println("The discount amount will be " + discountAmount);
                    break;
                } else if (c == 'n' || c == 'N') {
                    break;
                } else {
                    System.out.println("Inputs should only be y or n!");
                }
            }
        }
        while (true) {
            System.out.print("The total amount to pay is " + toPay + ", please input the exact amount: ");
            payment = Double.parseDouble(kbd.nextLine());
            if (payment == toPay) {
                System.out.println("Payment has been processed!");
                break;
            } else if (payment > toPay) {
                System.out.println("Payment should be exact!");
            } else if (payment < toPay) {
                System.out.println("Insufficient Amount!");
            }
            System.out.print("Do you want to retry the payment?[y/n]");
            String choice = kbd.nextLine();
            char c = choice.charAt(0);
            while (true) {
                if (c == 'y' || c == 'Y') {
                    break;
                } else if (c == 'n' || c == 'N') {
                    return;
                } else {
                    System.out.println("Inputs should only be y or n!");
                }
            }
        }
        // Retrieve the latest purchase IDs
        ArrayList<purchase> purchaseIDList = DriverManagers.showLatestPurchaseIDAndTicketNum();
        int latestPurchaseID = 0;

        for (purchase purchase : purchaseIDList) {
            latestPurchaseID = purchase.getPurchaseID();
        }
        if (latestPurchaseID == 0) {
            System.out.println("No purchases found.");
        }
        ArrayList<ticket> ticketNumList = DriverManagers.showLatestTicketNum();
        int latestTicketNum = 0;
        for (ticket tickets : ticketNumList) {
            latestTicketNum = tickets.getTicketID();
        }

        if (latestTicketNum == 0) {
            System.out.println("No ticket found.");
        }
        int purchaseID = latestPurchaseID + 1;
        int ticketNum = latestTicketNum + 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sysDateAndTime = sdf.format(new Date());
        String purchaseDate = sysDateAndTime;
        DriverManagers.insertTicketData(ticketNum, selectedScheduleID, payment, loyaltyCardID,"unused");
        DriverManagers.insertPurchaseData(purchaseID, ticketNum, discountAmount, purchaseDate, audienceID);
        if (discountAmount != 0) {
            DriverManagers.setPoints0(loyaltyCardID);
        }

        System.out.println("Successful Transaction!");
        System.out.println("Please use this ticket number " + ticketNum + " in the watch gig menu to watch the concert");
        System.out.println("Take note that you can take a screenshot to save your ticket number but don't share it with others.");
        System.out.println("You can also check it in your purchase history");
        System.out.println("Thank you!!!");
    }
    /**
     * Shows the purchase history of the current user.
     */
    public static void showUserPurchaseHistory(){
        ArrayList<audience> audienceList = DriverManagers.showAudienceTable();
        int audienceID = 0; // Default value
        for (audience audience : audienceList) {
            if (audience.getEmail().equals(currentUserEmail)) {
                audienceID = audience.getAudienceID();
                break;
            }
        }
        ArrayList<purchase> listPurchase = DriverManagers.userPurchaseHistory(audienceID);
        System.out.printf("%-12s %-20s %-15s %-25s %-18s  \n", "Ticket ID" ,"Name" , "Price", "PurchaseDate","Performer");
        System.out.println("=====================================================================================");
        for(purchase p: listPurchase){
            if(listPurchase.isEmpty()){
                System.out.println("No past transaction found");
            }
            System.out.printf("%-12s %-20s %-15s %-25s %-18s \n", p.getPurchaseID(),p.getBuyer(),p.getPrice(),p.getPurchaseDate(),p.getName());
        }
    }
    /**
     * Shows the loyalty points of the current user.
     */
    public static void showLoyaltyPoints() {
        int audienceID = 0;
        ArrayList<audience> audienceList = DriverManagers.showAudienceTable();
        for (audience audience : audienceList) {
            if (audience.getEmail().equals(currentUserEmail)) {
                audienceID = audience.getAudienceID();
                System.out.println("current audience id: " + audienceID); //debug only
                break;
            }
        }
        if(audienceID == 0){
            System.out.println("Sorry you don't have a loyalty card!");
        }else {
            ArrayList<loyalty_card> loyaltyCards = DriverManagers.showAudienceLoyaltyPoints(audienceID);
            if (loyaltyCards.isEmpty()) {
                System.out.println("You don't have any loyalty points yet.");
            } else {
                for (loyalty_card lc : loyaltyCards) {
                    System.out.println("Hello " + lc.getAudienceName() + ", you have a total loyalty points of " + lc.getLoyaltyPts());
                }
            }
        }
    }
    /**
     * Allows the user to watch a gig by redeeming a ticket.
     *
     * @throws SQLException if a database access error occurs
     */
    public static void watchGig() throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String redeem = sdf.format(new Date());

        ArrayList<watch_gig> watchIDList = DriverManagers.showLatestWatchID();
        ArrayList<audience> audienceList = DriverManagers.showAudienceTable();
        int latestWatchID = 0;
        for (watch_gig watchGig : watchIDList) {
            latestWatchID = watchGig.getWatchID();
            break;
        }
        int watchId = latestWatchID + 1;
        int audienceID = 0;
        for (audience audience : audienceList) {
            if (audience.getEmail().equals(currentUserEmail)) {
                audienceID = audience.getAudienceID();
                break;
            }
        }
        ArrayList<loyalty_card> loyaltyCardList = DriverManagers.showLoyaltyCardTable();
        Integer loyaltyCardID = null;
        for (loyalty_card loyaltyCard : loyaltyCardList) {
            if (loyaltyCard.getAudienceID() == audienceID) {
                loyaltyCardID = loyaltyCard.getLoyalty_CardID();
                break;
            }
        }
        System.out.print("Please enter your ticker number in order to watch a gig: ");
        int ticketNumber = Integer.parseInt(kbd.nextLine());
        boolean checkTicket = DriverManagers.validateTicket(ticketNumber);
        if(!checkTicket){
            System.out.println("The ticker number you provided doesn't exists! please try again...");
            return;
        }
        Boolean validateOwner = DriverManagers.validateOwnership(ticketNumber,audienceID);
        if (!validateOwner){
            System.out.println("You don't own the ticket number you insert! please try again...");
            return;
        }
        String checkIfUsed = DriverManagers.checkIfUsed(ticketNumber);
        if(checkIfUsed.equalsIgnoreCase("used")){
            System.out.println("The ticket you have provided is already been used! please use a valid ticket");
            return;
        }


        DriverManagers.insertWatchGigData(watchId,ticketNumber,audienceID,redeem);
        DriverManagers.setTicketUsed(ticketNumber);
        System.out.println("You may now enjoy watching the gig!");
        int currentPoints = DriverManagers.getPoints(loyaltyCardID);
        if (currentPoints == 30){
            System.out.println("Your points is now currently 30! you can't no longer add more points until you used it...");
        }else{
            int addedPts = currentPoints + 1;
            DriverManagers.addPoints(loyaltyCardID,addedPts);
            System.out.println("Your points is now currently "+addedPts);
        }
    }
    /**
     * Prompts the user to input details of a new show and adds it to the database.
     */
    public static void addShow() {
        ArrayList<performers> listPerformers = DriverManagers.showPerformers();
        int performerID;
        while (true) {
            System.out.println("Enter the name of the performer: ");
            String performer = kbd.nextLine();
            performerID = 0;
            for (performers performers : listPerformers) {
                if (performers.getName().equalsIgnoreCase(performer)) {
                    performerID = performers.getPerformID();
                    break;
                }
            }
            if (performerID != 0) {
                break;
            } else {
                System.out.println("No existing performer!");
                System.out.println("Do you want to try again?[y/n] ");
                String choice = kbd.nextLine();
                char c = choice.charAt(0);
                while (true) {
                    if (c == 'y' || c == 'Y') {
                        break;
                    } else if (c == 'n' || c == 'N') {
                        return;
                    } else {
                        System.out.println("Inputs should only be y or n!");
                    }
                }
            }
        }
        int year;
        int month;
        int day;
        while (true) {
            while (true) {
                System.out.println("Enter the date of the show (it should follow this format 2024-02-23 [yyyy-mm-dd]): ");
                System.out.print("Enter the year: ");
                year = Integer.parseInt(kbd.nextLine());
                System.out.print("Enter the month: ");
                month = Integer.parseInt(kbd.nextLine());
                System.out.print("Enter the day: ");
                day = Integer.parseInt(kbd.nextLine());
                if (!checkDate(year, month, day)) {
                    System.out.println("Invalid date! Please enter a valid date.");
                    System.out.println("Do you want to try again?[y/n] ");
                    String choice = kbd.nextLine();
                    char c = choice.charAt(0);
                    while (true) {
                        if (c == 'y' || c == 'Y') {
                            break;
                        } else if (c == 'n' || c == 'N') {
                            return;
                        } else {
                            System.out.println("Inputs should only be y or n!");
                        }
                    }
                } else {
                    break;
                }
            }
            LocalDate inputDate = LocalDate.of(year, month, day);
            LocalDate currentDate = LocalDate.now();
            if (inputDate.isBefore(currentDate)) {
                System.out.println("The date should not be earlier than the current date.");
                System.out.println("Do you want to try again?[y/n] ");
                String choice = kbd.nextLine();
                char c = choice.charAt(0);
                while (true) {
                    if (c == 'y' || c == 'Y') {
                        break;
                    } else if (c == 'n' || c == 'N') {
                        return;
                    } else {
                        System.out.println("Inputs should only be y or n!");
                    }
                }
            } else {
                break;
            }
        }
        String date = year + "-" + month + "-" + day;
        String start;
        String end;
        while (true) {
            while (true) {
                System.out.println("Enter the start time of the show (It should follow this format 15:00:00[hh:mm:ss])");
                start = kbd.nextLine();
                if (!checkTime(start)) {
                    System.out.println("Invalid time format!");
                    System.out.println("Do you want to try again?[y/n] ");
                    String choice = kbd.nextLine();
                    char c = choice.charAt(0);
                    while (true) {
                        if (c == 'y' || c == 'Y') {
                            break;
                        } else if (c == 'n' || c == 'N') {
                            return;
                        } else {
                            System.out.println("Inputs should only be y or n!");
                        }
                    }
                } else {
                    break;
                }
            }
            while (true) {
                System.out.println("Enter the end time of the show (It should follow this format 15:00:00[hh:mm:ss])");
                end = kbd.nextLine();
                if (!checkTime(end)) {
                    System.out.println("Invalid time format!");
                    System.out.println("Do you want to try again?[y/n] ");
                    String choice = kbd.nextLine();
                    char c = choice.charAt(0);
                    while (true) {
                        if (c == 'y' || c == 'Y') {
                            break;
                        } else if (c == 'n' || c == 'N') {
                            return;
                        } else {
                            System.out.println("Inputs should only be y or n!");
                        }
                    }
                } else {
                    break;
                }
            }
            if (!compareTime(start, end)) {
                System.out.println("Start time should be before end time!");
                String choice = kbd.nextLine();
                char c = choice.charAt(0);
                while (true) {
                    if (c == 'y' || c == 'Y') {
                        break;
                    } else if (c == 'n' || c == 'N') {
                        return;
                    } else {
                        System.out.println("Inputs should only be y or n!");
                    }
                }
            }else {
                break;
            }
        }
        int price = 0;
        while (true) {
            System.out.println("Enter the price for the show: ");
            price = Integer.parseInt(kbd.nextLine());
            if (price < 0) {
                System.out.println("Price cant be negative!");
                System.out.println("Do you want to try again?[y/n] ");
                String choice = kbd.nextLine();
                char c = choice.charAt(0);
                while (true) {
                    if (c == 'y' || c == 'Y') {
                        break;
                    } else if (c == 'n' || c == 'N') {
                        return;
                    } else {
                        System.out.println("Inputs should only be y or n!");
                    }
                }
            } else {
                break;
            }
        }
        ArrayList<schedule> schedules = DriverManagers.showLatestSchedID();
        int latestSchedID = 0;
        for (schedule schedule : schedules) {
            latestSchedID = schedule.getSchedID();
        }
        int schedID = latestSchedID + 1;
        DriverManagers.insertSchedData(schedID,performerID, date, start, end, price);
        System.out.println("Successfully added a show!");
        System.out.println("The ID for that show is " + schedID);
    }
    /**
     * Prompts the user to enter the ID of the show to be removed and deletes it from the database.
     *
     * @throws SQLException if a database access error occurs
     */
    private static void removeShow() throws SQLException {
        int showID = 0;
        do {
            System.out.print("Enter show ID: ");
            showID = Integer.parseInt(kbd.nextLine());
            boolean checkShow = DriverManagers.validateShow(showID);
            if (!checkShow) {
                System.out.println("The show you choose doesn't exists! please try again...");
            }
            System.out.print("Enter \"delete\" to delete show. ");
            String delete = kbd.nextLine();
            if(delete.equals("delete")){
                boolean isDeleted = DriverManagers.deleteShow(showID);
                if (isDeleted) {
                    System.out.println("Show is successfully deleted ");
                    break;
                }else {
                    System.out.println("The show can't be deleted because the customers have already bought a ticket ");
                    break;
                }
            } else {
                System.out.print("Enter \"cancel\" to cancel delete process. ");
                String cancel = kbd.nextLine();

                if (cancel.equals("cancel")){
                    System.out.println("Returning to menu...");
                    break;
                }
            }

        } while (true);
    }
    /**
     * Allows the user to edit the price of a specific show.
     */
    public static void editPrice(){
        System.out.print("Enter show ID: ");
        int showID = Integer.parseInt(kbd.nextLine());
        boolean checkShow = DriverManagers.validateShow(showID);
        if (!checkShow) {
            System.out.println("The show you choose doesn't exists! please try again...");
        }
        int price = 0;
        while (true) {
            System.out.println("Enter the price for the show: ");
            price = Integer.parseInt(kbd.nextLine());
            if (price < 0) {
                System.out.println("Price cant be negative!");
                System.out.println("Do you want to try again?[y/n] ");
                String choice = kbd.nextLine();
                char c = choice.charAt(0);
                while (true) {
                    if (c == 'y' || c == 'Y') {
                        break;
                    } else if (c == 'n' || c == 'N') {
                        return;
                    } else {
                        System.out.println("Inputs should only be y or n!");
                    }
                }
            } else {
                break;
            }
        }
        DriverManagers.editPrice(showID,price);
        System.out.println("Successfully updated the price!");
    }
    /**
     * Allows the user to edit the date and time of a specific show.
     */
    public static void editDateAndTime(){
        System.out.print("Enter show ID: ");
        int showID = Integer.parseInt(kbd.nextLine());
        boolean checkShow = DriverManagers.validateShow(showID);
        if (!checkShow) {
            System.out.println("The show you choose doesn't exists! please try again...");
        }
        int year;
        int month;
        int day;
        while (true) {
            while (true) {
                System.out.println("Enter the date of the show (it should follow this format 2024-02-23 [yyyy-mm-dd]): ");
                System.out.print("Enter the year: ");
                year = Integer.parseInt(kbd.nextLine());
                System.out.print("Enter the month: ");
                month = Integer.parseInt(kbd.nextLine());
                System.out.print("Enter the day: ");
                day = Integer.parseInt(kbd.nextLine());
                if (!checkDate(year, month, day)) {
                    System.out.println("Invalid date! Please enter a valid date.");
                    System.out.println("Do you want to try again?[y/n] ");
                    String choice = kbd.nextLine();
                    char c = choice.charAt(0);
                    while (true) {
                        if (c == 'y' || c == 'Y') {
                            break;
                        } else if (c == 'n' || c == 'N') {
                            return;
                        } else {
                            System.out.println("Inputs should only be y or n!");
                        }
                    }
                } else {
                    break;
                }
            }
            LocalDate inputDate = LocalDate.of(year, month, day);
            LocalDate currentDate = LocalDate.now();
            if (inputDate.isBefore(currentDate)) {
                System.out.println("The date should not be earlier than the current date.");
                System.out.println("Do you want to try again?[y/n] ");
                String choice = kbd.nextLine();
                char c = choice.charAt(0);
                while (true) {
                    if (c == 'y' || c == 'Y') {
                        break;
                    } else if (c == 'n' || c == 'N') {
                        return;
                    } else {
                        System.out.println("Inputs should only be y or n!");
                    }
                }
            } else {
                break;
            }
        }
        String date = year + "-" + month + "-" + day;
        String start;
        String end;
        while (true) {
            while (true) {
                System.out.println("Enter the start time of the show (It should follow this format 15:00:00[hh:mm:ss])");
                start = kbd.nextLine();
                if (!checkTime(start)) {
                    System.out.println("Invalid time format!");
                    System.out.println("Do you want to try again?[y/n] ");
                    String choice = kbd.nextLine();
                    char c = choice.charAt(0);
                    while (true) {
                        if (c == 'y' || c == 'Y') {
                            break;
                        } else if (c == 'n' || c == 'N') {
                            return;
                        } else {
                            System.out.println("Inputs should only be y or n!");
                        }
                    }
                } else {
                    break;
                }
            }
            while (true) {
                System.out.println("Enter the end time of the show (It should follow this format 15:00:00[hh:mm:ss])");
                end = kbd.nextLine();
                if (!checkTime(end)) {
                    System.out.println("Invalid time format!");
                    System.out.println("Do you want to try again?[y/n] ");
                    String choice = kbd.nextLine();
                    char c = choice.charAt(0);
                    while (true) {
                        if (c == 'y' || c == 'Y') {
                            break;
                        } else if (c == 'n' || c == 'N') {
                            return;
                        } else {
                            System.out.println("Inputs should only be y or n!");
                        }
                    }
                } else {
                    break;
                }
            }
            if (!compareTime(start, end)) {
                System.out.println("Start time should be before end time!");
                String choice = kbd.nextLine();
                char c = choice.charAt(0);
                while (true) {
                    if (c == 'y' || c == 'Y') {
                        break;
                    } else if (c == 'n' || c == 'N') {
                        return;
                    } else {
                        System.out.println("Inputs should only be y or n!");
                    }
                }
            }else {
                break;
            }
        }
        DriverManagers.editDateAndTime(showID,date,start,end);
        System.out.println("Successfully updated the date and time of the show!");
    }
    /**
     * Checks if the provided date is valid.
     *
     * @param year  the year of the date
     * @param month the month of the date
     * @param day   the day of the date
     * @return true if the date is valid, false otherwise
     */
    public static boolean checkDate(int year, int month, int day) {
        if (month < 1 || month > 12) {
            return false;
        }
        int maxDay = 31;
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            maxDay = 30;
        } else if (month == 2) {
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                maxDay = 29;
            } else {
                maxDay = 28;
            }
        }
        return day >= 1 && day <= maxDay;
    }
    /**
     * Checks if the provided time is in a valid format.
     *
     * @param time the time to be validated
     * @return true if the time is in a valid format, false otherwise
     */
    public static boolean checkTime(String time) {
        String format = "^([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }
    /**
     * Compares two times and checks if the start time is before the end time.
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return true if the start time is before the end time, false otherwise
     */
    public static boolean compareTime(String startTime, String endTime) {
        LocalTime start;
        LocalTime end;

        try {
            start = LocalTime.parse(startTime);
            end = LocalTime.parse(endTime);
        } catch (DateTimeParseException e) {
            return false;
        }

        return start.isBefore(end);
    }
    /**
     * Displays a list of audience members based on the provided name.
     *
     * @param audience the name of the audience member
     */
    public static void showListsOfAudience(String audience) {
        ArrayList<audience> audienceList = DriverManagers.listsOfAudienceBasedOnName(audience);
        System.out.printf("%-10s %-15s %-15s %-25s  %-15s  \n", "audienceID", "First Name", "Last Name", "Email", "Account Type");
        System.out.println("================================================================================================");
        int i = 0;
        for (audience a : audienceList) {
            System.out.printf("%-10s %-15s %-15s %-25s  %-15s  \n", a.getAudienceID(), a.getFirstName(), a.getLastName(), a.getEmail(), a.getAccountType());
            i++;
            if (i % 10 == 0) {
                System.out.println("Do you want to show more audience?[y/n]");
                String choice = kbd.nextLine();
                char c = choice.charAt(0);
                if (c != 'y' && c != 'Y') {
                    break;
                }
            }
            if (audienceList == null){
                break;
            }
        }
    }
    /**
     * Displays a list of performers.
     *
     * @throws SQLException if a database access error occurs
     */
    public static void showListsOfPerformers() throws SQLException{
        ArrayList<performers> listPerformers = DriverManagers.showPerformers();
        System.out.printf("%-10s %-25s %-15s  \n", "ID" ,"Name" , "Performer Type", "");
        System.out.println("=====================================================================================");
        for(performers pf: listPerformers){
            if(listPerformers.isEmpty()){
                System.out.println("No performers found");
            }
            System.out.printf("%-10s %-25s %-15s \n", pf.getPerformID(),pf.getName(),pf.getPerformerType());
        }
    }
    /**
     * Displays a list of purchases.
     *
     * @throws SQLException if a database access error occurs
     */
    public static void showPurchases() throws SQLException{
        ArrayList<purchase> listPurchases = DriverManagers.showPurchases();
        System.out.printf("%-15s %-15s  %-25s %-15s \n", "Purchase ID" ,"Buyer" ,"Amount Paid", "Purchase Date");
        System.out.println("======================================================================================================================");
        for(purchase p: listPurchases){
            if(listPurchases.isEmpty()){
                System.out.println("No purchases found");
            }
            System.out.printf("%-15s %-15s  %-25s %-15s \n", p.getPurchaseID(),  p.getName(),p.getPrice(), p.getPurchaseDate());
        }
    }
    /**
     * Initializes the connection to the database and starts the main program loop.
     *
     * @throws SQLException if a database access error occurs
     */
    public static void run() throws SQLException {
        boolean x = true;
        getConnection();
        showIntroduction();
        while (x){
            LogInOrReg();
        }
    }

}
