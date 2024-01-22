package com.leo.bsprediction;

public class ShouldersListModel {

    String rightShoulders, leftShoulders;

    public ShouldersListModel(String rightShoulders, String leftShoulders) {
        this.rightShoulders = rightShoulders;
        this.leftShoulders = leftShoulders;
    }

    public ShouldersListModel() {
    }

    public String getRightShoulders() {
        return rightShoulders;
    }

    public void setRightShoulders(String rightShoulders) {
        this.rightShoulders = rightShoulders;
    }

    public String getLeftShoulders() {
        return leftShoulders;
    }

    public void setLeftShoulders(String leftShoulders) {
        this.leftShoulders = leftShoulders;
    }

}
