package com.example.miwok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    private int mResourceColorid;
    public WordAdapter(Context context, ArrayList<Word> Words,int Backgroundcolor) {
        super(context,0, Words);
        mResourceColorid=Backgroundcolor;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(//here the layoutInflater convert the xml layout to actual view object
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID miwok_text_view.
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        // Get the Miwok translation from the currentWord object and set this text on
        // the Miwok TextView.
        miwokTextView.setText(currentWord.getmiwokTranslation());

        // Find the TextView in the list_item.xml layout with the ID default_text_view.
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        // Get the default translation from the currentWord object and set this text on
        // the default TextView.
        defaultTextView.setText(currentWord.getDefaultTranslation());



        //find Image view in the list_item.xml layout with thr id image
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        //Check if an image is provided for this word or not

        if(currentWord.hasImage()) {
            imageView.setImageResource(currentWord.Getimageid());
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);

        }



        //Adding background
        View textcontainer=listItemView.findViewById(R.id.text_container);
        int color= ContextCompat.getColor(getContext(),mResourceColorid);
        textcontainer.setBackgroundColor(color);

        return listItemView;

    }
}

