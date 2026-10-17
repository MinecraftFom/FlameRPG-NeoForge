package com.fomdev.flamerpg.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.Util;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.awt.*;

/**
 * 8 bit immutable ARGB value
 *
 * <p>In replacement of java.awt, solving the problem of the
 * instability of java.desktop in headless environment for
 * better server support
 * </p>
 */
public record RGBColor(int rgba)
{
    public static final Codec<RGBColor> CODEC;
    public static final StreamCodec<ByteBuf, RGBColor> STREAM_CODEC;

    public RGBColor(int r, int g, int b, int a)
    {
        this(Util.make(
                () ->
                {
                    int ca = (a << 24);
                    int cr = (r << 16);
                    int cg = (g << 8);
                    return ca | cr | cg | b;
                }
        ));
    }

    public RGBColor(int r, int g, int b)
    {
        this(r, g, b, 0xFF);
    }

    public RGBColor
    {
        if (0xFFFFFF - this.rgba() < 0)
            throw new IllegalArgumentException("Some color channels have errors occured");
    }

    public RGBColor(java.awt.Color color)
    {
        this(color.getRGB());
    }

    // Channel ALPHA
    public int alpha()
    {
        return (this.rgba() >> 24) & 0xFF;
    }

    // Channel RED
    public int red()
    {
        return (this.rgba() >> 16) & 0xFF;
    }

    // Channel GREEN
    public int green()
    {
        return (this.rgba() >> 8) & 0xFF;
    }

    // Channel BLUE
    public int blue()
    {
        return this.rgba() & 0xFF;
    }

    public RGBColor opposite()
    {
        int ca = this.alpha();
        int cr = this.red();
        int cg = this.green();
        int cb = this.blue();
        return new RGBColor(0xFF - cr, 0xFF - cg, 0xFF - cb, ca);
    }

    public RGBColor transparent(int alpha)
    {
        return new RGBColor(this.red(), this.green(), this.blue(), alpha);
    }

    public record HSL(double h, double s, double l)
    {
    }

    // Copied from java.awt.Color.HSGtoRGB
    public static RGBColor fromHSBtoRGB(float hue, float saturation, float brightness)
    {
        int r = 0, g = 0, b = 0;
        if (saturation == 0)
            r = g = b = (int) (brightness * 255.0f + 0.5f);
        else
        {
            float h = (hue - (float) Math.floor(hue)) * 6.0f;
            float f = h - (float) Math.floor(h);
            float p = brightness * (1.0f - saturation);
            float q = brightness * (1.0f - saturation * f);
            float t = brightness * (1.0f - (saturation * (1.0f - f)));
            switch ((int) h)
            {
                case 0:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (t * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 1:
                    r = (int) (q * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 2:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (t * 255.0f + 0.5f);
                    break;
                case 3:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (q * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 4:
                    r = (int) (t * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 5:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (q * 255.0f + 0.5f);
                    break;
            }
        }

        return new RGBColor(0xFF, r, g, b);
    }

    static
    {
        CODEC = RecordCodecBuilder.create(
                inst ->
                        inst
                                .group(
                                        Codec.INT
                                                .fieldOf("rgba")
                                                .forGetter(RGBColor::rgba)
                                )
                                .apply(
                                        inst,
                                        RGBColor::new
                                )
        );

        STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.VAR_INT,
                RGBColor::rgba,
                RGBColor::new
        );
    }
}