package hackathon.rm.com.hack4people;

/**
 * Created by josephvarghese on 24/02/18.
 */

public class AdsClass {
    String vendorId;
    String url;
    String dessc;


    public AdsClass() {
    }

    public AdsClass(String vendorId, String url, String dessc) {
        this.vendorId = vendorId;
        this.url = url;
        this.dessc = dessc;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDessc() {
        return dessc;
    }

    public void setDessc(String dessc) {
        this.dessc = dessc;
    }
}
