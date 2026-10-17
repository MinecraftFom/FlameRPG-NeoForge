package com.fomdev.flamerpg.util;

/**
 * 8 位 RGBA 颜色值（不可变）。
 *
 * <p>用于替代 {@code java.awt.Color}：后者会引入 {@code java.desktop} 模块依赖，
 * 在裁剪过的服务端运行时或 headless 环境下并不一定可用，而本模组的颜色数据
 * 只需要「四个字节」这一件事。</p>
 */
public record RGBColor(int r, int g, int b, int a)
{
    public RGBColor
    {
        checkChannel("r", r);
        checkChannel("g", g);
        checkChannel("b", b);
        checkChannel("a", a);
    }

    /** 以不透明方式构造。 */
    public static RGBColor ofRgb(int r, int g, int b)
    {
        return new RGBColor(r, g, b, 0xFF);
    }

    /** 从 {@code 0xRRGGBB} 形式的整数构造，alpha 取 0xFF。 */
    public static RGBColor ofArgb(int argb)
    {
        return new RGBColor((argb >> 16) & 0xFF, (argb >> 8) & 0xFF, argb & 0xFF, (argb >>> 24) & 0xFF);
    }

    /** 打包为 {@code 0xAARRGGBB}。 */
    public int toArgb()
    {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    /** 反色，保留 alpha。 */
    public RGBColor opposite()
    {
        return new RGBColor(0xFF - r, 0xFF - g, 0xFF - b, a);
    }

    /** 按给定不透明度返回新颜色。 */
    public RGBColor withAlpha(int alpha)
    {
        return new RGBColor(r, g, b, alpha);
    }

    private static void checkChannel(String name, int value)
    {
        if (value < 0 || value > 0xFF)
            throw new IllegalArgumentException("Color channel " + name + " out of range [0, 255]: " + value);
    }
}
