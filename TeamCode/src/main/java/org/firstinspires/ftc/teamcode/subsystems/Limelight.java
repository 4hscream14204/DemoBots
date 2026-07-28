package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import java.util.List;

public class Limelight {

    public enum LimelightPipeline{
        FIRST(0),
        SECOND(1),
        THIRD(2);
        public final int value;
        LimelightPipeline(int pipelineValue){
            value = pipelineValue;
        }
    }

    Limelight3A limelight;

    public Limelight(Limelight3A m_limelight){
        limelight = m_limelight;
        limelight.setPollRateHz(100);
    }

    public void initLimelight(LimelightPipeline pipelineValue){
        limelight.pipelineSwitch(pipelineValue.value);
        limelight.start();
    }

    public LLResult getLatestResult(){
        return limelight.getLatestResult();
    }

    public void update(){
        getLatestResult();
    }

    public int getAprilTagID(){
        int id = 0;
        List<LLResultTypes.FiducialResult> aprilTagResult = getLatestResult().getFiducialResults();
        for (LLResultTypes.FiducialResult fr : aprilTagResult) {
            id = fr.getFiducialId();
        }
        return id;
    }

    public void changePipeline(LimelightPipeline pipeline){
        limelight.pipelineSwitch(pipeline.value);
    }

}
