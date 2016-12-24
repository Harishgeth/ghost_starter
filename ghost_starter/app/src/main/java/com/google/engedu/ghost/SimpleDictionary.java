package com.google.engedu.ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        int left=0,right=words.size(),mid,n;
        int comp_result;
        n=prefix.length();
       while(left<=right){
           mid=(left+right)/2;
           if(words.get(mid).startsWith(prefix)){
               return words.get(mid);
           }
        comp_result=prefix.compareToIgnoreCase(words.get(mid).substring(1,n));
           if(comp_result<0)
               right=mid-1;

           else
              left=mid+1;

        }
        return null;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        String any_word=getAnyWordStartingWith(prefix);
        if(any_word==null){
            return null;
        }
        int left_range=words.indexOf(any_word),right_range=words.indexOf(any_word);
        int opt_size=prefix.length()+2;
        int n=words.size();
        boolean stop1=false,stop2=false;
        while((!(stop1)||!(stop2))){
         if(!stop1){
             left_range--;
             if(left_range<0){
                 left_range++;
                 stop1=true;
             }
             if(prefix!=words.get(left_range)){
                 left_range++;
                 stop1=true;
             }
         }
        if(!stop2){
            right_range++;
            if(right_range>n){
                right_range--;
                stop2=true;
            }
            if(prefix!=words.get(right_range)){
                right_range--;
                stop2=true;
            }
         }
       }
        int  i=left_range;
        while(true){

            if(i<=right_range) {
                if (words.get(i).length() ==opt_size){

                    return words.get(i);

                }

            }

            if(i>=right_range){
                if(opt_size<=prefix.length()+6){
                    opt_size+=2;
                    continue;
                }
                else
                break;
            }
            i++;
         }
        return null;
    }


}
