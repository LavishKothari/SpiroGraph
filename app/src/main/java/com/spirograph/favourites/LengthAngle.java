package com.spirograph.favourites;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class LengthAngle {

    private static final String DELIMITER = ",";

    private List<Integer> lengths;
    private List<Integer> angles;

    public LengthAngle(List<Integer> lengths, List<Integer> angles) {
        this.lengths = lengths;
        this.angles = angles;
    }

    public List<Integer> getLengths() {
        return lengths;
    }

    public void setLengths(List<Integer> lengths) {
        this.lengths = lengths;
    }

    public List<Integer> getAngles() {
        return angles;
    }

    public void setAngles(List<Integer> angles) {
        this.angles = angles;
    }

    public static String getStringRepresentation(LengthAngle lengthAngle) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer i : lengthAngle.lengths) {
            stringBuilder.append(i + DELIMITER);
        }
        for (Integer i : lengthAngle.angles) {
            stringBuilder.append(i + DELIMITER);
        }
        return stringBuilder.subSequence(0, stringBuilder.length() - 1).toString();
    }

    public static LengthAngle getObject(String string) {
        StringTokenizer stz = new StringTokenizer(string, DELIMITER);
        int n = stz.countTokens();
        if (n % 2 != 0) {
            throw new IllegalArgumentException("Invalid string to be parsed");
        }
        List<Integer> ls = new ArrayList<>();
        List<Integer> as = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (i < n / 2) {
                // populate lengths
                ls.add(Integer.parseInt(stz.nextToken()));
            } else {
                // populate angles
                as.add(Integer.parseInt(stz.nextToken()));
            }
        }
        return new LengthAngle(ls, as);
    }

    @Override
    public String toString() {
        return "Lengths: " + lengths + ", Speed: " + angles;
    }
}
