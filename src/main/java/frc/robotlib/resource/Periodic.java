package frc.robotlib.resource;

/**
 * A class that may have periodic behavior.
 * 
 * The difference between this, {@link Resource}, and {@link Subresource} is that this interface
 * does <b>not</b> guarantee that {@link #periodic()} will or not be called internally.
 * 
 * @see Resource
 * @see Subresource
 */
public sealed interface Periodic permits Resource, Subresource {
    default void periodic() {}
}
