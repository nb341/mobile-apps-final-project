package com.example.version3;
//Class used to provide an easy way to access data from arraylist
public class Memo {
   private long id;
   private String memo;
   private String date;
   private String time;
    //Constructor takes in id, date, memo, time
    public Memo(long id, String date, String memo, String time){
         this.id= id;
         this.memo = memo;
         this.date = date;
         this.time = time;
    }
    public Memo(){
        this.id = id;
        this.memo = memo;
        this.date = date;
        this.time = time;
    }
    //sets id of memo
    public void setId(long id){
         id = id;
    }
    public long getId(){
         return id;
    }
    //sets memo
    public void setMemo(String memo){
         this.memo=memo;
    }
    //gets memo
    public String getMemo(){
        return memo;
    }
    //get time
    public String getTime(){
        return time;
    }
    //sets time
    public void setTime(String time){
        this.time=time;
    }
    //sets data
    public void setDate(String date){
        this.date=date;
    }
    //gets date
    public String getDate(){
        return  date;
    }


}
