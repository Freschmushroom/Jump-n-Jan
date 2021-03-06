package at.jumpandjan;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class TextureManager
{
	public static final TextureManager instance = new TextureManager();

	private static java.util.Map<String, Integer> texMap = new java.util.HashMap<String, Integer>();
	private static Map<String, BufferedImage> imgMap = new java.util.HashMap<String, BufferedImage>();

	private TextureManager()
	{

	}

	public int getTexture(String texture)
	{
		if (texMap.get(texture) != null && texMap.get(texture) >= 0)
			return texMap.get(texture);
		int name = GL11.glGenTextures();
		BufferedImage bufferedImage = getImage(texture);
		if (bufferedImage == null)
			return -1;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, name);
		int i = bufferedImage.getWidth();
		int j = bufferedImage.getHeight();
		int pixels[] = new int[i * j];
		byte rgba[] = new byte[i * j * 4];
		bufferedImage.getRGB(0, 0, i, j, pixels, 0, i);

		for (int k = 0; k < pixels.length; k++)
		{
			int i1 = pixels[k] >> 24 & 0xff;
			int k1 = pixels[k] >> 16 & 0xff;
			int i2 = pixels[k] >> 8 & 0xff;
			int k2 = pixels[k] & 0xff;

			rgba[k * 4 + 0] = (byte) k1;
			rgba[k * 4 + 1] = (byte) i2;
			rgba[k * 4 + 2] = (byte) k2;
			rgba[k * 4 + 3] = (byte) i1;
		}
		ByteBuffer byteBuffer = BufferUtils.createByteBuffer(rgba.length);
		byteBuffer.put(rgba);
		byteBuffer.position(0);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		// GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
		// GL11.GL_CLAMP);
		// GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
		// GL11.GL_CLAMP);
		// GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
		// GL11.GL_LINEAR);
		// GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
		// GL11.GL_LINEAR);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, i, j, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, byteBuffer);
		texMap.put(texture, name);

		return name;
	}

	public BufferedImage getImage(String image)
	{
		if (imgMap.get(image) != null)
			return imgMap.get(image);
		InputStream is = null;
		try
		{
			System.out.println("Looking for " + image + " inside the program . . .");
			is = getClass().getResourceAsStream(image);
			if (is == null)
			{
				System.out.println("Looking for /img" + image + " inside the program . . .");
				is = getClass().getResourceAsStream("/img" + image);
			}
			if (is == null)
			{
				System.out.println("Looking for " + image + " outside the program . . . ");
				File f = new File(System.getProperty("user.dir") + "/img" + image);
				if (!f.exists() || !f.isFile())
				{
					System.out.println("Looking for /img" + image + " outside the program . . .");
					f = new File(System.getProperty("user.dir"));
				}
				System.out.println("Trying " + f.getAbsolutePath());
				is = new BufferedInputStream(new FileInputStream(f));
			}
			BufferedImage bufferedImage = ImageIO.read(is);
			imgMap.put(image, bufferedImage);
			return bufferedImage;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		} finally
		{
			if (is != null)
				try
				{
					is.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
		}
	}

	static
	{
		Out.inf(TextureManager.class, "29.08.2012", "Michael", null);
	}

	public void bindTexture(int texture)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
	}

	public void bindTexture(String texture)
	{
		bindTexture(getTexture(texture));
	}

	public void unbind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
}
