package hackathon.rm.com.hack4people;

/**
 * Created by josephvarghese on 24/02/18.
 */

public class AudioClass {

    String url;
    String uId;

    public AudioClass(String url, String uId) {
        this.url = url;
        this.uId = uId;
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
}
