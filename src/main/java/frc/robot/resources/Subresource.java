package frc.robot.resources;

/**
 * A subresource that may have periodic behavior.
 * 
 * The difference between this, {@link Periodic}, and {@link Resource} is that this interface
 * guarantees that {@link #periodic()} is <b>not</b> automatically called, and <b>needs to be called
 * externally</b>.
 * 
 * @see Periodic
 * @see Resource
 */
public non-sealed interface Subresource extends Periodic {

}
