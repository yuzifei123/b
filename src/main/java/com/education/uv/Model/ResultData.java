package com.education.uv.Model;

import java.io.Serializable;

public class ResultData implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Object data;

    public Object dataOne;

    public String result = "success";

    public String flag;

    public String message;

    public Integer total;

    public Object ranking;


    public Object getRanking() {
        return ranking;
    }

    public void setRanking(Object ranking) {
        this.ranking = ranking;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Object getDataOne() {
        return dataOne;
    }

    public void setDataOne(Object dataOne) {
        this.dataOne = dataOne;
    }
}
