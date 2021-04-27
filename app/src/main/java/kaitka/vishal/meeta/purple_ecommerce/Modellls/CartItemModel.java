package kaitka.vishal.meeta.purple_ecommerce.Modellls;

public class CartItemModel {
    public static final int CART_ITEM = 0;
    public static final int TOTAL_AMOUNT = 1;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /////// cart item
    private int product_image;
    private String productTitle;
    private int freeCoupens;
    private String productPrice;
    private String cuttedPrice;
    private int productQuantity;
    private int offerApplied;
    private int coupensApplied;

    public CartItemModel(int type, int product_image, String productTitle, int freeCoupens, String productPrice, String cuttedPrice, int productQuantity, int offerApplied, int coupensApplied) {
        this.type = type;
        this.product_image = product_image;
        this.productTitle = productTitle;
        this.freeCoupens = freeCoupens;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.productQuantity = productQuantity;
        this.offerApplied = offerApplied;
        this.coupensApplied = coupensApplied;
    }

    public int getProduct_image() {
        return product_image;
    }


    public void setProduct_image(int product_image) {
        this.product_image = product_image;
    }


    public String getProductTitle() {
        return productTitle;
    }


    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }


    public int getFreeCoupens() {
        return freeCoupens;
    }


    public void setFreeCoupens(int freeCoupens) {
        this.freeCoupens = freeCoupens;
    }


    public String getProductPrice() {
        return productPrice;
    }


    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }


    public String getCuttedPrice() {
        return cuttedPrice;
    }


    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }


    public int getProductQuantity() {
        return productQuantity;
    }


    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }


    public int getOfferApplied() {
        return offerApplied;
    }


    public void setOfferApplied(int offerApplied) {
        this.offerApplied = offerApplied;
    }


    public int getCoupensApplied() {
        return coupensApplied;
    }


    public void setCoupensApplied(int coupensApplied) {
        this.coupensApplied = coupensApplied;
    }

    /////// cart item


    /////cart total
    private String totalItems;
    private String totalItemPrice;
    private String deliveryPrice;
    private String savedAmount;
    private String totalAmount;

    public CartItemModel(int type, String totalItems, String totalItemPrice, String deliveryPrice,String totalAmount, String savedAmount) {
        this.totalAmount = totalAmount;
        this.type = type;
        this.totalItems = totalItems;
        this.totalItemPrice = totalItemPrice;
        this.deliveryPrice = deliveryPrice;
        this.savedAmount = savedAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(String totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(String savedAmount) {
        this.savedAmount = savedAmount;
    }

    /////cart total


}
