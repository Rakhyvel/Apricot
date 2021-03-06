package com.josephs_projects.apricotLibrary;

import java.io.Serializable;

/**
 * This class represents an ordered pair of coordinates. Can be used effectively
 * for 2D positions, velocities, or dimensions.
 * 
 * @author Joseph Shimel
 *
 */
public class Tuple implements Serializable {
	/**
	 * UHHHHHH
	 */
	private static final long serialVersionUID = -8618822965339782712L;
	public double x, y;

	public Tuple() {
		this.x = 0;
		this.y = 0;
	}

	public Tuple(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Tuple(Tuple t) {
		if (t == null) {
			this.x = 0;
			this.y = 0;
			return;
		}

		this.x = t.x;
		this.y = t.y;
	}

	/**
	 * @param t Tuple to be added
	 * @return A new Tuple object whose coordinates are a sum of the original and
	 *         parameter. Neither input Tuples are changed.
	 */
	public Tuple add(Tuple t) {
		return new Tuple(t.x + this.x, t.y + this.y);
	}

	/**
	 * @param t Tuple to increment this by
	 */
	public void inc(Tuple t) {
		x += t.x;
		y += t.y;
	}

	/**
	 * @param t Tuple to subtract from original
	 * @return A new Tuple object whose coordiantes are the difference of the
	 *         original from the parameter. Neither input Tuples coordinates are
	 *         changed.
	 */
	public Tuple sub(Tuple t) {
		return new Tuple(this.x - t.x, this.y - t.y);
	}

	/**
	 * @param t Tuple to decrement this by
	 */
	public void dec(Tuple t) {
		x -= t.x;
		y -= t.y;
	}

	/**
	 * @param t Tuple to find distance to
	 * @return Cartesian distance between the Tuples
	 */
	public double dist(Tuple t) {
		return Math.sqrt((this.x - t.x) * (this.x - t.x) + (this.y - t.y) * (this.y - t.y));
	}

	/**
	 * @param t Tuple to find distance to
	 * @return Cartesian distance between the Tuples squared. Java square root
	 *         function is slow, so using this method for finding the distances
	 *         between many Tuples can offer some time advantages. Squaring distance
	 *         preserves inequality.
	 */
	public double distSquared(Tuple t) {
		return (this.x - t.x) * (this.x - t.x) + (this.y - t.y) * (this.y - t.y);
	}

	/**
	 * @param t Tuple to find cab distance to
	 * @return The Cab Distance between two points. This method is slightly more
	 *         efficient than getDist(), and logically makes more sense on a grid.
	 */
	public double cabDist(Tuple t) {
		return Math.abs(this.x - t.x) + Math.abs(this.y - t.y);
	}

	/**
	 * @return The magnitude of a Tuple, or its distance from the origin.
	 */
	public double magnitude() {
		return Math.sqrt((x * x) + (y * y));
	}

	/**
	 * @param scalar The scalar to multiply by
	 * @return A new Tuple whose coordinates have been multiplied by the given
	 *         scalar. Original Tuples coordiantes are left unchanged.
	 */
	public Tuple scalar(double scalar) {
		return new Tuple(this.x * scalar, this.y * scalar);
	}

	/**
	 * Provides a string representation of a Tuple
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	/**
	 * Copies the values of one tuple to this one without creating another object
	 * 
	 * @param t Tuple to copy x and y from
	 */
	public void copy(Tuple t) {
		this.x = t.x;
		this.y = t.y;
	}

	/**
	 * Checks if a Tuple and another Object are equal. Other Object must not be
	 * null, must be a Tuple object, and must have a distance of 0.6 or less to be equal.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;

		if (o == null)
			return false;

		if (o instanceof Tuple) {
			Tuple t = (Tuple) o;
			// Use getDistSquared() because it is slightly faster
			return distSquared(t) < 0.36;
		}

		return false;
	}

	/**
	 * @return Performs vector normalization, returns new Tuple. Original Tuples coordinates are not changed.
	 */
	public Tuple normalize() {
		double magnitude = magnitude();
		if(magnitude == 0) {
			return new Tuple(0,0);
		}
		return new Tuple(x / magnitude, y / magnitude);
	}
	
	public double dot(Tuple t) {
		return this.x * t.x + this.y * t.y;
	}
	
	public Tuple mod(double x, double y) {
		if(this.x < 0)
			this.x += x;
		return new Tuple(this.x % x, this.y % y);
	}
}
