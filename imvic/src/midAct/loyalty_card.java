package midAct;

public class loyalty_card {
    private int loyalty_CardID;
    private int audienceID;
    private String audienceName;
    private int loyaltyPts;

    public loyalty_card(int loyalty_CardID,int audienceID){
        this.loyalty_CardID = loyalty_CardID;
        this.audienceID = audienceID;
    }

    public loyalty_card(String audienceName, int loyaltyPts) {
        this.audienceName =audienceName;
        this.loyaltyPts = loyaltyPts;
    }



    public int getAudienceID() {
        return audienceID;
    }

    public int getLoyalty_CardID() {
        return loyalty_CardID;
    }

    public String getAudienceName() {
        return audienceName;
    }

    public int getLoyaltyPts() {
        return loyaltyPts;
    }
}
