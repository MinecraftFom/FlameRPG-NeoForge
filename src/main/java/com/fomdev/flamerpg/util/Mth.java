package com.fomdev.flamerpg.util;

/**
 * 整数数学工具。
 */
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

    /**
     * 向上取整的整数除法，例如 {@code round(7, 3) == 3}。
     *
     * @param d  被除数
     * @param dr 除数，必须为正数
     * @throws ArithmeticException 当 {@code dr == 0} 时
     */
    public static int round(int d, int dr)
    {
        if (dr == 0)
            throw new ArithmeticException("Division by zero");

        int quotient = d / dr;
        return (d % dr != 0 && (d ^ dr) >= 0) ? quotient + 1 : quotient;
    }
}
