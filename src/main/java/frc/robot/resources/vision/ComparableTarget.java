package frc.robot.resources.vision;

interface ComparableTarget<ComparableTargetImpl extends ComparableTarget<ComparableTargetImpl>>
        extends Target {
    boolean compareWith(ComparableTargetImpl other);
}
