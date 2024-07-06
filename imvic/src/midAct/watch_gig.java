package midAct;

public class watch_gig {
    private int watchID;
    private int ticketID;
    private int audience;
    private String redeemTime;

    public watch_gig(int watchID, int ticketID, int audience, String redeemTime) {
        this.watchID = watchID;
        this.ticketID = ticketID;
        this.audience = audience;
        this.redeemTime = redeemTime;
    }

    public watch_gig(int watchID) {
        this.watchID = watchID;
    }



    public int getWatchID() {
        return watchID;
    }

}
