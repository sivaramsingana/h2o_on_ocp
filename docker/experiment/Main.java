import java.io.IOException;

import ai.h2o.mojos.runtime.MojoPipeline;
import ai.h2o.mojos.runtime.frame.MojoFrame;
import ai.h2o.mojos.runtime.frame.MojoFrameBuilder;
import ai.h2o.mojos.runtime.frame.MojoRowBuilder;
import ai.h2o.mojos.runtime.utils.SimpleCSV;
import ai.h2o.mojos.runtime.lic.LicenseException;

public class Main {

  public static void main(String[] args) throws IOException, LicenseException {
    // Load model and csv
    MojoPipeline model = MojoPipeline.loadFrom("pipeline.mojo");

    // Get and fill the input columns
    MojoFrameBuilder frameBuilder = model.getInputFrameBuilder();
    MojoRowBuilder rowBuilder = frameBuilder.getMojoRowBuilder();
    rowBuilder.setValue("AGE", "68");
    rowBuilder.setValue("RACE", "2");
    rowBuilder.setValue("DCAPS", "2");
    rowBuilder.setValue("VOL", "0");
    rowBuilder.setValue("GLEASON", "6");
    frameBuilder.addRow(rowBuilder);

    // Create a frame which can be transformed by MOJO pipeline
    MojoFrame iframe = frameBuilder.toMojoFrame();

    // Transform input frame by MOJO pipeline
    MojoFrame oframe = model.transform(iframe);
    // `MojoFrame.debug()` can be used to view the contents of a Frame
    // oframe.debug();

    // Output prediction as CSV
    SimpleCSV outCsv = SimpleCSV.read(oframe);
    outCsv.write(System.out);
  }
}
