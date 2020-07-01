package io.cordova.zhmh.bean;

/**
 * Created by Administrator on 2019/8/15 0015.
 */

public class LocationBean {
    /*{
        "success": 1,
            "message": "成功",
            "latitude": "34.819860",
            "longitude": "113.557899",
            "isBaidu":1,
            "signRecordEquipmentId","11165465464"，
            "address":"河南省郑州市862软件园"
    }*/

    private boolean success;

    private String message;

    private String latitude;

    private String longitude;

    private String isBaidu;
    private String signRecordEquipmentId;
    private String address;
    private String altitude;

    private String countryName;
    private String countryCode;
    private String province;
    private String city;
    private String cityCode;
    private String district;
    private String town;
    private String adcode;
    private String street;
    private String streetNumber;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
    public void setLatitude(String latitude){
        this.latitude = latitude;
    }
    public String getLatitude(){
        return this.latitude;
    }
    public void setLongitude(String longitude){
        this.longitude = longitude;
    }
    public String getLongitude(){
        return this.longitude;
    }

    public String getIsBaidu() {
        return isBaidu;
    }

    public void setIsBaidu(String isBaidu) {
        this.isBaidu = isBaidu;
    }

    public String getSignRecordEquipmentId() {
        return signRecordEquipmentId;
    }

    public void setSignRecordEquipmentId(String signRecordEquipmentId) {
        this.signRecordEquipmentId = signRecordEquipmentId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
