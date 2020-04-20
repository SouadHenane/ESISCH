package sortie;

import org.apache.poi.xssf.usermodel.*;

import Controllers.first;

import emploiDuTemps.Main2;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.io.FileOutputStream;


public class Excel {
    private static final String[] titles = {
            "Heur","Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi","Vendredi","Samedi"
    };


    public static void main(String[] args,String[][] tab,String s) throws Exception {

        Workbook wb;

        if(args.length > 0 && args[0].equals("-xls")) wb = new HSSFWorkbook();
        else wb = new XSSFWorkbook();

        Map<String, CellStyle> styles = createStyles(wb);

        Sheet sheet = wb.createSheet("ESI_Schedule");
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        //title row
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        String promo="";
        String se;
        if (first.pro.equals("1CPI")) promo="1er année Classe Préparatoire";
        else if (first.pro.equals("2CPI")) promo="2eme année Classe Préparatoire";
        else if (first.pro.equals("1CS")) promo="1ere année Cycle Supérieur";
        else if (first.pro.equals("2CS")) promo="2eme année Cycle Supérieur";
        else if (first.pro.equals("3CS")) promo="3eme année Cycle Supérieur";
        
        if (first.se.equals("1")) se="1er";
        else se="2eme";
        String t = "Ecole Supérieure en Informatique - Sidi Bel Abbes"+" \n" + "Emploi du temps "+promo+" "+se+" Semestre";
        titleCell.setCellValue(t);
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$H$1"));

        //header row
        Row headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        for (int i = 0; i < titles.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(titles[i]);
            headerCell.setCellStyle(styles.get("header"));
        }

        int rownum = 2;
        for (int i = 0; i < 9; i++) {
        	Row row = sheet.createRow(rownum++);
            for (int j = 0; j < 8; j++) {
                Cell cell = row.createCell(j);
                    cell.setCellStyle(styles.get("cell"));
                    
                    if(tab[j][i] == null) continue;
                    
                    
                    row.getCell(j).setCellValue((String)tab[j][i]);
                    
                    }
                
            }
        
       
        

        //set sample data
       
       


        //finally set column widths, the width is measured in units of 1/256th of a character width
        sheet.setColumnWidth(0, 30*256); //30 characters wide
        for (int i = 1; i < 8; i++) {
            sheet.setColumnWidth(i, 30*256);  //6 characters wide
        }
        sheet.setColumnWidth(10, 10*256); //10 characters wide

        // Write the output to a file
        String file = s+ ".xls";
        if(wb instanceof XSSFWorkbook) file += "x";
        FileOutputStream out = new FileOutputStream(file);
        wb.write(out);
        out.close();
    }

    /**
     * Create a library of cell styles
     */
    private static Map<String, CellStyle> createStyles(Workbook wb){
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)18);
        titleFont.setBold(true);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(titleFont);
        styles.put("title", style);

        Font monthFont = wb.createFont();
        monthFont.setFontHeightInPoints((short)11);
        monthFont.setColor(IndexedColors.WHITE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(monthFont);
        style.setWrapText(true);
        styles.put("header", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("cell", style);

        
        
        return styles;
    }
}