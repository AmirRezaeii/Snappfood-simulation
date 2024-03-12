package Model;

public class Discount {
    private String username;
    private int amount;
    private String code;

    public Discount(String username, int amount, String code) {
        this.username = username;
        this.amount = amount;
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public int getAmount() {
        return amount;
    }

    public String getCode() {
        return code;
    }
}
