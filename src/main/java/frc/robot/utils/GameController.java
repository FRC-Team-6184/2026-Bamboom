package frc.robot.utils;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.PS5Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GameController {
    private final GameControllerOption gameControllerOption = new GameControllerOption();
    public GenericHID internalController;

    public GameController(int port) {
        internalController = new GenericHID(port);
    }

    public double getLeftX() {
        switch (gameControllerOption.getSelected()) {
            case GameControllerOption.XBOX:
                return internalController.getRawAxis(XboxController.Axis.kLeftX.value);
            case GameControllerOption.PS5:
                return internalController.getRawAxis(PS5Controller.Axis.kLeftX.value);
            case GameControllerOption.PS4:
                return internalController.getRawAxis(PS4Controller.Axis.kLeftX.value);
            default:
                return 0;
        }

    }

    public double getLeftY() {
        switch (gameControllerOption.getSelected()) {
            case GameControllerOption.XBOX:
                return internalController.getRawAxis(XboxController.Axis.kLeftY.value);
            case GameControllerOption.PS5:
                return internalController.getRawAxis(PS5Controller.Axis.kLeftY.value);
            case GameControllerOption.PS4:
                return internalController.getRawAxis(PS4Controller.Axis.kLeftY.value);
            default:
                return 0;
        }
    }

    public double getRightX() {
        switch (gameControllerOption.getSelected()) {
            case GameControllerOption.XBOX:
                return internalController.getRawAxis(XboxController.Axis.kRightX.value);
            case GameControllerOption.PS5:
                return internalController.getRawAxis(PS5Controller.Axis.kRightX.value);
            case GameControllerOption.PS4:
                return internalController.getRawAxis(PS4Controller.Axis.kRightX.value);
            default:
                return 0;
        }
    }

    public double getRightY() {
        switch (gameControllerOption.getSelected()) {
            case GameControllerOption.XBOX:
                return internalController.getRawAxis(XboxController.Axis.kRightY.value);
            case GameControllerOption.PS5:
                return internalController.getRawAxis(PS5Controller.Axis.kRightY.value);
            case GameControllerOption.PS4:
                return internalController.getRawAxis(PS4Controller.Axis.kRightY.value);
            default:
                return 0;
        }
    }

    public double getLeftTrigger() {
        switch (gameControllerOption.getSelected()) {
            case GameControllerOption.XBOX:
                return internalController.getRawAxis(XboxController.Axis.kLeftTrigger.value);
            case GameControllerOption.PS5:
                return internalController.getRawAxis(PS5Controller.Axis.kL2.value);
            case GameControllerOption.PS4:
                return internalController.getRawAxis(PS4Controller.Axis.kL2.value);
            default:
                return 0;
        }
    }

    public double getRightTrigger() {
        switch (gameControllerOption.getSelected()) {
            case GameControllerOption.XBOX:
                return internalController.getRawAxis(XboxController.Axis.kRightTrigger.value);
            case GameControllerOption.PS5:
                return internalController.getRawAxis(PS5Controller.Axis.kR2.value);
            case GameControllerOption.PS4:
                return internalController.getRawAxis(PS4Controller.Axis.kR2.value);
            default:
                return 0;
        }
    }

    public boolean getLeftBumper() {
        switch (gameControllerOption.getSelected()) {
            case GameControllerOption.XBOX:
                return internalController.getRawButton(XboxController.Button.kLeftBumper.value);
            case GameControllerOption.PS5:
                return internalController.getRawButton(PS5Controller.Button.kL1.value);
            case GameControllerOption.PS4:
                return internalController.getRawButton(PS4Controller.Button.kL1.value);
            default:
                return false;
        }
    }

    public boolean getRightBumper() {
        switch (gameControllerOption.getSelected()) {
            case GameControllerOption.XBOX:
                return internalController.getRawButton(XboxController.Button.kRightBumper.value);
            case GameControllerOption.PS5:
                return internalController.getRawButton(PS5Controller.Button.kR1.value);
            case GameControllerOption.PS4:
                return internalController.getRawButton(PS4Controller.Button.kR1.value);
            default:
                return false;
        }
    }

    public boolean getFaceButtonLeft() {
        switch (gameControllerOption.getSelected()) {
            case GameControllerOption.XBOX:
                return internalController.getRawButton(XboxController.Button.kX.value);
            case GameControllerOption.PS5:
                return internalController.getRawButton(PS5Controller.Button.kSquare.value);
            case GameControllerOption.PS4:
                return internalController.getRawButton(PS4Controller.Button.kSquare.value);
            default:
                return false;
        }
    }

    public boolean getFaceButtonRight() {
        switch (gameControllerOption.getSelected()) {
            case GameControllerOption.XBOX:
                return internalController.getRawButton(XboxController.Button.kB.value);
            case GameControllerOption.PS5:
                return internalController.getRawButton(PS5Controller.Button.kCircle.value);
            case GameControllerOption.PS4:
                return internalController.getRawButton(PS4Controller.Button.kCircle.value);
            default:
                return false;
        }
    }

    public boolean getFaceButtonUp() {
        switch (gameControllerOption.getSelected()) {
            case GameControllerOption.XBOX:
                return internalController.getRawButton(XboxController.Button.kY.value);
            case GameControllerOption.PS5:
                return internalController.getRawButton(PS5Controller.Button.kTriangle.value);
            case GameControllerOption.PS4:
                return internalController.getRawButton(PS4Controller.Button.kTriangle.value);
            default:
                return false;
        }
    }

    public boolean getFaceButtonDown() {
        switch (gameControllerOption.getSelected()) {
            case GameControllerOption.XBOX:
                return internalController.getRawButton(XboxController.Button.kA.value);
            case GameControllerOption.PS5:
                return internalController.getRawButton(PS5Controller.Button.kCross.value);
            case GameControllerOption.PS4:
                return internalController.getRawButton(PS4Controller.Button.kCross.value);
            default:
                return false;
        }
    }

    public int getDPad() {
        switch (gameControllerOption.getSelected()) {
            case GameControllerOption.XBOX:
                return internalController.getPOV();
            case GameControllerOption.PS5:
                return internalController.getPOV();
            // case GameControllerOption.PS4:
            // return internalController.getRawButton(PS4Controller.Button.kCross.value);
            default:
                return 0;
        }
    }

    public class GameControllerOption {
        private final SendableChooser<String> option = new SendableChooser<>();
        public static final String XBOX = "XBox";
        public static final String PS5 = "Playstation 5";
        public static final String PS4 = "Playstation 4";

        public GameControllerOption() {
            option.setDefaultOption(XBOX, XBOX);
            option.addOption(PS5, PS5);
            option.addOption(PS4, PS4);
            SmartDashboard.putData("Controller Type", option);
        }

        public String getSelected() {
            return option.getSelected();
        }
    }
}