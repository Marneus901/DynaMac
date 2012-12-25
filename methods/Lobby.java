package org.dynamac.bot.api.methods;

import java.util.ArrayList;
import java.util.List;

import org.dynamac.bot.api.wrappers.InterfaceChild;

/**
 * @author !!1one!eleven1!
 */
public class Lobby
{
	public final static int ID_INTERFACE_LOBBY = 906;
	public final static int ID_INTERFACE_LOGIN = 596;
	public final static int ID_INTERFACE_PLAY_BUTTON = 186;
	public final static int ID_INTERFACE_MAIN_GAME_WINDOW_LOW_DETAIL = 548;

	public static boolean isInLobby()
	{
		final InterfaceChild lobby = Interfaces.get(ID_INTERFACE_LOBBY, 0);
		return lobby != null && lobby.isDisplayed();
	}

	public static boolean enterWorld()
	{
		final InterfaceChild playButton = Interfaces.get(ID_INTERFACE_LOBBY, ID_INTERFACE_PLAY_BUTTON);
		if(playButton == null)
			return false;
		
		playButton.click();
		
		return waitForLogin();
	}
	
	public static boolean enterWorld(final int world)
	{
		if(world <= 0)
			return false;
		
		if(!Lobby.Tab.WORLD_SELECT.selectTab())
			return false;
		
		final InterfaceChild worldbox = Interfaces.get(ID_INTERFACE_LOBBY, World.ID_INTERFACE_ROWS_WORLDENTRY);
		if(worldbox == null)
			return false;
		
		final InterfaceChild[] worldRows = worldbox.getChildren();
		if(worldRows != null && worldRows.length == 0)
			return false;
		
		if(worldRows.length < world)
			return false;
		
		final InterfaceChild worldRow = worldRows[world - 1];
		if(worldRow == null)
			return false;
		
		final InterfaceChild scrollBar = Interfaces.get(ID_INTERFACE_LOBBY, World.ID_INTERFACE_WORLD_SCROLL_BAR);
		if(scrollBar == null)
			return false;
		
		if(!Interfaces.scrollTo(worldRow, scrollBar))
			return false;
		
		worldRow.click();
		
		return waitForLogin();
	}

	private static boolean waitForLogin()
	{
		final Timer loadtimer = new Timer(30000);
		while(loadtimer.isRunning())
		{
			final Dialog dialog = Dialog.getOpenDialog();
			if(Dialog.TRANSFER_COUNTDOWN.isDisplayed() || (dialog != null && dialog.clickContinueButton()))
				loadtimer.reset();
			else if(dialog == null)
				break;				
			
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				break;
			}
		}
		final InterfaceChild mainGame = Interfaces.getChild(ID_INTERFACE_MAIN_GAME_WINDOW_LOW_DETAIL);
		if(mainGame == null)
			return false;
		return mainGame.isDisplayed();
	}

	public static class World
	{	
		private final static int ID_TEXTURE_ISLOOTSHARE = 699;		
		private final static int ID_INTERFACE_WORLDSELECT_SCREEN = 910;

		private final static int ID_INTERFACE_COLUMN_PING = 76;		
		private final static int ID_INTERFACE_COLUMN_ISLOOTSHARE= 76;
		private final static int ID_INTERFACE_COLUMN_ISMEMBERS = 74;
		private final static int ID_INTERFACE_COLUMN_WORLDACTIVITY = 72;
		private final static int ID_INTERFACE_COLUMN_NUMBERPLAYERS = 71;
		private final static int ID_INTERFACE_COLUMN_WORLDNUMBER = 69;
		private final static int ID_INTERFACE_ROWS_WORLDENTRY = 77;
		private final static int ID_INTERFACE_WORLD_SCROLL_BAR = 86;
		
		private final int worldNumber, numPlayers, ping;
		private final boolean isMembers, isLootShare, isOnline;
		private final String activity;

		private World(final int worldNumber, final int numberOfPayers, final int worldPing, final boolean isMembers, final boolean isLootshare, final boolean isOnline, final String worldActivity)
		{
			this.worldNumber = worldNumber;
			this.numPlayers = numberOfPayers;
			this.ping = worldPing;
			this.isMembers = isMembers;
			this.isLootShare = isLootshare;
			this.isOnline = isOnline;
			this.activity = worldActivity;
		}

		public static World getWorld(int worldnum)
		{
			return new World(worldnum, getNumberOfPlayers(worldnum), getPing(worldnum), isMembers(worldnum), isLootShare(worldnum), isOnline(worldnum), getActivity(worldnum));
		}

		public static World[] getAllWorlds()
		{
			final InterfaceChild column = Interfaces.get(ID_INTERFACE_WORLDSELECT_SCREEN, ID_INTERFACE_COLUMN_WORLDNUMBER);
			if(column != null)
			{
				final InterfaceChild[] row = column.getChildren();
				final List<World> worlds = new ArrayList<World>(row.length);
				for(InterfaceChild currentRow : row)
				{
					String worldNum = currentRow.getText();
					if (worldNum == null)
						continue;
					int world;
					try
					{
						world = Integer.parseInt(currentRow.getText());
					}
					catch(NumberFormatException nfe)
					{
						continue;
					}
					worlds.add(new World(world, getNumberOfPlayers(world), getPing(world), isMembers(world), isLootShare(world), isOnline(world), getActivity(world)));
				}
				return worlds.toArray(new World[worlds.size()]);
			}
			return null;
		}

		public static World[] getMembersWorlds()
		{
			final InterfaceChild column = Interfaces.get(ID_INTERFACE_WORLDSELECT_SCREEN, ID_INTERFACE_COLUMN_WORLDNUMBER);
			if(column != null)
			{
				final InterfaceChild[] row = column.getChildren();
				final List<World> worlds = new ArrayList<World>(row.length);
				for(InterfaceChild currentRow : row)
				{
					String worldNum = currentRow.getText();
					if (worldNum == null)
						continue;
					int world;
					try
					{
						world = Integer.parseInt(currentRow.getText());
					}
					catch(NumberFormatException nfe)
					{
						continue;
					}
					final boolean isMembers = isMembers(world);
					if(isMembers)
						worlds.add(new World(world, getNumberOfPlayers(world), getPing(world), isMembers, isLootShare(world), isOnline(world), getActivity(world)));
				}
				return worlds.toArray(new World[worlds.size()]);
			}
			return null;
		}

		public static World[] getFreeWorlds()
		{
			final InterfaceChild column = Interfaces.get(ID_INTERFACE_WORLDSELECT_SCREEN, ID_INTERFACE_COLUMN_WORLDNUMBER);
			if(column != null)
			{
				final InterfaceChild[] row = column.getChildren();
				final List<World> worlds = new ArrayList<World>(row.length);
				for(InterfaceChild currentRow : row)
				{
					String worldNum = currentRow.getText();
					if (worldNum == null)
						continue;
					int world;
					try
					{
						world = Integer.parseInt(currentRow.getText());
					}
					catch(NumberFormatException nfe)
					{
						continue;
					}
					final boolean isMembers = isMembers(world);
					if(!isMembers)
						worlds.add(new World(world, getNumberOfPlayers(world), getPing(world), isMembers, isLootShare(world), isOnline(world), getActivity(world)));
				}
				return worlds.toArray(new World[worlds.size()]);
			}
			return null;
		}

		public int getWorldNumber()
		{
			return worldNumber;
		}

		private static boolean isOnline(final int worldNumber)
		{
			final InterfaceChild column = Interfaces.get(ID_INTERFACE_WORLDSELECT_SCREEN, ID_INTERFACE_COLUMN_NUMBERPLAYERS);
			if(column != null)
			{
				final InterfaceChild[] row = column.getChildren();
				if(row != null && row.length > 0)
				{
					final String players = row[worldNumber - 1].getText();
					if(players != null)
					{
						return !players.equals("OFFLINE");
					}
				}
			}
			return false;
		}

		public boolean isOnline()
		{
			return this.isOnline;
		}

		private static boolean isMembers(final int worldNumber)
		{
			final InterfaceChild column = Interfaces.get(ID_INTERFACE_WORLDSELECT_SCREEN, ID_INTERFACE_COLUMN_ISMEMBERS);
			if(column != null)
			{
				final InterfaceChild[] row = column.getChildren();
				if(row != null && row.length > 0)
					return row[worldNumber - 1].getText().equals("Members");
			}
			return false;
		}

		public boolean isMembers()
		{
			return this.isMembers;
		}

		private static String getActivity(final int worldNumber)
		{
			final InterfaceChild column = Interfaces.get(ID_INTERFACE_WORLDSELECT_SCREEN, ID_INTERFACE_COLUMN_WORLDACTIVITY);
			if(column != null)
			{
				final InterfaceChild[] row = column.getChildren();
				if(row != null && row.length > 0)
					return row[worldNumber - 1].getText();
			}
			return null;
		}

		public String getActivity()
		{
			return this.activity;
		}

		private static boolean isLootShare(final int worldNumber)
		{
			final InterfaceChild column = Interfaces.get(ID_INTERFACE_WORLDSELECT_SCREEN, ID_INTERFACE_COLUMN_ISLOOTSHARE);
			if(column != null)
			{
				final InterfaceChild[] row = column.getChildren();
				if(row != null && row.length > 0)
					return row[worldNumber - 1].getTextureID() == ID_TEXTURE_ISLOOTSHARE;
			}
			return false;
		}

		public boolean isLootShare()
		{
			return this.isLootShare;
		}

		private static int getPing(final int worldNumber)
		{
			final InterfaceChild column = Interfaces.get(ID_INTERFACE_WORLDSELECT_SCREEN, ID_INTERFACE_COLUMN_PING);
			if(column != null)
			{
				final InterfaceChild[] row = column.getChildren();
				if(row != null && row.length > 0)
				{
					final String ping = row[worldNumber - 1].getText();
					if(ping != null)
					{
						try
						{
							return Integer.parseInt(ping);
						}
						catch(NumberFormatException ignored) { }
					}
				}
			}
			return -1;
		}

		public int getPing()
		{
			return this.ping;
		}

		private static int getNumberOfPlayers(final int worldNumber)
		{
			final InterfaceChild column = Interfaces.get(ID_INTERFACE_WORLDSELECT_SCREEN, ID_INTERFACE_COLUMN_NUMBERPLAYERS);
			if(column != null)
			{
				final InterfaceChild[] row = column.getChildren();
				if(row != null && row.length > 0)
				{
					final String players = row[worldNumber - 1].getText();
					if(players != null)
					{
						try
						{
							return Integer.parseInt(players);
						}
						catch(NumberFormatException ignored) { }
					}
				}
			}
			return -1;
		}

		public int getNumberOfPlayers()
		{
			return this.numPlayers;
		}
	}

	public static enum Tab
	{			
		PLAYER_INFO(230),
		WORLD_SELECT(28),
		FRIENDS(27),
		FRIENDS_CHAT(280),
		CLAN_CHAT(26),
		OPTIONS(25);

		private final static int TAB_SELECTED_TEXTURE = 4671;
		private int tabWidgetID;

		private Tab(int widgetID)
		{
			tabWidgetID = widgetID;
		}

		public boolean isSelected()
		{
			final InterfaceChild tab = Interfaces.get(ID_INTERFACE_LOBBY, tabWidgetID);		
			return tab != null && tab.getTextureID() == TAB_SELECTED_TEXTURE;
		}

		private int getID()
		{
			return tabWidgetID;
		}

		public static boolean selectTab(final Tab tab)
		{
			final InterfaceChild tabWidget = Interfaces.get(ID_INTERFACE_LOBBY, tab.getID());		
			if(tabWidget != null)
				if(tabWidget.getTextureID() != TAB_SELECTED_TEXTURE)
					tabWidget.click();

			return tab.isSelected();			
		}
		
		public boolean selectTab()
		{
			if(this.isSelected())
				return true;
			
			final InterfaceChild tabWidget = Interfaces.get(ID_INTERFACE_LOBBY, this.getID());		
			if(tabWidget != null)
				tabWidget.click();

			return this.isSelected();			
		}

		public static Tab getSelectedTab()
		{
			final Tab[] tabs = Tab.values();
			for(Tab tab : tabs)
			{
				if(tab.isSelected())
					return tab;
			}
			return null;
		}
	}

	public static enum Dialog
	{
		TRANSFER_COUNTDOWN(252, 255, -1, "You have only just left another world."),
		ACCOUNT_IN_USE(252, 260, -1, "Your account has not logged out from its last session."),
		LOGIN_LIMIT(252, 260, -1, "Login limit exceeded: too many connections from your address."),
		MEMBERS_ONLY(252, 260, -1, "You need a member's account to log in to this world."),
		SKILL_TOTAL(252, 260, -1, "You must have a total skill level of"),
		HIGH_RISK_WILDERNESS(113, 118, 120, "Warning: This is a High-risk Wilderness world.");

		private final int backButtonIndex;
		private final int continueButtonIndex;
		private final int dialogIndex;
		private final String dialogMessage;

		private Dialog(final int dialogIndex, final int backButtonIndex, final int continueButtonIndex,  final String dialogMessage)
		{
			this.backButtonIndex = backButtonIndex;
			this.continueButtonIndex = continueButtonIndex;
			this.dialogIndex = dialogIndex;
			this.dialogMessage = dialogMessage;
		}

		public boolean isDisplayed()
		{
			final InterfaceChild child = Interfaces.get(ID_INTERFACE_LOBBY, dialogIndex);
			if (child != null && child.isDisplayed())
			{
				final String text = child.getText();
				return text != null && text.equals(dialogMessage);
			}
			return false;
		}

		public boolean hasContinueButton()
		{
			return continueButtonIndex != -1;
		}

		public boolean clickContinueButton()
		{
			if (!hasContinueButton())			
				return false;
			
			final InterfaceChild child = Interfaces.get(ID_INTERFACE_LOBBY, continueButtonIndex);
			if (child != null && child.isDisplayed())
			{
				child.click();
				Timer waitTimer = new Timer(5000);
				while (waitTimer.isRunning() && child != null && child.isDisplayed())
				{
					try
					{
						Thread.sleep(150);
					}
					catch (InterruptedException e)
					{
						break;
					}
				}
				
				return child == null || !child.isDisplayed();
			}

			return false;
		}

		public boolean hasBackButton()
		{
			return backButtonIndex != -1;
		}

		public boolean clickBackButton()
		{
			if (!hasBackButton())			
				return false;
			
			final InterfaceChild child = Interfaces.get(ID_INTERFACE_LOBBY, backButtonIndex);
			if(child != null && child.isDisplayed())
			{
				child.click();
				Timer waitTimer = new Timer(5000);
				while (waitTimer.isRunning() && child != null && child.isDisplayed())
				{
					try
					{
						Thread.sleep(150);
					}
					catch (InterruptedException e)
					{
						break;
					}
				}
				
				return child == null || !child.isDisplayed();
			}
			
			return false;
		}
		
		public static Dialog getOpenDialog()
		{
			for (final Dialog dialog : Dialog.values())
			{
				if (dialog.isDisplayed())
				{
					return dialog;
				}
			}
			return null;
		}
	}
}
