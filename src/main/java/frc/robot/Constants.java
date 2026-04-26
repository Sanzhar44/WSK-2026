package frc.robot;

public final class Constants {  
      //Radius of drive wheel in mm
      public static final int wheelRadius             = 55;
      public static final double gearRadius = 12.5;
  
      //Encoder pulses per rotation of motor shaft
      public static final int pulsePerRotation        = 1440;
  
      //Gear ratio between motor shaft and output shaft
      public static final double gearRatio            = 1/1;
  
      //Pulse per rotation combined with gear ratio
      public static final double encoderPulseRatio    = pulsePerRotation * gearRatio;
      public static final double encoderGeareRatio = pulsePerRotation * gearRatio;
  
      //Distance per tick
      public static final double distancePerTick      = (Math.PI * 2 * wheelRadius) / encoderPulseRatio;
      public static final double distanceGearTick = (Math.PI * 2 * gearRadius) / encoderGeareRatio;
  } 
  