/**
 * Copyright 2014 tgrape Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ph.fingra.hadoop.mapred.common;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

import ph.fingra.hadoop.common.ConstantVars;

public class CopyToLocalFile {
    
    private Configuration conf;
    
    public CopyToLocalFile() {
        this.conf = new Configuration();
    }
    
    public Configuration getConf() {
        return this.conf;
    }
    
    public void dirToFile(String srcdir, String dstfile) throws IOException {
        
        FileSystem fs = FileSystem.get(URI.create(srcdir), getConf());
        FileSystem local = FileSystem.getLocal(getConf());
        Path srcPath = new Path(srcdir);
        Path dstPath = new Path(dstfile);
        
        // delete existed destination local file
        if (local.exists(dstPath)) {
            local.delete(dstPath, true);
        }
        
        // get hdfs file list
        PathFilter resultFileFilter = new PathFilter() {
            @Override
            public boolean accept(Path path) {
                return path.getName().startsWith(ConstantVars.RESULT_FILE_PREFIX);
            }
        };
        
        FileStatus[] status = fs.listStatus(srcPath, resultFileFilter);
        
        Path[] listedPaths = FileUtil.stat2Paths(status);
        
        if (listedPaths.length > 0 ) {
            // create local output stream
            FSDataOutputStream out = local.create(dstPath);
            for (int i=0; i<listedPaths.length; i++) {
                // create hdfs input stream
                FSDataInputStream in = fs.open(listedPaths[i]);
                byte buffer[] = new byte[256];
                int bytesRead = 0;
                while ( (bytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, bytesRead);
                }
                in.close();
            }
            out.close();
        }
        
        return;
    }
    
}
