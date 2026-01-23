// package frc.robot.subsystems.swervedrive;
// 
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import edu.wpi.first.wpilibj2.command.WaitCommand;
// import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
// import frc.robot.Constants;
// 
// import java.lang.reflect.Field;
// import java.lang.reflect.InvocationTargetException;
// import java.lang.reflect.Method;
// import java.lang.reflect.Modifier;
// 
// import com.ctre.phoenix.led.*;
// import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
// 
// 
// /**
//  * LED Subsystem
//  * 
//  * Capable of patterns
//  * 
//  * uses a CANdle for LED control
//  * 
//  * @see ctre.phoenix.led
//  */
// public class Bluetooth extends SubsystemBase {
// 
//   private CANdle candle = new CANdle(3);
// 
//   private String defaultColor = Constants.BluetoothConstants.DEFAULT_COLOR;
// 
// 
//   // Animations
//   private RainbowAnimation rainbowAnim =
//       new RainbowAnimation(0.25, 0.5, Constants.BluetoothConstants.NUM_LEDS);
// 
//   // Bofa these bluetooth
// 
//   // town hall 5 moment
//   // so acurate
//   // yes
// 
//   /**
//    * Initializes the LED Subsystem
//    */
//   public Bluetooth() {}
// 
// 
//   /**
//    * Sets all leds to a solid color given a string containing the name of the color
//    * {@link getColorValues} contains all preset color names
//    * 
//    * @param color The color as a string(Ex: "Blue") to set the led to
//    * @return void
//    * @version 1.1
//    */
//   public void color(String color) {
//     candle.clearAnimation(0);
//     int rGBvalue[] = findColorValues(color);
//     setColor(rGBvalue[0], rGBvalue[1], rGBvalue[2]);
//   }
// 
//   /**
//    * Returns an int array descibring a color in RGB format, provided a String containing the name of
//    * the Color
//    * 
//    * @param color The color as a string(Ex: "Blue") to set the led to Supported Colors are: orange
//    *        eggPlant vomitGreen beige yellow red blue white
//    * 
//    * @return int array containing the 3 RGB values as int
//    * @version 1.0
//    */
//   private int[] findColorValues(String color) {
//     switch (color) {
//       case "orange":
//         return new int[] {255, 121, 0};
//       case "eggPlant":
//         return new int[] {97, 64, 81};
//       case "vomitGreen":
//         return new int[] {137, 162, 3};
//       case "beige":
//         return new int[] {227, 180, 77};
//       case "yellow":
//         return new int[] {235, 229, 52};
//       case "red":
//         return new int[] {255, 0, 0};
//       case "blue":
//         return new int[] {0, 0, 255};
//       case "white":
//         return new int[] {255, 255, 255};
//       case "default":
//         return findColorValues(defaultColor);
//       default:
//         System.out.print("[Bluetooth] Unkown argument passed!");
//         return new int[] {0, 0, 0};
//     }
//   }
// 
// 
//   /**
//    * Sets color of the whole rgb strip based on an RGB value
//    *
//    * @param r Red brightness as int (0-255)
//    * @param g Green brightness as int (0-255)
//    * @param b Blue brightness as int (0-255)
//    * 
//    * @return void
//    * @version 1.0
//    */
//   public void setColor(int r, int g, int b) {
//     // candle.clearAnimation(0);
//     candle.setLEDs(r, g, b);
//   }
// 
//   public void setDefaultColor(String defaultColor) {
//     this.defaultColor = defaultColor;
//   }
// 
// 
//   /**
//    * Turns all Leds on with Rainbow Animation
//    * 
//    * @return void
//    * 
//    * @see com.ctre.phoenix.led.CANdle.animate
//    * @see com.ctre.phoenix.led.RainbowAnimation
//    * 
//    * @version 1.0
//    * @throws InterruptedException
//    */
//   public void rainbow() {
//     // hackerman works
//     candle.clearAnimation(0);
//     candle.animate(rainbowAnim, 0);
//   }
// 
// 
//   /**
//    * Blinks all Leds a certain color
//    * 
//    * @param color The color as a string(Ex: "Blue") to set the led to
//    * @return void
//    * 
//    * @see frc.robot.subsystems.Bluetooth.getColorValues for availible color names
//    * @see com.ctre.phoenix.led.StrobeAnimation.StrobeAnimation
//    * @see com.ctre.phoenix.led.CANdle.animate
//    * 
//    * @version 1.0
//    */
//   public void blink(String color) {
//     candle.clearAnimation(0);
//     int rGBvalue[] = findColorValues(color);
//     StrobeAnimation blinkingAnim = new StrobeAnimation(rGBvalue[0], rGBvalue[1], rGBvalue[2]);
//     blinkingAnim.setSpeed(0.000000009);
//     candle.animate(blinkingAnim, 0);
//   }
// 
//   /**
//    * Scrolling effect by turning the leds off, and then to a solid color, then off again, in a
//    * wave-like motion.
//    * 
//    * @param color The color as a string(Ex: "Blue") to use
//    * @return void
//    * 
//    * @see frc.robot.subsystems.Bluetooth.getColorValues for availible color names
//    * @see com.ctre.phoenix.led.ColorFlowAnimation.ColorFlowAnimation
//    * @see com.ctre.phoenix.led.CANdle.animate
//    * 
//    * @version 1.0
//    */
//   public void scroll(String color1) {
//     candle.clearAnimation(0);
//     int rGBvalue[] = findColorValues(color1);
//     // int rGBvalue2[] = getColorValues(color2);
//     ColorFlowAnimation Anim = new ColorFlowAnimation(rGBvalue[0], rGBvalue[1], rGBvalue[2], 0, 0.1,
//         20, Direction.Forward, 0);
//     // Anim.setSpeed(0.000000009);
//     candle.animate(Anim, 0);
//   }
// 
//   /**
//    * Update the LED strip to reflect the given progress bar.
//    * 
//    * Sets the active and inactive LEDs according to the number of steps in progress and total number
//    * of steps, or indicates interruption or completion if necessary.
//    * 
//    * @param progressBar The given progress bar.
//    */
//   public void updateWithProgressBar(ProgressBar progressBar) {
//     switch (progressBar.getStatus()) {
//       case IN_PROGRESS:
//         int rgb[] = findColorValues(progressBar.getColor());
// 
//         int numLEDsActive =
//             (Constants.BluetoothConstants.NUM_LEDS * progressBar.getStepsInProgress())
//                 / progressBar.getTotalStepsInProgress();
//         int numLEDsInactive = Constants.BluetoothConstants.NUM_LEDS - numLEDsActive;
// 
//         candle.setLEDs(rgb[0], rgb[1], rgb[2], 0, 0, numLEDsActive);
// 
//         if (numLEDsInactive > 0) {
//           candle.setLEDs(0, 0, 0, 0, numLEDsActive + 1, numLEDsInactive);
//         }
//         return;
//       case INTERRUPTED:
//         indicateInterruption();
//         return;
//       case COMPLETED:
//         indicateCompletion();
//         return;
//     }
//   }
// 
//   /**
//    * Indicate interruption.
//    * 
//    * Sets the LED strip to the interruption color.
//    */
//   public void indicateInterruption() {
//     int rgb[] = findColorValues(Constants.BluetoothConstants.INTERRUPTION_COLOR);
// 
//     candle.setLEDs(rgb[0], rgb[1], rgb[2]);
//   }
// 
//   /**
//    * Indicate completion.
//    * 
//    * Sets the LED strip to the completion color.
//    */
//   public void indicateCompletion() {
//     int rgb[] = findColorValues(Constants.BluetoothConstants.COMPLETION_COLOR);
// 
//     candle.setLEDs(rgb[0], rgb[1], rgb[2]);
//   }
// 
//   // Level 5 town hall ran out of elixer
// 
//   /**
//    * Turns All LEDs off
//    * 
//    * @return void
//    * @version 1.0
//    */
//   public void bluetoothOFF() {
//     candle.clearAnimation(0);
//     scroll(Constants.BluetoothConstants.DEFAULT_COLOR);
//   }
// 
//   /**
//    * Gets all fields and getter methods in this class and places their values from shuffleboard
//    * 
//    * @return void
//    * @version 1.0
//    */
//   public void pushData() {
//     String shuffleboardName = this.getClass().getCanonicalName().replace('.', '/').substring(10);
// 
//     Method[] methods = this.getClass().getDeclaredMethods();
//     for (Method method : methods) {
//       if (method.getName().substring(0, 3).equals("get")) {
//         try {
//           Object value = method.invoke(this);
//           if (value == null)
//             value = 0.0; // Set to zero in case we can't run method
//           SmartDashboard.putNumber(shuffleboardName + "/" + method.getName().substring(3),
//               Double.parseDouble(value.toString()));
//           // System.out.println(method.getName().substring(3) + " value:" +
//           // Double.parseDouble(value.toString()));
//         } catch (IllegalAccessException e) {
//           System.out.println("[" + shuffleboardName
//               + "] Somthing went wrong getting Shuffleboard data for: " + method.getName());
//         } catch (InvocationTargetException e) {
//           System.out.println("[" + shuffleboardName
//               + "] Somthing went wrong getting Shuffleboard data for: " + method.getName());
//         }
//       }
//     }
//     Field[] declaredFields = this.getClass().getDeclaredFields();
//     for (Field field : declaredFields) {
//       if (field.getType().isPrimitive()) {
//         try {
//           SmartDashboard.putNumber(shuffleboardName + "/" + field.getName(),
//               field.getDouble(this.getClass()));
//           // System.out.println(field.getName() + " value: " + field.getDouble(this.getClass()));
//         } catch (IllegalAccessException e) {
//           System.out.println("[" + shuffleboardName
//               + "] Somthing went wrong getting Shuffleboard data for: " + field.getName());
//         }
//       }
//     }
//   }
// 
//   /**
//    * Gets all feilds in this class and updates their values from shuffleboard !! Make sure to run
//    * pushData first !!
//    * 
//    * @return void
//    * @version 1.0
//    */
//   public void pullData() {
//     String shuffleboardName = this.getClass().getCanonicalName().replace('.', '/').substring(10);
//     Field[] declaredFields = this.getClass().getDeclaredFields();
//     for (Field field : declaredFields) {
//       if (field.getType().isPrimitive() && !Modifier.isStatic(field.getModifiers())) {
//         try {
//           field.setDouble(this, SmartDashboard.getNumber(
//               shuffleboardName + "/const/" + field.getName(), field.getDouble(this.getClass())));
//           System.out.println(field.getName() + " set " + field.getDouble(this.getClass()));
//         } catch (IllegalAccessException e) {
//           System.out.println("[" + shuffleboardName
//               + "] Somthing went wrong getting Shuffleboard data for: " + field.getName());
//         }
//       }
//     }
//   }
// 
//   /**
//    * Run once every periodic call as long as the Command is running
//    */
//   @Override
//   public void periodic() {
//     if (Constants.verbose_shuffleboard_logging) {
//       pushData();
//       pullData();
//     }
//   }
// }
// 
// 
// 