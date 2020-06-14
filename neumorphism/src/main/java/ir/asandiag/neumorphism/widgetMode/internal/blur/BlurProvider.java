package ir.asandiag.neumorphism.widgetMode.internal.blur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

public class BlurProvider {
    private WeakReference<Context> contextRef;

    public BlurProvider(Context context) {
        contextRef = new WeakReference(context);
    }

    public Bitmap blur(@NotNull Bitmap source, int radius, int sampling) {
        if (radius == -1) {
            radius = BlurFactor.DEFAULT_RADIUS;
        }
        if (sampling == -1) {
            sampling = BlurFactor.DEFAULT_SAMPLING;
        }
        BlurFactor factor = new BlurFactor(source.getWidth(), source.getHeight(), radius, sampling);
        return blur(source, factor);
    }

    private Bitmap blur(Bitmap source, BlurFactor factor) {
        int width = factor.width / factor.sampling;
        int height = factor.height / factor.sampling;
        if (width == 0 || height == 0) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas temp = new Canvas(bitmap);
        temp.scale(1 / (float) factor.sampling, 1 / (float) factor.sampling);
        Paint tempPaint = new Paint(Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);
        tempPaint.setColorFilter(new PorterDuffColorFilter(factor.color, PorterDuff.Mode.SRC_ATOP));
        temp.drawBitmap(source, 0f, 0f, tempPaint);

        Bitmap blurBitmap;


        try {
            blurBitmap = rs(bitmap, factor.radius);
        } catch (Exception e) {
            e.printStackTrace();
            blurBitmap = stack(bitmap, factor.radius, true);
        }

        if (factor.sampling == factor.DEFAULT_SAMPLING) {
            return blurBitmap;
        } else {
            Bitmap scaled = Bitmap.createScaledBitmap(blurBitmap, factor.width, factor.height, true);
            bitmap.recycle();
            return scaled;
        }
    }

    private final Bitmap rs(Bitmap bitmap, int radius) throws RSRuntimeException {
        Context var10000 = contextRef.get();
        if (var10000 != null) {
            Context context = var10000;
            RenderScript rs = null;
            Allocation input = null;
            Allocation output = null;
            ScriptIntrinsicBlur blur = null;
            boolean var10 = false;

            try {
                var10 = true;
                rs = RenderScript.create(context);
                rs.setMessageHandler(new RenderScript.RSMessageHandler());
                input = Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl.MIPMAP_NONE, 1);
                output = Allocation.createTyped(rs, input.getType());
                blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
                blur.setInput(input);
                blur.setRadius((float) radius);
                blur.forEach(output);
                output.copyTo(bitmap);
                var10 = false;
            } finally {
                if (var10) {
                    if (rs != null) {
                        rs.destroy();
                    }

                    if (input != null) {
                        input.destroy();
                    }

                    if (output != null) {
                        output.destroy();
                    }

                    if (blur != null) {
                        blur.destroy();
                    }

                }
            }

            rs.destroy();
            input.destroy();
            if (output != null) {
                output.destroy();
            }

            if (blur != null) {
                blur.destroy();
            }

            return bitmap;
        } else {
            return null;
        }
    }

    private final Bitmap stack(Bitmap sentBitmap, int radius, boolean canReuseInBitmap) {
        if (radius < 1) {
            return null;
        } else {
            Bitmap tempBitmap;
            if (canReuseInBitmap) {
                tempBitmap = sentBitmap;
            } else {
                tempBitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
            }

            Bitmap bitmap = tempBitmap;
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            int[] pix = new int[w * h];
            bitmap.getPixels(pix, 0, w, 0, 0, w, h);
            int wm = w - 1;
            int hm = h - 1;
            int wh = w * h;
            int div = radius + radius + 1;
            int[] r = new int[wh];
            int[] g = new int[wh];
            int[] b = new int[wh];
            int rsum;
            int gsum;
            int bsum;
            int x;
            int y;
            int i;
            int p;
            int yp;
            int yi;
            int yw;
            int[] vmin = new int[Math.max(w, h)];
            int divsum = div + 1 >> 1;
            divsum *= divsum;
            int[] dv = new int[256 * divsum];

            for (i = 0; i < 256 * divsum; ++i) {
                dv[i] = i / divsum;
            }

            yi = 0;
            yw = yi;
            int[][] var29 = new int[div][];
            int[][] stack = var29;

            int stackpointer = 0;
            int stackstart = 0;
            int[] sir = null;
            int rbs;
            int r1 = radius + 1;
            int routsum;
            int goutsum;
            int boutsum;
            int rinsum;
            int ginsum;
            int binsum;

            for (stackstart = 0; stackstart < div; ++stackstart) {
                rbs = 0;
                int[] var44 = new int[3];
                var29[stackstart] = var44;
            }



            for (y = 0; y < h; ++y) {
                bsum = 0;
                gsum = bsum;
                rsum = bsum;
                boutsum = bsum;
                goutsum = bsum;
                routsum = bsum;
                binsum = bsum;
                ginsum = bsum;
                rinsum = bsum;

                for (i = -radius; i <= radius; ++i) {
                    p = pix[yi + Math.min(wm, Math.max(i, 0))];
                    sir = stack[i + radius];
                    sir[0] = (p & 0xff0000) >> 16;
                    sir[1] = (p & 0x00ff00) >> 8;
                    sir[2] = p & 0x0000ff;
                    rbs = r1 - Math.abs(i);
                    rsum += sir[0] * rbs;
                    gsum += sir[1] * rbs;
                    bsum += sir[2] * rbs;
                    if (i > 0) {
                        rinsum += sir[0];
                        ginsum += sir[1];
                        binsum += sir[2];
                    } else {
                        routsum += sir[0];
                        goutsum += sir[1];
                        boutsum += sir[2];
                    }
                }

                stackpointer = radius;

                for (x = 0; x < w; ++x) {
                    r[yi] = dv[rsum];
                    g[yi] = dv[gsum];
                    b[yi] = dv[bsum];
                    rsum -= routsum;
                    gsum -= goutsum;
                    bsum -= boutsum;
                    stackstart = stackpointer - radius + div;
                    sir = stack[stackstart % div];
                    routsum -= sir[0];
                    goutsum -= sir[1];
                    boutsum -= sir[2];
                    if (y == 0) {
                        vmin[x] = Math.min(x + radius + 1, wm);
                    }

                    p = pix[yw + vmin[x]];
                    sir[0] = (p & 0xff0000) >> 16;
                    sir[1] = (p & 0x00ff00) >> 8;
                    sir[2] = p & 0x0000ff;
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                    rsum += rinsum;
                    gsum += ginsum;
                    bsum += binsum;
                    stackpointer = (stackpointer + 1) % div;
                    sir = stack[stackpointer % div];
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                    rinsum -= sir[0];
                    ginsum -= sir[1];
                    binsum -= sir[2];
                    ++yi;
                }

                yw += w;
            }

            for (x = 0; x < w; ++x) {
                bsum = 0;
                gsum = bsum;
                rsum = bsum;
                boutsum = bsum;
                goutsum = bsum;
                routsum = bsum;
                binsum = bsum;
                ginsum = bsum;
                rinsum = bsum;
                yp = -radius * w;

                for (i = -radius; i <= radius; ++i) {
                    yi = Math.max(0, yp) + x;
                    sir = stack[i + radius];
                    sir[0] = r[yi];
                    sir[1] = g[yi];
                    sir[2] = b[yi];
                    rbs = r1 - Math.abs(i);
                    rsum += r[yi] * rbs;
                    gsum += g[yi] * rbs;
                    bsum += b[yi] * rbs;
                    if (i > 0) {
                        rinsum += sir[0];
                        ginsum += sir[1];
                        binsum += sir[2];
                    } else {
                        routsum += sir[0];
                        goutsum += sir[1];
                        boutsum += sir[2];
                    }

                    if (i < hm) {
                        yp += w;
                    }
                }

                yi = x;
                stackpointer = radius;

                for (y = 0; y < h; ++y) {
                    pix[yi] = -0x1000000 & pix[yi] | dv[rsum] << 16 | dv[gsum] << 8 | dv[bsum];
                    rsum -= routsum;
                    gsum -= goutsum;
                    bsum -= boutsum;
                    stackstart = stackpointer - radius + div;
                    sir = stack[stackstart % div];
                    routsum -= sir[0];
                    goutsum -= sir[1];
                    boutsum -= sir[2];
                    if (x == 0) {
                        vmin[y] = Math.min(y + r1, hm) * w;
                    }

                    p = x + vmin[y];
                    sir[0] = r[p];
                    sir[1] = g[p];
                    sir[2] = b[p];
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                    rsum += rinsum;
                    gsum += ginsum;
                    bsum += binsum;
                    stackpointer = (stackpointer + 1) % div;
                    sir = stack[stackpointer];
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                    rinsum -= sir[0];
                    ginsum -= sir[1];
                    binsum -= sir[2];
                    yi += w;
                }
            }

            bitmap.setPixels(pix, 0, w, 0, 0, w, h);
            return bitmap;
        }
    }
}
