package midAct;

public class purchase {
    private int purchaseID;
    private int ticketNum;
    private int discount;
    private String purchaseDate;
    private int audienceBuyer;
    private String name;
    private int price;
    private String buyer;



    public purchase(int purchaseID, int ticketNum){
        this.purchaseID = purchaseID;
        this.ticketNum = ticketNum;
    }
    public purchase(int purchaseID,  String name,int price, String purchaseDate){
        this.purchaseID = purchaseID;
        this.price = price;
        this.name = name;
        this.purchaseDate = purchaseDate;
    }
    public purchase(int purchaseID, String buyer, int price, String purchaseDate, String name) {
        this.purchaseID = purchaseID;
        this.buyer = buyer;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.name = name;
    }

    public purchase(int purchaseID, int price,String name, String purchaseDate){
        this.purchaseID = purchaseID;
        this.price = price;
        this.name = name;
        this.purchaseDate = purchaseDate;
    }
    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getPurchaseID() {
        return purchaseID;
    }



    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getBuyer() {
        return buyer;
    }

}
