package com.example.anthony.loot;

public class Upload {
    private String mName;
    private String mDescription;
    private String mImageUrl;
    private String mTimer;

    public Upload(){
        //needed for firebase
    }

    public Upload(String name, String description, String timer, String imageUrl){
        if(name.trim().equals("")){
            name = "No name";
        }

        mName = name;
        mDescription = description;
        mTimer = timer;
        mImageUrl = imageUrl;

    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmTimer() {
        return mTimer;
    }

    public void setmTimer(String mTimer) {
        this.mTimer = mTimer;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

}
