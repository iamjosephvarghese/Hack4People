package hackathon.rm.com.hack4people;

/**
 * Created by josephvarghese on 24/02/18.
 */

public class AudioClass {

    String url;
    String uId;
    Boolean status;
    String answer;

    public AudioClass(String url, String uId, Boolean status, String answer) {
        this.url = url;
        this.uId = uId;
        this.status = status;
        this.answer = answer;
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


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
