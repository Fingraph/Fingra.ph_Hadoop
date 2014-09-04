package ph.fingra.hadoop.common;

import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class FingraphConfigTest extends TestCase {
    
    public FingraphConfigTest(String method) {
        super(method);
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new FingraphConfigTest("testFingraphConfig"));
        return suite;
    }
    
    public void testFingraphConfig() {
        try {
            FingraphConfig config = new FingraphConfig();
            assertEquals(config.getHadoop_user_path(), "/home/hadoop/workspace_oss/Fingraph_Hadoop/");
            assertEquals(config.getSetting().getHfs_input_path(), "input/{yyyy}/{MM}/");
            assertEquals(config.getDebug().isDebug_show_verbose(), true);
        }
        catch (IOException ioe) {}
    }
    
}
