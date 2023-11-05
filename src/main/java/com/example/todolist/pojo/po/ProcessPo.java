package com.example.todolist.pojo.po;

public class ProcessPo {
    String account;
    String phone;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ProcessPo(String account, String phone) {
        this.account = account;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessPo processPo = (ProcessPo) o;
        return account.equals(processPo.account) && phone.equals(processPo.phone);
    }

    @Override
    public int hashCode() {
        int result = account.hashCode();
        result = 31 * result + phone.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ProcessPo{" +
                "account='" + account + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
