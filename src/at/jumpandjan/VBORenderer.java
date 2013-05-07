package at.jumpandjan;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import org.lwjgl.opengl.ARBVertexBufferObject;

import at.jumpandjan.Out;

import static org.lwjgl.opengl.GL11.*;

import static org.lwjgl.util.glu.GLU.gluPerspective;

public class VBORenderer
{
	protected static int dimension = 0;

	private static int oldWidth;
	private static int oldHeight;

	public static void useDimension(int dim)
	{
		if (dim == dimension)
			return;
		dimension = dim;
		projection();
	}

	public static void update()
	{
		if (Constants.getDISPLAY_WIDTH() != oldWidth
				|| Constants.getDISPLAY_HEIGHT() != oldHeight)
			projection();
	}

	private static void projection()
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		if (dimension == 3)
		{
			gluPerspective(30, (float) Constants.getDISPLAY_WIDTH()
					/ (float) Constants.getDISPLAY_HEIGHT(), 0.01f, 2500f);
			// glScalef(-1, -1, 1);
		} else if (dimension == 2)
		{
			glOrtho(0, Constants.getDISPLAY_WIDTH(), Constants.getDISPLAY_HEIGHT(), 0, 1,
					-1);

		}
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		oldWidth = Constants.getDISPLAY_WIDTH();
		oldWidth = Constants.getDISPLAY_HEIGHT();
	}
	
	public static final VBORenderer instance;

	private ByteBuffer byteBuffer;
	private IntBuffer intBuffer;
	private IntBuffer vertexBuffers;
	private int[] buffer;

	private int drawMode;
	private int vboIndex;
	private int vertexCount;
	private int bufferIndex;

	private double textureU;
	private double textureV;

	private boolean texture;
	private boolean hasColor;
	
	private int color;

	private VBORenderer()
	{
		int limit = 1048576;
		byteBuffer = ByteBuffer.allocateDirect(limit * 4).order(
				ByteOrder.nativeOrder());
		byteBuffer.asFloatBuffer();
		intBuffer = byteBuffer.asIntBuffer();
		byteBuffer.asShortBuffer();
		vertexBuffers = ByteBuffer.allocateDirect(10 << 2)
				.order(ByteOrder.nativeOrder()).asIntBuffer();
		buffer = new int[limit];
		ARBVertexBufferObject.glGenBuffersARB(vertexBuffers);
	}

	public void addVertex(double x, double y, double z)
	{
		if(bufferIndex >= 1048544)
			drawToScreen();
		vertexCount++;
		if (texture)
		{
			buffer[bufferIndex + 3] = Float.floatToRawIntBits((float) textureU);
			buffer[bufferIndex + 4] = Float.floatToRawIntBits((float) textureV);
		}
		if(hasColor)
		{
			buffer[bufferIndex + 5] = color;
		}
		buffer[bufferIndex + 0] = Float.floatToRawIntBits((float) (x));
		buffer[bufferIndex + 1] = Float.floatToRawIntBits((float) (y));
		buffer[bufferIndex + 2] = Float.floatToRawIntBits((float) (z));
		bufferIndex += 8;
	}

	public void setTextureCoords(double u, double v)
	{
		texture = true;
		textureU = u;
		textureV = v;
	}

	public void startQuads()
	{
		start(GL_QUADS);
	}

	public void startTriangles()
	{
		start(GL_TRIANGLES);
	}

	public void startLines()
	{
		start(GL_LINES);
	}

	public void startPoints()
	{
		start(GL_POINTS);
	}

	public void start(int mode)
	{
		drawMode = mode;
	}

	public void drawToScreen()
	{
		intBuffer.clear();
		intBuffer.put(buffer, 0, bufferIndex);
		byteBuffer.position(0);
		byteBuffer.limit(bufferIndex * 4);
		vboIndex = (vboIndex + 1) % 10;
		ARBVertexBufferObject.glBindBufferARB(
				ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB,
				vertexBuffers.get(vboIndex));
		ARBVertexBufferObject.glBufferDataARB(
				ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, byteBuffer,
				ARBVertexBufferObject.GL_STREAM_DRAW_ARB);
		if (texture)
		{
			glTexCoordPointer(2, GL_FLOAT, 32, 12L);
			glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		}
		if (hasColor)
		{
			glColorPointer(4, GL_UNSIGNED_BYTE, 32, 20L);
			glEnableClientState(GL_COLOR_ARRAY);
		}
		glVertexPointer(3, GL_FLOAT, 32, 0L);
		glEnableClientState(GL_VERTEX_ARRAY);
		if (drawMode == GL_TRIANGLES)// || drawMode == GL_QUADS)
			glDrawArrays(GL_TRIANGLES, 0, vertexCount);
		else
			glDrawArrays(drawMode, 0, vertexCount);
		glDisableClientState(GL_VERTEX_ARRAY);
		if (texture)
			glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		if (hasColor)
			glDisableClientState(GL_COLOR_ARRAY);
			
		vertexCount = 0;
		byteBuffer.clear();
		bufferIndex = 0;
		texture = false;
		hasColor = false;
	}

	public void addTexturedVertex(double x, double y, double z, double u,
			double v)
	{
		setTextureCoords(u, v);
		addVertex(x, y, z);
	}

	public void setColor(byte[] color)
	{
		if(color.length == 3)
			color = new byte[] {color[0], color[1], color[2], (byte) 255};
		this.hasColor = true;
		this.color = color[0] << 24 | color[1] << 16 | color[2] << 8 | color[3];
	}
	
	static {
		instance = new VBORenderer();
		Out.inf(VBORenderer.class, "29.08.2012", "Michael", null);
	}
}