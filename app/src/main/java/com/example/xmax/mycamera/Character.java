package com.example.xmax.mycamera;

/**
 * Created by xmax on 2017/3/22.
 */

public class Character {
    public int top;
    public int down;
    public int left;
    public int right;
    public boolean available;

    public Character(int top, int down, int left, int right) {
        this.top = top;
        this.down = down;
        this.left = left;
        this.right = right;
        this.available = true;
    }

    public void unAvailable(){
        available = false;
    }

    public void printChar(){
        System.out.println("top:"+this.top+" down:"+this.down+" left:"+this.left+" right:"+this.right);
    }
}
