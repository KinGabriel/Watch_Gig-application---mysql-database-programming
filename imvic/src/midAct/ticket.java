package midAct;

public class ticket {
    private int ticketID;
    private int schedID;
    private double price;
    private int loyaltyCardID;

    public ticket( int ticketID,int schedID,double price,int loyaltyCardID){
        this.ticketID = ticketID;
        this.schedID = schedID;
        this.price = price;
        this.loyaltyCardID = loyaltyCardID;
    }
    public ticket( int ticketID){
        this.ticketID = ticketID;

    }

    public int getTicketID() {
        return ticketID;
    }
}
