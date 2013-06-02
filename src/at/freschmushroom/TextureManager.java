package at.freschmushroom;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;

import javax.imageio.ImageIO;

import at.freschmushroom.Out;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

/**
 * 
 * @author Michael
 * Provides support for Image loading and much more
 */
public class TextureManager
{
	/**
	 * The TextureManager used
	 */
	public static final TextureManager instance = new TextureManager();

	/**
	 * Maps the texture files with the corresponding OpenGL texture name
	 */
	private static java.util.Map<String, Integer> texMap = new java.util.HashMap<String, Integer>();
	
	/**
	 * Maps the texture files with the corresponding images
	 */
	private static Map<String, BufferedImage> imgMap = new java.util.HashMap<String, BufferedImage>();

	
	/**
	 * Private; YOU SHALL NOT CREATE ZE INSTANCE!
	 */
	private TextureManager()
	{

	}
	
	/**
	 * If not already loaded, loads the texture file at the specified path.
	 * Then, it returns the OpenGL name mapped with the resource.
	 * @param texture The path of the texture file
	 * @return The corresponding OpenGL name
	 */
	public int getTexture(String texture)
	{
		if (texMap.get(texture) != null && texMap.get(texture) >= 0)
			return texMap.get(texture);
		int name = GL11.glGenTextures();
		BufferedImage bufferedImage = getImage(texture);
		if(bufferedImage == null)
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
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, i, j, 0,
				GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, byteBuffer);
		texMap.put(texture, name);
		
		return name;
	}
	
	/**
	 * Parses the given file into a BufferedImage
	 * @param image The path of the texture
	 * @return The image which is generated from the specified file
	 */
	public BufferedImage getImage(String image)
	{
		if(imgMap.get(image) != null)
			return imgMap.get(image);
		InputStream is = null;
		try {
			is = getClass().getResourceAsStream(image);
			if(is == null)
				is = new BufferedInputStream(new FileInputStream(System.getProperty("user.dir") + "/img/" + image));
			return ImageIO.read(is);
		} catch(Exception e) {
			Errorhandling.handle(e);
			return null;
		} finally {
			if(is != null)
				try {
					is.close();
				} catch (IOException e) {
					Errorhandling.handle(e);
				}
		}
	} 
	
	static {
		Out.inf(TextureManager.class, "29.09.12", "Michael", null);
	}

	/**
	 * Binds the specified texture
	 * @param texture The OpenGL name of the texture to bind
	 */
	public void bindTexture(int texture)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
	}
	
	/**
	 * Gets the texture from the path, then binds it
	 * @param texture The path of the texture
	 */
	public void bindTexture(String texture) {
		bindTexture(texture == null ? 0 : getTexture(texture));
	}

	/**
	 * Unbinds textures. Equivalent to bindTexture(0) or bindTexture(null).
	 * Convenience method
	 */
	public void unbind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	/**
	 * Clears the maps. Useful for resetting, e.g. after the Intro
	 */
	public void clearMaps() {
		texMap.clear();
	}
}
