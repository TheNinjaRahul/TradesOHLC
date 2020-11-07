package com.upstoxassignment.upstoxassignment.pojo;

public class Trade {
    private String sym;
    private String T;
    private String P;
    private String Q;

    private String TS2;


    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    public String getT() {
        return T;
    }

    public void setT(String t) {
        T = t;
    }

    public String getP() {
        return P;
    }

    public void setP(String p) {
        P = p;
    }

    public String getQ() {
        return Q;
    }

    public void setQ(String q) {
        Q = q;
    }

    public String getTS2() {
        return TS2;
    }

    public void setTS2(String TS2) {
        this.TS2 = TS2;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "sym='" + sym + '\'' +
                ", T='" + T + '\'' +
                ", P='" + P + '\'' +
                ", Q='" + Q + '\'' +
                ", TS2=" + TS2 +
                '}';
    }
}
