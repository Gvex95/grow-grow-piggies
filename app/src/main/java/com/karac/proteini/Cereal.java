package com.karac.proteini;

public class Cereal {

    private int mResourceId;
    private String mName;
    private int mMass;
    private float mProteinPercent;

    public Cereal(int resourceId, String name, float proteinPercent){
        mResourceId = resourceId;
        mName = name;
        mProteinPercent = proteinPercent;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getMass() {
        return mMass;
    }

    public void setMass(int mass) {
        mMass = mass;
    }

    public int getResourceId() {
        return mResourceId;
    }

    public void setResourceId(int resourceId) {
        mResourceId = resourceId;
    }

    public float getProteinPercent() {
        return mProteinPercent;
    }

    public void setProteinPercent(float proteinPercent) {
        this.mProteinPercent = proteinPercent;
    }

}
