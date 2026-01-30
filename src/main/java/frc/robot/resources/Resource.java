package frc.robot.resources;

/**
 * A shared resource that may have periodic behavior.
 * 
 * The difference between this, {@link Periodic}, and {@link Subresource} is that this interface
 * guarantees that {@link #periodic()} is automatically called, and <b>should not be called
 * externally</b>.
 * 
 * @see Periodic
 * @see Subresource
 */
public non-sealed interface Resource extends Periodic {
}
