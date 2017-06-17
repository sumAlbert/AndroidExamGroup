package hash;

import android.util.Log;

/**
 * Created by dell2 on 2017/6/17.
 */

public class HashName {
    private static String characters = "abcdefghijklmnopqrstuvwxyz";
    public static  String getHashRandom(int num){
        StringBuffer sb = new StringBuffer();
        int len = characters.length();
        for (int i = 0; i < num; i++) {
            sb.append(characters.charAt((int) Math.floor(Math.random()*(len-1))));
        }
        return sb.toString();
    }


}
