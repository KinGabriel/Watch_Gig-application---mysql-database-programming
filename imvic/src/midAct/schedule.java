package midAct;

public class schedule {
    private String name;
    private int schedID;
    private int performerID;
    private String date;
    private String startTime;
    private String endTime;
    private int price;

    public schedule(int schedID, String name,int price, String date, String startTime, String endTime){
        this.schedID = schedID;
        this.name = name;
        this.date = date;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public schedule(int schedID){
        this.schedID = schedID;
    }

    public int getPrice() {
        return price;
    }

    public int getSchedID() {
        return schedID;
    }

    public String getDate() {
        return date;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getName() {
        return name;
    }
}
