package org.firstinspires.ftc.teamcode.trajectory;

import org.firstinspires.ftc.teamcode.trajectory.markers.Marker;

import java.util.ArrayList;
import java.util.Arrays;

public class Trajectory {

    private static final MovementMotionProfileFactory profileFactory = MovementMotionProfileFactory.getInstance();

    private Path[] paths;
    private MotionProfile profile;
    private Marker[] markers;

    public Trajectory(Path[] paths, MotionProfile profile, Marker[] markers) {
        this.paths = paths;
        this.profile = profile;
        this.markers = markers;
    }

    public double getLength() {
        return profile.getTotalTime();
    }

    public ArrayList<Marker> getMarkerArray() {
        return new ArrayList<Marker>(Arrays.asList(markers));
    }

    public TrajectoryState get(double t) {
        TrajectoryState state = new TrajectoryState();
        state.velocity = profile.getVelocity(t);
        double displacement = profile.getPosition(t);
        double currentDisplacement = 0;
        for (Path p : paths) {
            if (p.length >= displacement - currentDisplacement) {
                state.target = p.getPoint(displacement - currentDisplacement);
                state.rotationalVelocity = p.getHeadingVelocity(displacement - currentDisplacement);
                return state;
            } else {
                currentDisplacement += paths.length;
            }
        }
        //Default to end position
        state.target = paths[paths.length-1].getPoint(paths[paths.length-1].length);
        state.rotationalVelocity = 0;
        return state;
    }

    public static class Builder {

        private ArrayList<Path> b_paths = new ArrayList<Path>();
        private ArrayList<Marker> b_markers = new ArrayList<Marker>();

        // In inches and DEGREES
        private double lastX;
        private double lastY;
        private double lastHeading;

        private MotionProfile b_profile;
        private double b_distance;

        public Builder(double startX, double startY, double startHeading) {
            updateLastPose(startX, startY, startHeading);
            b_paths.clear();
            b_markers.clear();
            b_distance = 0;
        }

        /** Spline to a certain point
         * @param newX          The new x position
         * @param newY          The new y position
         * @param newHeading    The new heading (degrees)
         */
        public Builder splineTo(double newX, double newY, double newHeading) {
            b_paths.add(new Path(lastX, lastY, lastHeading, newX, newY, newHeading));
            updateLastPose(newX, newY, newHeading);
            return this;
        }

        /** Spline to a certain point
         * @param newX          The new x position
         * @param newY          The new y position
         * @param newHeading    The new heading (degrees)
         * @param weight        The heading weight at both waypoints
         */
        public Builder splineTo(double newX, double newY, double newHeading, double weight) {
            b_paths.add(new Path(lastX, lastY, lastHeading, newX, newY, newHeading, weight));
            updateLastPose(newX, newY, newHeading);
            return this;
        }

        /** Spline to a certain point
         * @param newX          The new x position
         * @param newY          The new y position
         * @param newHeading    The new heading (degrees)
         * @param startWeight   The heading weight at the starting waypoint
         * @param endWeight     The heading weight at the ending waypoint
         */
        public Builder splineTo(double newX, double newY, double newHeading, double startWeight, double endWeight) {
            b_paths.add(new Path(lastX, lastY, lastHeading, startWeight, newX, newY, newHeading, endWeight));
            updateLastPose(newX, newY, newHeading);
            return this;
        }

        public Builder addMarker(Marker marker) {
            b_markers.add(marker);
            return this;
        }

        public Trajectory build() {
            return build(true, true);
        }

        public Trajectory build(boolean start, boolean end) {
            // calculate the length
            for (Path i : b_paths) {
                b_distance += i.length;
            }

            // Create the motion profile
            if (start && end) {
                b_profile = profileFactory.generateStandardProfile(b_distance);
            } else if (start) {
                b_profile = profileFactory.generateAccelOnlyProfile(b_distance);
            } else if (end) {
                b_profile = profileFactory.generateDeccelOnlyProfile(b_distance);
            } else {
                b_profile = profileFactory.generateSteadyProfile(b_distance);
            }

            return new Trajectory(b_paths.toArray(new Path[0]), b_profile, b_markers.toArray(new Marker[0]));
        }

        private void updateLastPose(double lX, double lY, double lH) {
            this.lastX = lX;
            this.lastY = lY;
            this.lastHeading = lH;
        }

    }
}
