package frc.robot.utils;

public class Utilities {

    //In the version of Java used in FRC, apparently the Clamp function does not natively exist within the Math class.
    //The existencde of Math.clamp() is actually relatively recent (Java 21)
    public static double clamp(double value, double max, double min) {
        return Math.max(Math.min(value, max), min);
    }
    
}
