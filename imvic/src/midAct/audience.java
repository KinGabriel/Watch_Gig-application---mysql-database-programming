package midAct;

public class audience {
    private int audienceID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String accountType;

    public audience(int audienceID, String fName, String lName,String email){
        this.audienceID = audienceID;
        this.firstName = fName;
        this.lastName = lName;
        this.email = email;
    }

    public audience(String email, String password, String fName, String lName) {
        this.firstName = fName;
        this.lastName = lName;
        this.email = email;
        this.password = password;
    }

    public audience(String email, String password, String fName, String lName, String accountType) {
        this.firstName = fName;
        this.lastName = lName;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
    }
    public audience(int audienceID, String fName, String lName,String email, String accountType){
        this.audienceID = audienceID;
        this.firstName = fName;
        this.lastName = lName;
        this.email = email;
        this.accountType = accountType;
    }

    public audience(int audienceID, String email) {
        this.audienceID = audienceID;
        this.email = email;
    }


    public void setAudienceID(int audienceID) {
        this.audienceID = audienceID;
    }

    public String getAccountType() {
        return accountType;
    }

    public int getAudienceID() {
        return audienceID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
