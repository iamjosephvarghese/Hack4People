package hackathon.rm.com.hack4people;

import android.content.Intent;

/**
 * Created by josephvarghese on 25/02/18.
 */

public class MiddleClass {
    String uid;
    String name;
    Integer rating;
    String address;
    Integer contactNo;


    public MiddleClass(String uid, String name, Integer rating, String address, Integer contactNo) {
        this.uid = uid;
        this.name = name;
        this.rating = rating;
        this.address = address;
        this.contactNo = contactNo;
    }

    public MiddleClass() {
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getContactNo() {
        return contactNo;
    }

    public void setContactNo(Integer contactNo) {
        this.contactNo = contactNo;
    }
}
