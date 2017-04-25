package mn.mcs.electronics.court.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ReportUtil {
	public static final String DEFAULT_REPORT_SHEET_NAME = "Court Report";
	public static final int DEFAULT_COLUMN_WIDTH = 256 * 10; // 10 chars


	// sheet selected
	public static final boolean DEFAULT_SHEET_SELECTED = true;

	// sheet.setZoom(numerator,denominator);
	public static final int DEFAULT_NUMERATOR = 3;
	public static final int DEFAULT_DENOMINATOR = 4;

	// grid lines
	public static final boolean DEFAULT_PRINT_GRIDLINES = false;
	public static final boolean DEFAULT_DISPLAY_GRIDLINES = false;

	public static HSSFSheet createSheet(HSSFWorkbook wb) {
		HSSFSheet sheet = wb.createSheet(DEFAULT_REPORT_SHEET_NAME);

		sheet.setSelected(DEFAULT_SHEET_SELECTED);
		sheet.setPrintGridlines(DEFAULT_PRINT_GRIDLINES);
		sheet.setDisplayGridlines(DEFAULT_DISPLAY_GRIDLINES);
		sheet.setZoom(DEFAULT_NUMERATOR, DEFAULT_DENOMINATOR);

		return sheet;
	}

	/**
	 * @param wb
	 *            - Workbook
	 * @param sheetName
	 *            - Sheet name
	 * 
	 * @return HSSFSheet
	 */
	public static HSSFSheet createSheet(HSSFWorkbook wb, String sheetName) {
		HSSFSheet sheet = wb.createSheet(sheetName);

		sheet.setSelected(DEFAULT_SHEET_SELECTED);
		sheet.setPrintGridlines(DEFAULT_PRINT_GRIDLINES);
		sheet.setDisplayGridlines(DEFAULT_DISPLAY_GRIDLINES);
		sheet.setZoom(DEFAULT_NUMERATOR, DEFAULT_DENOMINATOR);

		return sheet;
	}

	public static Map<String, HSSFCellStyle> createStyles(HSSFWorkbook wb) {

		Map<String, HSSFCellStyle> styles = new HashMap<String, HSSFCellStyle>();

		HSSFCellStyle style = null;
		HSSFFont titleFont = wb.createFont();
		HSSFDataFormat dformat = wb.createDataFormat();
		titleFont.setFontHeightInPoints((short) 13);
		titleFont.setFontName(HSSFFont.FONT_ARIAL);
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(titleFont);
		styles.put("title", style);

		HSSFFont subTitleFont = wb.createFont();
		subTitleFont.setFontHeightInPoints((short) 12);
		subTitleFont.setFontName(HSSFFont.FONT_ARIAL);
		subTitleFont.setItalic(true);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(subTitleFont);
		styles.put("sub-title", style);

		HSSFFont boldFont = wb.createFont();
		boldFont.setFontHeightInPoints((short) 10);
		boldFont.setFontName(HSSFFont.FONT_ARIAL);
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(boldFont);
		styles.put("bold", style);

		HSSFFont plainFont = wb.createFont();
		plainFont.setFontHeightInPoints((short) 10);
		plainFont.setFontName(HSSFFont.FONT_ARIAL);
		
		/* Sep 4, 2012 tuguldur.j Start*/
		HSSFFont plainFont1 = wb.createFont();
		plainFont1.setFontHeightInPoints((short) 9);
		plainFont1.setFontName(HSSFFont.FONT_ARIAL);
		/* Sep 4, 2012 tuguldur.j End*/
		
		/* Sep 4, 2012 tuguldur.j Start*/
		HSSFFont plainFont2 = wb.createFont();
		plainFont2.setFontHeightInPoints((short) 16);
		plainFont2.setFontName(HSSFFont.FONT_ARIAL);
		/* Sep 4, 2012 tuguldur.j End*/
		
		/* Sep 4, 2012 tuguldur.j Start*/
		HSSFFont plainFont3 = wb.createFont();
		plainFont3.setFontHeightInPoints((short) 18);
		plainFont3.setFontName(HSSFFont.FONT_ARIAL);
		/* Sep 4, 2012 tuguldur.j End*/
		
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(plainFont);
		styles.put("plain", style);

		HSSFFont plainTextFont = wb.createFont();
		plainFont.setFontHeightInPoints((short) 10);
		plainFont.setFontName(HSSFFont.FONT_ARIAL);
		
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(plainTextFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		styles.put("plain-text", style);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(plainTextFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		styles.put("plain-text-right", style);

		plainTextFont = wb.createFont();
		plainTextFont.setFontHeightInPoints((short) 10);
		plainTextFont.setFontName(HSSFFont.FONT_ARIAL);
		plainTextFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(plainTextFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		styles.put("plain-text-left", style);
		
		plainTextFont = wb.createFont();
		plainTextFont.setFontHeightInPoints((short) 10);
		plainTextFont.setFontName(HSSFFont.FONT_ARIAL);
		plainTextFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(plainTextFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		styles.put("plain-text-center", style);

		HSSFFont plainTotalFont = wb.createFont();
		plainTotalFont.setFontHeightInPoints((short) 14);
		plainTotalFont.setFontName(HSSFFont.FONT_ARIAL);
		plainTotalFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(plainTotalFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		styles.put("total", style);

		plainTotalFont = wb.createFont();
		plainTotalFont.setFontHeightInPoints((short) 14);
		plainTotalFont.setFontName(HSSFFont.FONT_ARIAL);
		plainTotalFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(plainTotalFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		styles.put("total-right", style);

		style = wb.createCellStyle();
		HSSFFont plainFontItalic = wb.createFont();
		plainFontItalic.setFontHeightInPoints((short) 10);
		plainFontItalic.setFontName(HSSFFont.FONT_ARIAL);
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		plainFontItalic.setItalic(true);
		style.setFont(plainFontItalic);
		styles.put("plain-left-italic", style);

		plainTextFont = wb.createFont();
		plainTextFont.setFontHeightInPoints((short) 12);
		plainTextFont.setFontName(HSSFFont.FONT_ARIAL);
		plainTextFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		style = wb.createCellStyle();
		plainTextFont = wb.createFont();
		plainTextFont.setFontHeightInPoints((short) 16);
		plainTextFont.setFontName(HSSFFont.FONT_ARIAL);
		plainTextFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(plainTextFont);

		styles.put("plain-header", style);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(plainFont);
		styles.put("plain-left", style);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(plainFont);
		style.setWrapText(true);
		styles.put("plain-left-wrap", style);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(plainFont);
		styles.put("plain-center", style);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(plainFont);
		style.setWrapText(true);
		styles.put("plain-center-wrap", style);
		
		/* Sep 4, 2012 tuguldur.j Start*/
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(plainFont3);
		style.setWrapText(true);
		styles.put("plain-center-wrap1", style);
		/* Sep 4, 2012 tuguldur.j End*/
		
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(plainFont);
		style.setWrapText(true);
		styles.put("plain-right-wrap", style);
		
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(plainFont);
		style.setWrapText(true);
		style.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
		style.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);
		style.setBorderRight(HSSFCellStyle.BORDER_DOTTED);
		style.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
		styles.put("plain-left-wrap-border", style);
	
		/* Sep 3, 2012 tuguldur.j Start*/
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(plainFont1);
		style.setWrapText(true);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styles.put("plain-center-wrap-border", style);
		
		/* Sep 3, 2012 tuguldur.j End*/
		
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(plainFont);
		styles.put("plain-right", style);

		/* Jun 7, 2011 У.Наранхүү Start */
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(plainFont);
		style.setWrapText(true);
		styles.put("plain-right-wrap", style);
		/* Jun 7, 2011 У.Наранхүү End */
		
		/* Sep 4, 2012 tuguldur.j Start*/
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(plainFont2);
		style.setWrapText(true);
		styles.put("plain-right-wrap1", style);
		/* Sep 4, 2012 tuguldur.j End*/

		/* Sep 4, 2012 tuguldur.j Start*/
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(plainFont2);
		style.setWrapText(true);
		styles.put("plain-left-wrap1", style);
		/* Sep 4, 2012 tuguldur.j End*/
		
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(boldFont);
		styles.put("bold-left", style);

		/* Sep 3, 2012 tuguldur.j Start*/
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(boldFont);
		styles.put("bold-center", style);
		/* Sep 3, 2012 tuguldur.j End*/
		
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(boldFont);
		styles.put("bold-right", style);

		HSSFFont headerFont = wb.createFont();
		headerFont.setFontHeightInPoints((short) 11);
		headerFont.setFontName(HSSFFont.FONT_ARIAL);
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		/* Sep 5, 2012 tuguldur.j Start*/
		HSSFFont headerFont10 = wb.createFont();
		headerFont10.setFontHeightInPoints((short) 12);
		headerFont10.setFontName(HSSFFont.FONT_ARIAL);
		headerFont10.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		/* Sep 5, 2012 tuguldur.j End*/
		
		/* Sep 5, 2012 tuguldur.j Start*/
		HSSFFont headerFont8 = wb.createFont();
		headerFont8.setFontHeightInPoints((short) 8);
		headerFont8.setFontName(HSSFFont.FONT_ARIAL);
		headerFont8.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		/* Sep 5, 2012 tuguldur.j End*/
		
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(headerFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		styles.put("header", style);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(headerFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		styles.put("header-left", style);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(headerFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		styles.put("header-right", style);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(headerFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		style.setWrapText(true);

		styles.put("header-wrap", style);
		
		/* Sep 3, 2012 tuguldur.j Start*/
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(headerFont10);
		headerFont10.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		style.setWrapText(true);
		styles.put("header-wrap10", style);
		/* Sep 3, 2012 tuguldur.j End*/
		
		/* Sep 3, 2012 tuguldur.j Start*/
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(headerFont8);
		headerFont8.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		style.setWrapText(true);
		styles.put("header-wrap8", style);
		/* Sep 3, 2012 tuguldur.j End*/
		
		/* Sep 3, 2012 tuguldur.j Start*/
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(headerFont10);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		style.setWrapText(true);
		style.setRotation((short) 90);
		styles.put("header-wrap-vertical", style);
		/* Sep 3, 2012 tuguldur.j End*/
		
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(headerFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		style.setRotation((short) 90);
		styles.put("header-vertical", style);

		HSSFFont normalFont = wb.createFont();
		normalFont.setFontHeightInPoints((short) 12);
		normalFont.setFontName(HSSFFont.FONT_ARIAL);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(normalFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		styles.put("normal", style);
		
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(normalFont);
//		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
//		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style.setLeftBorderColor(HSSFColor.BLACK.index);
//		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style.setRightBorderColor(HSSFColor.BLACK.index);
		styles.put("border", style);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(normalFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		styles.put("normal-left", style);
		
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(normalFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setWrapText(true);
		styles.put("normal-left-wrap", style);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(normalFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		styles.put("normal-right", style);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(normalFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		styles.put("normal-center", style);

		HSSFFont normalBoldFont = wb.createFont();
		normalBoldFont.setFontHeightInPoints((short) 12);
		normalBoldFont.setFontName(HSSFFont.FONT_ARIAL);
		normalBoldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(normalBoldFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		styles.put("normal-bold", style);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(normalBoldFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		styles.put("normal-bold-left", style);
		
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(normalBoldFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setWrapText(true);
		styles.put("normal-bold-left-wrap", style);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(normalBoldFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		styles.put("normal-bold-right", style);

		HSSFFont summaryFont = wb.createFont();
		summaryFont.setFontHeightInPoints((short) 12);
		summaryFont.setFontName("Arial");
		summaryFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		style.setFont(summaryFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		styles.put("summary", style);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		style.setFont(summaryFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setDataFormat(dformat.getFormat(Constants.NUMBER_FORMAT));
		styles.put("summary-right", style);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		style.setFont(summaryFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setDataFormat(dformat.getFormat(Constants.PERCENT_FORMAT));
		styles.put("summary-percent", style);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(normalFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setDataFormat(dformat.getFormat(Constants.NUMBER_FORMAT));
		styles.put("number-right", style);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(normalFont);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setDataFormat(dformat.getFormat(Constants.PERCENT_FORMAT));
		styles.put("percent", style);

		HSSFFont font = wb.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setFontHeightInPoints((short) 12);
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style = createBorderedStyle(style, HSSFCellStyle.BORDER_THIN,
				HSSFColor.BLACK.index);
		style.setFont(font);
		styles.put("results_data", style);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style = createBorderedStyle(style, HSSFCellStyle.BORDER_THIN,
				HSSFColor.BLACK.index);
		style.setFont(font);
		styles.put("results_data-right", style);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style = createBorderedStyle(style, HSSFCellStyle.BORDER_THIN,
				HSSFColor.BLACK.index);
		style.setFont(font);
		styles.put("results_data-center", style);

		font = wb.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setFontHeightInPoints((short) 12);
		font.setItalic(true);
		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(font);
		styles.put("description", style);

		style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFont(font);
		styles.put("today_date", style);

		HSSFPalette pl = wb.getCustomPalette();
		pl.setColorAtIndex(HSSFColor.GREY_80_PERCENT.index, (byte) 211,
				(byte) 211, (byte) 211);
		pl.setColorAtIndex(HSSFColor.GREY_40_PERCENT.index, (byte) 160,
				(byte) 160, (byte) 160);

		return styles;
	}

	public static HSSFCellStyle createBorderedStyle(HSSFCellStyle style,
			short border, short borderColor) {
		style.setBorderRight(border);
		style.setRightBorderColor(borderColor);
		style.setBorderBottom(border);
		style.setBottomBorderColor(borderColor);
		style.setBorderLeft(border);
		style.setLeftBorderColor(borderColor);
		style.setBorderTop(border);
		style.setTopBorderColor(borderColor);
		return style;
	}

	public static void setColumnWidths(HSSFSheet sheet, double... ds) {
		for (int i = 0; i < ds.length; i += 2) {
			int index = (int) ds[i];
			double scale = ds[i + 1];
			
			setColumnWidth(sheet, index, scale);
		}
	}

	public static void setColumnWidth(HSSFSheet sheet, int columnIndex,
			double scale) {
		if (scale > 0) {
			sheet.setColumnWidth(columnIndex,
					(int) (scale * DEFAULT_COLUMN_WIDTH));
		}
	}

	public static void setRowHeights(HSSFSheet sheet, double... ds) {
		for (int i = 0; i < ds.length; i += 2) {
			int index = (int) ds[i];
			double scale = ds[i + 1];

			setRowHeight(sheet, index, scale);
		}
	}
	
	public static void setRowHeight(HSSFSheet sheet, int rowIndex, double scale) {
		HSSFRow row = sheet.getRow(rowIndex);
		if (scale > 0 && row != null) {
			row.setHeightInPoints((float) (row.getHeightInPoints() * scale));
		}
	}
}
