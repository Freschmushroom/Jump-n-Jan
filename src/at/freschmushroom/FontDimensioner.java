package at.freschmushroom;

public class FontDimensioner
{
	public static float[][] dim(char c, double w, double h)
	{
		float[][] vertics = FontParser.getCharVertices(c);
		double lw = 0, lh = 0;
		double resW, resH;
		for (float[] f : vertics)
		{
			if (lw < f[0])
			{
				lw = (int) f[0];
			}
			if (lh < f[1])
			{
				lh = (int) f[1];
			}
		}
		lw += 10;
		lh += 10;
		resW = w / lw;
		resH = h / lh;
		float[][] v = new float[vertics.length][2];
		for (int i = 0; i < vertics.length; i++)
		{
			v[i][0] = (float) (vertics[i][0] * resW);
			v[i][1] = (float) (vertics[i][1] * resH);
		}
		return v;
	}
}
