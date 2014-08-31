package at.freschmushroom;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class FontParser
{
	private static HashMap<Character, float[][]> chars = new HashMap<>();

	public static void init()
	{
		try
		{
			chars.put('A', loadFontChar('A'));
			chars.put('B', loadFontChar('B'));
			chars.put('F', loadFontChar('F'));
		} catch (IOException e)
		{
		}
	}

	private static float[][] loadFontChar(char c) throws IOException
	{
		ArrayList<float[]> floats = new ArrayList<float[]>();
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("Font/font_" + c)));
		String n = in.readLine();
		while (n != null && !n.equals(""))
		{
			float[] f = new float[2];
			f[0] = Float.parseFloat(n.split("#")[1]);
			f[1] = Float.parseFloat(n.split("#")[0]);
			floats.add(f);
			n = in.readLine();
		}
		in.close();
		float[][] f = new float[floats.size()][2];
		for (int i = 0; i < floats.size(); i++)
		{
			f[i][0] = floats.get(i)[0];
			f[i][1] = floats.get(i)[1];
		}
		return f;
	}

	public static float[][] getCharVertices(char c)
	{
		return chars.get(c);
	}
}
