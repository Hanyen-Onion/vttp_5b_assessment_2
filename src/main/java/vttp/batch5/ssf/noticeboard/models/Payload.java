package vttp.batch5.ssf.noticeboard.models;

import java.util.Date;

public class Payload {
    private String id;
    private Date timeStamp;
    
    public String getId() {    return id;}
    public void setId(String id) {    this.id = id;}
    
    public Date getTimeStamp() {    return timeStamp;}
    public void setTimeStamp(Date timeStamp) {    this.timeStamp = timeStamp;}

    @Override
    public String toString() {
        return "id: " + id + ", timestamp: " + timeStamp;
    }
}
