package pcl.opensecurity.tileentity;

import li.cil.oc.api.network.SimpleComponent;
import pcl.opensecurity.OpenSecurity;
import pcl.opensecurity.client.sounds.MachineSound;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityMachineBase extends TileEntity implements SimpleComponent {
	
	protected String componentName;
	public TileEntityMachineBase(String name) {
		super();
		this.componentName = name;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if(worldObj.isRemote && hasSound()) {
			updateSound();
		}
	}
	
	// Sound related, thanks to EnderIO code for this!

	@SideOnly(Side.CLIENT)
	private MachineSound sound;

	private ResourceLocation soundRes;

	public String SoundTest;
	
	public String getSoundName() {
		return null;
	}
	
	public ResourceLocation setSound(String sound) {
		soundRes = null;
		soundRes = new ResourceLocation(OpenSecurity.MODID + ":" + sound);
		return soundRes;
	}

	public ResourceLocation getSoundRes() {
		return soundRes;
	}

	public boolean shouldPlaySound() {
		return false;
	}

	public boolean hasSound() {
		return getSoundName() != null;
	}

	public float getVolume() {
		return 1.0f;
	}

	public float getPitch() {
		return 1.0f;
	}

	public boolean shouldRepeat() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	private void updateSound() {
		if(hasSound()) {
			if(shouldPlaySound() && !isInvalid()) {
				if(sound == null) {
					sound = new MachineSound(getSoundRes(), xCoord + 0.5f, yCoord + 0.5f, zCoord + 0.5f, getVolume(), getPitch(), shouldRepeat());
					FMLClientHandler.instance().getClient().getSoundHandler().playSound(sound);
				}
			} else if(sound != null) {
				sound.endPlaying();
				sound = null;
			}
		}
	}

	@Override
	public String getComponentName() {
		return this.componentName;
	}
	
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
	}	
}