package com.fomdev.flamerpg.util;

/**
 * 通用工具。
 *
 * <p>数值相关的实现统一收敛到 {@link Mth}，本类只做转发，避免同一份
 * 逻辑在两个类里各写一遍。</p>
 */
public final class Util
{
    private Util()
    {
    }

    /** @see RGBColor#opposite() */
    public static RGBColor opposite(RGBColor org)
    {
        return org.opposite();
    }

    /** @see Mth#round(int, int) */
    public static int round(int d, int dr)
    {
        return Mth.round(d, dr);
    }

    /** @see Mth#even(int) */
    public static boolean even(int value)
    {
        return Mth.even(value);
    }

    /** @see Mth#odd(int) */
    public static boolean odd(int value)
    {
        return Mth.odd(value);
    }
}
