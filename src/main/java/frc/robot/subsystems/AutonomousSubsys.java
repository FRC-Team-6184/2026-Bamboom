package frc.robot.subsystems;

import java.nio.channels.SeekableByteChannel;
import com.pathplanner.lib.commands.PathPlannerAuto;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap.SoftwareObjects;

/**
 * Exists to manage what auto gets run at the start of the match among some other important things
 */
public class AutonomousSubsys extends SubsystemBase {
    private PathPlannerAuto blueDepotTrench = new PathPlannerAuto("Blue Depot Trench");
    private PathPlannerAuto blueHumanTrench = new PathPlannerAuto("Blue Human Station Trench");
    private PathPlannerAuto redDepotTrench = new PathPlannerAuto("Red Depot Trench");
    private PathPlannerAuto redHumanTrench = new PathPlannerAuto("Red Human Station Trench");
    private PathPlannerAuto practice = new PathPlannerAuto("TestingAndPractice");

    private enum AutoEnum {
        BlueDepotTrench, BlueHumanTrench, RedDepotTrench, RedHumanTrench, Practice
    }

    private SendableChooser<AutoEnum> autoChooser = new SendableChooser<AutoEnum>();

    public AutonomousSubsys() {
        super();

        autoChooser.addOption("Blue Depot Trench", AutoEnum.BlueDepotTrench);
        autoChooser.addOption("Blue Human Trench", AutoEnum.BlueHumanTrench);
        autoChooser.addOption("Red Depot Trench", AutoEnum.RedDepotTrench);
        autoChooser.addOption("Red Human Trench", AutoEnum.RedHumanTrench);
        autoChooser.addOption("TestingAndPractice", AutoEnum.Practice);

        autoChooser.setDefaultOption("Blue Human Trench", AutoEnum.BlueHumanTrench);
        SmartDashboard.putData("AutoChooser", autoChooser);
    }

    public PathPlannerAuto getSelectedAuto() {
        AutoEnum selected = autoChooser.getSelected();
        if (selected == null) {
            setAsBlueAlliance();
            return blueHumanTrench;
        } else {
            switch (selected) {
                case BlueDepotTrench:
                    setAsBlueAlliance();
                    return blueDepotTrench;

                case BlueHumanTrench:
                    setAsBlueAlliance();
                    return blueHumanTrench;

                case RedDepotTrench:
                    setAsRedAlliance();
                    return redDepotTrench;

                case RedHumanTrench:
                    setAsRedAlliance();
                    return redHumanTrench;

                case Practice:
                    setAsBlueAlliance();
                    return practice;

                default: //Defaults to Blue Human Trench in a hail mary, something has gone terribly wrong
                    System.out.println("SOMETHING WITH THE AUTO CHOOSER WENT TERRIBLY WRONG | " + selected.toString());
                    setAsBlueAlliance();
                    return blueHumanTrench;
            }
        }
    }

    private void setAsBlueAlliance() {
        SoftwareObjects.IS_BLUE_ALLIANCE = true;
        SoftwareObjects.IS_RED_ALLIANCE = false;
    }

    private void setAsRedAlliance() {
        SoftwareObjects.IS_BLUE_ALLIANCE = false;
        SoftwareObjects.IS_RED_ALLIANCE = true;
    }



}
