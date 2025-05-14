package com.gpcoder.staffpanel;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.bind.*;
import com.gpcoder.security.*;

public class StaffXMLProcessor {
    private static final String XML_FILE = "staffs.xml";

    public static StaffList readStaffs() {
        try {
            // 1. Đọc file đã mã hóa
            String encryptedXML = new String(Files.readAllBytes(Paths.get(XML_FILE)));
            String decryptedXML = AESUtil.decrypt(encryptedXML);

            // 2. Convert XML string → Object
            JAXBContext context = JAXBContext.newInstance(StaffList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (StaffList) unmarshaller.unmarshal(new StringReader(decryptedXML));

        } catch (Exception e) {
            System.out.println("Không đọc được file (hoặc chưa tồn tại). Tạo mới.");
            return new StaffList(); // trả về danh sách rỗng nếu lỗi
        }
    }

    public static void writeStaffs(StaffList staffList) {
        try {
            JAXBContext context = JAXBContext.newInstance(StaffList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // 1. Marshal Object → XML String
            StringWriter sw = new StringWriter();
            marshaller.marshal(staffList, sw);
            String plainXML = sw.toString();

            // 2. Mã hóa XML
            String encryptedXML = AESUtil.encrypt(plainXML);

            // 3. Ghi ra file
            try (FileWriter fw = new FileWriter(XML_FILE)) {
                fw.write(encryptedXML);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
