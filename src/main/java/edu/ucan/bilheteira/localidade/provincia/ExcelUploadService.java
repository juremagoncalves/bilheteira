package edu.ucan.bilheteira.localidade.provincia;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ExcelUploadService {

    public static boolean isValidExcelFile(MultipartFile file)
    {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public static List<Provincia> getProvinciaDataFromExcel(InputStream inputStream) throws IOException
    {
        List<Provincia> provincaList = new ArrayList<>();

        try{
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("provincias");
            int rowIndex = 0;
            for (Row row : sheet)
            {
                if (rowIndex == 0)
                {
                    rowIndex ++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                Provincia provincia = new Provincia();

                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    switch (cellIndex)
                    {

                        case 0:
                            provincia.setDesignacao(cell.getStringCellValue());
//                            provincia.setEstado(true);
//                            provincaList.add(provincia);
                            break;
                        default:
                            System.err.println("invalid cellIndex");
                    }
                    cellIndex++;
                }
                provincia.setEstado(true);
                provincaList.add(provincia);
            }

        }
        catch (IOException e)
        {
            e.getStackTrace();
        }
        return provincaList;
    }
}
