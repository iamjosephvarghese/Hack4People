package hackathon.rm.com.hack4people;

/**
 * Created by josephvarghese on 24/02/18.
 */

public class FarmerUploadClass {
    String url;
    String farmerId;
    String item;
    Integer qty;


    public FarmerUploadClass(String url, String farmerId, String item, Integer qty) {
        this.url = url;
        this.farmerId = farmerId;
        this.item = item;
        this.qty = qty;
    }

    public FarmerUploadClass() {
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
