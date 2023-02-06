package lk.icet.pos.view.tm;

public class OrderDetailsTM {
    private String item;
    private String description;
    private int qty;
    private double unitPrice;

    public OrderDetailsTM() {
    }

    public OrderDetailsTM(String item, String description, int qty, double unitPrice) {
        this.item = item;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
