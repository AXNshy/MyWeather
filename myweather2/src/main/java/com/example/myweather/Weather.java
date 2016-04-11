package com.example.myweather;

public class Weather {
    private String sCityName;   //城市名
    private String sCityId;     //城市Id
    private String sPM;             //PM值
    private String sPMLevel;     //PM的级别
    private String sTemp;         //温度
    private String sWeather;     //天气
    private String sWind;         //风
    private String sDate;         //日期

    public Weather(String sCityName,String sCityId, String sPM, String sPMLevel, String sTemp,
                   String sWeather, String sWind, String sDate) {
        super();
        this.sCityName = sCityName;
        this.sCityId=sCityId;
        this.sPM = sPM;
        this.sPMLevel = sPMLevel;
        this.sTemp = sTemp;
        this.sWeather = sWeather;
        this.sWind = sWind;
        this.sDate = sDate;
    }

    public String getsCityName() {
        return sCityName;
    }

    public String getsCityId(){
        return sCityId;
    }

    public void setsCityName(String sCityName) {
        this.sCityName = sCityName;
    }

    public String getsPM() {
        return sPM;
    }

    public void setsPM(String sPM) {
        this.sPM = sPM;
    }

    public String getsPMLevel() {
        return sPMLevel;
    }

    public void setsPMLevel(String sPMNum) {
        this.sPMLevel = sPMLevel;
    }

    public String getsTemp() {
        return sTemp;
    }

    public void setsTemp(String sTemp) {
        this.sTemp = sTemp;
    }

    public String getsWeather() {
        return sWeather;
    }

    public void setsWeather(String sWeather) {
        this.sWeather = sWeather;
    }

    public String getsWind() {
        return sWind;
    }

    public void setsWind(String sWind) {
        this.sWind = sWind;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    @Override
    public String toString() {
        return "Weather [sCityName=" + sCityName + ", sPM=" + sPM + ", sPMLevel="
                + sPMLevel + ", sTemp=" + sTemp + ", sWeather=" + sWeather
                + ", sWind=" + sWind + ", sDate=" + sDate + "]";
    }

}
