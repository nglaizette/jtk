/****************************************************************************
Copyright (c) 2004, Colorado School of Mines and others. All rights reserved.
This program and accompanying materials are made available under the terms of
the Common Public License - v1.0, which accompanies this distribution, and is
available at http://www.eclipse.org/legal/cpl-v10.html
****************************************************************************/
package edu.mines.jtk.sgl;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import edu.mines.jtk.gui.*;

/**
 * A mode for selecting and dragging objects.
 * @author Dave Hale, Colorado School of Mines
 * @version 2005.06.20
 */
public class SelectDragMode extends Mode {
  private static final long serialVersionUID = 1L;

  /**
   * Constructs a select-drag mode with specified manager.
   * @param modeManager the mode manager for this mode.
   */
  public SelectDragMode(ModeManager modeManager) {
    super(modeManager);
    setName("Select");
    Class cls = SelectDragMode.class;
    setIcon(loadIcon(cls,"resources/SelectDragIcon16.png"));
    setCursor(loadCursor(cls,"resources/SelectDragCursor16.png",1,1));
    setMnemonicKey(KeyEvent.VK_S);
    setAcceleratorKey(KeyStroke.getKeyStroke(KeyEvent.VK_S,0));
    setShortDescription("Select/drag");
  }

  ///////////////////////////////////////////////////////////////////////////
  // protected
  
  protected void setActive(Component component, boolean active) {
    if ((component instanceof ViewCanvas)) {
      if (active) {
        component.addMouseListener(_ml);
      } else {
        component.removeMouseListener(_ml);
      }
    }
  }

  ///////////////////////////////////////////////////////////////////////////
  // private

  private ViewCanvas _canvas; // the canvas
  private View _view; // the view
  private World _world; // the world
  private int _xmouse; // mouse x coordinate
  private int _ymouse; // mouse y coordinate

  private MouseListener _ml = new MouseAdapter() {
    public void mousePressed(MouseEvent e) {
      beginSelect(e);
    }
    public void mouseReleased(MouseEvent e) {
    }
  };

  private MouseMotionListener _mml = new MouseMotionAdapter() {
    public void mouseDragged(MouseEvent e) {
    }
  };

  private void beginSelect(MouseEvent event) {
    _xmouse = event.getX();
    _ymouse = event.getY();
    _canvas = (ViewCanvas)event.getSource();
    _canvas.addMouseMotionListener(_mml);
    _view = _canvas.getView();
    _world = _view.getWorld();
    PickContext pc = new PickContext(event);
    _world.pickApply(pc);
    PickResult pr = pc.getClosest();
    if (pr!=null) {
      Point3 pointLocal = pr.getPointLocal();
      Point3 pointWorld = pr.getPointWorld();
      System.out.println("Pick");
      System.out.println("  local="+pointLocal);
      System.out.println("  world="+pointWorld);
      Selectable node = (Selectable)pr.getNode(Selectable.class);
      if (node!=null)
        node.setSelected(true);
    } else {
      System.out.println("Pick nothing");
    }
  }
}

