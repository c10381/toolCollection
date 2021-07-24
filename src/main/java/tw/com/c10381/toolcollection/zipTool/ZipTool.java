package tw.com.c10381.toolcollection.zipTool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ZipTool {

  /**
   * 壓縮檔案
   * @param needZipFiles
   * @param zipLocation
   * @return
   */
  public boolean zipFile(Path[] needZipFiles , String zipLocation){
    try(var zout = new ZipOutputStream(new FileOutputStream(zipLocation))){
      for(Path file : needZipFiles){
        var fin = new FileInputStream(file.toAbsolutePath().toString());
        var zipEntry = new ZipEntry(file.getFileName().toString());
        zout.putNextEntry(zipEntry);
        zout.write(fin.readAllBytes());
      }
    }catch(Exception e) {
      log.error(e.toString());
      return false;
    }
    return true;
  }


  /**
   * 解壓縮檔案
   * @param zipLocation
   * @param unZipLocation
   * @return
   */
  public boolean unZipFile(String zipLocation,String unZipLocation){
    var path = Path.of(zipLocation).toAbsolutePath().toString();
    var inputFile = new File(path);
    try(var bis = new ZipInputStream(new FileInputStream(inputFile))){
      ZipEntry entry;
      int c;
      while((entry = bis.getNextEntry()) != null){
        // check path exists
        var unZip = new File(Path.of(unZipLocation).toAbsolutePath().toString());
        if(!unZip.exists()){
          unZip.mkdirs();
        }
        //check file exist
        var unZipPath = Path.of(unZipLocation).toAbsolutePath().toString()+"/" + entry.getName();
        var file = new File(unZipPath);
        if(!file.exists()){
          file.createNewFile();
        }
        // write the file
        try(var os = new FileOutputStream(file)){
          while((c = bis.read()) != -1) {
            os.write(c);
          }
        }
      }
    }catch(Exception e){
      log.info(e.toString());
      return false;
    }
    return true;
  }
}
