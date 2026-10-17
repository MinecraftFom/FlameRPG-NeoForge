package com.fomdev.flamerpg.util;

/**
 * 8 位 RGBA 颜色值（不可变）。
 *
 * <p>用于替代 {@code java.awt.Color}：后者会引入 {@code java.desktop} 模块依赖，
 * 在裁剪过的服务端运行时或 headless 环境下并不一定可用，而本模组的颜色数据
 * 只需要「四个字节」这一件事。</p>
 *
 * <p>若调用方手里恰好持有 {@code java.awt.Color}，可直接使用
 * {@link #RGBColor(java.awt.Color)} 转换。该构造器是<b>可选的桥接</b>：
 * JVM 对方法描述符中的类型采取惰性解析，只要不调用它，{@code java.awt.Color}
 * 就不会被加载，因此不会影响无 AWT 的运行时。</p>
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

    /**
     * 从 {@code java.awt.Color} 桥接构造。
     *
     * @param color AWT 颜色对象，不得为 {@code null}
     */
    public RGBColor(java.awt.Color color)
    {
        this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
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

    // ─────────────────────────── HSL ───────────────────────────

    /**
     * HSL 表示：色相 {@code h} 取值 {@code [0, 360)}，饱和度 {@code s} 与
     * 亮度 {@code l} 取值 {@code [0, 1]}。
     */
    public record HSL(double h, double s, double l)
    {
    }

    /**
     * 由 HSL 构造颜色，alpha 取 {@code 0xFF}。
     *
     * <p>色相按 360 取模（负值会被折回），饱和度与亮度会被夹取到 {@code [0, 1]}，
     * 因此非法输入不会抛异常，只会被规整。</p>
     */
    public static RGBColor ofHsl(double hue, double saturation, double lightness)
    {
        double h = ((hue % 360.0) + 360.0) % 360.0;
        double s = clamp01(saturation);
        double l = clamp01(lightness);

        double c = (1.0 - Math.abs(2.0 * l - 1.0)) * s;
        double hp = h / 60.0;
        double x = c * (1.0 - Math.abs(hp % 2.0 - 1.0));
        double m = l - c / 2.0;

        double r1;
        double g1;
        double b1;

        switch ((int) Math.floor(hp) % 6)
        {
            case 0 -> { r1 = c; g1 = x; b1 = 0; }
            case 1 -> { r1 = x; g1 = c; b1 = 0; }
            case 2 -> { r1 = 0; g1 = c; b1 = x; }
            case 3 -> { r1 = 0; g1 = x; b1 = c; }
            case 4 -> { r1 = x; g1 = 0; b1 = c; }
            default -> { r1 = c; g1 = 0; b1 = x; }
        }

        return new RGBColor(
                toChannel(r1 + m),
                toChannel(g1 + m),
                toChannel(b1 + m),
                0xFF);
    }

    /** 转换为 HSL。灰度色的色相返回 {@code 0}。 */
    public HSL toHsl()
    {
        double rn = r / 255.0;
        double gn = g / 255.0;
        double bn = b / 255.0;

        double max = Math.max(rn, Math.max(gn, bn));
        double min = Math.min(rn, Math.min(gn, bn));
        double delta = max - min;

        double l = (max + min) / 2.0;

        if (delta == 0.0)
            return new HSL(0.0, 0.0, l);

        double s = delta / (1.0 - Math.abs(2.0 * l - 1.0));

        double h;
        if (max == rn)
            h = 60.0 * (((gn - bn) / delta) % 6.0);
        else if (max == gn)
            h = 60.0 * (((bn - rn) / delta) + 2.0);
        else
            h = 60.0 * (((rn - gn) / delta) + 4.0);

        if (h < 0.0)
            h += 360.0;

        return new HSL(h, s, l);
    }

    private static double clamp01(double value)
    {
        return value < 0.0 ? 0.0 : (value > 1.0 ? 1.0 : value);
    }

    private static int toChannel(double value)
    {
        int channel = (int) Math.round(value * 255.0);
        return channel < 0 ? 0 : (channel > 0xFF ? 0xFF : channel);
    }

    private static void checkChannel(String name, int value)
    {
        if (value < 0 || value > 0xFF)
            throw new IllegalArgumentException("Color channel " + name + " out of range [0, 255]: " + value);
    }
}
