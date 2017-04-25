/* 
 * Package Name: mn.mcs.electronics.esss.report
 * File Name: ExcelAPI.java
 * 
 * Created By: Z.Zolbayar
 * Created Date: 2010/10/01
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * 2010/10/01 1.0.0 			Z.Zolbayar					РЁРёРЅСЌСЌСЂ ТЇТЇСЃРіСЌРІ.
 * 2010/10/04 1.0.0 			B.Boldbaatar				Пакэжийг өөрчилөв.
 * ------------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2010 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class  ExcelAPI {

	/**
	 * @param excelFileName
	 * @return
	 */
	public static HSSFWorkbook openExcel(String excelFileName)
			throws IOException {
		POIFSFileSystem poiStream = new POIFSFileSystem(new FileInputStream(
				excelFileName));
		return new HSSFWorkbook(poiStream);
	}

	/**
	 * @param document
	 */
	private static void evaluate(HSSFWorkbook document) {
		HSSFFormulaEvaluator evaluator = new HSSFFormulaEvaluator(document);
		for (int sheetNum = 0; sheetNum < document.getNumberOfSheets(); sheetNum++) {
			HSSFSheet sheet = document.getSheetAt(sheetNum);

			for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum(); i++) {
				HSSFRow row = sheet.getRow(i);
				if (row != null) {
					for (int j = row.getFirstCellNum(); j < row
							.getLastCellNum(); j++) {
						HSSFCell cell = row.getCell(j);

						if (cell != null
								&& cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
							evaluator.evaluateFormulaCell(cell);
						}
					}
				}
			}
		}
	}

	/**
	 * @param document
	 * @param tempFile
	 */
	public static File closeExcel(HSSFWorkbook document, String tempFile)
			throws IOException {
		evaluate(document);
		File file = new File(tempFile);
		FileOutputStream modSpreadsheet = new FileOutputStream(file);
		document.write(modSpreadsheet);
		modSpreadsheet.flush();
		modSpreadsheet.close();
		return file;
	}

	/**
	 * @param document
	 * @param sheetNo
	 * @param rowindex
	 * @param cellindex
	 * 
	 * @return HSSFCell
	 */
	public static HSSFCell getCell(HSSFWorkbook document, int sheetNo,
			int rowindex, int cellindex) {
		HSSFSheet sheet = document.getSheetAt(sheetNo);

		if (sheet != null) {
			HSSFRow row = sheet.getRow(rowindex);
			if (row == null) {
				row = sheet.createRow(rowindex);
			}

			HSSFCell cell = row.getCell(cellindex);
			if (cell == null) {
				cell = row.createCell(cellindex);
			}
			return cell;
		}

		return null;
	}
	
	public static HSSFCell getCell1(HSSFWorkbook document, int sheetNo,
			int rowStartIndex, int colStartIndex, int rowEndIndex,
			int colEndIndex, int rowIndex, int cellIndex, HSSFCellStyle style) {
		HSSFSheet sheet = document.getSheetAt(sheetNo);

		if (sheet != null) {
			HSSFRow row = sheet.getRow(rowIndex);
			if (row == null) {
				row = sheet.createRow(rowIndex);
			}

			HSSFCell cell = row.getCell(cellIndex);
			if (cell == null) {
				cell = row.createCell(cellIndex);
			}
			return cell;
		}

		return null;
	}

	/**
	 * @param document
	 * @param sheetNo
	 * @param rowindex
	 * @param cellindex
	 * @return
	 */
	public static boolean isCellPercentFormat(HSSFWorkbook document,
			int sheetNo, int rowindex, int cellindex) {
		HSSFSheet sheet = document.getSheetAt(sheetNo);

		if (sheet != null) {
			HSSFRow row = sheet.getRow(rowindex);
			if (row != null) {
				HSSFCell cell = row.getCell(cellindex);
				if (cell != null) {
					String format = cell.getCellStyle().getDataFormatString();
					if (format != null && format.indexOf("%") > -1) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * @param document
	 * @param sheetNo
	 * @param rowindex
	 * @param cellindex
	 * @return
	 */
	public static boolean isCellPercentFormat(HSSFCell cell) {
		HSSFCellStyle style = cell.getCellStyle();
		String format = style != null ? style.getDataFormatString() : null;
		if (format != null && format.indexOf("%") > -1) {
			return true;
		}
		return false;
	}

	/**
	 * @param document
	 * @param sheetNo
	 * @param rowindex
	 * @param cellindex
	 * @param value
	 */
	public static void setCellValue(HSSFWorkbook document, int sheetNo,
			int rowindex, int cellindex, String value) {
		
		HSSFSheet sheet = document.getSheetAt(sheetNo);

		HSSFRow row = sheet.getRow(rowindex);

		if (row == null) {
			row = sheet.createRow(rowindex);
		}

		HSSFCell cell = row.getCell(cellindex);

		if (cell == null) {
			cell = row.createCell(cellindex);
		}

		if (value != null && cell.getCellType() != HSSFCell.CELL_TYPE_FORMULA) {
			try {
				double dvalue = Double.parseDouble(value);

				if (isCellPercentFormat(cell)) {
					cell.setCellValue(dvalue / 100);
				} else {
					cell.setCellValue(dvalue);
				}
			} catch (NumberFormatException e) {
				cell.setCellValue(new HSSFRichTextString(value));
			}
		}
	}

	/**
	 * @param document
	 * @param sheetNo
	 * @param rowindex
	 * @param cellindex
	 * @param value
	 */
	public static void setCellValueAsString(HSSFWorkbook document, int sheetNo,
			int rowindex, int cellindex, String value) {
		HSSFSheet sheet = document.getSheetAt(sheetNo);

		HSSFRow row = sheet.getRow(rowindex);

		if (row == null) {
			row = sheet.createRow(rowindex);
		}

		HSSFCell cell = row.getCell(cellindex);

		if (cell == null) {
			cell = row.createCell(cellindex);
		}

		if (value != null && cell.getCellType() != HSSFCell.CELL_TYPE_FORMULA) {
			cell.setCellValue(new HSSFRichTextString(value));
		}
	}

	/**
	 * @param document
	 * @param matrix
	 * @param dataset
	 */
	public static void setCellValues(HSSFWorkbook document, ResultSet set,
			String[] matrix, int sheetNo, int startRow, int startColumn)
			throws SQLException {
		int count = 0;
		while (set.next()) {
			for (int i = 0; i < matrix.length; i++) {
				setCellValue(document, sheetNo, startRow + count, startColumn
						+ i, set.getString(matrix[i]));
			}
			count++;
		}
	}

	/**
	 * @param document
	 * @param sheetNo
	 * @param rowindex
	 * @param cellindex
	 * @param value
	 */
	public static void copyRow(HSSFWorkbook document, int sheetNo, int rowIndex) {
		HSSFSheet sheet = document.getSheetAt(sheetNo);
		sheet.shiftRows(rowIndex, rowIndex, 1);

		HSSFRow row1 = sheet.getRow(rowIndex);
		HSSFRow row2 = sheet.getRow(rowIndex + 1);

		if (row1 == null) {
			row1 = sheet.createRow(rowIndex);
		}

		if (row2 == null) {
			row2 = sheet.createRow(rowIndex);
		}

		for (int i = row1.getFirstCellNum(); i < row1.getLastCellNum(); i++) {
			HSSFCell cell = row1.getCell(i);
			HSSFCell cell2 = row2.createCell(i);

			if (cell != null) {
				cell2.setCellStyle(cell.getCellStyle());
				cell2.setCellType(cell.getCellType());
			}
		}

	}

	/**
	 * @param document
	 * @param sheetNo
	 * @param rowindex
	 * @param cellindex
	 * @param value
	 */
	public static void copyLastRowDown(HSSFWorkbook document, int sheetNo,
			int rowIndex) {
		HSSFSheet sheet = document.getSheetAt(sheetNo);

		sheet.shiftRows(rowIndex + 1, sheet.getPhysicalNumberOfRows(), 1);

		HSSFRow row1 = sheet.getRow(rowIndex);
		HSSFRow row2 = sheet.getRow(rowIndex + 1);

		try {
			for (int i = row1.getFirstCellNum(); i < row1.getLastCellNum(); i++) {
				HSSFCell cell = row1.getCell(i);
				HSSFCell cell2 = row2.createCell(i);

				if (cell != null) {
					cell2.setCellStyle(cell.getCellStyle());
					cell2.setCellType(cell.getCellType());
				}
			}
		} catch (Exception e) {

		}
	}

	/**
	 * @param document
	 * @param sheetNo
	 * @param rowStartIndex
	 * @param colStartIndex
	 * @param rowEndIndex
	 * @param colEndIndex
	 */
	public static void mergeCells(HSSFWorkbook document, int sheetNo,
			int rowStartIndex, int colStartIndex, int rowEndIndex,
			int colEndIndex) {
		HSSFSheet sheet = document.getSheetAt(sheetNo);

		sheet.addMergedRegion(new CellRangeAddress(rowStartIndex, rowEndIndex,
				colStartIndex, colEndIndex));
	}
	
	/**
	 * @param document
	 * @param sheetNo
	 * @param rowStartIndex
	 * @param colStartIndex
	 * @param rowEndIndex
	 * @param colEndIndex
	 * @param style
	 */
	public static void mergeCells1(HSSFWorkbook document, int sheetNo,
			int rowStartIndex, int colStartIndex, int rowEndIndex,
			int colEndIndex, int rowIndex, int cellIndex, HSSFCellStyle style) {
		HSSFSheet sheet = document.getSheetAt(sheetNo);
		HSSFCell cell = getCell1(document, sheetNo, rowStartIndex, colStartIndex,rowEndIndex,colEndIndex,  rowIndex, cellIndex, style);
		
		sheet.addMergedRegion(new CellRangeAddress(rowStartIndex, rowEndIndex,
				colStartIndex, colEndIndex));
		
		if (style == null) {
			style = document.createCellStyle();
		}
		cell.setCellStyle(style);
	}
		
	/**
	 * @param document
	 * @param sheetNo
	 * @param rowindex
	 * @param colindex
	 * @param top
	 * @param right
	 * @param left
	 * @param bottom
	 */
	public static void setBorders(HSSFWorkbook document, int sheetNo,
			int rowindex, int colindex, short top, short right, short left,
			short bottom) {
		HSSFCellStyle style = document.createCellStyle();

		HSSFCell cell = getCell(document, sheetNo, rowindex, colindex);

		style.setBorderTop(top);
		style.setBorderRight(right);
		style.setBorderLeft(left);
		style.setBorderBottom(bottom);

		cell.setCellStyle(style);
	}

	/**
	 * @param document
	 * @param sheetNo
	 * @param rowindex
	 * @param colindex
	 * @param top
	 * @param right
	 * @param left
	 * @param bottom
	 */
	public static void setBordersSideThinDotted(HSSFWorkbook document,
			int sheetNo, int rowindex, int colindex) {
		setBorders(document, sheetNo, rowindex, colindex,
				HSSFCellStyle.BORDER_DOTTED, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_DOTTED);
	}

	/**
	 * @param document
	 * @param sheetNo
	 * @param rowindex
	 * @param colindex
	 * @param top
	 * @param right
	 * @param left
	 * @param bottom
	 */
	public static void setBordersSideAndBottomThin(HSSFWorkbook document,
			int sheetNo, int rowindex, int colindex) {
		setBorders(document, sheetNo, rowindex, colindex,
				HSSFCellStyle.BORDER_DOTTED, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN);
	}

	/**
	 * @param document
	 * @param sheetNo
	 * @param rowindex
	 * @param colindex
	 * @param top
	 * @param right
	 * @param left
	 * @param bottom
	 */
	public static void setBordersSideAndTopThin(HSSFWorkbook document,
			int sheetNo, int rowindex, int colindex) {
		setBorders(document, sheetNo, rowindex, colindex,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_DOTTED);
	}

	/**
	 * @param document
	 * @param sheetNo
	 * @param rowindex
	 * @param colindex
	 * @param halign
	 * @param valign
	 */
	public static void setCellAlignment(HSSFWorkbook document, int sheetNo,
			int rowindex, int colindex, short halign, short valign) {
		HSSFCell cell = getCell(document, sheetNo, rowindex, colindex);

		HSSFCellStyle style = cell.getCellStyle();
		if (style == null) {
			style = document.createCellStyle();
		}
		style.setVerticalAlignment(valign);
		style.setAlignment(halign);
		cell.setCellStyle(style);
	}

	/**
	 * @param HSSFWorkbook
	 *            document
	 * @param int sheetNo
	 * @param int rowIndex
	 * @param int cellIndex
	 * @param boolean wrapped
	 */
	public static void setCellWrapText(HSSFWorkbook document, int sheetNo,
			int rowIndex, int cellIndex, boolean wrapped) {

		HSSFCell cell = getCell(document, sheetNo, rowIndex, cellIndex);

		HSSFCellStyle style = cell.getCellStyle();

		if (style == null) {
			style = document.createCellStyle();
		}

		style.setWrapText(wrapped);
		cell.setCellStyle(style);
	}

	/**
	 * @param HSSFWorkbook
	 *            document
	 * @param int sheetNo
	 * @param int rowIndex
	 * @param int cellIndex
	 * @param short foregroundColor
	 */
	public static void setCellBackgroundColor(HSSFWorkbook document,
			int sheetNo, int rowIndex, int cellIndex, short foregroundColor) {

		HSSFCell cell = getCell(document, sheetNo, rowIndex, cellIndex);

		HSSFCellStyle style = cell.getCellStyle();

		if (style == null) {
			style = document.createCellStyle();
		}

		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(foregroundColor);

		cell.setCellStyle(style);
	}

	/**
	 * @param HSSFWorkbook
	 *            document
	 * @param int sheetNo
	 * @param int rowIndex
	 * @param int cellIndex
	 * @param short boldWeight
	 * @param short color
	 * @param short fontName
	 * @param short fontHeight
	 */
	public static void setCellFont(HSSFWorkbook document, int sheetNo,
			int rowIndex, int cellIndex, short boldWeight, short color,
			String fontName, short fontHeight) {

		HSSFCell cell = getCell(document, sheetNo, rowIndex, cellIndex);

		HSSFCellStyle style = cell.getCellStyle();

		if (style == null) {
			style = document.createCellStyle();
		}

		HSSFFont font = document.createFont();

		font.setBoldweight(boldWeight);
		font.setColor(color);
		font.setFontName(fontName);
		font.setFontHeight(fontHeight);
		font.setFontHeightInPoints(fontHeight);

		style.setFont(font);
		cell.setCellStyle(style);
	}

	/**
	 * @param HSSFWorkbook
	 *            document
	 * @param int sheetNo
	 * @param int rowIndex
	 * @param int cellIndex
	 * @param HSSFCellStyle
	 *            style
	 */
	public static void setCellStyle(HSSFWorkbook document, int sheetNo,
			int rowIndex, int cellIndex, HSSFCellStyle style) {

		HSSFCell cell = getCell(document, sheetNo, rowIndex, cellIndex);
		cell.setCellStyle(style);
	}

	/**
	 * @param document
	 * @param sheetNo
	 * @param rowindex
	 * @param colindex
	 * @param border
	 */
	public static void setBorders(HSSFWorkbook document, int sheetNo,
			int rowindex, int colindex, short border) {
		HSSFCellStyle style = document.createCellStyle();

		HSSFCell cell = getCell(document, sheetNo, rowindex, colindex);

		style.setBorderTop(border);
		style.setBorderRight(border);
		style.setBorderLeft(border);
		style.setBorderBottom(border);

		cell.setCellStyle(style);
	}

	/**
	 * @param HSSFWorkbook
	 *            document
	 * @param int sheetNo
	 * @param int rowIndex
	 * @param int colIndex
	 * @param int cellType
	 */
	public static void setCellType(HSSFWorkbook document, int sheetNo,
			int rowIndex, int colIndex, int cellType) {
		HSSFCell cell = getCell(document, sheetNo, rowIndex, colIndex);

		cell.setCellType(cellType);
	}

	/**
	 * @param document
	 * @param sheetNo
	 * @param name
	 */
	public static void setSheetHidden(HSSFWorkbook document, int sheetNo) {
		document.setSheetHidden(sheetNo, true);
	}

	/**
	 * @param document
	 * @param sheetNo
	 * @param name
	 */
	public static void setSheetActive(HSSFWorkbook document, int sheetNo) {
		document.setActiveSheet(sheetNo);
	}

	public static void setCellValue(HSSFWorkbook document, int sheetNo,
			int rowindex, int cellindex, String value, HSSFCellStyle style,
			int colspan) {
		setCellValue(document, sheetNo, rowindex, cellindex, value, style);

		HSSFSheet sheet = document.getSheetAt(sheetNo);

		if (colspan > 1) {
			CellRangeAddress region = new CellRangeAddress(rowindex, rowindex,
					cellindex, cellindex + colspan - 1);
			sheet.addMergedRegion(region);
		}
	}

	public static void setCellValue(HSSFWorkbook document, int sheetNo,
			int rowindex, int cellindex, String value, HSSFCellStyle style,
			int colspan, int rowspan) {
		HSSFSheet sheet = document.getSheetAt(sheetNo);

		if (rowspan < 2) {
			rowspan = 1;
		}

		if (colspan < 2) {
			colspan = 1;
		}

		if (colspan > 1 || rowspan > 1) {
			for (int i = 0; i < rowspan; i++) {
				for (int j = 0; j < colspan; j++) {
					setCellValue(document, sheetNo, rowindex + i,
							cellindex + j, "", style);
				}
			}

			CellRangeAddress region = new CellRangeAddress(rowindex, rowindex
					+ rowspan - 1, cellindex, cellindex + colspan - 1);

			sheet.addMergedRegion(region);
		}

		setCellValue(document, sheetNo, rowindex, cellindex, value, style);

	}
	
	/**
	 * @param document
	 * @param sheetNo
	 * @param rowindex
	 * @param cellindex
	 * @param value
	 * @param style
	 */
	public static void setCellValue(HSSFWorkbook document, int sheetNo,
			int rowindex, int cellindex, String value, HSSFCellStyle style) {
		HSSFSheet sheet = document.getSheetAt(sheetNo);

		HSSFRow row = sheet.getRow(rowindex);

		if (row == null) {
			row = sheet.createRow(rowindex);
		}

		HSSFCell cell = row.getCell(cellindex);

		if (cell == null) {
			cell = row.createCell(cellindex);
		}

		if (value != null && cell.getCellType() != HSSFCell.CELL_TYPE_FORMULA) {
			try {
				double dvalue = Double.parseDouble(value);

				if (isCellPercentFormat(cell)) {
					cell.setCellValue(dvalue / 100);
				} else {
					cell.setCellValue(dvalue);
				}
			} catch (NumberFormatException e) {
				cell.setCellValue(new HSSFRichTextString(value));
			}
		}

		cell.setCellStyle(style);

	}

	/**
	 * @param HSSFWorkbook
	 *            document
	 * @param short fontWeight
	 * @param short fontColor
	 * @param short fontName
	 * @param short fontSize
	 * @param boolean italic
	 * 
	 * @return HSSFFont
	 */
	public static HSSFFont getCellFont(HSSFWorkbook document, short fontWeight,
			short fontColor, String fontName, short fontSize, boolean italic) {

		HSSFFont font = document.createFont();

		font.setBoldweight(fontWeight);
		font.setColor(fontColor);
		font.setFontName(fontName);
		font.setFontHeightInPoints(fontSize);
		font.setItalic(italic);

		return font;
	}

	/**
	 * @param HSSFWorkbook
	 *            document
	 * @param short foregroundColor
	 * @param short halign
	 * @param short valign
	 * @param boolean wrapped
	 * @param short border
	 * @param HSSFFont
	 *            font
	 * 
	 * @return HSSFFont
	 */
	public static HSSFCellStyle getCellStyle(HSSFWorkbook document,
			short foregroundColor, short halign, short valign, boolean wrapped,
			short border, HSSFFont font) {

		HSSFCellStyle style = document.createCellStyle();

		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(foregroundColor);

		style.setAlignment(halign);
		style.setVerticalAlignment(valign);

		style.setWrapText(wrapped);

		style.setBorderRight(border);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBorderBottom(border);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(border);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(border);
		style.setTopBorderColor(HSSFColor.BLACK.index);

		style.setFont(font);

		return style;
	}

	/* 2009-10-26 B.Boldbaatar Begin */
	/**
	 * @param HSSFWorkbook
	 *            document
	 * @param int sheetIndex
	 * @return HSSFSheet
	 */
	/*
	 * public static HSSFSheet getSheet(HSSFWorkbook document, int sheetIndex) {
	 * return document.getSheetAt(sheetIndex); }
	 */

	/* 2009-10-26 B.Boldbaatar End */

	/* Mar 19, 2010 B.Byambatsetseg Begin */

	/*
	 * @SuppressWarnings("unchecked") public static Iterator<Row>
	 * getExcelRows(InputStream input, int sheetNo) throws IOException {
	 * POIFSFileSystem fileSystem = null; Iterator<Row> rows = null; try {
	 * fileSystem = new POIFSFileSystem(input); HSSFWorkbook workBook = new
	 * HSSFWorkbook(fileSystem); HSSFSheet sheet = workBook.getSheetAt(0); rows
	 * = sheet.iterator(); } catch (IOException e) { e.printStackTrace(); }
	 * finally { input.close(); }
	 * 
	 * return rows; }
	 */

	/* Mar 19, 2010 B.Byambatsetseg End */

	public static Object getCellValue(HSSFCell cell) {

		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:
		case HSSFCell.CELL_TYPE_FORMULA:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				return cell.getNumericCellValue();
			}
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		case HSSFCell.CELL_TYPE_STRING:
			return cell.getRichStringCellValue().getString();
		case HSSFCell.CELL_TYPE_ERROR:
			return cell.getErrorCellValue();
		default:
			return null;
		}
	}

	/* Apr 28, 2011 У.Наранхүү Start */

	/**
	 * @param document
	 * @param sheetNo
	 * @param i
	 * @param col
	 * @param id
	 */
	public static void setCellValue(HSSFWorkbook document, int sheetNo, int i,
			int col, Long longNum) {

		setCellValue(document, sheetNo, i, col, String.valueOf(longNum));
	}

	/**
	 * @param document
	 * @param sheetNo
	 * @param i
	 * @param col
	 * @param ognoo
	 */
	public static void setCellValue(HSSFWorkbook document, int sheetNo, int i,
			int col, Date ognoo, SimpleDateFormat sdf) {

		if (ognoo != null)
			setCellValue(document, sheetNo, i, col, sdf.format(ognoo));
	}

	/* Apr 28, 2011 У.Наранхүү End */

	/* Apr 29, 2011 У.Наранхүү Start */

	/**
	 * @param document
	 * @param sheetNo
	 * @param row
	 * @param col
	 * @param d
	 */
	public static void setCellValue(HSSFWorkbook document, int sheetNo,
			int row, int col, Double d) {

		if (d != null)
			setCellValue(document, sheetNo, row, col, String.valueOf(d));
	}

	/* Apr 29, 2011 У.Наранхүү End */

	/* May 10, 2011 Г.Болорчимэг Start */

	/**
	 * @param document
	 * @param sheetNo
	 * @param row
	 * @param col
	 * @param i
	 *            /Integer/
	 */
	public static void setCellValue(HSSFWorkbook document, int sheetNo,
			int row, int col, Integer i) {
		/* May 26, 2011 У.Наранхүү Start */
		if (i != null)
			/* May 26, 2011 У.Наранхүү End */
			setCellValue(document, sheetNo, row, col, String.valueOf(i));
	}

	/**
	 * @param document
	 * @param sheetNo
	 * @param row
	 * @param col
	 * @param b
	 *            /Boolean/
	 */
	public static void setCellValue(HSSFWorkbook document, int sheetNo,
			int row, int col, Boolean b) {

		setCellValue(document, sheetNo, row, col, String.valueOf(b));
	}

	/* May 10, 2011 Г.Болорчимэг End */

	/* May 9, 2011 Start Uuganbaatar */
//	@SuppressWarnings("unchecked")
//	public static Iterator<HSSFRow> getExcelRows(InputStream input, int sheetNo)
//			throws IOException {
//		POIFSFileSystem fileSystem = null;
//		Iterator<HSSFRow> rows = null;
//		try {
//			fileSystem = new POIFSFileSystem(input);
//			HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
//			HSSFSheet sheet = workBook.getSheetAt(0);
//			rows = sheet.rowIterator();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			input.close();
//		}
//
//		return rows;
//	}

	/* May 9, 2011 End Uuganbaatar */

	/* May 12, 2011 У.Наранхүү Start */
	/**
	 * @param document
	 * @param sheetNo
	 * @param rowStartIndex
	 * @param colStartIndex
	 * @param rowEndIndex
	 * @param colEndIndex
	 */
	public static void setRangeCellStyle(HSSFWorkbook document, int sheetNo,
			int rowStartIndex, int rowEndIndex, int colStartIndex,
			int colEndIndex, HSSFCellStyle style) {

		for (int row = rowStartIndex; row <= rowEndIndex; row++)
			for (int col = colStartIndex; col <= colEndIndex; col++)
				setCellStyle(document, sheetNo, row, col, style);

	}
	/* May 12, 2011 У.Наранхүү End */

}