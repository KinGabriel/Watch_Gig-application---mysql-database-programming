package midAct;

public class performers {
    private int performID;
    private String name;
    private String performerType;
    private String thumbnail;

    public performers(int performID, String name, String performerType){
        this.performID = performID;
        this.name = name;
        this.performerType = performerType;
    }



    public String getName() {
        return name;
    }

    public int getPerformID() {
        return performID;
    }

    public String getPerformerType() {
        return performerType;
    }

}
