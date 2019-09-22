package com.example.nazia_000.DesPatPro.classPack;


public class RequestClass{

    private String name,number, blood_Group,needed_Amount, email;

    public RequestClass(){

    }

    public RequestClass(String name, String number, String blood_Group, String needed_Amount,String email) {
        this.name = name;
        this.number = number;
        this.blood_Group = blood_Group;
        this.needed_Amount = needed_Amount;
        this.email = email;
    }


    public String getname() { return name; }

    public String getnumber() { return number; }

    public String getEmail() { return email; }

    public String getblood_Group() {
        return blood_Group;
    }

    public String getneeded_Amount() {
        return needed_Amount;
    }

}
