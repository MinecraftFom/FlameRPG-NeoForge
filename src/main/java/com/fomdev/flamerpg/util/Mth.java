package com.fomdev.flamerpg.util;

public class Mth
{
    public static boolean even(int value)
    {
        return (value & 1) == 0;
    }

    public static boolean odd(int value)
    {
        return (value & 1) == 1;
    }

    public static int round(int d, int dr)
    {
        return d % dr != 0? d / dr + 1: d / dr;
    }
}