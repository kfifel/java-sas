package service;

import model.BookBorrow;
import model.dto.StaticticsData;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.engine.JREmptyDataSource;


import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {
    private final BookService bookService;
    private final BorrowerService borrowerService;
    private final BorrowService bookBorrowService;

    public ReportService() {
        this.bookService = new BookService();
        this.borrowerService = new BorrowerService();
        this.bookBorrowService = new BorrowService();
    }


    public StaticticsData generateRapport() {
        StaticticsData statisticsData = new StaticticsData();

        statisticsData.bookCount = bookService.countBooks();
        statisticsData.borrowerCount = borrowerService.countBorrowers();
        statisticsData.mostBorrow = bookService.getMostBorrowedBook();
        statisticsData.borrowCountThisYear = bookBorrowService.countBorrowsThisYear();

        return statisticsData;
    }
    public void generateRapport_PDF() {

        try {
            // Compile your JasperReport template (replace "YourReportTemplate.jasper" with your actual template file)
            String reportTemplatePath = "C:\\Users\\fifle\\Desktop\\YouCode\\SAS JAVA\\sas-java\\Application\\src\\configuration\\rapport.jrxml";
            JasperCompileManager.compileReportToFile(reportTemplatePath);

            // Fetch data for your report (e.g., a list of book borrowings)
            List<BookBorrow> bookBorrows = bookBorrowService.read();

            // Create an instance of StaticticsData and populate it with your statistics
            StaticticsData statisticsData = new StaticticsData();
            statisticsData.bookCount = bookService.countBooks();
            statisticsData.borrowerCount = borrowerService.countBorrowers();
            statisticsData.mostBorrow = bookService.getMostBorrowedBook();
            statisticsData.borrowCountThisYear = bookBorrowService.countBorrowsThisYear();

            // Create parameters for your report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("CollectionBeanParam", new JRBeanCollectionDataSource(bookBorrows));
            parameters.put("StatisticsData", statisticsData);

            // Fill the JasperReport with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportTemplatePath, parameters, new JREmptyDataSource());

            // Export the report to PDF (you can choose a different format if needed)
            String outputFilePath = "output.pdf";
            File outputFile = new File(outputFilePath);
            FileOutputStream fos = new FileOutputStream(outputFile);

            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            configuration.setCompressed(true);

            Exporter exporter = (Exporter) new SimplePdfExporterConfiguration();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fos));
            exporter.setConfiguration(configuration);
            exporter.exportReport();

            fos.close();

            System.out.println("Report generated successfully at " + outputFilePath);
        } catch (JRException e) {
            Logger.error("Error generating the report: " + e);
        } catch (Exception e) {
            System.err.println("General error: " + e.getMessage());
        }
    }
}
