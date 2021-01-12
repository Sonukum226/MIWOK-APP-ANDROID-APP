package com.example.miwok;
/*
*{@link word} represents a vocabulary word that the user wnats to learn.
* It contains a default translation and a Miwok translation for that word.
 */

public class Word {
    //*Default translation of the word
    private String mDefaultTranslation;

    //* Miwok translation of the word
    private String mWiokTranslation;
    private static final int NO_IMAGE_PROVIDED=-1;

    private int getimageid=NO_IMAGE_PROVIDED;
    private int mAudioresourceid;

    //* Constructor
    public Word(String DefaultTranslation,String MWiokTranslation,int audioresourceid){
        mWiokTranslation=DefaultTranslation;
        mWiokTranslation=MWiokTranslation;
        mAudioresourceid=audioresourceid;
    }
    //constructor overloading
    public Word(String DefaultTranslation,String MWiokTranslation,int id,int audioresourceid){
        mDefaultTranslation=DefaultTranslation;
        mWiokTranslation=MWiokTranslation;
        getimageid=id;
        mAudioresourceid=audioresourceid;
    }

    //* Get the Defaluttranslation
    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }

    //* Get the Miwok translation of word
    public String getmiwokTranslation(){
        return mWiokTranslation;
    }

    //*Get teh image id
    public int Getimageid(){
        return getimageid;
    }

    public boolean hasImage(){
        return getimageid != NO_IMAGE_PROVIDED;
    }

    public int getmAudioresourceid() {
        return mAudioresourceid;
    }
}
