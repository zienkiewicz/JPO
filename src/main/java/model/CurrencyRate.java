package model;

import java.util.Objects;

public class CurrencyRate {
    //private String no;
    private String effectiveDate;
    private String bid;
    private String ask;

    //public String getNo() {return no;}

    //public void setNo(String no) {this.no = no;}

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    @Override
    public boolean equals(Object o)
    {
        return effectiveDate.equals(((CurrencyRate)o).getEffectiveDate()) &&
                bid.equals(((CurrencyRate)o).getBid()) &&
                ask.equals(((CurrencyRate)o).getAsk());
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(effectiveDate.hashCode(),bid.hashCode(),ask.hashCode());
    }
}

