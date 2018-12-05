package com.feed_the_beast.mods.money.net;

import com.feed_the_beast.ftblib.lib.io.DataIn;
import com.feed_the_beast.ftblib.lib.io.DataOut;
import com.feed_the_beast.ftblib.lib.net.MessageToServer;
import com.feed_the_beast.ftblib.lib.net.NetworkWrapper;
import com.feed_the_beast.mods.money.FTBMoney;
import com.feed_the_beast.mods.money.shop.Shop;
import com.feed_the_beast.mods.money.shop.ShopEntry;
import com.feed_the_beast.mods.money.shop.ShopTab;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.server.permission.PermissionAPI;

/**
 * @author LatvianModder
 */
public class MessageDeleteShopEntry extends MessageToServer
{
	private int tab;
	private int id;

	public MessageDeleteShopEntry()
	{
	}

	public MessageDeleteShopEntry(ShopEntry e)
	{
		tab = e.tab.getIndex();
		id = e.getIndex();
	}

	@Override
	public NetworkWrapper getWrapper()
	{
		return FTBMoneyNetHandler.NET;
	}

	@Override
	public void writeData(DataOut data)
	{
		data.writeVarInt(tab);
		data.writeVarInt(id);
	}

	@Override
	public void readData(DataIn data)
	{
		tab = data.readVarInt();
		id = data.readVarInt();
	}

	@Override
	public void onMessage(EntityPlayerMP player)
	{
		if (PermissionAPI.hasPermission(player, FTBMoney.PERM_EDIT_SHOP))
		{
			ShopTab t = Shop.SERVER.tabs.get(tab);
			t.entries.remove(id);
			t.shop.markDirty();
		}
	}
}