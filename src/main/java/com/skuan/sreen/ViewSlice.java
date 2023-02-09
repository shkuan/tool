package com.skuan.sreen;

import java.awt.geom.Point2D;

public class ViewSlice {

    private int sno;
    private Point2D p0;
    private Point2D p1;
    private Point2D p2;
    private Point2D p3;
    private Point2D anchor;

    public ViewSlice(int sno) {
        this.sno = sno;
    }

    public int getSno() {
        return sno;
    }
    public void setSno(int sno) {
        this.sno = sno;
    }
    public Point2D getP0() {
        return p0;
    }
    public void setP0(Point2D p0) {
        this.p0 = p0;
    }
    public Point2D getP1() {
        return p1;
    }
    public void setP1(Point2D p1) {
        this.p1 = p1;
    }
    public Point2D getP2() {
        return p2;
    }
    public void setP2(Point2D p2) {
        this.p2 = p2;
    }
    public Point2D getP3() {
        return p3;
    }
    public void setP3(Point2D p3) {
        this.p3 = p3;
    }

    public Point2D getAnchor() {
        return anchor;
    }

    public void setAnchor(Point2D anchor) {
        this.anchor = anchor;
    }

    // public void bulid(int ){

    // }
    



}
