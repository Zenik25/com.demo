package com.demo.utils;

import com.demo.POJO.OrderBill;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.util.List;

public class PdfGeneratorUtil {

    public static void generateOrderBillPdf(OrderBill bill, String filePath) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        document.add(new Paragraph("🧾 Order Bill 🧾"));
        document.add(new Paragraph("Table Number: " + bill.getTableNumber()));
        document.add(new Paragraph("Order Date: " + bill.getOrderDate() + " " + bill.getOrderTime()));
        document.add(new Paragraph(" "));

        List<String> items = bill.getOrderedProductNames();
        List<Integer> quantities = bill.getQuantity();
        List<Integer> prices = bill.getPrice();

        for (int i = 0; i < items.size(); i++) {
            document.add(new Paragraph(items.get(i) + " x " + quantities.get(i)
                    + " = " + (prices.get(i) * quantities.get(i)) + " Ks"));
        }

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Tax (5%): " + bill.getTaxAmount() + " Ks"));
        document.add(new Paragraph("Total: " + bill.getNetTotal() + " Ks"));

        document.close();
    }
}

