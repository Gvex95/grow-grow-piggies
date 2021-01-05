package com.karac.proteini;

public class Result {
    private float mTotalMass;
    private float mTotalProteinMass;
    private float mProteinPercent;

    public Result(float totalMass, float totalProteinMass, float proteinPercent){
        mTotalMass = totalMass;
        mTotalProteinMass = totalProteinMass;
        mProteinPercent = proteinPercent;
    }

    public float getTotalMass() {
        return mTotalMass;
    }

    public void setTotalMass(float totalMass) {
        mTotalMass = totalMass;
    }

    public float getTotalProteinMass() {
        return mTotalProteinMass;
    }

    public void setTotalProteinMass(float totalProteinMass) {
        mTotalProteinMass = totalProteinMass;
    }

    public float getProteinPercent() {
        return mProteinPercent;
    }

    public void setProteinPercent(float proteinPercent) {
        mProteinPercent = proteinPercent;
    }
}
