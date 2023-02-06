package lk.icet.pos.view.tm;

import javafx.scene.control.Button;

public class CartTM {
    private String itemId;
    private String description;
    private double unitPrice;
    private int QTY;
    private double total;
    private Button btn;

    public CartTM() {
    }

    public CartTM(String itemId, String description, double unitPrice, int QTY, double total, Button btn) {
        this.itemId = itemId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.QTY = QTY;
        this.total = total;
        this.btn = btn;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQTY() {
        return QTY;
    }

    public void setQTY(int QTY) {
        this.QTY = QTY;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
