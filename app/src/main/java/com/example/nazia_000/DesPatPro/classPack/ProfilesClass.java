package com.example.nazia_000.DesPatPro.classPack;

import android.os.Parcel;
import android.os.Parcelable;


public class ProfilesClass implements Parcelable {

    private String name, age, number, blood_group, status, address , imgUri , email , user_id ;
    double lat, lon;

    public ProfilesClass(){

    }

    public ProfilesClass(String name, String age, String number,String blood_group,String status,
                         String address,String email,String imgUri,String user_id,double lat,double lon){
        this.name = name;
        this.age = age;
        this.number = number;
        this.blood_group = blood_group;
        this.status = status;
        this.address = address;
        this.email=email;
        this.imgUri = imgUri;
        this.user_id = user_id;
        this.lat = lat;
        this.lon = lon;
    }


    protected ProfilesClass(Parcel in) {
        name = in.readString();
        age = in.readString();
        number = in.readString();
        blood_group = in.readString();
        status = in.readString();
        address = in.readString();
        imgUri = in.readString();
        email = in.readString();
        user_id = in.readString();
        lat = in.readDouble();
        lon = in.readDouble();
    }

    public static final Creator<ProfilesClass> CREATOR = new Creator<ProfilesClass>() {
        @Override
        public ProfilesClass createFromParcel(Parcel in) {
            return new ProfilesClass(in);
        }

        @Override
        public ProfilesClass[] newArray(int size) {
            return new ProfilesClass[size];
        }
    };

    public String getname() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getaddress() { return address; }

    public String getblood_group() { return blood_group; }

    public String getStatus() { return status; }

    public String getnumber() {
        return number;
    }

    public String getUser_id() { return user_id; }

    public double getLat() { return lat; }

    public double getLon() { return lon; }


    public String getImgUri() { return imgUri; }

    public String getEmail() {
        return email;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(age);
        dest.writeString(number);
        dest.writeString(blood_group);
        dest.writeString(status);
        dest.writeString(address);
        dest.writeString(imgUri);
        dest.writeString(email);
        dest.writeString(user_id);
    }

}
