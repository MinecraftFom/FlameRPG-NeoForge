package com.fomdev.flamerpg.util;

import java.awt.*;

public class Util
{
    public static Color opposite(Color org)
    {
        int a = org.getAlpha();
        int r = org.getRed();
        int g = org.getGreen();
        int b = org.getBlue();

        int rr = 0xFF - r;
        int rg = 0xFF - g;
        int rb = 0xFF - b;
        return new Color(a, rr, rg, rb);
    }

    public static int round(int d, int dr)
    {
        return d % dr != 0? d / dr + 1: d / dr;
    }
}