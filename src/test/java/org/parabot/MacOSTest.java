package org.parabot;

import org.junit.Assert;
import org.junit.Test;
import org.parabot.api.misc.OperatingSystem;
import org.parabot.api.ui.SwingUtil;

import javax.swing.*;

public class MacOSTest {

    @Test
    public void testMacOS() {
        if (OperatingSystem.getOS().equals(OperatingSystem.MAC)) {
            JFrame frame = new JFrame();
            frame.setSize(500, 500);
            Assert.assertTrue(SwingUtil.setParabotIcon(frame));
        }
    }
}
