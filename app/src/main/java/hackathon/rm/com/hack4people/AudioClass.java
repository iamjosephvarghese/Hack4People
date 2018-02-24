package hackathon.rm.com.hack4people;

/**
 * Created by josephvarghese on 24/02/18.
 */

public class AudioClass {

    String url;
    String uId;
    Boolean status;

    public AudioClass(String url, String uId, Boolean status) {
        this.url = url;
        this.uId = uId;
        this.status = status;
    }

    public AudioClass() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
