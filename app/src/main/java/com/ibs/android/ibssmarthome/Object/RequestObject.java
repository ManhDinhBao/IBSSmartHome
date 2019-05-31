package com.ibs.android.ibssmarthome.Object;

import java.util.Date;

public class RequestObject {
    private String RequestId;
    private Date   RequestDate;
    private String RequestSubject;
    private String RequestContent;
    private int    Status;

    public RequestObject(String requestId, Date requestDate, String requestSubject, String requestContent,int status) {
        RequestId = requestId;
        RequestDate = requestDate;
        RequestSubject = requestSubject;
        RequestContent = requestContent;
        Status         = status;
    }

    public RequestObject() {
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public Date getRequestDate() {
        return RequestDate;
    }

    public void setRequestDate(Date requestDate) {
        RequestDate = requestDate;
    }

    public String getRequestSubject() {
        return RequestSubject;
    }

    public void setRequestSubject(String requestSubject) {
        RequestSubject = requestSubject;
    }

    public String getRequestContent() {
        return RequestContent;
    }

    public void setRequestContent(String requestContent) {
        RequestContent = requestContent;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
