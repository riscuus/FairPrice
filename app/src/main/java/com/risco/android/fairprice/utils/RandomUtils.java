package com.risco.android.fairprice.utils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Albert Risco on 23/01/2018.
 */

public class RandomUtils {
    public static void main (String [ ] args) {
        long a = 450;
        setRandomPrices(a);
    }
    public static void setRandomPrices(Long longReal){
        int real = longReal.intValue();
        int lowLimit1=real/2;
        int lowLimit2=real-(real/10);
        int highLimit1=real+(real/10);
        int highLimit2=real*2;
        ArrayList<Integer> prices = new ArrayList<>();
        ArrayList<Integer> numbers = new ArrayList<>();

        Random r = new Random();
        for(int i=0; i<3; i++){
            prices.add(r.nextInt(highLimit2-highLimit1)+highLimit1);
            prices.add(r.nextInt(lowLimit2-lowLimit1)+lowLimit1);
            prices.remove(r.nextInt(prices.size()-1));
        }
        System.out.print(prices.toString());

    }
}
