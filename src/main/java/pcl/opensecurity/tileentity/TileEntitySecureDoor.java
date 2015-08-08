package pcl.opensecurity.tileentity;

import pcl.opensecurity.OpenSecurity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySecureDoor extends TileEntity {

	String ownerUUID = "";
	String password = "";

	public TileEntitySecureDoor() {

	}

	public void setOwner(String UUID) {
		this.ownerUUID = UUID;
	}

	public void setPassword(String pass) {
		this.password = pass;
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity te = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
			if (te instanceof TileEntitySecureDoor) {
				if (((TileEntitySecureDoor) te).getOwner().equals(this.ownerUUID)) {
					if (((TileEntitySecureDoor) te).getPass().isEmpty() && !this.password.isEmpty()) {
						((TileEntitySecureDoor) te).setPassword(this.password);	
					}
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.ownerUUID = tag.getString("owner");
		this.password = tag.getString("password");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setString("owner", this.ownerUUID);
		tag.setString("password", this.password);
	}

	public String getOwner() {
		return this.ownerUUID;
	}

	public String getPass() {
		return this.password;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (worldObj.getTileEntity(xCoord, yCoord - 1, zCoord) instanceof TileEntitySecureDoor) {
			TileEntitySecureDoor lowerDoor = (TileEntitySecureDoor) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
			if (ownerUUID == null) {
				ownerUUID = lowerDoor.ownerUUID;
			}
		}
	}
}
