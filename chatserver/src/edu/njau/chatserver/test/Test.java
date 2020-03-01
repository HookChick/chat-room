package edu.njau.chatserver.test;

import edu.njau.chatserver.ui.MainFrame;
import edu.njau.chatserver.util.Tools;

public class Test {

	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		Tools.mainFrameList.add(mainFrame);
		mainFrame.init();
	}
}
