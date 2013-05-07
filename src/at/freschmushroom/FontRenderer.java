package at.freschmushroom;

import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL11;

public class FontRenderer
{
	public static VBORenderer renderer = VBORenderer.instance;
	public static final BufferedImage font = TextureManager.instance
			.getImage("/font.png");

	private static final java.util.Map<Character, Integer[]> charDims = new java.util.HashMap<Character, Integer[]>();
	private static final java.util.Map<Character, Integer[]> charPos = new java.util.HashMap<Character, Integer[]>();
	public static FontRenderer instance = new FontRenderer();

	protected FontRenderer()
	{
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜabcdefghijklmnopqrstuvwxyzäöüß0123456789";
		int[] width = new int[]
		{ /* A */12, /* B */9, /* C */11, /* D */11, /* E */
		8, /* F */8, /* G */12, /* H */10, /* I */6, /* J */6, /* K */10,/* L */8, /* M */
		11,/* N */9, /* O */12, /* P */8, /* Q */12, /* R */11,/* S */10, /* T */
		11, /* U */9, /* V */12, /* W */16,/* X */10,/* Y */10, /* Z */10,/* Ä */
		12, /* Ö */12, /* Ü */9, /* a */9, /* b */9, /* c */8, /* d */9, /* e */10, /* f */
		6, /* g */9, /* h */8, /* i */2, /* j */5, /* k */10,/* l */2, /* m */14,/* n */
		8, /* o */10, /* p */9,/* q */9,/* r */7,/* s */8, /* t */7, /* u */8,/* v */
		9, /* w */13, /* x */9,/* y */9, /* z */7,/* ä */9,/* ö */10,/* ü */8,/* ß */
		9,/* 0 */9,/* 1 */8,/* 2 */9, /* 3 */10, /* 4 */9,/* 5 */10,/* 6 */8, /* 7 */9,
		/* 8 */9, /* 9 */9 };

		for (int i = 0; i < chars.length(); i++)
			charDims.put(chars.charAt(i), new Integer[]
			{ width[i], 15 });
		charDims.put('Ä', new Integer[]
		{ charDims.get('Ä')[0], 18 });
		charDims.put('Ö', new Integer[]
		{ charDims.get('Ö')[0], 18 });
		charDims.put('Ü', new Integer[]
		{ charDims.get('Ü')[0], 18 });
		charDims.put('g', new Integer[]
		{ charDims.get('g')[0], 18 });
		charDims.put('j', new Integer[]
		{ charDims.get('j')[0], 18 });
		charDims.put('p', new Integer[]
		{ charDims.get('p')[0], 18 });
		charDims.put('q', new Integer[]
		{ charDims.get('q')[0], 18 });
		charDims.put('y', new Integer[]
		{ charDims.get('y')[0], 18 });
		for (int i = chars.indexOf('0'); i <= chars.indexOf('9'); i++)
			charDims.put(chars.charAt(i), new Integer[]
			{ charDims.get(chars.charAt(i))[0], 13 });
		int pos = 0;
		for (int i = 0; i <= chars.indexOf('Z'); i++)
		{
			charPos.put(chars.charAt(i), new Integer[]
			{ pos, 3 });
			pos += charDims.get(chars.charAt(i))[0] + 1;
		}
		for (int i = chars.indexOf('Ä'); i <= chars.indexOf('Ü'); i++)
		{
			charPos.put(chars.charAt(i), new Integer[]
			{ pos, 0 });
			pos += charDims.get(chars.charAt(i))[0] + 1;
		}
		pos = 0;
		for (int i = chars.indexOf('a'); i <= chars.indexOf('ß'); i++)
		{
			charPos.put(chars.charAt(i), new Integer[]
			{ pos, 18 });

			pos += charDims.get(chars.charAt(i))[0] + 1;
		}
		pos = 0;
		for (int i = chars.indexOf('0'); i <= chars.indexOf('9'); i++)
		{
			charPos.put(chars.charAt(i), new Integer[]
			{ pos, 37 });
			pos += charDims.get(chars.charAt(i))[0] + 1;
		}
	}

	private void renderCharAt(char c, double x, double y, double x1, double y1)
	{
		if (charDims.get(c) == null || charPos.get(c) == null)
		{
			System.out.println("UNKNOWN CHAR! " + c);
			return;
		}
		float f = 1 / (float) font.getWidth();
		float f1 = 1 / (float) font.getHeight();
		renderer.startQuads();
		renderer.addTexturedVertex(x, y, 0,
				charPos.get(c)[0] * f, charPos.get(c)[1] * f1);
		renderer.addTexturedVertex(x1, y, 0,
				charPos.get(c)[0] * f + charDims.get(c)[0] * f,
				charPos.get(c)[1] * f1);
		renderer.addTexturedVertex(x1, y1, 0,
				charPos.get(c)[0] * f + charDims.get(c)[0] * f,
				charPos.get(c)[1] * f1 + charDims.get(c)[1] * f1);
		renderer.addTexturedVertex(x, y1, 0,
				charPos.get(c)[0] * f, charPos.get(c)[1] * f1
						+ charDims.get(c)[1] * f1);
		renderer.drawToScreen();
	}

	public int getStringWidth(String s)
	{
		s = s.toUpperCase();
		char[] ac = s.toCharArray();
		int cnt = 0;
		for (int i = 0; i < ac.length - 1; i++)
			cnt += charDims.get(ac[i])[0] + 3;
		cnt += charDims.get(ac[ac.length - 1])[0];
		return cnt;
	}

	public void drawStringAt(String string, double x, double y,
			double x1, double y1, float[] color)
	{
		byte[] col = new byte[color.length];
		for (int i = 0; i < color.length; i++)
		{
			col[i] = (byte) (color[i] * 127);
		}
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,
				TextureManager.instance.getTexture("/font.png"));
		final double startX = x;
		for (int i = 0; i < string.length(); i++)
		{
			double k = charDims.get(string.charAt(i))[0];
			k /= getStringWidth(string);
			double x2 = x + (x1 - startX) * k;
			double y2 = y1 + 15;
			renderer.setColor(col);
			GL11.glColor3f(color[0], color[1], color[2]);
			renderCharAt(string.charAt(i), x, y, x2, y2);
			x = x2;
			x += 3 / (double) getStringWidth(string) * (x1 - startX);
		}

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	static
	{
		Out.inf(FontRenderer.class, "29.08.12", "Michael", null);
	}
}
