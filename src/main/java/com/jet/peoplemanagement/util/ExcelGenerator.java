package com.jet.peoplemanagement.util;

import com.jet.peoplemanagement.shipment.Shipment;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class ExcelGenerator {

    private String[] headers;
    private final String sheetName;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Shipment> listShipments;

    public ExcelGenerator(String fileName, String sheetName, List<Shipment> shipmentList, String... headers) {
        this.listShipments = shipmentList;
        this.headers = headers;
        this.sheetName = sheetName;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet(sheetName);
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        this.headers = new String[]{"Parceiro", "Data", "Envio",
                "Venda", "Região", "Preço", "Motoboy", "Status", "Bairro", "Destinatário", "Produto"};

        for (int i = 0; i < headers.length; i++) {
            createCell(row, i, headers[i], style,true);
        }
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style, boolean autoSize) {
        if(autoSize){
            sheet.autoSizeColumn(columnCount);
        }
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Shipment ship : listShipments) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, ship.getClient().getName(), style, true);
            createCell(row, columnCount++, ship.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), style, true);
            createCell(row, columnCount++, ship.getShipmentCode(), style, true);
            createCell(row, columnCount++, ship.getSaleCode(), style, true);
            createCell(row, columnCount++, ship.getZone(), style, true);
            columnCount = setCellPrice(style, ship, row, columnCount);

            createCell(row, columnCount++, ship.getCurrentProviderName(), style, true);
            createCell(row, columnCount++, ship.getStatus().getDesc(), style, true);
            createCell(row, columnCount++, ship.getReceiverNeighbor(), style, true);
            createCell(row, columnCount++, ship.getReceiverName(), style, true);
            createCell(row, columnCount++, ship.getProductName(), style, false);
        }
    }

    private int setCellPrice(CellStyle style, Shipment ship, Row row, int columnCount) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        double shipPrice = ship.getPrice() != null ? ship.getPrice(): 0.0;
        //String.format("%.2f", shipPrice)

        createCell(row, columnCount++, shipPrice , style, true);
        return columnCount;
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        /*String path = new File(".").getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "teste" + ".xlsx";
        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();*/

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public static void writeToExcel(String fileName, String sheetName, Collection<String> headers, Collection<String> rows) throws IOException {

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet(sheetName);
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = createHeaderStyle(workbook);

        //Header cell
        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Age");
        headerCell.setCellStyle(headerStyle);


        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        Row row = sheet.createRow(2);
        Cell cell = row.createCell(0);
        cell.setCellValue("John Smith");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(20);
        cell.setCellStyle(style);


        writeToFile(fileName, workbook);

    }

    private static void writeToFile(String fileName, Workbook workbook) throws IOException {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + fileName + ".xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
    }

    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);
        return headerStyle;
    }
}
