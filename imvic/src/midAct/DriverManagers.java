package midAct;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

public class DriverManagers {
    private static Connection con = null;
    /**
     * Constructs a new DriverManagers object.
     */
    public DriverManagers() {
    }
    /**
     * Establishes a connection to the database.
     *
     * @return Connection object for database interaction.
     */
    public static Connection getConnection() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/imvic", "root", "");
        } catch (SQLException e) {
            System.out.println("No database found");
        }
        return con;
    }
    /**
     * Validates login credentials and logs in the user.
     *
     * @param email    The email of the user.
     * @param password The password of the user.
     * @return Account type and full name of the user if login is successful, otherwise null.
     * @throws SQLException If an SQL exception occurs.
     */
    public static String logInToGIG(String email, String password) throws SQLException {
        String query = "SELECT accountType, concat(fName,'',lName) Name FROM audience WHERE email = ? AND password = ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String accountType = rs.getString("accountType");
                String fullName = rs.getString("Name");
                return accountType + "|" + fullName; // Return the account type if login is successful
            } else {
                return null; // Return null if login fails
            }
        } catch (SQLException e) {
            System.out.println("Error during login");
            e.printStackTrace();
            throw e;
        }
    }
    /**
     * Checks if an email already exists in the database.
     *
     * @param email The email to be checked.
     * @return True if email exists, otherwise false.
     */
    public static boolean checkEmailExistence(String email) {
        String query = "SELECT email FROM audience WHERE email = ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Checks if an audience exists in the database.
     *
     * @param audID The audience ID to be checked.
     * @return True if audience exists, otherwise false.
     */
    public static boolean checkAudienceExistence(int audID) {
        String query = "SELECT audienceID FROM audience WHERE audienceID = ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, audID);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Checks if an audience is inactive.
     *
     * @param audID The audience ID to be checked.
     * @return True if audience is inactive, otherwise false.
     */
    public static boolean isAudienceInactive(int audID) {
        String query = "SELECT accountType FROM audience WHERE audienceID = ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, audID);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Checks if a password exists for a given email.
     *
     * @param email    The email of the user.
     * @param password The password to be checked.
     * @return True if password exists, otherwise false.
     */
    public static boolean checkPasswordExistence(String email, String password) {
        String query = "SELECT email, password FROM audience WHERE email = ? AND BINARY password = ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Checks if a user is an admin based on first name and last name.
     *
     * @param fName The first name of the user.
     * @param lName The last name of the user.
     * @return True if user is an admin, otherwise false.
     */
    public static boolean isAdmin(String fName, String lName) {
        String query = "SELECT accountType FROM audience WHERE fName = ? and lName = ? and accountType = ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, fName);
            statement.setString(2, lName);
            statement.setString(3,"Admin");
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Registers an audience member to GIG.
     *
     * @param a The audience object to be registered.
     * @return True if registration is successful, otherwise false.
     */
    public static boolean RegisterToGig(audience a) {
        boolean validate = validateRegistration(a.getEmail());
        if (!validate) {
            return false; // Return false if email is already taken
        }
        String query = "INSERT INTO audience(fname, lName, email, password, accountType) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, a.getFirstName());
            statement.setString(2, a.getLastName());
            statement.setString(3, a.getEmail());
            statement.setString(4, a.getPassword());
            statement.setString(5, a.getAccountType());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Account registered successfully!");
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int audienceID = generatedKeys.getInt(1);
                    a.setAudienceID(audienceID);
                }
                return true;
            } else {
                System.out.println("Failed to register account.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error during register");
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Validates registration by checking if email already exists.
     *
     * @param email The email to be validated.
     * @return True if email is available, otherwise false.
     */
    public static boolean validateRegistration(String email){
        String query = "SELECT email FROM audience WHERE email = ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                System.out.println("Email is already taken! please use another email");
                return false; // Email already exists
            } else {
                return true; // Email is available
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Checks if a ticket is used.
     *
     * @param ticketID The ID of the ticket.
     * @return String indicating whether ticket is used or not, or null if not found.
     * @throws SQLException If an SQL exception occurs.
     */
    public static String checkIfUsed(int ticketID) throws SQLException {
        String query = "SELECT isTicketUsed FROM ticket WHERE ticketID = ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, ticketID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String checker = rs.getString("isTicketUsed");
                return checker;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error during login");
            e.printStackTrace();
            throw e;
        }
    }
    /**
     * Retrieves a list of scheduled shows.
     *
     * @return ArrayList of scheduled shows.
     */
    public static ArrayList<schedule> listsOfShows() {
        ArrayList<schedule> schedule = new ArrayList<>();
        String query = "SELECT s.schedID, p.name,s.price,s.date, s.startTime, s.endTime FROM performers p NATURAL JOIN schedule s WHERE s.date >= CURRENT_DATE()";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                schedule s = new schedule(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getString(4),resultSet.getString(5),
                        resultSet.getString(6));
                schedule.add(s);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return schedule;
    }
    /**
     * Retrieves a list of past shows.
     *
     * @return ArrayList of past shows.
     */
    public static ArrayList<schedule> listsOfPastShows() {
        ArrayList<schedule> schedule = new ArrayList<>();
        String query = "SELECT s.schedID, p.name,s.price,s.date, s.startTime, s.endTime FROM performers p NATURAL JOIN schedule s WHERE s.date <= CURRENT_DATE()";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                schedule s = new schedule(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getString(4),resultSet.getString(5),
                        resultSet.getString(6));
                schedule.add(s);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return schedule;
    }
    /**
     * Retrieves a list of shows based on performer name.
     *
     * @param name The name of the performer.
     * @return ArrayList of shows based on performer name.
     */
    public static ArrayList<schedule> listsOfShowsBasedOnPerformers(String name) {
        ArrayList<schedule> schedule = new ArrayList<>();
        String query = "SELECT s.schedID, p.name, s.price,s.date, s.startTime, s.endTime FROM performers p NATURAL JOIN schedule s WHERE s.date >= CURRENT_DATE() and p.name like ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1,  name+ "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    schedule s = new schedule(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6)
                    );
                    schedule.add(s);
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return schedule;
    }
    /**
     * Retrieves a list of performers based on name.
     *
     * @param name The name of the performer.
     * @return ArrayList of performers.
     */
    public static ArrayList<performers> listsOfPerformers(String name) {
        ArrayList<performers> performer = new ArrayList<>();
        String query = "SELECT performerID,name,performerType FROM performers p where name like ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1,  name+ "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    performers p = new performers(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3));
                    performer.add(p);
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return performer;
    }
    /**
     * Retrieves the price of a show.
     *
     * @param showId The ID of the show.
     * @return The price of the show.
     */
    public static double getPrice(int showId) {
        String query = "SELECT price from schedule where schedID = ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, showId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    /**
     * Retrieves the loyalty points of a loyalty card.
     *
     * @param lcID The ID of the loyalty card.
     * @return The loyalty points of the loyalty card.
     */
    public static int getPoints(int lcID){
        String query = "SELECT earnedPoints from loyalty_card where loyaltyCardID = ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, lcID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    /**
     * Sets loyalty points of a loyalty card to zero.
     *
     * @param lcID The ID of the loyalty card.
     */
    public static void setPoints0(int lcID){
        String query = "UPDATE loyalty_card set earnedPoints=? where loyaltyCardID= ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1,0);
            statement.setInt(2,lcID);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Edits the price of a show.
     *
     * @param schedID The ID of the show.
     * @param price   The new price.
     */
    public static void editPrice(int schedID,int price){
        String query = "UPDATE schedule set price=? where schedID= ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1,price);
            statement.setInt(2,schedID);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Edits the date and time of a show.
     *
     * @param schedID    The ID of the show.
     * @param date       The new date.
     * @param startTime  The new start time.
     * @param endTime    The new end time.
     */
    public static void editDateAndTime(int schedID,String date,String startTime,String endTime){
        String query = "UPDATE schedule set date=?,startTime =? ,endTime =? where schedID= ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1,date);
            statement.setString(2,startTime);
            statement.setString(3,endTime);
            statement.setInt(4,schedID);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Sets a ticket as used.
     *
     * @param tickID The ID of the ticket.
     */
    public static void setTicketUsed(int tickID) {
        String query = "UPDATE ticket set isTicketUsed=? where ticketID= ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, "Used");
            statement.setInt(2,tickID);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Adds points to a loyalty card.
     *
     * @param lcID   The ID of the loyalty card.
     * @param points The points to be added.
     */
    public static void addPoints(int lcID,int points){
        String query = "UPDATE loyalty_card set earnedPoints=? where loyaltyCardID= ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1,points);
            statement.setInt(2,lcID);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Retrieves a list of audience members from the database.
     *
     * @return ArrayList of audience members.
     */
    public static ArrayList<audience> showAudienceTable(){
        ArrayList<audience> audience = new ArrayList<>();
        String query = "SELECT audienceID, fname, lName, email, accountType from audience";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)){
            ResultSet resultSet =  statement.executeQuery();
            while (resultSet.next()){
                audience a = new audience(resultSet.getInt(1),resultSet.getString(2)
                        ,resultSet.getString(3),resultSet.getString(4), resultSet.getString(5));
                audience.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return audience;
    }
    /**
     * Retrieves loyalty points of an audience member.
     *
     * @param audienceID The ID of the audience member.
     * @return ArrayList of loyalty card with points.
     */
    public static ArrayList<loyalty_card> showAudienceLoyaltyPoints(int audienceID){
        ArrayList<loyalty_card> loyalty_Card = new ArrayList<>();
        String query = "select concat(a.fName, \" \",a.lName) buyer,lc.earnedPoints from loyalty_card lc natural join audience a where audienceId = ?;";
        try(Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, audienceID);
            ResultSet resultSet =  statement.executeQuery();
            while(resultSet.next()){
                loyalty_card lc = new loyalty_card(resultSet.getString(1),resultSet.getInt(2));
                loyalty_Card.add(lc);
            }
            return loyalty_Card;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Validates if a ticket exists.
     *
     * @param ticketId The ID of the ticket.
     * @return True if ticket exists, otherwise false.
     */
    public static boolean validateTicket(int ticketId){
        String query = "Select ticketID from ticket where ticketID =?";
        try(Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1,ticketId);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Validates ownership of a ticket.
     *
     * @param ticketId    The ID of the ticket.
     * @param audienceID  The ID of the audience member.
     * @return True if ownership is valid, otherwise false.
     */
    public static boolean validateOwnership(int ticketId,int audienceID){
        String query = "select ticketNum from purchase where ticketNum= ? and audienceBuyer=?";
        try(Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1,ticketId);
            statement.setInt(2,audienceID);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Validates if a show exists.
     *
     * @param showId The ID of the show.
     * @return True if show exists, otherwise false.
     */
    public static boolean validateShow(int showId){
        String query = "Select schedID from schedule where schedID =?";
        try(Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1,showId);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Inserts purchase data into the database.
     *
     * @param purchaseID    The ID of the purchase.
     * @param ticketNum     The ticket number.
     * @param discount      The discount applied.
     * @param purchaseDate  The date of purchase.
     * @param audienceBuyer The ID of the audience buyer.
     */
    public static void insertPurchaseData(int purchaseID, int ticketNum, double discount, String purchaseDate, int audienceBuyer) {
        String query = "INSERT INTO purchase (purchaseID, ticketNum, discount, purchaseDate, audienceBuyer) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, purchaseID);
            statement.setInt(2, ticketNum);
            statement.setDouble(3, discount);
            statement.setString(4, purchaseDate);
            statement.setInt(5, audienceBuyer);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error during purchase data insertion");
            e.printStackTrace();
        }
    }
    /**
     * Inserts ticket data into the database.
     *
     * @param ticketID      The ID of the ticket.
     * @param schedID       The ID of the show.
     * @param payment       The payment amount.
     * @param loyaltyCardID The ID of the loyalty card.
     * @param used          The usage status of the ticket.
     */
    public static void insertTicketData(int ticketID, int schedID, double payment, int loyaltyCardID, String used) {
        String query = "INSERT INTO ticket (ticketID, schedID,amountPaid,loyaltyCardID,isTicketUsed) VALUES (?, ?, ?,?,?)";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, ticketID);
            statement.setInt(2, schedID);
            statement.setDouble(3,payment);
            if (loyaltyCardID != -1) {
                statement.setInt(4, loyaltyCardID);
                statement.setString(5,used);
            } else {
                statement.setNull(4, Types.INTEGER);
                statement.setString(5,used);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error during ticket data insertion");
            e.printStackTrace();
        }
    }
    /**
     * Inserts watch gig data into the database.
     *
     * @param watchID    The ID of the watch gig.
     * @param ticketId   The ID of the ticket.
     * @param audience   The ID of the audience.
     * @param redeemTime The redeem time of the gig.
     */
    public static void insertWatchGigData(int watchID, int ticketId, int audience, String redeemTime) {
        String query = "INSERT INTO watch_gig (watchID, ticketID, audience, redeemTime) VALUES (?,?,?,?)";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, watchID);
            statement.setInt(2, ticketId);
            statement.setInt(3, audience);
            statement.setString(4, redeemTime);
            statement.executeUpdate();
            System.out.println("Watch Gig data inserted successfully");
        } catch (SQLException e) {
            System.out.println("Error during watch gig data insertion");
            e.printStackTrace();
        }
    }
    /**
     * Inserts a loyalty card for an audience member into the database.
     *
     * @param audience The ID of the audience member.
     */
    public static void insertLoyaltyCard(int audience) {
        String query = "INSERT INTO loyalty_card (audienceID, earnedPoints) VALUES (?,?)";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, audience);
            statement.setInt(2, 0);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error during watch gig data insertion");
            e.printStackTrace();
        }
    }
    /**
     * Retrieves a list of tickets from the database.
     *
     * @return ArrayList of tickets.
     */
    public static ArrayList<ticket> showTicketTable(){
        ArrayList<ticket> tickets = new ArrayList<>();
        String query = "SELECT ticketID, schedID, price loyaltyCardID FROM ticket";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)){
            ResultSet resultSet =  statement.executeQuery();
            while (resultSet.next()){
                ticket a = new ticket(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getDouble(3),
                        resultSet.getInt(4));
                tickets.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }
    /**
     * Retrieves a list of loyalty cards from the database.
     *
     * @return ArrayList of loyalty cards.
     */
    public static ArrayList<loyalty_card> showLoyaltyCardTable(){
        ArrayList<loyalty_card> loyaltyCard = new ArrayList<>();
        String query = "SELECT * FROM loyalty_card";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)){
            ResultSet resultSet =  statement.executeQuery();
            while (resultSet.next()){
                loyalty_card a = new loyalty_card(
                        resultSet.getInt(1),
                        resultSet.getInt(2));
                loyaltyCard.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loyaltyCard;
    }
    /**
     * Retrieves the latest scheduled show ID from the database.
     *
     * @return ArrayList containing the latest scheduled show ID.
     */
    public static ArrayList<schedule> showLatestSchedID(){
        ArrayList<schedule> schedules = new ArrayList<>();
        String query = "SELECT max(schedID) schedID FROM schedule;";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)){
            ResultSet resultSet =  statement.executeQuery();
            while (resultSet.next()){
                schedule s = new schedule(resultSet.getInt(1));
                schedules.add(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return schedules;
    }
    /**
     * Retrieves the latest purchase ID and ticket number from the database.
     *
     * @return ArrayList containing the latest purchase ID and ticket number.
     */
    public static ArrayList<purchase> showLatestPurchaseIDAndTicketNum(){
        ArrayList<purchase> purchase = new ArrayList<>();
        String query = "SELECT max(purchaseID) purchaseID, max(ticketNum) ticketNum FROM purchase;";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)){
            ResultSet resultSet =  statement.executeQuery();
            while (resultSet.next()){
                purchase a = new purchase(
                        resultSet.getInt(1),
                        resultSet.getInt(2));
                purchase.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return purchase;
    }
    /**
     * Retrieves the latest ticket number from the database.
     *
     * @return ArrayList containing the latest ticket number.
     */
    public static ArrayList<ticket> showLatestTicketNum(){
        ArrayList<ticket> tickets = new ArrayList<>();
        String query = "SELECT max(ticketID) ticketNum FROM ticket;";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)){
            ResultSet resultSet =  statement.executeQuery();
            while (resultSet.next()){
                ticket a = new ticket(
                        resultSet.getInt(1));
                tickets.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }
    /**
     * Retrieves the latest watch gig ID from the database.
     *
     * @return ArrayList containing the latest watch gig ID.
     */
    public static ArrayList<watch_gig> showLatestWatchID(){
        ArrayList<watch_gig> watchGig = new ArrayList<>();
        String query = "SELECT max(watchID) watchID FROM watch_gig";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)){
            ResultSet resultSet =  statement.executeQuery();
            while (resultSet.next()){
                watch_gig a = new watch_gig(
                        resultSet.getInt(1));
                watchGig.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return watchGig;
    }
    /**
     * Retrieves a list of performers from the database.
     *
     * @return ArrayList of performers.
     */
    public static ArrayList<performers> showPerformers(){
        ArrayList<performers> performers = new ArrayList<>();
        String query = "SELECT performerID, name,performerType from performers;";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)){
            ResultSet resultSet =  statement.executeQuery();
            while (resultSet.next()){
                performers pf = new performers(resultSet.getInt(1),resultSet.getString(2)
                        ,resultSet.getString(3));
                performers.add(pf);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return performers;
    }
    /**
     * Retrieves a list of purchases from the database.
     *
     * @return ArrayList of purchases.
     */
    public static ArrayList<purchase> showPurchases(){
        ArrayList<purchase> purchases = new ArrayList<>();
        String query = "SELECT p.purchaseID," +
                "concat(a.fName,\" \",a.lName) as Buyer,t.amountPaid, " +
                "p.purchaseDate from purchase p " +
                "inner join ticket t on p.ticketNum = ticketID " +
                "inner join audience a on audienceID = audienceBuyer";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)){
            ResultSet resultSet =  statement.executeQuery();
            while (resultSet.next()){
                purchase p = new purchase(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3), resultSet.getString(4));
                purchases.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return purchases;
    }
    /**
     * Retrieves the purchase history of a specific audience member from the database.
     *
     * @param audienceId The ID of the audience member.
     * @return ArrayList containing the purchase history of the audience member.
     */
    public static ArrayList<purchase> userPurchaseHistory(int audienceId) {
        ArrayList<purchase> purchase = new ArrayList<>();
        String query = "select p.ticketNum,concat(a.fName,\" \",a.lName) as Name,t.amountPaid,p.purchaseDate,pf.name as performer from purchase p inner join ticket t on p.ticketNum = t.ticketID natural join schedule s natural join performers pf inner join audience a on p.audienceBuyer = a.audienceID where audienceId = ?;";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1,audienceId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                purchase p = new purchase(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getString(5));
                purchase.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return purchase;
    }
    /**
     * Adds a new performer to the database.
     *
     * @param name          The name of the performer.
     * @param performerType The type of performer.
     */
    public static void addPerformer(String name, String performerType){
        int pIDMax = 0;
        String call = "SELECT performerID FROM performers WHERE performerID ORDER BY performerID DESC LIMIT 1";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(call)){
            ResultSet resultSet =  statement.executeQuery();
            while (resultSet.next()){
                pIDMax = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String query = "INSERT INTO performers (performerID, name, performerType, thumbnail) VALUES (?,?,?,?)";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, (pIDMax+1));
            statement.setString(2, name);
            statement.setString(3, performerType);
            statement.setString(4, "https://placehold.co/600x400");
            statement.executeUpdate();
            System.out.println("Performer data inserted successfully");
        } catch (SQLException e) {
            System.out.println("Error during performer data insertion");
            e.printStackTrace();
        }
    }
    /**
     * Deletes a scheduled show from the database.
     *
     * @param schedID The ID of the scheduled show to be deleted.
     * @return True if deletion is successful, otherwise false.
     */
    public static boolean deleteShow(int schedID){
        String query = "DELETE FROM schedule WHERE schedID = ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1,schedID);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    /**
     * Deletes a performer from the database.
     *
     * @param aID The ID of the performer to be deleted.
     * @return True if deletion is successful, otherwise false.
     */
    public static Boolean deletePerformer(int aID){
        String query = "DELETE FROM performers WHERE performerID = ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1,aID);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    /**
     * Inserts scheduled show data into the database.
     *
     * @param schedID     The ID of the scheduled show.
     * @param performerID The ID of the performer.
     * @param date        The date of the show.
     * @param startTime   The start time of the show.
     * @param endTime     The end time of the show.
     * @param price       The price of the show.
     */
    public static void insertSchedData(int schedID,int performerID, String date, String startTime, String endTime, int price) {
        String query = "INSERT INTO schedule (schedID,performerID, date, startTime, endTime,price) VALUES (?,?, ?, ?, ?,?)";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1,schedID);
            statement.setInt(2, performerID);
            statement.setString(3, date);
            statement.setString(4, startTime);
            statement.setString(5, endTime);
            statement.setInt(6, price);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error during purchase data insertion");
            e.printStackTrace();
        }
    }
    /**
     * Retrieves a list of audience members based on name from the database.
     *
     * @param name The name of the audience member.
     * @return ArrayList of audience members matching the provided name.
     */
    //Manage audience [2]
    public static ArrayList<audience> listsOfAudienceBasedOnName(String name) {
        ArrayList<audience> audienceList = new ArrayList<>();
        String query = "SELECT audienceID, concat(fName,' ', lName) as fullName, email, accountType FROM audience WHERE concat(fName,' ', lName) LIKE ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1,  name + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String[] names = resultSet.getString("fullName").split(" ");
                    String firstName = names[0];
                    String lastName = names.length > 1 ? names[1] : "";

                    audience a = new audience(
                            resultSet.getInt("audienceID"),
                            firstName,
                            lastName,
                            resultSet.getString("email"),
                            resultSet.getString("accountType")
                    );
                    audienceList.add(a);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return audienceList;
    }
    /**
     * Adds a new audience member to the database.
     *
     * @param email       The email of the audience member.
     * @param password    The password of the audience member.
     * @param fName       The first name of the audience member.
     * @param lName       The last name of the audience member.
     * @param accountType The account type of the audience member.
     */
    //Manage audience [3]
    public static void addAudience(String email, String password, String fName, String lName, String accountType){
        int aIDMax = 0;
        String call = "SELECT audienceID FROM audience WHERE audienceID ORDER BY audienceID DESC LIMIT 1";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(call)){
            ResultSet resultSet =  statement.executeQuery();
            while (resultSet.next()){
                aIDMax = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String query = "INSERT INTO audience (audienceID, fName, lName, email, password, accountType) VALUES (?,?,?,?,?,?)";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, (aIDMax+1));
            statement.setString(2, fName);
            statement.setString(3, lName);
            statement.setString(4, email);
            statement.setString(5, password);
            statement.setString(6, accountType);
            statement.executeUpdate();
            System.out.println("Audience data inserted successfully");
        } catch (SQLException e) {
            System.out.println("Error during audience data insertion");
        }
    }
    /**
     * Deletes an audience member from the database.
     *
     * @param aID The ID of the audience member to be deleted.
     */
    //Manage audience [4]
    public static void deleteAudience(int aID){///revise code
        String query = "UPDATE audience set accountType = \"Inactive\" where audienceID = ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, aID);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error during audience inactivation.");
        }
    }
    /**
     * Assigns an admin account to an existing audience member.
     *
     * @param fName The first name of the audience member.
     * @param lName The last name of the audience member.
     * @return ArrayList of audience members.
     */
    //Manage audience [5]
    public static ArrayList<audience> assignAdminAccount(String fName, String lName) {
        ArrayList<audience> audienceList = new ArrayList<>();
        int audienceID = 0;
        String call = "SELECT audienceID FROM audience WHERE fName = ? and lName = ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(call)) {
            statement.setString(1, fName);
            statement.setString(2, lName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                audienceID = resultSet.getInt(1);
            } else {
                System.out.println("No audience found with the provided name.");
                return audienceList;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String query = "UPDATE audience set accountType = \"Admin\" where audienceID = ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, audienceID);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Account type updated to 'Admin' for audience with ID: " + audienceID);
            } else {
                System.out.println("No audience found with the provided ID.");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return audienceList;
    }



}
