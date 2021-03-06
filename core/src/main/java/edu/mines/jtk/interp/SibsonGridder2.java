/****************************************************************************
Copyright 2009, Colorado School of Mines and others.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
****************************************************************************/
package edu.mines.jtk.interp;

import edu.mines.jtk.dsp.Sampling;

/**
 * Gridding by Sibson interpolation of scattered samples of f(x1,x2).
 * This class exists only to implement the interface {@link Gridder2}.
 * It otherwise adds no significant functionality to its base class 
 * {@link SibsonInterpolator2}.
 * @author Dave Hale, Colorado School of Mines
 * @version 2009.07.22
 */
public class SibsonGridder2 extends SibsonInterpolator2 implements Gridder2 {

  /**
   * Constructs a gridder with specified known (scattered) samples.
   * @param f array of known sample values f(x1,x2).
   * @param x1 array of known sample x1 coordinates.
   * @param x2 array of known sample x2 coordinates.
   */
  public SibsonGridder2(float[] f, float[] x1, float[] x2) {
    super(f,x1,x2);
  }

  /**
   * Sets the smoothness of the Sibson interpolant. 
   * Two values for smoothness are possible.
   * <p>
   * If false (the default), the interpolant is C1 everywhere except 
   * at the known sample points, where it is C0, with a discontinuous 
   * derivative. For this default, interpolated values are guaranteed 
   * to be within the range of known sample values. 
   * <p>
   * If true, the interpolant is smoother, C1 everywhere, but interpolated
   * values may be outside the range of known sample values.
   * @param smooth true, for C1 everywhere; false, for C0 at known samples.
   * @see SibsonInterpolator2#setGradientPower(double)
   */
  public void setSmooth(boolean smooth) {
    if (smooth) {
      setGradientPower(1.0);
    } else {
      setGradientPower(0.0);
    }
  }

  /**
   * Sets the known (scattered) samples to be interpolated.
   * @param f array of sample values f(x1,x2).
   * @param x1 array of sample x1 coordinates.
   * @param x2 array of sample x2 coordinates.
   */
  public void setScattered(float[] f, float[] x1, float[] x2) {
    super.setSamples(f,x1,x2);
  }

  /**
   * Computes gridded sample values from the known sample values.
   * Before interpolating, this method sets the bounds to be consistent 
   * with the first and last values of the specified samplings, so that
   * interpolated values will never be null.
   * @param s1 sampling of x1.
   * @param s2 sampling of x2.
   * @return array of gridded sample values.
   */
  public float[][] grid(Sampling s1, Sampling s2) {
    super.setBounds(s1,s2);
    return super.interpolate(s1,s2);
  }
}
