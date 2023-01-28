package org.makingstan.ui.xpdrops;

public class XPDrop {
    public float xp = 0;
    public float y = 0;

    public XPDrop(int xp, int y) {
        this.xp = xp;
        this.y = y;
    }
    public XPDrop(int xp) {
        this.xp = xp;
        this.y = 300;
    }
    public XPDrop() {

    }
}