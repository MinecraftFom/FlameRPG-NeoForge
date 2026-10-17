package com.fomdev.flamerpg.util;

public final class Mth
{
    private Mth()
    {
    }

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
        if (dr == 0)
            throw new ArithmeticException("Division by zero");

        int quotient = d / dr;
        return (d % dr != 0 && (d ^ dr) >= 0) ? quotient + 1 : quotient;
    }
}
