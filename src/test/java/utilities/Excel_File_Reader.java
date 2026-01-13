package utilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel_File_Reader {
    private String path;

    public Excel_File_Reader(String path) {
        this.path = path;
     //   ensureSampleExcel();
    }

//    private void ensureSampleExcel() {
//        try {
//            File f = new File(path);
//            if (!f.exists()) {
//                f.getParentFile().mkdirs();
//                try (XSSFWorkbook wb = new XSSFWorkbook()) {
//                    Sheet s = wb.createSheet("Sheet1");
//                    Row header = s.createRow(0);
//                    header.createCell(0).setCellValue("username");
//                    header.createCell(1).setCellValue("password");
//                    Row r1 = s.createRow(1);
//                    r1.createCell(0).setCellValue("standard_user");
//                    r1.createCell(1).setCellValue("secret_sauce");
//                    try (FileOutputStream fos = new FileOutputStream(f)) {
//                        wb.write(fos);
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public Object[][] getTestData() {
        List<Object[]> data = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(path); 
        	XSSFWorkbook wb = new XSSFWorkbook(fis)) {
            XSSFSheet s = wb.getSheetAt(0);
            int rows = s.getPhysicalNumberOfRows();
            for (int i = 1; i < rows; i++) {
                XSSFRow r = s.getRow(i);
                if (r == null) continue;
                String user = r.getCell(0).getStringCellValue();
                String pass = r.getCell(1).getStringCellValue();
                data.add(new Object[] { user, pass });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object[][] arr = new Object[data.size()][2];
        for (int i = 0; i < data.size(); i++) {
            arr[i] = data.get(i);
        }
        return arr;
    }
}
   
