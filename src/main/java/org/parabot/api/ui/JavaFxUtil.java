package org.parabot.api.ui;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import org.parabot.api.Configuration;
import org.parabot.api.io.ProgressListener;
import org.parabot.api.io.WebUtil;
import org.parabot.api.output.Verboser;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * If a JavaFX program runs as an "Application" it can only be launched once due to
 * how the UI threadding works. To solve this, JavaFX should be used by embedded the FX content inside an FXPanel,
 * which itself should just fill the content of a empty JFrame.
 *
 * @author Shadowrs
 */
public abstract class JavaFxUtil {

    private       JFrame     frame;
    private       JFXPanel   jfxp;
    private       JavaFxUtil INSTANCE;
    private final URL        end;
    private final File       target;
    private final Class<?> controller;
    private final ProgressListener listener;

    /**
     * Constructor to use when stylesheet.fxml is in the Parabot Jar
     * @param fxmlSheet Location to .fxml such as "/storage/ui/notifications.fxml"
     */
    public JavaFxUtil(final URL fxmlSheet, final Class<?> controller) {
        this.end = fxmlSheet;
        this.target = null;
        this.listener = null;
        this.controller = controller;
        launchJFX();
    }

    /**
     * Constructor to use when stylesheet.fxml requires downloading from a remote target.
     * @param endpoint
     * @param target
     * @param listener
     * @param controller
     */
    public JavaFxUtil(URL endpoint, final File target, final ProgressListener listener, final Class<?> controller) {
        this.end = endpoint;
        this.target = target;
        this.listener = listener;
        this.controller = controller;

        if (!target.exists() || !target.canRead()) {
            WebUtil.downloadFile(end, target, listener);
        }
        Verboser.verbose("ui from "+end);

        launchJFX();
    }

    /**
     * Kick off the GUI by creating a JFrame on the Swing Thread, then load the JavaFX on the Platform Thread
     */
    private void launchJFX() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createJFrame();
                Platform.setImplicitExit(false);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        addJavaFX();
                    }
                });
            }
        });
    }

    /**
     * Create the JFrame and attach the window listener
     */
    private void createJFrame() {
        Verboser.verbose("Creating JFrame for JavaFXPanel");
        if (getFrame() != null) {
            System.err.println("frame exists");
            return;
        }
        frame = new JFrame();
        jfxp  = new JFXPanel();
        getFrame().add(jfxp);
        getFrame().setSize(230, 300);
        getFrame().setLocationRelativeTo(null);
        getFrame().setVisible(true);

        getFrame().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        getFrame().addWindowListener(getWindowAdapter());
        Verboser.verbose("frame created: "+ getFrame());
    }

    protected abstract WindowAdapter getWindowAdapter();

    /**
     * Add the JavaFX based on the controller and stylesheet to the Scene of the JavaFxPanel
     */
    private void addJavaFX() {
        Verboser.verbose("panel init");
        INSTANCE = this;
        try {
            FXMLLoader loader = new FXMLLoader(end); // ui.fxml
            if (loader.getController() == null)
                loader.setController(controller.newInstance());
            Pane page = (Pane) loader.load();

            Scene scene = new Scene(page);
            jfxp.setScene(scene);
            getFrame().pack();

            onLaunched();
            Verboser.verbose("UI showing");
        } catch (IOException | InstantiationException | IllegalAccessException e) {
            System.err.println("Error loading ui.fxml!");
            e.printStackTrace();
        }
    }

    protected abstract void onLaunched();

    public JFrame getFrame() {
        return frame;
    }
}
