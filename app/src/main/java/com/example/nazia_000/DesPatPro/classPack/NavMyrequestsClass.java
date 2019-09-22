package com.example.nazia_000.DesPatPro.classPack;

public class NavMyrequestsClass {

    private String amount,bloodGroup;

    public NavMyrequestsClass() {
    }

    public NavMyrequestsClass(String amount, String bloodGroup) {
        this.amount = amount;
        this.bloodGroup = bloodGroup;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
