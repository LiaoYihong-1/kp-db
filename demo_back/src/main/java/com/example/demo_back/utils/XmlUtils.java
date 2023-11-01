package com.example.demo_back.utils;
import com.example.demo_back.domain.SecurityAccount;
import com.example.demo_back.domain.SecurityAccountSet;
import org.springframework.util.ResourceUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class XmlUtils {
    //private final static String path = "src/main/resources/MySecurityUsers.xml";
    private final static String path = "MySecurityUsers.xml";

    public static void createUser(SecurityAccount user) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(SecurityAccount.class);
        Marshaller mar= context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(user, new File(ResourceUtils.getURL("classpath:").getPath()));
    }

    public static void createUsers(SecurityAccountSet list) throws JAXBException,IOException{
        JAXBContext context = JAXBContext.newInstance(SecurityAccountSet.class);
        Marshaller mar= context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(list, new File(path));
    }
    public static SecurityAccountSet unmarshall() throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(SecurityAccountSet.class);
        return (SecurityAccountSet) context.createUnmarshaller()
                .unmarshal(new FileReader(path));
    }
}