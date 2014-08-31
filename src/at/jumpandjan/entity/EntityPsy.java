package at.jumpandjan.entity;

import at.freschmushroom.Out;
import at.freschmushroom.audio.SoundContainer;
import at.jumpandjan.Body;
import at.jumpandjan.level.Level;

/**
 * Funny Entity representing world famous Psy doing his gangnam style.
 * 
 * Everytime he hits you you hear the famous words 'Oppan Gangnam Style'
 * 
 * @author Felix
 *
 */
public class EntityPsy extends Entity
{
	/**
	 * The cooldown for the jump
	 */
	private int cdJump;
	/**
	 * The cooldown for the animation
	 */
	private int cdAnimation;
	/**
	 * The cooldown for the attack
	 */
	private int cdAttack;
	/**
	 * The state of the animation
	 */
	private boolean animState;

	/**
	 * Constructs a new Entity Psy with the deault width and height
	 * 
	 * @param x
	 *            the x coordinate of the top left corner
	 * @param y
	 *            the y coordinate of the top right corner
	 * @param level
	 *            the level the entity should spawn in
	 */
	public EntityPsy(double x, double y, Level level)
	{
		super(x, y, 32, 64, level);
	}

	@Override
	public void update()
	{
		super.update();
		this.motion.x = 0;
		if (Math.abs(level.getPlayer().getPivotX() - this.getPivotX()) < 320 && Math.abs(level.getPlayer().getPivotX() - this.getPivotX()) > 1)
		{
			motion.x = (float) ((level.getPlayer().getPivotX() - this.getPivotX()) / Math.abs(level.getPlayer().getPivotX() - this.getPivotX()) * 1.5f);
		}
		if (level.getPlayer().bounds.y + level.getPlayer().bounds.height < this.bounds.y && onGround && level.getPlayer().onGround)
		{
			if (cdJump <= 0)
			{
				motion.y = -20;
				cdJump = 10;
			}
			else
				cdJump--;
		}
		cdAttack--;
	}

	@Override
	public void collide(Body withObject)
	{
		if (withObject instanceof EntityPlayer && cdAttack <= 0)
		{
			level.getPlayer().hurt(10);
			cdAttack = 120;
			SoundContainer.play_once("opp_psy_attack");
		}
	}

	@Override
	public void render()
	{
		super.renderHealthbar();
		super.render("/Opp_Psy.png", bounds.width, bounds.height, bounds.x, bounds.y, 32, 64, state ^ animState);
		if (cdAnimation == 0)
		{
			animState = !animState;
			cdAnimation = 10;
		}
		cdAnimation--;
	}

	static
	{
		Out.inf(EntityPsy.class, "23.10.12", "Felix", null);
	}
}
