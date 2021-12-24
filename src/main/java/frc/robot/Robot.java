// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  XboxController controller = new XboxController(0);
  WPI_VictorSPX m_left_1 = new WPI_VictorSPX(1);
  WPI_VictorSPX m_left_2 = new WPI_VictorSPX(2);
  WPI_VictorSPX m_right_1 = new WPI_VictorSPX(3);
  WPI_VictorSPX m_right_2 = new WPI_VictorSPX(4);
  WPI_VictorSPX roll_front = new WPI_VictorSPX(5);
  WPI_VictorSPX roll_rear = new WPI_VictorSPX(6);
  SpeedControllerGroup m_left = new SpeedControllerGroup(m_left_1, m_left_2);
  SpeedControllerGroup m_right = new SpeedControllerGroup(m_right_1, m_right_2);
  DifferentialDrive drive = new DifferentialDrive(m_left, m_right);

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    int POV = controller.getPOV();
    switch(POV){
      case 0:drive.arcadeDrive(0.5, 0);break;
      case 45:drive.arcadeDrive(0.5, 0.4);break;
      case 90:drive.arcadeDrive(0, 0.5);break;
      case 135:drive.arcadeDrive(-0.5, -0.4);break;
      case 180:drive.arcadeDrive(-0.5, 0);break;
      case 225:drive.arcadeDrive(-0.5, 0.4);break;
      case 270:drive.arcadeDrive(0, -0.5);break;
      case 315:drive.arcadeDrive(0.5, -0.4);break;
      case -1:drive.arcadeDrive(-controller.getY(Hand.kLeft)*0.5, controller.getX(Hand.kRight)*0.6);
    }
    if(controller.getBumper(Hand.kRight))
      roll_front.set(1);
    else
      roll_front.set(0);
    
    if(controller.getBumper(Hand.kLeft))
      roll_rear.set(-1);
    else
      roll_rear.set(0);
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
