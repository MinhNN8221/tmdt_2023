package com.example.controller.admin;

import com.example.entity.BillEntity;
import com.example.entity.ProductEntity;
import org.dom4j.DocumentException;

import java.awt.Color;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.imageio.ImageIO;;

public class FileExport {
    private String fontPath = "src/main/resources/static/fontpdf/arialuni.ttf";

    public FileExport() {
    }

    private void writeTableHeader(PdfPTable table) throws IOException {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);


        BaseFont bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font=new Font(bf, 12);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("STT", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Tên sản phẩm", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Đơn giá", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Số lượng", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Tổng giá", font));
        table.addCell(cell);
    }

    private void writeTableData(List<ProductEntity> productEntities, PdfPTable table, PdfPTable tableT) throws IOException {
        BaseFont bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font=new Font(bf, 12);
        Font fontT=new Font(bf, 13);
        fontT.setStyle(Font.BOLD);
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        DecimalFormat formatter = new DecimalFormat("#,### đ");
        int index=0;
        long tong=0;
        for (ProductEntity product: productEntities) {
            index+=1;
            cell.setPhrase(new Phrase(index+"", font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(product.getName()+"", font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(formatter.format(product.getPrice())+"", font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(product.getQuantity()+"", font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(formatter.format(product.getPrice()*product.getQuantity())+"", font));
            table.addCell(cell);

            tong+=product.getPrice()* product.getQuantity();
        }

        PdfPCell cellT = new PdfPCell();
        cellT.setPadding(5);
        cellT.setPhrase(new Phrase("Tổng tiền thanh toán: "+formatter.format(tong), fontT));
        tableT.addCell(cellT);
    }

    private Paragraph writeData(BillEntity billEntity) throws IOException{
        BaseFont bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font1=new Font(bf, 15);
        font1.setStyle(Font.BOLD);
        Font font2=new Font(bf, 14);

        Chunk maHD = new Chunk("\nMã hóa đơn: ", font1);
        Chunk idHD = new Chunk(billEntity.getId()+"\n\n", font2);
        Chunk tenKH = new Chunk("Tên khách hàng: "+billEntity.getFullname().toUpperCase()+"\n\n", font1);
        Chunk sdt1 = new Chunk("Số điện thoại: ", font1);
        Chunk sdt2 = new Chunk(billEntity.getTelephone()+"\n\n", font2);
        Chunk email1 = new Chunk("Email: ", font1);
        Chunk email2 = new Chunk(billEntity.getEmail()+"\n\n", font2);
        Chunk country1 = new Chunk("Quốc gia: ", font1);
        Chunk country2 = new Chunk(billEntity.getCountry()+"\n\n", font2);
        Chunk city1 = new Chunk("Tỉnh/Thành phố: ", font1);
        Chunk city2 = new Chunk(billEntity.getProvince()+"\n\n", font2);
        Chunk diachi1 = new Chunk("Địa chỉ chi tiết: ", font1);
        Chunk diachi2 = new Chunk(billEntity.getWard()+"\n\n", font2);
        Chunk time1 = new Chunk("Thời gian đăt hàng: ", font1);
        Chunk time2 = new Chunk(billEntity.getCreate_time()+"\n\n", font2);
        Paragraph paragraph=new Paragraph();
        paragraph.add(maHD);
        paragraph.add(idHD);
        paragraph.add(tenKH);
        paragraph.add(sdt1);
        paragraph.add(sdt2);
        paragraph.add(email1);
        paragraph.add(email2);
        paragraph.add(country1);
        paragraph.add(country2);
        paragraph.add(city1);
        paragraph.add(city2);
        paragraph.add(diachi1);
        paragraph.add(diachi2);
        paragraph.add(time1);
        paragraph.add(time2);

        return  paragraph;
    }
    public void export(BillEntity billEntity, List<ProductEntity> productEntities, HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        String imagePath = "image/logo/logo_big-1.png";
        // Đọc ảnh từ file
        BufferedImage image = ImageIO.read(new File(imagePath));
        // Chuyển ảnh sang mảng byte để ghi vào file PDF
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] imageData = baos.toByteArray();
        // Tạo đối tượng Image từ mảng byte ảnh
        Image pdfImage = Image.getInstance(imageData);
        // Chỉnh vị trí của ảnh trong file PDF
        pdfImage.setAbsolutePosition(35, PageSize.A4.getHeight() - 50);
        pdfImage.scaleToFit(125, 30);
        document.add(pdfImage);

        BaseFont bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font=new Font(bf, 18);
        font.setStyle(Font.BOLD);
        Font font2=new Font(bf, 14);
        document.addTitle("Hóa đơn");
        Paragraph p = new Paragraph("HÓA ĐƠN MUA HÀNG\n", font);
        p.setSpacingBefore(10);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);

        document.add(writeData(billEntity));

        PdfPTable table = new PdfPTable(5);
        PdfPTable tableT = new PdfPTable(1);
        tableT.setWidthPercentage(100f);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1f, 3.5f, 3.0f, 1.5f, 2.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(productEntities, table, tableT);

        writeData(billEntity);
        document.add(table);
        document.add(tableT);

        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateTime = dateFormatter.format(new Date());
        String curentDate[]=currentDateTime.split("-");
        Paragraph paraDate=new Paragraph("Hà Nội, ngày "+curentDate[0]+" tháng "+curentDate[1]+
                                                    " năm "+curentDate[2], font2);
        paraDate.setSpacingBefore(20);
        paraDate.setAlignment(Element.ALIGN_RIGHT);
        Paragraph paraSign=new Paragraph("Minh", font2);
        paraSign.setIndentationLeft(400);
        paraSign.setSpacingBefore(20);
        Paragraph paraName=new Paragraph("Nguyễn Ngọc Minh", font2);
        paraName.setIndentationLeft(355);
        paraName.setSpacingBefore(20);

        document.add(paraDate);
        document.add(paraSign);
        document.add(paraName);

        document.close();

    }
}
