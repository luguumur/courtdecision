package mn.mcs.electronics.court.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


public class PDFGenerator{
		
	  static Document document = new Document();

	
		@Inject
		private AssetSource imageUrl;
		
		///media/84DE8121DE810C9C/Windows/Fonts/arialbd.ttf
		@Inject
		@Path("context:/fonts/arialbd.ttf")
		private static Asset basic;
		
        public static FileOutputStream generatePDF() throws IOException {
                // step 1: creation of a document-object

        
              ByteArrayOutputStream baos = new ByteArrayOutputStream();
              FileOutputStream fos = new FileOutputStream("anket.pdf");
                try {
                        // step 2:
                        // we create a writer that listens to the document
                        // and directs a PDF-stream to a file
                        PdfWriter writer = PdfWriter.getInstance(document,fos);
                        // step 3: we open the document
                        document.open();
                        // step 4: we add a paragraph to the document
                      
                        BaseFont bf = BaseFont.createFont("arialbd.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                        
            			document.add(new Paragraph("Font: " + bf.getPostscriptFontName()
                                   + " with encoding: " + bf.getEncoding()));
                        
            			String str =  "Төрийн албан хаагчийн хувийн хэрэг хөтлөх журам-ын 1 дүгээр хавсралт ";
        	        	/* Paragraph p = new Paragraph(str);
        	        	 p.setAlignment(Element.ALIGN_RIGHT);*/
        	        	 
            			document.add(new Paragraph(str));
            			
                        getSmallTitle();
                        	
                         document.add(Chunk.NEWLINE);
                        	
                } catch (DocumentException de) {
                        System.err.println(de.getMessage());
                }
                // step 5: we close the document
                document.close();
               // ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
             //   FileInputStream fis = new FileInputStream(fos);
				return fos ;
        }
        
        public static void getSmallTitle() throws DocumentException, IOException{
        
        	
         	
            
        }
        
        public void createPdf(String filename) throws IOException, DocumentException {
	    	// step 1
	        Document document = new Document();
	        // step 2
	        PdfWriter.getInstance(document, new FileOutputStream(filename));
	        // step 3
	        document.open();
	        // step 4
	        document.add(createFirstTable());
	        // step 5
	        document.close();
    }
        
        public static PdfPTable createFirstTable() {
        	// a table with three columns
            PdfPTable table = new PdfPTable(3);
            // the cell object
            PdfPCell cell;
            // we add a cell with colspan 3
            cell = new PdfPCell(new Phrase("Cell with colspan 3"));
            cell.setColspan(3);
            table.addCell(cell);
            // now we add a cell with rowspan 2
            cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
            cell.setRowspan(2);
            table.addCell(cell);
            // we add the four remaining cells with addCell()
            table.addCell("row 1; cell 1");
            table.addCell("row 1; cell 2");
            table.addCell("row 2; cell 1");
            table.addCell("row 2; cell 2");
            return table;
        }
        
}